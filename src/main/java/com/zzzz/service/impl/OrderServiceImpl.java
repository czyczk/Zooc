package com.zzzz.service.impl;

import com.zzzz.dao.CourseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.OrderDao;
import com.zzzz.dao.UserDao;
import com.zzzz.po.Order;
import com.zzzz.po.OrderStatusEnum;
import com.zzzz.service.OrderService;
import com.zzzz.service.OrderServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

import static com.zzzz.service.OrderServiceException.ExceptionTypeEnum.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final GeneralDao generalDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final ParameterChecker<OrderServiceException> checker = new ParameterChecker<>();

    @Autowired
    public OrderServiceImpl(GeneralDao generalDao, OrderDao orderDao, UserDao userDao, CourseDao courseDao) {
        this.generalDao = generalDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
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
}
