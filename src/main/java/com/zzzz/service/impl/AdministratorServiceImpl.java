package com.zzzz.service.impl;

import com.zzzz.dao.AdministratorDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.po.Administrator;
import com.zzzz.po.AdministratorTypeEnum;
import com.zzzz.po.Enterprise;
import com.zzzz.service.AdministratorService;
import com.zzzz.service.AdministratorServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.AdministratorServiceException.ExceptionTypeEnum.*;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    private ParameterChecker<AdministratorServiceException> checker = new ParameterChecker<>();

    private static Enterprise createEnterpriseTemplate() {
        Enterprise result = new Enterprise();
        result.setName("");
        result.setImgUrl("");
        result.setVideoUrl("");
        result.setDetail("");
        result.setIntroduction("");
        return result;
    }

    @Override
    @Transactional(rollbackFor = AdministratorServiceException.class)
    public long createSystemAccount(String username, String password) throws AdministratorServiceException {
        // Check if the parameters are empty
        checker.rejectIfNullOrEmpty(username, new AdministratorServiceException(EMPTY_USERNAME));
        checker.rejectIfNullOrEmpty(password, new AdministratorServiceException(EMPTY_PASSWORD));

        Administrator administrator = new Administrator();
        administrator.setType(AdministratorTypeEnum.SYSTEM);
        administrator.setUsername(username);
        administrator.setPassword(password);

        try {
            // Insert
            administratorDao.insert(administrator);
            // Fetch the ID of the last inserted item
            long lastId = administratorDao.getLastInsertId();
            return lastId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AdministratorServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = AdministratorServiceException.class)
    public long createEnterpriseAccount(String username, String password) throws AdministratorServiceException {
        // Check if the parameters are empty
        checker.rejectIfNullOrEmpty(username, new AdministratorServiceException(EMPTY_USERNAME));
        checker.rejectIfNullOrEmpty(password, new AdministratorServiceException(EMPTY_PASSWORD));

        Administrator administrator = new Administrator();
        administrator.setType(AdministratorTypeEnum.ENTERPRISE);
        administrator.setUsername(username);
        administrator.setPassword(password);

        try {
            // Insert a new enterprise with the default template
            Enterprise enterprise = createEnterpriseTemplate();
            // Fetch the ID of the last inserted item (enterprise)
            long lastId = enterpriseDao.getLastInsertId();
            administrator.setEnterpriseId(lastId);
            // Insert the new administrator
            administratorDao.insert(administrator);
            // Fetch the ID of the last inserted item
            lastId = administratorDao.getLastInsertId();
            return lastId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AdministratorServiceException(INTERNAL_ERROR);
        }
    }
}
