package com.zzzz.service.impl;

import com.zzzz.dao.AdministratorDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dto.EnterpriseDetail;
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
    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    private ParameterChecker<EnterpriseServiceException> checker = new ParameterChecker<>();

    private static Enterprise createEmptyTemplate(long administratorId) {
        Enterprise result = new Enterprise();
        result.setAdministratorId(administratorId);
        result.setName("");
        result.setImgUrl("");
        result.setVideoUrl("");
        result.setDetail("");
        result.setIntroduction("");
        return result;
    }

    @Override
    @Transactional(rollbackFor = EnterpriseServiceException.class)
    public void createTemplate(String administratorId) throws EnterpriseServiceException {
        // Check if the administrator ID is valid
        checker.isNullOrEmpty(administratorId, new EnterpriseServiceException(EMPTY_ADMINISTRATOR_ID));
        long administratorIdLong = checker.parseUnsignedLong(administratorId, new EnterpriseServiceException(INVALID_ADMINISTRATOR_ID));

        try {
            // Check if the administrator exists
            boolean isExisting = administratorDao.checkExistenceById(administratorIdLong);
            if (!isExisting)
                throw new EnterpriseServiceException(ADMINISTRATOR_NOT_EXISTING);

            // Insert one with the default template
            enterpriseDao.insert(createEmptyTemplate(administratorIdLong));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EnterpriseServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = EnterpriseServiceException.class)
    public void insert(String administratorId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException {
        // TODO implemented later
        throw new UnsupportedOperationException();

        // Check if the parameters are valid

        // Check if the administrator exists

        // Insert
    }

    @Override
    @Transactional(readOnly = true)
    public Enterprise getById(String enterpriseId) throws EnterpriseServiceException {
        // TODO implemented later
        throw new UnsupportedOperationException();

        // Check if the enterprise ID is valid

        // Check if the enterprise exists

        // Fetch the enterprise
    }

    @Override
    @Transactional(readOnly = true)
    public EnterpriseDetail getDtoById(String enterpriseId) throws EnterpriseServiceException {
        EnterpriseDetail result;

        // Check if the enterprise ID is valid
        checker.isNullOrEmpty(enterpriseId, new EnterpriseServiceException(EMPTY_ENTERPRISE_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new EnterpriseServiceException(INVALID_ENTERPRISE_ID));

        try {
            // Fetch the enterprise
            result = enterpriseDao.getDtoById(enterpriseIdLong);
            if (result == null)
                throw new EnterpriseServiceException(ENTERPRISE_NOT_EXISTING);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EnterpriseServiceException(INTERNAL_ERROR);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = EnterpriseServiceException.class)
    public void update(String targetEnterpriseId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException {
        // Check if the ID is valid
        checker.isNullOrEmpty(targetEnterpriseId, new EnterpriseServiceException(EMPTY_ENTERPRISE_ID));
        long targetEnterpriseIdLong = checker.parseUnsignedLong(targetEnterpriseId, new EnterpriseServiceException(INVALID_ENTERPRISE_ID));

        try {
            // Fetch the original enterprise
            Enterprise enterprise = enterpriseDao.getById(targetEnterpriseIdLong);
            if (enterprise == null)
                throw new EnterpriseServiceException(ENTERPRISE_NOT_EXISTING);

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EnterpriseServiceException(INTERNAL_ERROR);
        }

    }
}