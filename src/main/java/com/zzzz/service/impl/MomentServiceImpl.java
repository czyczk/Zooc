package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.MomentDao;
import com.zzzz.po.Moment;
import com.zzzz.service.MomentService;
import com.zzzz.service.MomentServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.MomentServiceException.ExceptionTypeEnum.*;

@Service
public class MomentServiceImpl implements MomentService {
    private final GeneralDao generalDao;
    private final MomentDao momentDao;
    private final EnterpriseDao enterpriseDao;
    private final ParameterChecker<MomentServiceException> checker = new ParameterChecker<>();

    @Autowired
    public MomentServiceImpl(GeneralDao generalDao, MomentDao momentDao, EnterpriseDao enterpriseDao) {
        this.generalDao = generalDao;
        this.momentDao = momentDao;
        this.enterpriseDao = enterpriseDao;
    }

    @Override
    @Transactional(rollbackFor = { MomentServiceException.class, SQLException.class })
    public Long insert(String enterpriseId, String content, Date time) throws MomentServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new MomentServiceException(EMPTY_ENTERPRISE_ID));
        if (time == null) {
            throw new MomentServiceException(EMPTY_TIME);
        }
        if (content == null)
            throw new MomentServiceException(EMPTY_CONTENT);
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new MomentServiceException(INVALID_ENTERPRISE_ID));

        // Check if the enterprise exists
        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new MomentServiceException(ENTERPRISE_NOT_EXISTING);

        // Insert
        Moment moment = new Moment();
        moment.setEnterpriseId(enterpriseIdLong);
        moment.setContent(content);
        moment.setTime(time);
        momentDao.insert(moment);

        // Fetch the last ID
        long lastId = generalDao.getLastInsertId();
        return lastId;
    }

    @Override
    @Transactional(rollbackFor = { MomentServiceException.class, SQLException.class })
    public void delete(String momentId) throws MomentServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentServiceException(EMPTY_MOMENT_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentServiceException(INVALID_MOMENT_ID));

        // Check if the moment exists
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentServiceException(MOMENT_NOT_EXISTING);

        // Delete
        momentDao.delete(momentIdLong);
    }

    @Override
    @Transactional(rollbackFor = { MomentServiceException.class, SQLException.class })
    public void update(String momentId, String content) throws MomentServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentServiceException(EMPTY_MOMENT_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentServiceException(INVALID_MOMENT_ID));

        // Check if the moment exists
        Moment moment = momentDao.getById(momentIdLong);
        if (moment == null)
            throw new MomentServiceException(MOMENT_NOT_EXISTING);

        // Update
        moment.setContent(content);
        momentDao.update(moment);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<Moment> list(String usePagination, String targetPage, String pageSize, String enterpriseId) throws MomentServiceException, SQLException {
        ListResult<Moment> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new MomentServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new MomentServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new MomentServiceException(EMPTY_ENTERPRISE_ID));
        
        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new MomentServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new MomentServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new MomentServiceException(INVALID_ENTERPRISE_ID));

        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new MomentServiceException(ENTERPRISE_NOT_EXISTING);

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = momentDao.countTotal(enterpriseIdLong);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<Moment> list = momentDao.list(usePaginationBool, starting, pageSizeLong, enterpriseIdLong);
        result.setList(list);
        return result;
    }
}
