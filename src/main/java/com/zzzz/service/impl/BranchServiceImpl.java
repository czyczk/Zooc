package com.zzzz.service.impl;

import com.zzzz.dao.BranchDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.po.Branch;
import com.zzzz.service.BranchService;
import com.zzzz.service.BranchServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;

import static com.zzzz.service.BranchServiceException.ExceptionTypeEnum.*;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private BranchDao branchDao;

    private ParameterChecker<BranchServiceException> checker = new ParameterChecker<>();

    @Override
    @Transactional(rollbackFor = { BranchServiceException.class, SQLException.class })
    public long insert(String enterpriseId, String name, String address, String latitude, String longitude, String telephone) throws BranchServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new BranchServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(name, new BranchServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(address, new BranchServiceException(EMPTY_ADDRESS));
        checker.rejectIfNullOrEmpty(latitude, new BranchServiceException(EMPTY_LATITUDE));
        checker.rejectIfNullOrEmpty(longitude, new BranchServiceException(EMPTY_LONGITUDE));
        checker.rejectIfNullOrEmpty(telephone, new BranchServiceException(EMPTY_TELEPHONE));

        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new BranchServiceException(INVALID_ENTERPRISE_ID));
        checker.rejectTelephoneIfInvalid(telephone, new BranchServiceException(INVALID_TELEPHONE));
        BigDecimal latitudeBd = checker.parseBigDecimal(latitude, new BranchServiceException(INVALID_LATITUDE));
        BigDecimal longitudeBd = checker.parseBigDecimal(longitude, new BranchServiceException(INVALID_LONGITUDE));

        // Check if the enterprise exists
        boolean isExisting = branchDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new BranchServiceException(ENTERPRISE_NOT_EXISTING);

        // Insert
        Branch branch = new Branch();
        branch.setEnterpriseId(enterpriseIdLong);
        branch.setName(name);
        branch.setAddress(address);
        branch.setLatitude(latitudeBd);
        branch.setLongitude(longitudeBd);
        branchDao.insert(branch);

        // Fetch the last ID
        long lastId = generalDao.getLastInsertId();
        return lastId;

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistenceById(String branchId) throws BranchServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        return branchDao.checkExistenceById(branchIdLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Branch getById(String branchId) throws BranchServiceException, SQLException {
        Branch result;

        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        result = branchDao.getById(branchIdLong);
        // Check if the enterprise exists
        if (result == null)
            throw new BranchServiceException(BRANCH_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { BranchServiceException.class, SQLException.class })
    public void update(String targetBranchId, String name, String address, String latitude, String longitude, String telephone) throws BranchServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(targetBranchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(targetBranchId, new BranchServiceException(INVALID_BRANCH_ID));

        // Check if the target branch exists
        Branch branch = branchDao.getById(branchIdLong);
        if (branch == null)
            throw new BranchServiceException(BRANCH_NOT_EXISTING);

        // Check if the parameters need updating
        if (name != null) {
            if (name.isEmpty())
                throw new BranchServiceException(EMPTY_NAME);
            else
                branch.setName(name);
        }
        if (address != null) {
            if (address.isEmpty())
                throw new BranchServiceException(EMPTY_ADDRESS);
            else
                branch.setAddress(address);
        }
        BigDecimal latitudeBd;
        if (latitude != null) {
            latitudeBd = checker.parseBigDecimal(latitude, new BranchServiceException(INVALID_LATITUDE));
            branch.setLatitude(latitudeBd);
        }
        BigDecimal longitudeBd;
        if (longitude != null) {
            longitudeBd = checker.parseBigDecimal(longitude, new BranchServiceException(INVALID_LONGITUDE));
            branch.setLongitude(longitudeBd);
        }
        if (telephone != null) {
            checker.rejectTelephoneIfInvalid(telephone, new BranchServiceException(INVALID_TELEPHONE));
            branch.setTelephone(telephone);
        }

        // Update
        branchDao.update(branch);

    }
}
