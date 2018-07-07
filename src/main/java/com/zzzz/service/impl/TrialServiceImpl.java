package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.Trial;
import com.zzzz.po.TrialStatusEnum;
import com.zzzz.service.TrialService;
import com.zzzz.service.TrialServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.TrialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

import static com.zzzz.service.TrialServiceException.ExceptionTypeEnum.*;

@Service
public class TrialServiceImpl implements TrialService {
    private final GeneralDao generalDao;
    private final TrialDao trialDao;
    private final CourseCategoryDao categoryDao;
    private final BranchDao branchDao;
    private final LecturerDao lecturerDao;
    private final ParameterChecker<TrialServiceException> checker = new ParameterChecker<>();

    @Autowired
    public TrialServiceImpl(GeneralDao generalDao, TrialDao trialDao, CourseCategoryDao categoryDao, BranchDao branchDao, LecturerDao lecturerDao) {
        this.generalDao = generalDao;
        this.trialDao = trialDao;
        this.categoryDao = categoryDao;
        this.branchDao = branchDao;
        this.lecturerDao = lecturerDao;
    }

    @Override
    @Transactional(rollbackFor = { TrialServiceException.class, SQLException.class })
    public long insert(String name, String detail, String imgUrl, String categoryId, String branchId, String lecturerId, Date releaseTime) throws TrialServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(name, new TrialServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(imgUrl, new TrialServiceException(EMPTY_IMG_URL));
        checker.rejectIfNullOrEmpty(categoryId, new TrialServiceException(EMPTY_CATEGORY_ID));
        checker.rejectIfNullOrEmpty(lecturerId, new TrialServiceException(EMPTY_LECTURER_ID));
        if (releaseTime == null)
            throw new TrialServiceException(EMPTY_RELEASE_TIME);
        long categoryIdLong = checker.parseUnsignedLong(categoryId, new TrialServiceException(INVALID_CATEGORY_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new TrialServiceException(INVALID_BRANCH_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new TrialServiceException(INVALID_LECTURER_ID));

        // Check if the category, the branch and the lecturer exist
        boolean isExisting = categoryDao.checkExistenceById(categoryIdLong);
        if (!isExisting)
            throw new TrialServiceException(CATEGORY_NOT_EXISTING);
        isExisting = branchDao.checkExistenceById(branchIdLong);
        if (!isExisting)
            throw new TrialServiceException(BRANCH_NOT_EXISTING);
        isExisting = lecturerDao.checkExistenceById(lecturerIdLong);
        if (!isExisting)
            throw new TrialServiceException(LECTURER_NOT_EXISTING);

        // Prepare a new trial
        Trial trial = new Trial();
        trial.setName(name);
        trial.setDetail(detail);
        trial.setImgUrl(imgUrl);
        trial.setCategoryId(categoryIdLong);
        trial.setBranchId(branchIdLong);
        trial.setLecturerId(lecturerIdLong);
        trial.setReleaseTime(releaseTime);
        // TODO a new trial is created to be AVAILABLE before reviewing modules are implemented
        trial.setStatus(TrialStatusEnum.AVAILABLE);

        // Insert
        trialDao.insert(trial);
        return generalDao.getLastInsertId();
    }

    @Override
    @Transactional(readOnly = true)
    public TrialDetail getVoById(String trialId) throws TrialServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(trialId, new TrialServiceException(EMPTY_TRIAL_ID));
        long trialIdLong = checker.parseUnsignedLong(trialId, new TrialServiceException(INVALID_TRIAL_ID));

        // Fetch the trial and check if it's null
        TrialDetail result = trialDao.getVoById(trialIdLong);
        if (result == null)
            throw new TrialServiceException(TRIAL_NOT_EXISTING);

        return result;
    }

    @Override
    @Transactional(rollbackFor = { TrialServiceException.class, SQLException.class })
    public void update(String targetTrialId, String name, String detail, String imgUrl, String categoryId, String branchId, String lecturerId, String releaseTime, String status) throws TrialServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetTrialId, new TrialServiceException(EMPTY_TRIAL_ID));
        long targetTrialIdLong = checker.parseUnsignedLong(targetTrialId, new TrialServiceException(INVALID_TRIAL_ID));

        // Fetch the trial and check if it's null
        Trial trial = trialDao.getById(targetTrialIdLong);
        if (trial == null)
            throw new TrialServiceException(TRIAL_NOT_EXISTING);

        if (name != null) {
            if (name.isEmpty())
                throw new TrialServiceException(EMPTY_NAME);
            else
                trial.setName(name);
        }
        if (detail != null) {
            if (detail.isEmpty())
                throw new TrialServiceException(EMPTY_DETAIL);
            else
                trial.setDetail(detail);
        }
        if (imgUrl != null) {
            if (imgUrl.isEmpty())
                throw new TrialServiceException(EMPTY_IMG_URL);
            else
                trial.setImgUrl(imgUrl);
        }
        if (categoryId != null) {
            if (categoryId.isEmpty())
                throw new TrialServiceException(EMPTY_CATEGORY_ID);
            long categoryIdLong = checker.parseUnsignedLong(categoryId, new TrialServiceException(INVALID_CATEGORY_ID));
            boolean isExisting = categoryDao.checkExistenceById(categoryIdLong);
            if (!isExisting)
                throw new TrialServiceException(CATEGORY_NOT_EXISTING);
            trial.setCategoryId(categoryIdLong);
        }
        if (branchId != null) {
            if (branchId.isEmpty())
                throw new TrialServiceException(EMPTY_BRANCH_ID);
            long branchIdLong = checker.parseUnsignedLong(branchId, new TrialServiceException(INVALID_BRANCH_ID));
            boolean isExisting = branchDao.checkExistenceById(branchIdLong);
            if (!isExisting)
                throw new TrialServiceException(BRANCH_NOT_EXISTING);
            trial.setBranchId(branchIdLong);
        }
        if (lecturerId != null) {
            if (lecturerId.isEmpty())
                throw new TrialServiceException(EMPTY_LECTURER_ID);
            long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new TrialServiceException(INVALID_LECTURER_ID));
            boolean isExisting = lecturerDao.checkExistenceById(lecturerIdLong);
            if (!isExisting)
                throw new TrialServiceException(LECTURER_NOT_EXISTING);
            trial.setLecturerId(lecturerIdLong);
        }
        if (releaseTime != null) {
            if (releaseTime.isEmpty())
                throw new TrialServiceException(EMPTY_RELEASE_TIME);
            long releaseTimeLong = checker.parseUnsignedLong(releaseTime, new TrialServiceException(INVALID_RELEASE_TIME));
            trial.setReleaseTime(new Date(releaseTimeLong));
        }
        if (status != null) {
            if (status.isEmpty())
                throw new TrialServiceException(EMPTY_STATUS);
            try {
                TrialStatusEnum statusEnum = TrialStatusEnum.valueOf(status);
                trial.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new TrialServiceException(INVALID_STATUS);
            }
        }

        // Update
        trialDao.update(trial);
    }
}
