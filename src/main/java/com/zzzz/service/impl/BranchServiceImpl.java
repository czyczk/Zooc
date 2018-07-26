package com.zzzz.service.impl;

import com.zzzz.dao.BranchDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.po.Branch;
import com.zzzz.repo.BranchRepo;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.service.BranchService;
import com.zzzz.service.BranchServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static com.zzzz.service.BranchServiceException.ExceptionTypeEnum.*;

@Service
public class BranchServiceImpl implements BranchService {
    private final GeneralDao generalDao;
    private final BranchDao branchDao;
    private final EnterpriseDao enterpriseDao;
    private final BranchRepo branchRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<BranchServiceException> checker = new ParameterChecker<>();

    @Autowired
    public BranchServiceImpl(GeneralDao generalDao, BranchDao branchDao, EnterpriseDao enterpriseDao,
                             BranchRepo branchRepo, EnterpriseRepo enterpriseRepo) {
        this.generalDao = generalDao;
        this.branchDao = branchDao;
        this.enterpriseDao = enterpriseDao;
        this.branchRepo = branchRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

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
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new BranchServiceException(ENTERPRISE_NOT_EXISTING);

        // Insert
        Branch branch = new Branch();
        branch.setEnterpriseId(enterpriseIdLong);
        branch.setName(name);
        branch.setAddress(address);
        branch.setLatitude(latitudeBd);
        branch.setLongitude(longitudeBd);
        branch.setTelephone(telephone);
        branchDao.insert(branch);

        // Fetch the last ID
        long lastId = generalDao.getLastInsertId();

        // Redis: cache the branch
        branchRepo.saveBranch(branch);
        return lastId;

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistenceById(String branchId) throws BranchServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        return branchRepo.isCached(branchIdLong) || branchDao.checkExistenceById(branchIdLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Branch getById(String branchId) throws BranchServiceException, SQLException {
        Branch result;

        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        // Redis: Check if the branch is cached
        result = branchRepo.getBranch(branchIdLong);
        // DB: On missing, fetch it from the database
        if (result == null)
            result = branchDao.getById(branchIdLong);
        // On missing, throw an exception
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
        Branch branch = branchRepo.getBranch(branchIdLong);
        if (branch == null)
            branchDao.getById(branchIdLong);
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
        branchRepo.updateBranch(branch);
    }

    @Override
    @Transactional(rollbackFor = { BranchServiceException.class, SQLException.class })
    public void disable(String branchId) throws BranchServiceException, SQLException {
        // Check if the parameter is valid
        checker.rejectIfNullOrEmpty(branchId, new BranchServiceException(EMPTY_BRANCH_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));

        // Fetch the branch and check if it exists
        Branch branch = branchRepo.getBranch(branchIdLong);
        if (branch == null)
            branchDao.getById(branchIdLong);
        if (branch == null)
            throw new BranchServiceException(BRANCH_NOT_EXISTING);

        // Set the branch to disabled
        branch.setDisabled(true);
        branchDao.update(branch);
        branchRepo.updateBranch(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<Branch> list(String usePagination, String targetPage, String pageSize, String enterpriseId, String branchId, String nameContaining, String addressContaining) throws BranchServiceException, SQLException {
        ListResult<Branch> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new BranchServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new BranchServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new BranchServiceException(EMPTY_ENTERPRISE_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new BranchServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new BranchServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new BranchServiceException(INVALID_ENTERPRISE_ID));
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new BranchServiceException(ENTERPRISE_NOT_EXISTING);

        // Optional fields
        Long branchIdLong = null;
        if (branchId != null && !branchId.isEmpty())
            branchIdLong = checker.parseUnsignedLong(branchId, new BranchServiceException(INVALID_BRANCH_ID));
        if (nameContaining != null && nameContaining.isEmpty())
            nameContaining = null;
        if (addressContaining != null && addressContaining.isEmpty())
            addressContaining = null;

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = branchDao.countTotal(enterpriseIdLong, branchIdLong, nameContaining, addressContaining);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<Branch> list = branchDao.list(usePaginationBool, starting, pageSizeLong, enterpriseIdLong, branchIdLong, nameContaining, addressContaining);
        result.setList(list);
        return result;
    }
}
