package com.zzzz.service.impl;

import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.MomentDao;
import com.zzzz.dao.MomentLikeDao;
import com.zzzz.dao.UserDao;
import com.zzzz.po.MomentLike;
import com.zzzz.repo.MomentLikeTotalRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.MomentLikeService;
import com.zzzz.service.MomentLikeServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.MomentLikeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.MomentLikeServiceException.ExceptionTypeEnum.*;

@Service
public class MomentLikeServiceImpl implements MomentLikeService {
    private final GeneralDao generalDao;
    private final MomentLikeDao momentLikeDao;
    private final MomentDao momentDao;
    private final UserDao userDao;
    private final MomentLikeTotalRepo momentLikeTotalRepo;
    private final UserRepo userRepo;
    private final ParameterChecker<MomentLikeServiceException> checker = new ParameterChecker<>();

    @Autowired
    public MomentLikeServiceImpl(GeneralDao generalDao, MomentLikeDao momentLikeDao,
                                 MomentDao momentDao, UserDao userDao,
                                 MomentLikeTotalRepo momentLikeTotalRepo, UserRepo userRepo) {
        this.momentLikeDao = momentLikeDao;
        this.generalDao = generalDao;
        this.momentDao = momentDao;
        this.userDao = userDao;
        this.momentLikeTotalRepo = momentLikeTotalRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(rollbackFor = { MomentLikeServiceException.class, SQLException.class })
    public long insert(String momentId, String userId, Date time) throws MomentLikeServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentLikeServiceException(EMPTY_MOMENT_ID));
        checker.rejectIfNullOrEmpty(userId, new MomentLikeServiceException(EMPTY_USER_ID));
        if (time == null)
            throw new MomentLikeServiceException(EMPTY_TIME);
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentLikeServiceException(INVALID_MOMENT_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new MomentLikeServiceException(INVALID_USER_ID));

        // Check if the moment and the user exist
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(MOMENT_NOT_EXISTING);
        isExisting = userRepo.isCached(userIdLong) || userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(USER_NOT_EXISTING);

        // Prepare a like
        MomentLike like = new MomentLike();
        like.setMomentId(momentIdLong);
        like.setUserId(userIdLong);
        like.setTime(time);

        // Insert and return the last ID
        momentLikeDao.insert(like);
        long lastId = generalDao.getLastInsertId();

        // Redis: total++ (if cached)
        momentLikeTotalRepo.incrTotalIfCached(momentIdLong);
        return lastId;
    }

    @Override
    @Transactional(rollbackFor = { MomentLikeServiceException.class, SQLException.class })
    public void delete(String momentLikeId) throws MomentLikeServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(momentLikeId, new MomentLikeServiceException(EMPTY_MOMENT_LIKE_ID));
        long momentLikeIdLong = checker.parseUnsignedLong(momentLikeId, new MomentLikeServiceException(INVALID_MOMENT_LIKE_ID));

        // Fetch the moment like and delete it
        MomentLike like = momentLikeDao.getById(momentLikeIdLong);
        if (like == null)
            throw new MomentLikeServiceException(MOMENT_LIKE_NOT_EXISTING);
        momentLikeDao.delete(momentLikeIdLong);

        // Redis: total-- (if cached)
        momentLikeTotalRepo.decrTotalIfCached(like.getMomentId());
    }

    @Override
    public void delete(String momentId, String userId) throws MomentLikeServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentLikeServiceException(EMPTY_MOMENT_ID));
        checker.rejectIfNullOrEmpty(userId, new MomentLikeServiceException(EMPTY_USER_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentLikeServiceException(INVALID_MOMENT_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new MomentLikeServiceException(INVALID_USER_ID));

        // Check if the moment and the user exist
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(MOMENT_NOT_EXISTING);
        isExisting = userRepo.isCached(userIdLong) ||  userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(USER_NOT_EXISTING);

        // Get the ID of the moment like
        MomentLike like = momentLikeDao.getByMomentIdAndUserId(momentIdLong, userIdLong);
        if (like == null)
            throw new MomentLikeServiceException(MOMENT_LIKE_NOT_EXISTING);

        momentLikeDao.delete(like.getMomentLikeId());
        // Redis: total-- (if cached)
        momentLikeTotalRepo.decrTotalIfCached(like.getMomentId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasLiked(String momentId, String userId) throws MomentLikeServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentLikeServiceException(EMPTY_MOMENT_ID));
        checker.rejectIfNullOrEmpty(userId, new MomentLikeServiceException(EMPTY_USER_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentLikeServiceException(INVALID_MOMENT_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new MomentLikeServiceException(INVALID_USER_ID));

        // Check if the moment and the user exist
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(MOMENT_NOT_EXISTING);
        isExisting = userRepo.isCached(userIdLong) ||  userDao.checkExistenceById(userIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(USER_NOT_EXISTING);

        // Check if the like of the moment produced by the user exists
        long total = momentLikeDao.countTotal(momentIdLong, userIdLong);
        return total > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotal(String momentId) throws MomentLikeServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentLikeServiceException(EMPTY_MOMENT_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentLikeServiceException(INVALID_MOMENT_ID));

        // Redis: Try to hit it from cache
        Long total = momentLikeTotalRepo.getTotal(momentIdLong);
        if (total == null) {
            // Check if the moment exists
            boolean isExisting = momentDao.checkExistenceById(momentIdLong);
            if (!isExisting)
                throw new MomentLikeServiceException(MOMENT_NOT_EXISTING);

            // Fetch it from the database and cache it
            total = momentLikeDao.countTotal(momentIdLong, null);
            momentLikeTotalRepo.saveTotal(momentIdLong, total);
        }
        return total;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MomentLikeDetail> listLatest(String momentId, String n) throws MomentLikeServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentLikeServiceException(EMPTY_MOMENT_ID));
        checker.rejectIfNullOrEmpty(n, new MomentLikeServiceException(EMPTY_LATEST_NUMBER));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentLikeServiceException(INVALID_MOMENT_ID));
        int nInt = checker.parsePositiveInt(n, new MomentLikeServiceException(INVALID_LATEST_NUMBER));

        // Check if the moment exists
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentLikeServiceException(MOMENT_NOT_EXISTING);

        // Fetch the list
        List<MomentLikeDetail> result = momentLikeDao.list(momentIdLong, nInt);
        return result;
    }
}
