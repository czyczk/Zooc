package com.zzzz.service.impl;

import com.zzzz.dao.AdministratorDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
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
    private GeneralDao generalDao;

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
    @Transactional(rollbackFor = { AdministratorServiceException.class, SQLException.class })
    public long createSystemAccount(String username, String password) throws AdministratorServiceException, SQLException {
        // Check if the parameters are empty
        checker.rejectIfNullOrEmpty(username, new AdministratorServiceException(EMPTY_USERNAME));
        checker.rejectIfNullOrEmpty(password, new AdministratorServiceException(EMPTY_PASSWORD));

        Administrator administrator = new Administrator();
        administrator.setType(AdministratorTypeEnum.SYSTEM);
        administrator.setUsername(username);
        administrator.setPassword(password);

        // Insert
        administratorDao.insert(administrator);
        // Fetch the ID of the last inserted item
        long lastId = generalDao.getLastInsertId();
        return lastId;
    }

    @Override
    @Transactional(rollbackFor = { AdministratorServiceException.class, SQLException.class })
    public long createEnterpriseAccount(String username, String password) throws AdministratorServiceException, SQLException {
        // Check if the parameters are empty
        checker.rejectIfNullOrEmpty(username, new AdministratorServiceException(EMPTY_USERNAME));
        checker.rejectIfNullOrEmpty(password, new AdministratorServiceException(EMPTY_PASSWORD));

        Administrator administrator = new Administrator();
        administrator.setType(AdministratorTypeEnum.ENTERPRISE);
        administrator.setUsername(username);
        administrator.setPassword(password);

        // Insert a new enterprise with the default template
        Enterprise enterprise = createEnterpriseTemplate();
        // Fetch the ID of the last inserted item (enterprise)
        long lastId = generalDao.getLastInsertId();
        administrator.setEnterpriseId(lastId);
        // Insert the new administrator
        administratorDao.insert(administrator);
        // Fetch the ID of the last inserted item
        lastId = generalDao.getLastInsertId();
        return lastId;
    }

    @Override
    @Transactional(readOnly = true)
    public Administrator getById(String administratorId) throws AdministratorServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(administratorId, new AdministratorServiceException(EMPTY_ADMINISTRATOR_ID));
        long administratorIdLong = checker.parseUnsignedLong(administratorId, new AdministratorServiceException(INVALID_ADMINISTRATOR_ID));

        // Fetch the administrator and check if the administrator exists
        Administrator administrator = administratorDao.getById(administratorIdLong);
        if (administrator == null)
            throw new AdministratorServiceException(ADMINISTRATOR_NOT_EXISTING);
        return administrator;
    }

    @Override
    @Transactional(rollbackFor = { AdministratorServiceException.class, SQLException.class })
    public void update(String targetAdministratorId, String username, String password) throws AdministratorServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetAdministratorId, new AdministratorServiceException(EMPTY_ADMINISTRATOR_ID));
        long targetAdministratorIdLong = checker.parseUnsignedLong(targetAdministratorId, new AdministratorServiceException(INVALID_ADMINISTRATOR_ID));

        // Fetch the original administrator and check if it exists
        Administrator administrator = administratorDao.getById(targetAdministratorIdLong);
        if (administrator == null)
            throw new AdministratorServiceException(ADMINISTRATOR_NOT_EXISTING);
        // Update fields if not null
        if (username != null)
            administrator.setUsername(username);
        // TODO password validity checker
        if (password != null)
            administrator.setPassword(password);
        // Update
        administratorDao.update(administrator);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrator logIn(String administratorId, String password) throws AdministratorServiceException, SQLException {
        // Check if the parameters are empty
        checker.rejectIfNullOrEmpty(administratorId, new AdministratorServiceException(EMPTY_ADMINISTRATOR_ID));
        checker.rejectIfNullOrEmpty(password, new AdministratorServiceException(EMPTY_PASSWORD));
        long administratorIdLong = checker.parseUnsignedLong(administratorId, new AdministratorServiceException(INVALID_ADMINISTRATOR_ID));

        // Fetch the administrator and check if the administrator exists
        Administrator administrator = administratorDao.getById(administratorIdLong);
        if (administrator == null)
            throw new AdministratorServiceException(ADMINISTRATOR_NOT_EXISTING);

        // Check if the password is correct
        if (!password.equals(administrator.getPassword()))
            throw new AdministratorServiceException(INCORRECT_PASSWORD);

        // Hide the password
        administrator.setPassword(null);
        return administrator;
    }
}
