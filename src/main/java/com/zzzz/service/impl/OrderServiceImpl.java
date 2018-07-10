package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.Order;
import com.zzzz.po.OrderStatusEnum;
import com.zzzz.service.OrderService;
import com.zzzz.service.OrderServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CourseDao courseDao;
    private final ParameterChecker<OrderServiceException> checker = new ParameterChecker<>();

    @Autowired
    public OrderServiceImpl(GeneralDao generalDao, OrderDao orderDao, UserDao userDao, EnterpriseDao enterpriseDao, CourseDao courseDao) {
        this.generalDao = generalDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.courseDao = courseDao;
    }

    @Override
    @Transactional(rollbackFor = { OrderServiceException.class, SQLException.class })
    public long insert(String userId, String courseId, Date time) throws SQLException, OrderServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(userId, new OrderServiceException(EMPTY_USER_ID));
        checker.rejectIfNullOrEmpty(courseId, new OrderServiceException(EMPTY_COURSE_ID));
        if (time == null)
            throw new OrderServiceException(EMPTY_TIME);

        long userIdLong = checker.parseUnsignedLong(userId, new OrderServiceException(INVALID_USER_ID));
        long courseIdLong = checker.parseUnsignedLong(courseId, new OrderServiceException(INVALID_COURSE_ID));

        // Check if the user exists
        boolean isExisting = userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new OrderServiceException(USER_NOT_EXISTING);
        // Check if the course exists
        isExisting = courseDao.checkExistenceById(courseIdLong);
        if (!isExisting)
            throw new OrderServiceException(COURSE_NOT_EXISTING);

        // Prepare a new order
        Order order = new Order();
        order.setUserId(userIdLong);
        order.setCourseId(courseIdLong);
        order.setTime(time);
        // An order is created to be `PENDING` (Not paid yet) by default
        order.setStatus(OrderStatusEnum.PENDING);

        // Insert
        orderDao.insert(order);
        long lastId = generalDao.getLastInsertId();
        return lastId;
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
            boolean isExisting = userDao.checkExistenceById(userIdLong);
            if (!isExisting)
                throw new OrderServiceException(USER_NOT_EXISTING);
        }
        Long enterpriseIdLong = null;
        if (enterpriseId != null && !enterpriseId.isEmpty()) {
            enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new OrderServiceException(INVALID_ENTERPRISE_ID));
            // Check if the enterprise exists
            boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
            if (!isExisting)
                throw new OrderServiceException(ENTERPRISE_NOT_EXISTING);
        }
        Long courseIdLong = null;
        if (courseId != null && !courseId.isEmpty())
            courseIdLong = checker.parseUnsignedLong(courseId, new OrderServiceException(INVALID_COURSE_ID));
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
            long totalNumItems = orderDao.countTotal(orderIdLong, userIdLong, enterpriseIdLong, courseIdLong, courseNameContaining, statusEnum);
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
}
