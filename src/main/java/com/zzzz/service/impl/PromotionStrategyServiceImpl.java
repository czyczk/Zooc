package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.PromotionStrategyDao;
import com.zzzz.po.PromotionStrategy;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.PromotionStrategyRepo;
import com.zzzz.service.PromotionStrategyService;
import com.zzzz.service.PromotionStrategyServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.PromotionStrategyServiceException.ExceptionTypeEnum.*;

@Service
public class PromotionStrategyServiceImpl implements PromotionStrategyService {
    private final PromotionStrategyDao promotionStrategyDao;
    private final EnterpriseDao enterpriseDao;
    private final PromotionStrategyRepo promotionStrategyRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<PromotionStrategyServiceException> checker = new ParameterChecker<>();

    @Autowired
    public PromotionStrategyServiceImpl(PromotionStrategyDao promotionStrategyDao, EnterpriseDao enterpriseDao,
                                        PromotionStrategyRepo promotionStrategyRepo, EnterpriseRepo enterpriseRepo) {
        this.promotionStrategyDao = promotionStrategyDao;
        this.enterpriseDao = enterpriseDao;
        this.promotionStrategyRepo = promotionStrategyRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    @Transactional(rollbackFor = { PromotionStrategyServiceException.class, SQLException.class })
    public void update(String enterpriseId, String useCoupons, String usePoints, String pointsPerYuan) throws PromotionStrategyServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new PromotionStrategyServiceException(EMPTY_ENTERPRISE_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new PromotionStrategyServiceException(INVALID_ENTERPRISE_ID));

        // Check if the enterprise exists
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new PromotionStrategyServiceException(ENTERPRISE_NOT_EXISTING);

        // Fetch the existing strategy
        PromotionStrategy strategy = promotionStrategyRepo.getByEnterpriseId(enterpriseIdLong);
        if (strategy == null)
            strategy = promotionStrategyDao.getByEnterpriseId(enterpriseIdLong);

        // Update if necessary
        if (useCoupons != null && !useCoupons.isEmpty()) {
            boolean useCouponsBoolean = Boolean.parseBoolean(useCoupons);
            if (strategy.isUseCoupons() && !useCouponsBoolean)
                throw new PromotionStrategyServiceException(COUPON_STRATEGY_CANNOT_BE_DISABLED);
            strategy.setUseCoupons(useCouponsBoolean);
        }
        if (usePoints != null && !usePoints.isEmpty()) {
            boolean usePointsBoolean = Boolean.parseBoolean(usePoints);
            if (strategy.isUsePoints() && !usePointsBoolean)
                throw new PromotionStrategyServiceException(POINT_STRATEGY_CANNOT_BE_DISABLED);
            strategy.setUsePoints(usePointsBoolean);
        }
        if (pointsPerYuan != null && !pointsPerYuan.isEmpty()) {
            int pointsPerYuanInt = checker.parsePositiveInt(pointsPerYuan, new PromotionStrategyServiceException(INVALID_POINTS_PER_YUAN));
            strategy.setPointsPerYuan(pointsPerYuanInt);
        }

        promotionStrategyDao.update(strategy);
        promotionStrategyRepo.update(strategy);
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionStrategy getByEnterpriseId(String enterpriseId) throws PromotionStrategyServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(enterpriseId, new PromotionStrategyServiceException(EMPTY_ENTERPRISE_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new PromotionStrategyServiceException(INVALID_ENTERPRISE_ID));

        // Check if the enterprise exists
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new PromotionStrategyServiceException(ENTERPRISE_NOT_EXISTING);

        // Fetch the existing strategy
        PromotionStrategy strategy = promotionStrategyRepo.getByEnterpriseId(enterpriseIdLong);
        if (strategy == null)
            strategy = promotionStrategyDao.getByEnterpriseId(enterpriseIdLong);

        PromotionStrategy result = promotionStrategyRepo.getByEnterpriseId(enterpriseIdLong);
        return result == null ? promotionStrategyDao.getByEnterpriseId(enterpriseIdLong) : result;
    }
}
