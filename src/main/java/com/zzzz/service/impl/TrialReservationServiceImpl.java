package com.zzzz.service.impl;

import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.TrialDao;
import com.zzzz.dao.TrialReservationDao;
import com.zzzz.dao.UserDao;
import com.zzzz.po.TrialReservation;
import com.zzzz.po.TrialReservationStatusEnum;
import com.zzzz.service.TrialReservationService;
import com.zzzz.service.TrialReservationServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.TrialReservationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

import static com.zzzz.service.TrialReservationServiceException.ExceptionTypeEnum.*;

@Service
public class TrialReservationServiceImpl implements TrialReservationService {
    private final GeneralDao generalDao;
    private final TrialReservationDao trialReservationDao;
    private final UserDao userDao;
    private final TrialDao trialDao;
    private final ParameterChecker<TrialReservationServiceException> checker = new ParameterChecker<>();

    @Autowired
    public TrialReservationServiceImpl(GeneralDao generalDao, TrialReservationDao trialReservationDao, UserDao userDao, TrialDao trialDao) {
        this.generalDao = generalDao;
        this.trialReservationDao = trialReservationDao;
        this.userDao = userDao;
        this.trialDao = trialDao;
    }


    @Override
    @Transactional(rollbackFor = { TrialReservationServiceException.class, SQLException.class })
    public long insert(String userId, String trialId, Date time, String message) throws SQLException, TrialReservationServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(userId, new TrialReservationServiceException(EMPTY_USER_ID));
        checker.rejectIfNullOrEmpty(trialId, new TrialReservationServiceException(EMPTY_TRIAL_ID));
        if (time == null)
            throw new TrialReservationServiceException(EMPTY_TIME);
        long userIdLong = checker.parseUnsignedLong(userId, new TrialReservationServiceException(INVALID_USER_ID));
        long trialIdLong = checker.parseUnsignedLong(trialId, new TrialReservationServiceException(INVALID_TRIAL_ID));

        // Check if the user and the trial exist
        boolean isExisting = userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new TrialReservationServiceException(USER_NOT_EXISTING);
        isExisting = trialDao.checkExistenceById(trialIdLong);
        if (!isExisting)
            throw new TrialReservationServiceException(TRIAL_NOT_EXISTING);

        // Prepare a new reservation
        TrialReservation reservation = new TrialReservation();
        reservation.setUserId(userIdLong);
        reservation.setTrialId(trialIdLong);
        reservation.setTime(time);
        reservation.setMessage(message);
        // A reservation is by default inserted to be `PENDING`
        reservation.setStatus(TrialReservationStatusEnum.PENDING);

        trialReservationDao.insert(reservation);
        return generalDao.getLastInsertId();
    }

    @Override
    @Transactional(readOnly = true)
    public TrialReservation getById(String reservationId) throws SQLException, TrialReservationServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(reservationId, new TrialReservationServiceException(EMPTY_RESERVATION_ID));
        long reservationIdLong = checker.parseUnsignedLong(reservationId, new TrialReservationServiceException(INVALID_RESERVATION_ID));

        // Fetch the reservation and check if it exists
        TrialReservation result = trialReservationDao.getById(reservationIdLong);
        if (result == null)
            throw new TrialReservationServiceException(RESERVATION_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public TrialReservationDetail getVoById(String reservationId) throws SQLException, TrialReservationServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(reservationId, new TrialReservationServiceException(EMPTY_RESERVATION_ID));
        long reservationIdLong = checker.parseUnsignedLong(reservationId, new TrialReservationServiceException(INVALID_RESERVATION_ID));

        // Fetch the reservation and check if it exists
        TrialReservationDetail result = trialReservationDao.getVoById(reservationIdLong);
        if (result == null)
            throw new TrialReservationServiceException(RESERVATION_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { TrialReservationServiceException.class, SQLException.class })
    public void update(String targetReservationId, String status) throws SQLException, TrialReservationServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetReservationId, new TrialReservationServiceException(EMPTY_RESERVATION_ID));
        long targetReservationIdLong = checker.parseUnsignedLong(targetReservationId, new TrialReservationServiceException(INVALID_RESERVATION_ID));

        // Fetch the reservation and check if it exists
        TrialReservation reservation = trialReservationDao.getById(targetReservationIdLong);
        if (reservation == null)
            throw new TrialReservationServiceException(RESERVATION_NOT_EXISTING);

        // Change the status if it's not null
        if (status != null) {
            if (status.isEmpty())
                throw new TrialReservationServiceException(EMPTY_STATUS);
            try {
                TrialReservationStatusEnum statusEnum = TrialReservationStatusEnum.valueOf(status);
                reservation.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new TrialReservationServiceException(INVALID_STATUS);
            }
        }

        // Update
        trialReservationDao.update(reservation);
    }
}
