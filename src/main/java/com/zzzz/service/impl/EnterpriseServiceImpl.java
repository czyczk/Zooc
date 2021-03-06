package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.po.Enterprise;
import com.zzzz.service.EnterpriseService;
import com.zzzz.service.EnterpriseServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.EnterpriseServiceException.ExceptionTypeEnum.*;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseDao enterpriseDao;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<EnterpriseServiceException> checker = new ParameterChecker<>();

    @Autowired
    public EnterpriseServiceImpl(EnterpriseDao enterpriseDao,
                                 EnterpriseRepo enterpriseRepo) {
        this.enterpriseDao = enterpriseDao;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Enterprise getById(String enterpriseId) throws EnterpriseServiceException, SQLException {
        Enterprise result;

        // Check if the enterprise ID is valid
        checker.rejectIfNullOrEmpty(enterpriseId, new EnterpriseServiceException(EMPTY_ENTERPRISE_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new EnterpriseServiceException(INVALID_ENTERPRISE_ID));

        // Fetch the enterprise
        result = enterpriseRepo.getEnterpriseById(enterpriseIdLong);
        if (result == null) {
            result = enterpriseDao.getById(enterpriseIdLong);
            if (result == null)
                throw new EnterpriseServiceException(ENTERPRISE_NOT_EXISTING);
            enterpriseRepo.saveEnterprise(result);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = { EnterpriseServiceException.class, SQLException.class })
    public void update(String targetEnterpriseId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetEnterpriseId, new EnterpriseServiceException(EMPTY_ENTERPRISE_ID));
        long targetEnterpriseIdLong = checker.parseUnsignedLong(targetEnterpriseId, new EnterpriseServiceException(INVALID_ENTERPRISE_ID));

        // Fetch the original enterprise
        Enterprise enterprise = enterpriseRepo.getEnterpriseById(targetEnterpriseIdLong);
        if (enterprise == null) {
            enterprise = enterpriseDao.getById(targetEnterpriseIdLong);
            if (enterprise == null)
                throw new EnterpriseServiceException(ENTERPRISE_NOT_EXISTING);
        }

        if (name != null) {
            if (name.isEmpty())
                throw new EnterpriseServiceException(EMPTY_NAME);
            else
                enterprise.setName(name);
        }
        if (imgUrl != null) {
            if (imgUrl.isEmpty())
                throw new EnterpriseServiceException(EMPTY_IMG_URL);
            else
                enterprise.setImgUrl(imgUrl);
        }
        if (introduction != null) {
            if (introduction.isEmpty())
                throw new EnterpriseServiceException(EMPTY_INTRODUCTION);
            else
                enterprise.setIntroduction(introduction);
        }
        if (videoUrl != null) {
            if (videoUrl.isEmpty())
                throw new EnterpriseServiceException(EMPTY_VIDEO_URL);
            else
                enterprise.setVideoUrl(videoUrl);
        }
        if (detail != null) {
            if (detail.isEmpty())
                throw new EnterpriseServiceException(EMPTY_DETAIL);
            else
                enterprise.setDetail(detail);
        }

        // Update
        enterpriseDao.update(enterprise);
        enterpriseRepo.updateEnterprise(enterprise);
    }
}
