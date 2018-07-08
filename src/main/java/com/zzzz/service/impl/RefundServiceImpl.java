package com.zzzz.service.impl;

import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.OrderDao;
import com.zzzz.dao.RefundDao;
import com.zzzz.po.Order;
import com.zzzz.po.OrderStatusEnum;
import com.zzzz.po.Refund;
import com.zzzz.service.RefundService;
import com.zzzz.service.RefundServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

import static com.zzzz.service.RefundServiceException.ExceptionTypeEnum.*;

@Service
public class RefundServiceImpl implements RefundService {
    private final GeneralDao generalDao;
    private final RefundDao refundDao;
    private final OrderDao orderDao;
    private final ParameterChecker<RefundServiceException> checker = new ParameterChecker<>();

    @Autowired
    public RefundServiceImpl(GeneralDao generalDao, RefundDao refundDao, OrderDao orderDao) {
        this.generalDao = generalDao;
        this.refundDao = refundDao;
        this.orderDao = orderDao;
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class, RefundServiceException.class })
    public long insert(String orderId, Date time, String reason) throws SQLException, RefundServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(orderId, new RefundServiceException(EMPTY_ORDER_ID));
        if (time == null)
            throw new RefundServiceException(EMPTY_TIME);
        checker.rejectIfNullOrEmpty(reason, new RefundServiceException(EMPTY_REASON));
        long orderIdLong = checker.parseUnsignedLong(orderId, new RefundServiceException(INVALID_ORDER_ID));

        // Check if the order exists
        Order order = orderDao.getById(orderIdLong);
        if (order == null)
            throw new RefundServiceException(ORDER_NOT_EXISTING);

        // A refund can only be issued when the order is `AVAILABLE`
        if (order.getStatus() != OrderStatusEnum.AVAILABLE)
            throw new RefundServiceException(INCORRECT_ORDER_STATUS);

        // Set the order to `REFUND_REQUESTED`
        order.setStatus(OrderStatusEnum.REFUND_REQUESTED);
        orderDao.update(order);

        // Create a new refund record
        Refund refund = new Refund();
        refund.setOrderId(orderIdLong);
        refund.setTime(time);
        refund.setReason(reason);
        refundDao.insert(refund);

        return generalDao.getLastInsertId();
    }

    @Override
    @Transactional(readOnly = true)
    public Refund getById(String refundId) throws SQLException, RefundServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(refundId, new RefundServiceException(EMPTY_REFUND_ID));
        long refundIdLong = checker.parseUnsignedLong(refundId, new RefundServiceException(INVALID_REFUND_ID));

        // Fetch the result from the database and check if it exists
        Refund result = refundDao.getById(refundIdLong);
        if (result == null)
            throw new RefundServiceException(REFUND_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class, RefundServiceException.class })
    public void update(String targetRefundId, String reason) throws SQLException, RefundServiceException {
        // Check if the target refund ID is valid
        checker.rejectIfNullOrEmpty(targetRefundId, new RefundServiceException(EMPTY_REFUND_ID));
        long targetRefundIdLong = checker.parseUnsignedLong(targetRefundId, new RefundServiceException(INVALID_REFUND_ID));

        // Fetch the result from the database and check if it exists
        Refund refund = refundDao.getById(targetRefundIdLong);
        if (refund == null)
            throw new RefundServiceException(REFUND_NOT_EXISTING);

        if (reason != null) {
            if (reason.isEmpty())
                throw new RefundServiceException(EMPTY_REASON);
            refund.setReason(reason);
        }
        refundDao.update(refund);
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class, RefundServiceException.class })
    public void delete(String refundId) throws SQLException, RefundServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(refundId, new RefundServiceException(EMPTY_REFUND_ID));
        long refundIdLong = checker.parseUnsignedLong(refundId, new RefundServiceException(INVALID_REFUND_ID));

        // Fetch the refund record and the order from the database and check if it exists
        Refund refund = refundDao.getById(refundIdLong);
        if (refund == null)
            throw new RefundServiceException(REFUND_NOT_EXISTING);
        Order order = orderDao.getById(refund.getOrderId());
        if (order == null)
            throw new RefundServiceException(ORDER_NOT_EXISTING);

        // Set the order back to `AVAILABLE`
        order.setStatus(OrderStatusEnum.AVAILABLE);
        orderDao.update(order);
        refundDao.delete(refundIdLong);
    }
}
