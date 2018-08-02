package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.LecturerDao;
import com.zzzz.po.Lecturer;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.zzzz.service.LecturerServiceException.ExceptionTypeEnum.*;

@Service
public class LecturerServiceImpl implements LecturerService {
    private final GeneralDao generalDao;
    private final LecturerDao lecturerDao;
    private final EnterpriseDao enterpriseDao;
    private final EnterpriseRepo enterpriseRepo;
    private ParameterChecker<LecturerServiceException> checker = new ParameterChecker<>();

    @Autowired
    public LecturerServiceImpl(GeneralDao generalDao, LecturerDao lecturerDao, EnterpriseDao enterpriseDao,
                               EnterpriseRepo enterpriseRepo) {
        this.generalDao = generalDao;
        this.lecturerDao = lecturerDao;
        this.enterpriseDao = enterpriseDao;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    @Transactional(rollbackFor = { LecturerServiceException.class, SQLException.class })
    public long insert(String enterpriseId, String name, String photoUrl, String introduction) throws LecturerServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new LecturerServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(name, new LecturerServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(photoUrl, new LecturerServiceException(EMPTY_PHOTO_URL));
        checker.rejectIfNullOrEmpty(introduction, new LecturerServiceException(EMPTY_INTRODUCTION));

        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new LecturerServiceException(INVALID_ENTERPRISE_ID));

        // Check if the enterprise exists
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new LecturerServiceException(ENTERPRISE_NOT_EXISTING);

        // Insert
        Lecturer lecturer = new Lecturer();
        lecturer.setEnterpriseId(enterpriseIdLong);
        lecturer.setName(name);
        lecturer.setPhotoUrl(photoUrl);
        lecturer.setIntroduction(introduction);
        lecturerDao.insert(lecturer);

        // Fetch the last ID
        long lastId = generalDao.getLastInsertId();
        return lastId;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistenceById(String lecturerId) throws LecturerServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(lecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        // Query
        return lecturerDao.checkExistenceById(lecturerIdLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Lecturer getById(String lecturerId) throws LecturerServiceException, SQLException {
        Lecturer result;

        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(lecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        result = lecturerDao.getById(lecturerIdLong);
        // Check if the lecturer exists
        if (result == null)
            throw new LecturerServiceException(LECTURER_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { LecturerServiceException.class, SQLException.class })
    public void update(String targetLecturerId, String name, String photoUrl, String introduction) throws LecturerServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetLecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(targetLecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        // Fetch the old one
        Lecturer lecturer = lecturerDao.getById(lecturerIdLong);
        if (lecturer == null)
            throw new LecturerServiceException(LECTURER_NOT_EXISTING);

        // Check if the parameters are valid
        if (name != null) {
            if (name.isEmpty())
                throw new LecturerServiceException(EMPTY_NAME);
            else
                lecturer.setName(name);
        }
        if (photoUrl != null) {
            if (photoUrl.isEmpty())
                throw new LecturerServiceException(EMPTY_PHOTO_URL);
            else
                lecturer.setPhotoUrl(photoUrl);
        }
        if (introduction != null) {
            if (introduction.isEmpty())
                throw new LecturerServiceException(EMPTY_INTRODUCTION);
            else
                lecturer.setIntroduction(introduction);
        }

        // Update
        lecturerDao.update(lecturer);
    }

    @Override
    @Transactional(rollbackFor = { LecturerServiceException.class, SQLException.class })
    public void disable(String lecturerId) throws LecturerServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(lecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        // Get the lecturer and check if it exists
        Lecturer lecturer = lecturerDao.getById(lecturerIdLong);
        if (lecturer == null)
            throw new LecturerServiceException(LECTURER_NOT_EXISTING);

        // Set the lecturer disabled
        lecturer.setDisabled(true);
        lecturerDao.update(lecturer);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<Lecturer> list(String usePagination, String targetPage, String pageSize, String enterpriseId, String lecturerId, String name) throws LecturerServiceException, SQLException {
        ListResult<Lecturer> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new LecturerServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new LecturerServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new LecturerServiceException(EMPTY_ENTERPRISE_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new LecturerServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new LecturerServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new LecturerServiceException(INVALID_ENTERPRISE_ID));
        boolean isExisting = enterpriseRepo.isCached(enterpriseIdLong) || enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new LecturerServiceException(ENTERPRISE_NOT_EXISTING);

        // Optional fields
        Long lecturerIdLong = null;
        if (lecturerId != null && !lecturerId.isEmpty())
            lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));
        if (name != null && name.isEmpty())
            name = null;

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = lecturerDao.countTotal(enterpriseIdLong, lecturerIdLong, name);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<Lecturer> list = lecturerDao.list(usePaginationBool, starting, pageSizeLong, enterpriseIdLong, lecturerIdLong, name);
        result.setList(list);
        return result;
    }
}
