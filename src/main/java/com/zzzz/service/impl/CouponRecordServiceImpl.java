package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.CouponRecord;
import com.zzzz.repo.CouponRepo;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.CouponRecordService;
import com.zzzz.service.CouponRecordServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static com.zzzz.service.CouponRecordServiceException.ExceptionTypeEnum.*;

@Service
public class CouponRecordServiceImpl implements CouponRecordService {
    private final GeneralDao generalDao;
    private final CouponRecordDao couponRecordDao;
    private final UserDao userDao;
    private final CouponDao couponDao;
    private final EnterpriseDao enterpriseDao;
    private final UserRepo userRepo;
    private final CouponRepo couponRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<CouponRecordServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CouponRecordServiceImpl(GeneralDao generalDao,
                                   CouponRecordDao couponRecordDao, UserDao userDao,
                                   CouponDao couponDao, EnterpriseDao enterpriseDao,
                                   UserRepo userRepo, CouponRepo couponRepo,
                                   EnterpriseRepo enterpriseRepo) {
        this.generalDao = generalDao;
        this.couponRecordDao = couponRecordDao;
        this.userDao = userDao;
        this.couponDao = couponDao;
        this.enterpriseDao = enterpriseDao;
        this.userRepo = userRepo;
        this.couponRepo = couponRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    public CouponRecord getById(String couponRecordId) throws CouponRecordServiceException, SQLException {
        long couponRecordIdLong = parseCouponRecordId(couponRecordId);
        CouponRecord record = couponRecordDao.getById(couponRecordIdLong);
        if (record == null)
            throw new CouponRecordServiceException(COUPON_RECORD_NOT_EXISTING);
        return record;
    }

    @Override
    public ListResult<CouponRecord> list(String usePagination, String targetPage, String pageSize,
                                         String userId, String enterpriseId, String couponRecordId, String couponId)
            throws CouponRecordServiceException, SQLException {
        ListResult<CouponRecord> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new CouponRecordServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new CouponRecordServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(userId, new CouponRecordServiceException(EMPTY_USER_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new CouponRecordServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new CouponRecordServiceException(INVALID_PAGE_SIZE));
        }
        long userIdLong = checker.parseUnsignedLong(userId, new CouponRecordServiceException(INVALID_USER_ID));
        checkIfTheUserExists(userIdLong);

        // Optional fields
        Long enterpriseIdLong = null;
        if (enterpriseId != null && !enterpriseId.isEmpty()) {
            enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CouponRecordServiceException(INVALID_ENTERPRISE_ID));
            checkIfTheEnterpriseExists(enterpriseIdLong);
        }
        Long couponRecordIdLong = null;
        if (couponRecordId != null && !couponRecordId.isEmpty()) {
            couponRecordIdLong = checker.parseUnsignedLong(couponRecordId, new CouponRecordServiceException(INVALID_COUPON_RECORD_ID));
            checkIfTheCouponRecordExists(couponRecordIdLong);
        }
        Long couponIdLong = null;
        if (couponId != null && couponId.isEmpty()) {
            couponIdLong = checker.parseUnsignedLong(couponId, new CouponRecordServiceException(INVALID_COUPON_ID));
            checkIfTheCouponExists(couponIdLong);
        }

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = couponRecordDao.countTotal(userIdLong, enterpriseIdLong, couponRecordIdLong, couponIdLong);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<CouponRecord> list = couponRecordDao.list(usePaginationBool, starting, pageSizeLong,
                userIdLong, enterpriseIdLong, couponRecordIdLong, couponIdLong);
        result.setList(list);
        return result;
    }

    private long parseCouponRecordId(String couponRecordId) throws CouponRecordServiceException {
        checker.rejectIfNullOrEmpty(couponRecordId, new CouponRecordServiceException(EMPTY_COUPON_RECORD_ID));
        return checker.parseUnsignedLong(couponRecordId, new CouponRecordServiceException(INVALID_COUPON_RECORD_ID));
    }

    private void checkIfTheCouponRecordExists(long couponRecordId) throws SQLException, CouponRecordServiceException {
        boolean isExisting = couponRecordDao.checkExistenceById(couponRecordId);
        if (!isExisting)
            throw new CouponRecordServiceException(COUPON_RECORD_NOT_EXISTING);
    }
    
    private void checkIfTheUserExists(long userId) throws SQLException, CouponRecordServiceException {
        boolean isExisting = userRepo.isCached(userId) || userDao.checkExistenceById(userId);
        if (!isExisting)
            throw new CouponRecordServiceException(COUPON_RECORD_NOT_EXISTING);
    }

    private void checkIfTheEnterpriseExists(Long enterpriseIdLong) throws SQLException, CouponRecordServiceException {
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CouponRecordServiceException(ENTERPRISE_NOT_EXISTING);
    }
    
    private void checkIfTheCouponExists(long couponId) throws SQLException, CouponRecordServiceException {
        boolean isExisting = couponRepo.isCached(couponId) || couponDao.checkExistenceById(couponId);
        if (!isExisting)
            throw new CouponRecordServiceException(COUPON_NOT_EXISTING);
    }
}
