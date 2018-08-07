package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.CheckinRecord;
import com.zzzz.po.PromotionStrategy;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.PointRepo;
import com.zzzz.repo.PromotionStrategyRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.CheckinService;
import com.zzzz.service.CheckinServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.CheckinServiceException.ExceptionTypeEnum.*;

@Service
public class CheckinServiceImpl implements CheckinService {
    private final PointDao pointDao;
    private final CheckinRecordDao checkinRecordDao;
    private final PromotionStrategyDao promotionStrategyDao;
    private final UserDao userDao;
    private final EnterpriseDao enterpriseDao;
    private final PointRepo pointRepo;
    private final PromotionStrategyRepo promotionStrategyRepo;
    private final UserRepo userRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<CheckinServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CheckinServiceImpl(PointDao pointDao, CheckinRecordDao checkinRecordDao,
                              PromotionStrategyDao promotionStrategyDao,
                              UserDao userDao, EnterpriseDao enterpriseDao,
                              PointRepo pointRepo, PromotionStrategyRepo promotionStrategyRepo,
                              UserRepo userRepo, EnterpriseRepo enterpriseRepo) {
        this.pointDao = pointDao;
        this.checkinRecordDao = checkinRecordDao;
        this.promotionStrategyDao = promotionStrategyDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.pointRepo = pointRepo;
        this.promotionStrategyRepo = promotionStrategyRepo;
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    @Transactional(rollbackFor = { CheckinServiceException.class, SQLException.class })
    public void checkIn(String userId, String enterpriseId) throws CheckinServiceException, SQLException {
        // Check if the parameters are valid
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);

        // Check if the user and the enterprise exist
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        // Get the promotion strategy
        PromotionStrategy strategy = promotionStrategyRepo.getByEnterpriseId(enterpriseIdLong);
        if (strategy == null)
            strategy = promotionStrategyDao.getByEnterpriseId(enterpriseIdLong);
        int checkinPoints = strategy.getCheckinPoints();

        // Insert checkin record
        CheckinRecord record = getCheckinRecord(userIdLong, enterpriseIdLong, DateUtil.getToday(), false);
        if (record != null)
            throw new CheckinServiceException(ALREADY_CHECKED_IN_TODAY);
        record = new CheckinRecord();
        record.setUserId(userIdLong);
        record.setEnterpriseId(enterpriseIdLong);
        record.setDate(DateUtil.getToday());
        checkinRecordDao.insert(record);

        // Assign corresponding points to the user in the enterprise
        pointDao.incrBy(userIdLong, enterpriseIdLong, checkinPoints);
        pointRepo.incrByIfExisting(userIdLong, enterpriseIdLong, checkinPoints);
    }

    @Override
    public boolean checkCheckedInOrNot(String userId, String enterpriseId, String date) throws CheckinServiceException, SQLException {
        // Check if the parameters are valid
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);

        // Check if the user and the enterprise exist
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        Date targetDate = DateUtil.toStartOfDay(parseDate(date));
        CheckinRecord record = getCheckinRecord(userIdLong, enterpriseIdLong, targetDate, false);
        return record != null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckinRecord> list(String userId, String enterpriseId, String year, String month) throws CheckinServiceException, SQLException {
        // Check if the parameters are valid
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);
        int yearInt = parseYear(year);
        short monthShort = parseMonth(month);

        // Check if the user and the enterprise exist
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        // Fetch history from the database
        List<CheckinRecord> list = checkinRecordDao.list(userIdLong, enterpriseIdLong, yearInt, monthShort);
        return list;
    }

    private long parseUserId(String userId) throws CheckinServiceException {
        checker.rejectIfNullOrEmpty(userId, new CheckinServiceException(EMPTY_USER_ID));
        return checker.parseUnsignedLong(userId, new CheckinServiceException(INVALID_USER_ID));
    }

    private long parseEnterpriseId(String enterpriseId) throws CheckinServiceException {
        checker.rejectIfNullOrEmpty(enterpriseId, new CheckinServiceException(EMPTY_ENTERPRISE_ID));
        return checker.parseUnsignedLong(enterpriseId, new CheckinServiceException(INVALID_ENTERPRISE_ID));
    }

    private int parseYear(String year) throws CheckinServiceException {
        checker.rejectIfNullOrEmpty(year, new CheckinServiceException(EMPTY_YEAR));
        return checker.parseUnsignedInt(year, new CheckinServiceException(INVALID_YEAR));
    }

    private short parseMonth(String month) throws CheckinServiceException {
        checker.rejectIfNullOrEmpty(month, new CheckinServiceException(EMPTY_MONTH));
        int monthInt = checker.parsePositiveInt(month, new CheckinServiceException(INVALID_MONTH));
        if (monthInt > 12)
            throw new CheckinServiceException(INVALID_MONTH);
        return (short) monthInt;
    }

    private Calendar parseDate(String date) throws CheckinServiceException {
        checker.rejectIfNullOrEmpty(date, new CheckinServiceException(EMPTY_DATE));
        long timeLong = checker.parseUnsignedLong(date, new CheckinServiceException(INVALID_DATE));
        return DateUtil.toCalendar(timeLong);
    }

    private void checkIfTheUserExists(long userId) throws SQLException, CheckinServiceException {
        boolean isExisting = userRepo.isCached(userId) || userDao.checkExistenceById(userId);
        if (!isExisting)
            throw new CheckinServiceException(USER_NOT_EXISTING);
    }

    private void checkIfTheEnterpriseExists(long enterpriseId) throws SQLException, CheckinServiceException {
        boolean isExisting = enterpriseRepo.isCached(enterpriseId) || enterpriseDao.checkExistenceById(enterpriseId);
        if (!isExisting)
            throw new CheckinServiceException(ENTERPRISE_NOT_EXISTING);
    }

    private CheckinRecord getCheckinRecord(long userId, long enterpriseId, Date date, boolean throwException) throws SQLException, CheckinServiceException {
        CheckinRecord result = checkinRecordDao.getByPk(userId, enterpriseId, DateUtil.toDateString(date));
        if (throwException && result == null)
            throw new CheckinServiceException(CHECKIN_RECORD_NOT_EXISTING);
        return result;
    }
}
