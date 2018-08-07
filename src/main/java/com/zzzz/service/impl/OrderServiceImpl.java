package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.*;
import com.zzzz.repo.CouponRepo;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.OrderService;
import com.zzzz.service.OrderServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.OrderDetail;
import com.zzzz.vo.OrderPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.OrderServiceException.ExceptionTypeEnum.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final GeneralDao generalDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final EnterpriseDao enterpriseDao;
    private final CouponDao couponDao;
    private final CourseDao courseDao;
    private final UserRepo userRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final CouponRepo couponRepo;
    private final CouponServiceImpl couponService;
    private final CouponRecordServiceImpl couponRecordService;
    private final PointServiceImpl pointService;
    private final PromotionStrategyServiceImpl promotionStrategyService;
    private final ParameterChecker<OrderServiceException> checker = new ParameterChecker<>();

    @Autowired
    public OrderServiceImpl(GeneralDao generalDao,
                            OrderDao orderDao, UserDao userDao,
                            EnterpriseDao enterpriseDao, CourseDao courseDao,
                            CouponDao couponDao,
                            UserRepo userRepo, EnterpriseRepo enterpriseRepo,
                            CouponRepo couponRepo,
                            CouponServiceImpl couponService, CouponRecordServiceImpl couponRecordService,
                            PointServiceImpl pointService, PromotionStrategyServiceImpl promotionStrategyService) {
        this.generalDao = generalDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.courseDao = courseDao;
        this.couponDao = couponDao;
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
        this.couponRepo = couponRepo;
        this.couponService = couponService;
        this.couponRecordService = couponRecordService;
        this.pointService = pointService;
        this.promotionStrategyService = promotionStrategyService;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderPreview preview(String userId, String courseId, String couponId, String usePoints) throws SQLException, OrderServiceException {
        // Order preview
        OrderPreview orderPreview = new OrderPreview();

        // Check if the parameters are valid
        long userIdLong = parseUserId(userId);
        long courseIdLong = parseCourseId(courseId);
        Long couponIdLong = parseCouponId(couponId);
        boolean usePointsBool = Boolean.parseBoolean(usePoints);

        // Check if the user exists
        checkIfTheUserExists(userIdLong);
        // Fetch the course (exception thrown if not existing)
        Course course = getCourse(courseIdLong);
        long enterpriseId = course.getEnterpriseId();
        BigDecimal price = course.getPrice();
        // Get the point of the user
        BigDecimal point = new BigDecimal(pointService.getByPk(userIdLong, enterpriseId));

        orderPreview.setEnterpriseId(enterpriseId);
        orderPreview.setUserId(userIdLong);
        orderPreview.setCourseId(courseIdLong);
        orderPreview.setCourseName(course.getName());
        orderPreview.setTotalPoints(point.longValue());
        orderPreview.setOriginalPrice(new BigDecimal(price.toString()));
        orderPreview.setActualPayment(new BigDecimal(price.toString()));

        // Check validity and discount with the coupon if the user uses one
        if (couponIdLong != null) {
            // Check if the coupon exists
            Coupon coupon = getCoupon(couponIdLong);
            // Check if the coupon is in the available list
            // - created by the enterprise
            // - not used by the user
            // - with thresholds no greater than the price of the course
            checkIfTheCouponIsAvailable(userIdLong, course, couponIdLong);

            // Discount if the user wishes to use the coupon
            price = discountUsingCoupon(price, coupon);
            orderPreview.setDiscountedByCoupon(coupon.getValue());
            orderPreview.setActualPayment(new BigDecimal(price.toString()));
        }

        // Discount if the user wishes to use points
        if (usePointsBool) {
            // Calculate the value the points can offset
            PromotionStrategy strategy = promotionStrategyService.getByEnterpriseId(enterpriseId);
            BigDecimal pointsPerYuan = new BigDecimal(strategy.getPointsPerYuan());
            BigDecimal offset = point.divide(pointsPerYuan, 2, RoundingMode.HALF_UP);

            BigDecimal numPointsUsed;
            BigDecimal discountedByPoints;
            // If offset > price, pointUsed = price * pointsPerYuan & price = 0
            if (offset.compareTo(price) > 0) {
                numPointsUsed = price.multiply(pointsPerYuan);
                discountedByPoints = new BigDecimal(price.toString());
                price = BigDecimal.ZERO;
            }
            // else, price -= offset & pointUsed = all
            else {
                price = price.subtract(offset);
                discountedByPoints = new BigDecimal(offset.toString());
                numPointsUsed = new BigDecimal(point.longValue());
            }
            orderPreview.setActualPayment(price);
            orderPreview.setDiscountedByPoints(discountedByPoints);
            orderPreview.setNumPointsUsed(numPointsUsed.longValue());
        }

        return orderPreview;
    }

    @Override
    @Transactional(rollbackFor = { OrderServiceException.class, SQLException.class })
    public long insert(String userId, String courseId, String couponId, String usePoints, Date time) throws SQLException, OrderServiceException {
        // Check if the parameters are valid
        if (time == null)
            throw new OrderServiceException(EMPTY_TIME);
        // Check other parameters and get a preview
        OrderPreview orderPreview = preview(userId, courseId, couponId, usePoints);

        // Prepare a new order
        Order order = new Order();
        order.setUserId(orderPreview.getUserId());
        order.setCourseId(orderPreview.getCourseId());
        order.setTime(time);
        // An order is created to be `PENDING` (Not paid yet) by default
        order.setStatus(OrderStatusEnum.PENDING);

        // Insert the order
        orderDao.insert(order);
        long newOrderId = generalDao.getLastInsertId();

        // If the user uses a coupon, insert a coupon record
        if (couponId != null) {
            couponRecordService.insert(orderPreview.getUserId(),
                    orderPreview.getCouponId(),
                    orderPreview.getEnterpriseId(),
                    time);
        }

        // If the user uses points, update the points
        if (orderPreview.getNumPointsUsed() > 0) {
            pointService.decrBy(orderPreview.getUserId(), orderPreview.getEnterpriseId(), orderPreview.getNumPointsUsed());
        }

        return newOrderId;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(String orderId) throws SQLException, OrderServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(orderId, new OrderServiceException(EMPTY_ORDER_ID));
        long orderIdLong = checker.parseUnsignedLong(orderId, new OrderServiceException(INVALID_ORDER_ID));

        // Fetch the order and check if it exists
        Order result = orderDao.getById(orderIdLong);
        if (result == null)
            throw new OrderServiceException(ORDER_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetail getVoById(String orderId) throws SQLException, OrderServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(orderId, new OrderServiceException(EMPTY_ORDER_ID));
        long orderIdLong = checker.parseUnsignedLong(orderId, new OrderServiceException(INVALID_ORDER_ID));

        // Fetch the order and check if it exists
        OrderDetail result = orderDao.getVoById(orderIdLong);
        if (result == null)
            throw new OrderServiceException(ORDER_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { OrderServiceException.class, SQLException.class })
    public void update(String targetOrderId, String status) throws SQLException, OrderServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetOrderId, new OrderServiceException(EMPTY_ORDER_ID));
        long targetOrderIdLong = checker.parseUnsignedLong(targetOrderId, new OrderServiceException(INVALID_ORDER_ID));

        // Fetch the order and check if it exists
        Order order = orderDao.getById(targetOrderIdLong);
        if (order == null)
            throw new OrderServiceException(ORDER_NOT_EXISTING);

        if (status != null) {
            if (status.isEmpty())
                throw new OrderServiceException(EMPTY_STATUS);
            try {
                OrderStatusEnum statusEnum = OrderStatusEnum.valueOf(status);
                order.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new OrderServiceException(INVALID_STATUS);
            }
        }

        // Update
        orderDao.update(order);
    }

    @Override
    public ListResult<OrderDetail> list(String usePagination, String targetPage, String pageSize, String orderId, String userId, String enterpriseId, String courseId, String courseNameContaining, String status) throws SQLException, OrderServiceException {
        ListResult<OrderDetail> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new OrderServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new OrderServiceException(EMPTY_PAGE_SIZE));
        }

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new OrderServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new OrderServiceException(INVALID_PAGE_SIZE));
        }

        // Optional fields
        Long orderIdLong = null;
        if (orderId != null && !orderId.isEmpty())
            orderIdLong = checker.parseUnsignedLong(orderId, new OrderServiceException(INVALID_ORDER_ID));
        Long userIdLong = null;
        if (userId != null && !userId.isEmpty()) {
            userIdLong = checker.parseUnsignedLong(userId, new OrderServiceException(INVALID_USER_ID));
            // Check if the user exists
            boolean isExisting = userRepo.isCached(userIdLong) || userDao.checkExistenceById(userIdLong);
            if (!isExisting)
                throw new OrderServiceException(USER_NOT_EXISTING);
        }
        Long enterpriseIdLong = null;
        if (enterpriseId != null && !enterpriseId.isEmpty()) {
            enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new OrderServiceException(INVALID_ENTERPRISE_ID));
            // Check if the enterprise exists
            boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
            if (!isExisting)
                throw new OrderServiceException(ENTERPRISE_NOT_EXISTING);
        }
        Long courseIdLong = null;
        if (courseId != null && !courseId.isEmpty()) {
            courseIdLong = checker.parseUnsignedLong(courseId, new OrderServiceException(INVALID_COURSE_ID));
            boolean isExisting = courseDao.checkExistenceById(courseIdLong);
            if (!isExisting)
                throw new OrderServiceException(COURSE_NOT_EXISTING);
        }
        if (courseNameContaining != null && courseNameContaining.isEmpty())
            courseNameContaining = null;
        OrderStatusEnum statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = OrderStatusEnum.valueOf(status);
            } catch (IllegalArgumentException e) {
                throw new OrderServiceException(INVALID_STATUS);
            }
        }

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = orderDao.countTotal(enterpriseIdLong, orderIdLong, userIdLong, null, null, courseIdLong, courseNameContaining, statusEnum);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<OrderDetail> list = orderDao.list(usePaginationBool, starting, pageSizeLong, orderIdLong, userIdLong, enterpriseIdLong, courseIdLong, courseNameContaining, statusEnum);
        result.setList(list);
        return result;
    }

    @Override
    public ListResult<OrderDetail> listRefund(String usePagination, String targetPage, String pageSize, String enterpriseId, String orderId, String userId, String userEmail, String userMobile, String courseId, String courseNameContaining, String status) throws SQLException, OrderServiceException {
        ListResult<OrderDetail> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new OrderServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new OrderServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new OrderServiceException(EMPTY_ENTERPRISE_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new OrderServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new OrderServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new OrderServiceException(INVALID_ENTERPRISE_ID));
        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new OrderServiceException(ENTERPRISE_NOT_EXISTING);

        // Optional fields
        Long orderIdLong = null;
        if (orderId != null && !orderId.isEmpty())
            orderIdLong = checker.parseUnsignedLong(orderId, new OrderServiceException(INVALID_ORDER_ID));
        Long userIdLong = null;
        if (userId != null && !userId.isEmpty()) {
            userIdLong = checker.parseUnsignedLong(userId, new OrderServiceException(INVALID_USER_ID));
            // Check if the user exists
            isExisting = userRepo.isCached(userIdLong) || userDao.checkExistenceById(userIdLong);
            if (!isExisting)
                throw new OrderServiceException(USER_NOT_EXISTING);
        }
        if (userEmail != null && userEmail.isEmpty())
            userEmail = null;
        if (userMobile != null && userMobile.isEmpty())
            userMobile = null;
        Long courseIdLong = null;
        if (courseId != null && !courseId.isEmpty()) {
            courseIdLong = checker.parseUnsignedLong(courseId, new OrderServiceException(INVALID_COURSE_ID));
            isExisting = courseDao.checkExistenceById(courseIdLong);
            if (!isExisting)
                throw new OrderServiceException(COURSE_NOT_EXISTING);
        }
        if (courseNameContaining != null && courseNameContaining.isEmpty())
            courseNameContaining = null;
        OrderStatusEnum statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = OrderStatusEnum.valueOf(status);
                if (statusEnum != OrderStatusEnum.REFUND_REQUESTED && statusEnum != OrderStatusEnum.REFUNDED)
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                throw new OrderServiceException(INVALID_STATUS);
            }
        }

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = 0;
            if (statusEnum != null)
                totalNumItems = orderDao.countTotal(enterpriseIdLong, orderIdLong, userIdLong, userEmail, userMobile, courseIdLong, courseNameContaining, statusEnum);
            else {
                totalNumItems += orderDao.countTotal(enterpriseIdLong, orderIdLong, userIdLong, userEmail, userMobile, courseIdLong, courseNameContaining, OrderStatusEnum.REFUND_REQUESTED);
                totalNumItems += orderDao.countTotal(enterpriseIdLong, orderIdLong, userIdLong, userEmail, userMobile, courseIdLong, courseNameContaining, OrderStatusEnum.REFUNDED);
            }
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<OrderDetail> list = orderDao.listRefund(usePaginationBool, starting, pageSizeLong, enterpriseIdLong, orderIdLong, userIdLong, userEmail, userMobile, courseIdLong, courseNameContaining, statusEnum);
        result.setList(list);
        return result;
    }

    private long parseUserId(String userId) throws OrderServiceException {
        checker.rejectIfNullOrEmpty(userId, new OrderServiceException(EMPTY_USER_ID));
        return checker.parseUnsignedLong(userId, new OrderServiceException(INVALID_USER_ID));
    }

    private long parseCourseId(String courseId) throws OrderServiceException {
        checker.rejectIfNullOrEmpty(courseId, new OrderServiceException(EMPTY_COURSE_ID));
        return checker.parseUnsignedLong(courseId, new OrderServiceException(INVALID_COURSE_ID));
    }

    private Long parseCouponId(String couponId) throws OrderServiceException {
        if (couponId == null || couponId.isEmpty())
            return null;
        return checker.parseUnsignedLong(couponId, new OrderServiceException(INVALID_COUPON_ID));
    }

    private void checkIfTheUserExists(long userId) throws OrderServiceException, SQLException {
        boolean isExisting = userRepo.isCached(userId) || userDao.checkExistenceById(userId);
        if (!isExisting)
            throw new OrderServiceException(USER_NOT_EXISTING);
    }

    private void checkIfTheCourseExists(long courseId) throws OrderServiceException, SQLException {
        boolean isExisting = courseDao.checkExistenceById(courseId);
        if (!isExisting)
            throw new OrderServiceException(COURSE_NOT_EXISTING);
    }

//    private void checkIfTheCouponExists(long couponId) throws SQLException, OrderServiceException {
//        boolean isExisting = couponRepo.isCached(couponId) || couponDao.checkExistenceById(couponId);
//        if (!isExisting)
//            throw new OrderServiceException(COUPON_NOT_EXISTING);
//    }

    private void checkIfTheCouponIsAvailable(long userId, Course course, long couponId) throws SQLException, OrderServiceException {
        List<Coupon> availableCoupons = couponService.listUserAvailable(
                course.getEnterpriseId(),
                userId, course.getPrice());
        if (availableCoupons.parallelStream().noneMatch(
                it -> it.getCouponId() == couponId
        ))
            throw new OrderServiceException(COUPON_NOT_AVAILABLE);
    }

    private Course getCourse(long courseId) throws SQLException, OrderServiceException {
        Course result = courseDao.getById(courseId);
        if (result == null)
            throw new OrderServiceException(COURSE_NOT_EXISTING);
        return result;
    }

    private Coupon getCoupon(long couponId) throws SQLException, OrderServiceException {
        Coupon result = couponService.getById(couponId);
        if (result == null)
            throw new OrderServiceException(COUPON_NOT_EXISTING);
        return result;
    }

    private BigDecimal discountUsingCoupon(BigDecimal price, Coupon coupon) {
        // If the value >= the price, the price will be 0
        if (coupon.getValue().compareTo(price) >= 0)
            price = BigDecimal.ZERO;
        // Else, price = price - value
        else
            price = price.subtract(coupon.getValue());
        return price;
    }
}
