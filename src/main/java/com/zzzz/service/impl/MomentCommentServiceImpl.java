package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.MomentComment;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.MomentCommentService;
import com.zzzz.service.MomentCommentServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.MomentCommentDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.MomentCommentServiceException.ExceptionTypeEnum.*;

@Service
public class MomentCommentServiceImpl implements MomentCommentService {
    private final GeneralDao generalDao;
    private final MomentCommentDao momentCommentDao;
    private final MomentDao momentDao;
    private final UserDao userDao;
    private final UserRepo userRepo;
    private final ParameterChecker<MomentCommentServiceException> checker = new ParameterChecker<>();

    @Autowired
    public MomentCommentServiceImpl(GeneralDao generalDao, MomentCommentDao momentCommentDao,
                                    MomentDao momentDao, UserDao userDao,
                                    UserRepo userRepo) {
        this.generalDao = generalDao;
        this.momentCommentDao = momentCommentDao;
        this.momentDao = momentDao;
        this.userDao = userDao;
        this.userRepo = userRepo;
    }

    @Override
    public long insert(String momentId, String userId, String content, Date time) throws MomentCommentServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentCommentServiceException(EMPTY_MOMENT_ID));
        checker.rejectIfNullOrEmpty(userId, new MomentCommentServiceException(EMPTY_USER_ID));
        checker.rejectIfNullOrEmpty(content, new MomentCommentServiceException(EMPTY_CONTENT));
        if (time == null)
            throw new MomentCommentServiceException(EMPTY_TIME);
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentCommentServiceException(INVALID_MOMENT_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new MomentCommentServiceException(INVALID_USER_ID));

        // Check if the moment and the user exist
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentCommentServiceException(MOMENT_NOT_EXISTING);
        isExisting = userRepo.isCached(userIdLong) || userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new MomentCommentServiceException(USER_NOT_EXISTING);

        // Prepare a new comment
        MomentComment comment = new MomentComment();
        comment.setMomentId(momentIdLong);
        comment.setUserId(userIdLong);
        comment.setContent(content);
        comment.setTime(time);
        momentCommentDao.insert(comment);
        return generalDao.getLastInsertId();
    }

    @Override
    public void delete(String momentCommentId) throws MomentCommentServiceException, SQLException {
        // Check if the moment comment ID is valid
        checker.rejectIfNullOrEmpty(momentCommentId, new MomentCommentServiceException(EMPTY_MOMENT_COMMENT_ID));
        long momentCommentIdLong = checker.parseUnsignedLong(momentCommentId, new MomentCommentServiceException(INVALID_MOMENT_COMMENT_ID));

        // Check if the comment exists
        boolean isExisting = momentCommentDao.checkExistenceById(momentCommentIdLong);
        if (!isExisting)
            throw new MomentCommentServiceException(MOMENT_COMMENT_NOT_EXISTING);

        // Delete
        momentCommentDao.delete(momentCommentIdLong);
    }

    @Override
    public void update(String targetMomentCommentId, String content) throws MomentCommentServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(targetMomentCommentId, new MomentCommentServiceException(EMPTY_MOMENT_COMMENT_ID));
        long targetMomentCommentIdLong = checker.parseUnsignedLong(targetMomentCommentId, new MomentCommentServiceException(INVALID_MOMENT_COMMENT_ID));

        // Check if the moment comment exists
        MomentComment comment = momentCommentDao.getById(targetMomentCommentIdLong);
        if (content != null) {
            if (content.trim().isEmpty())
                throw new MomentCommentServiceException(EMPTY_CONTENT);
            comment.setContent(content);
        }
        
        // Update
        momentCommentDao.update(comment);
    }

    @Override
    public ListResult<MomentCommentDetail> list(String usePagination, String targetPage, String pageSize, String momentId) throws MomentCommentServiceException, SQLException {
        ListResult<MomentCommentDetail> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new MomentCommentServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new MomentCommentServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(momentId, new MomentCommentServiceException(EMPTY_MOMENT_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new MomentCommentServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new MomentCommentServiceException(INVALID_PAGE_SIZE));
        }
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentCommentServiceException(INVALID_MOMENT_ID));

        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentCommentServiceException(MOMENT_NOT_EXISTING);

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = momentCommentDao.countTotal(momentIdLong);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<MomentCommentDetail> list = momentCommentDao.list(usePaginationBool, starting, pageSizeLong, momentIdLong);
        result.setList(list);
        return result;
    }
}
