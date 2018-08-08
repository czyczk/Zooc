package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.Coupon;
import com.zzzz.po.CouponRecord;
import com.zzzz.po.CouponStatusEnum;
import com.zzzz.repo.CouponRepo;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.CouponService;
import com.zzzz.service.CouponServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import static com.zzzz.service.CouponServiceException.ExceptionTypeEnum.*;

@Service
public class CouponServiceImpl implements CouponService {
    private final GeneralDao generalDao;
    private final CouponDao couponDao;
    private final EnterpriseDao enterpriseDao;
    private final UserDao userDao;
    private final CouponRecordDao couponRecordDao;
    private final CouponRepo couponRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final UserRepo userRepo;
    private final ParameterChecker<CouponServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CouponServiceImpl(GeneralDao generalDao, CouponDao couponDao,
                             EnterpriseDao enterpriseDao, UserDao userDao,
                             CouponRecordDao couponRecordDao,
                             CouponRepo couponRepo, EnterpriseRepo enterpriseRepo,
                             UserRepo userRepo) {
        this.generalDao = generalDao;
        this.couponDao = couponDao;
        this.enterpriseDao = enterpriseDao;
        this.userDao = userDao;
        this.couponRecordDao = couponRecordDao;
        this.couponRepo = couponRepo;
        this.enterpriseRepo = enterpriseRepo;
        this.userRepo = userRepo;
    }

    @Override
    public long insert(String enterpriseId, String value, String threshold, Date time) throws CouponServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new CouponServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(value, new CouponServiceException(EMPTY_VALUE));
        checker.rejectIfNullOrEmpty(threshold, new CouponServiceException(EMPTY_THRESHOLD));
        if (time == null)
            throw new CouponServiceException(EMPTY_TIME);
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CouponServiceException(INVALID_ENTERPRISE_ID));
        BigDecimal valueBd = checker.parsePositiveBigDecimal(value, new CouponServiceException(INVALID_VALUE));
        BigDecimal thresholdBd = checker.parseUnsignedBigDecimal(threshold, new CouponServiceException(INVALID_THRESHOLD));

        // Check if the enterprise exists
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CouponServiceException(ENTERPRISE_NOT_EXISTING);

        // Prepare and insert
        Coupon coupon = new Coupon();
        coupon.setEnterpriseId(enterpriseIdLong);
        coupon.setValue(valueBd);
        coupon.setThreshold(thresholdBd);
        coupon.setTime(time);

        couponDao.insert(coupon);
        long lastId = generalDao.getLastInsertId();
        coupon.setCouponId(lastId);
        couponRepo.save(coupon);
        couponRepo.deleteUserAvailableCoupons(enterpriseIdLong);

        return lastId;
    }

    @Override
    public void disable(String couponId) throws CouponServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(couponId, new CouponServiceException(EMPTY_COUPON_ID));
        long couponIdLong = checker.parseUnsignedLong(couponId, new CouponServiceException(INVALID_COUPON_ID));

        // Fetch the existing coupon
        Coupon coupon = couponRepo.getById(couponIdLong);
        if (coupon == null) {
            coupon = couponDao.getById(couponIdLong);
            if (coupon == null)
                throw new CouponServiceException(COUPON_NOT_EXISTING);
            // There's no need to cache it, since it will be deleted soon
        }

        // Disable it
        coupon.setStatus(CouponStatusEnum.DISABLED);
        couponDao.update(coupon);
        couponRepo.delete(couponIdLong);
        couponRepo.deleteUserAvailableCoupons(coupon.getEnterpriseId());
    }

    @Override
    public void update(String targetCouponId, String value, String threshold) throws CouponServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(targetCouponId, new CouponServiceException(EMPTY_COUPON_ID));
        long targetCouponIdLong = checker.parseUnsignedLong(targetCouponId, new CouponServiceException(INVALID_COUPON_ID));

        // Fetch the existing coupon
        Coupon coupon = couponRepo.getById(targetCouponIdLong);
        if (coupon == null) {
            coupon = couponDao.getById(targetCouponIdLong);
            if (coupon == null)
                throw new CouponServiceException(COUPON_NOT_EXISTING);
            if (coupon.getStatus() == CouponStatusEnum.DISABLED)
                throw new CouponServiceException(COUPON_DISABLED);
        }

        // Update if necessary
        if (value != null && !value.isEmpty()) {
            BigDecimal valueBd = checker.parsePositiveBigDecimal(value, new CouponServiceException(INVALID_VALUE));
            coupon.setValue(valueBd);
        }
        if (threshold != null && !threshold.isEmpty()) {
            BigDecimal thresholdBd = checker.parseUnsignedBigDecimal(threshold, new CouponServiceException(INVALID_THRESHOLD));
            coupon.setThreshold(thresholdBd);
        }

        couponDao.update(coupon);
        couponRepo.update(coupon);
        couponRepo.deleteUserAvailableCoupons(coupon.getEnterpriseId());
    }

    @Override
    public Coupon getById(String couponId) throws CouponServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(couponId, new CouponServiceException(EMPTY_COUPON_ID));
        long couponIdLong = checker.parseUnsignedLong(couponId, new CouponServiceException(INVALID_COUPON_ID));

        // Fetch the existing coupon
        Coupon coupon = getById(couponIdLong);
        if (coupon == null)
            throw new CouponServiceException(COUPON_NOT_EXISTING);
        return coupon;
    }

    /**
     * For in-package use only. Get a coupon by its ID.
     * The parameter won't be checked. Make sure it is valid.
     * @param couponId Coupon ID
     * @return Coupon; null if not existing
     */
    Coupon getById(long couponId) throws SQLException {
        // Fetch the existing coupon
        Coupon coupon = couponRepo.getById(couponId);
        if (coupon == null) {
            coupon = couponDao.getById(couponId);
            if (coupon != null)
                couponRepo.save(coupon);
        }
        return coupon;
    }

    @Override
    public ListResult<Coupon> list(String usePagination, String targetPage, String pageSize, String enterpriseId, String couponId, String minValue, String maxValue, String minThreshold, String maxThreshold, String laterThan, String earlierThan) throws CouponServiceException, SQLException {
        ListResult<Coupon> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new CouponServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new CouponServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new CouponServiceException(EMPTY_ENTERPRISE_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new CouponServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new CouponServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CouponServiceException(INVALID_ENTERPRISE_ID));
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CouponServiceException(ENTERPRISE_NOT_EXISTING);

        // Optional fields
        Long couponIdLong = null;
        if (couponId != null && !couponId.isEmpty())
            couponIdLong = checker.parseUnsignedLong(couponId, new CouponServiceException(INVALID_COUPON_ID));
        BigDecimal minValueBd = null;
        BigDecimal maxValueBd = null;
        if (minValue != null && !minValue.isEmpty())
            minValueBd = checker.parseUnsignedBigDecimal(minValue, new CouponServiceException(INVALID_VALUE_RANGE));
        if (maxValue != null && !maxValue.isEmpty())
            maxValueBd = checker.parseUnsignedBigDecimal(maxValue, new CouponServiceException(INVALID_VALUE_RANGE));
        BigDecimal minThresholdBd = null;
        BigDecimal maxThresholdBd = null;
        if (minThreshold != null && !minThreshold.isEmpty())
            minThresholdBd = checker.parseUnsignedBigDecimal(minThreshold, new CouponServiceException(INVALID_THRESHOLD_RANGE));
        if (maxThreshold != null && !maxThreshold.isEmpty())
            maxThresholdBd = checker.parseUnsignedBigDecimal(maxThreshold, new CouponServiceException(INVALID_THRESHOLD_RANGE));
        Long laterThanLong = null, earlierThanLong = null;
        Date laterThanDate = null, earlierThanDate = null;
        if (laterThan != null && !laterThan.isEmpty()) {
            laterThanLong = checker.parseUnsignedLong(laterThan, new CouponServiceException(INVALID_TIME_RANGE));
            laterThanDate = new Date(laterThanLong);
        }
        if (earlierThan != null && !earlierThan.isEmpty()) {
            earlierThanLong = checker.parseUnsignedLong(earlierThan, new CouponServiceException(INVALID_TIME_RANGE));
            earlierThanDate = new Date(earlierThanLong);
        }

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = couponDao.countTotal(enterpriseIdLong, couponIdLong, minValueBd, maxValueBd, minThresholdBd, maxThresholdBd, laterThanDate, earlierThanDate);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<Coupon> list = couponDao.list(usePaginationBool, starting, pageSizeLong, enterpriseIdLong, couponIdLong, minValueBd, maxValueBd, minThresholdBd, maxThresholdBd, laterThanDate, earlierThanDate);
        result.setList(list);
        return result;
    }

    @Override
    public List<Coupon> listUserAvailable(String enterpriseId, String userId, String price) throws CouponServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new CouponServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(userId, new CouponServiceException(EMPTY_USER_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CouponServiceException(INVALID_ENTERPRISE_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new CouponServiceException(INVALID_USER_ID));
        BigDecimal priceBd = null;
        if (price != null && !price.isEmpty())
            priceBd = checker.parseUnsignedBigDecimal(price, new CouponServiceException(INVALID_COURSE_PRICE));

        // Check if the enterprise and the user exist
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CouponServiceException(ENTERPRISE_NOT_EXISTING);
        isExisting = userRepo.isCached(userIdLong) || userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new CouponServiceException(COUPON_NOT_EXISTING);

        return listUserAvailable(enterpriseIdLong, userIdLong, priceBd);
    }

    /**
     * For in-package use only!
     * List user available coupons.
     * Beware that the parameters won't be checked.
     * @param enterpriseId Enterprise ID
     * @param userId User ID
     * @param price Price
     * @return User available coupons
     */
    List<Coupon> listUserAvailable(long enterpriseId, long userId, BigDecimal price) throws SQLException {
        // Try to fetch it from the cache first
        List<Coupon> notUsedCoupons = couponRepo.getUserAvailableCoupons(enterpriseId, userId);
        // On missing,
        if (notUsedCoupons == null || notUsedCoupons.isEmpty()) {
            // Fetch all coupons of the enterprise
            List<Coupon> allCoupons = couponDao.list(false, null, null, enterpriseId,
                    null, null, null, null, null,
                    null, null);
            // Fetch all coupon records in the enterprise of the user
            List<CouponRecord> allRecords = couponRecordDao.list(false, null, null,
                    userId, enterpriseId, null, null);
            // Put them to maps in order to speed up the filter process
            Map<Long, Coupon> availableCouponsMap = new HashMap<>();
            allCoupons.parallelStream().forEach(it -> availableCouponsMap.put(it.getCouponId(), it));
            allRecords.parallelStream().forEach(it -> availableCouponsMap.remove(it.getCouponId()));

            // Filter those that haven't been used by the user
            notUsedCoupons = new ArrayList<>(availableCouponsMap.values());
            notUsedCoupons.sort(Comparator.comparing(Coupon::getCouponId));

            couponRepo.saveUserAvailableCoupons(enterpriseId, userId, notUsedCoupons);
        }

        // Filter those with thresholds no greater the the price (optional)
        List<Coupon> availableCoupons;
        if (price == null)
            availableCoupons = notUsedCoupons;
        else {
            availableCoupons = new ArrayList<>();
            notUsedCoupons.forEach(it -> {
                if (it.getThreshold().compareTo(price) <= 0)
                    availableCoupons.add(it);
            });
        }
        return availableCoupons;
    }
}
