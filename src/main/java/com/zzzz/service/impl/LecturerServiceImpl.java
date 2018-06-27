package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.dao.LecturerDao;
import com.zzzz.po.Lecturer;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.LecturerServiceException.ExceptionTypeEnum.*;

@Service
public class LecturerServiceImpl implements LecturerService {
    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private LecturerDao lecturerDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    private ParameterChecker<LecturerServiceException> checker = new ParameterChecker<>();

    @Override
    @Transactional(rollbackFor = LecturerServiceException.class)
    public long insert(String enterpriseId, String name, String photoUrl, String introduction) throws LecturerServiceException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new LecturerServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(name, new LecturerServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(photoUrl, new LecturerServiceException(EMPTY_PHOTO_URL));
        checker.rejectIfNullOrEmpty(introduction, new LecturerServiceException(EMPTY_INTRODUCTION));

        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new LecturerServiceException(INVALID_ENTERPRISE_ID));

        try {
            // Check if the enterprise exists
            boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LecturerServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistenceById(String lecturerId) throws LecturerServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(lecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        // Query
        try {
            return lecturerDao.checkExistenceById(lecturerIdLong);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LecturerServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Lecturer getById(String lecturerId) throws LecturerServiceException {
        Lecturer result;

        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(lecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        try {
            result = lecturerDao.getById(lecturerIdLong);
            // Check if the lecturer exists
            if (result == null)
                throw new LecturerServiceException(LECTURER_NOT_EXISTING);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LecturerServiceException(INTERNAL_ERROR);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = LecturerServiceException.class)
    public void update(String targetLecturerId, String name, String photoUrl, String introduction) throws LecturerServiceException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetLecturerId, new LecturerServiceException(EMPTY_LECTURER_ID));
        long lecturerIdLong = checker.parseUnsignedLong(targetLecturerId, new LecturerServiceException(INVALID_LECTURER_ID));

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LecturerServiceException(INTERNAL_ERROR);
        }
    }
}
