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
    @Transactional(rollbackFor = BranchServiceException.class)
    public long insert(String enterpriseId, String name, String address, String latitude, String longitude, String telephone) throws BranchServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new BranchServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(name, new BranchServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(address, new BranchServiceException(EMPTY_ADDRESS));
        checker.rejectIfNullOrEmpty(latitude, new BranchServiceException(EMPTY_LATITUDE));
        checker.rejectIfNullOrEmpty(longitude, new BranchServiceException(EMPTY_LONGITUDE));
        checker.rejectIfNullOrEmpty(telephone, new BranchServiceException(EMPTY_TELEPHONE));

        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new BranchServiceException(INVALID_ENTERPRISE_ID));
        checker.rejectIfInvalidTelephone(telephone, new BranchServiceException(INVALID_TELEPHONE));
        BigDecimal latitudeBd = checker.parseBigDecimal(latitude, new BranchServiceException(INVALID_LATITUDE));
        BigDecimal longitudeBd = checker.parseBigDecimal(longitude, new BranchServiceException(INVALID_LONGITUDE));

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BranchServiceException(INTERNAL_ERROR);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistenceById(String branchId) throws BranchServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        // Query
        try {
            return branchDao.checkExistenceById(branchIdLong);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BranchServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Branch getById(String branchId) throws BranchServiceException {
        Branch result;

        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        try {
            result = branchDao.getById(branchIdLong);
            // Check if the enterprise exists
            if (result == null)
                throw new BranchServiceException(ENTERPRISE_NOT_EXISTING);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BranchServiceException(INTERNAL_ERROR);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = BranchServiceException.class)
    public void update(String targetBranchId, String name, String address, String latitude, String longitude, String telephone) throws BranchServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(targetBranchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(targetBranchId, new BranchServiceException(INVALID_BRANCH_ID));

        // Check if the target branch exists
        try {
            Branch branch = branchDao.getById(branchIdLong);

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
                checker.rejectIfInvalidTelephone(telephone, new BranchServiceException(INVALID_TELEPHONE));
                branch.setTelephone(telephone);
            }

            // Update
            branchDao.update(branch);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BranchServiceException(INTERNAL_ERROR);
        }

    }
}
