package com.zzzz.service.impl;

import com.zzzz.dao.*;
import com.zzzz.po.CourseOffering;
import com.zzzz.service.CourseOfferingService;
import com.zzzz.service.CourseOfferingServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.CourseOfferingServiceException.ExceptionTypeEnum.*;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {
    private final GeneralDao generalDao;
    private final CourseOfferingDao courseOfferingDao;
    private final CourseDao courseDao;
    private final BranchDao branchDao;
    private final LecturerDao lecturerDao;
    private final ParameterChecker<CourseOfferingServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CourseOfferingServiceImpl(GeneralDao generalDao, CourseOfferingDao courseOfferingDao, CourseDao courseDao, BranchDao branchDao, LecturerDao lecturerDao) {
        this.generalDao = generalDao;
        this.courseOfferingDao = courseOfferingDao;
        this.courseDao = courseDao;
        this.branchDao = branchDao;
        this.lecturerDao = lecturerDao;
    }

    @Override
    @Transactional(rollbackFor = { CourseOfferingServiceException.class, SQLException.class })
    public long insert(String courseId, String branchId, String lecturerId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(courseId, new CourseOfferingServiceException(EMPTY_COURSE_ID));
        checker.rejectIfNullOrEmpty(branchId, new CourseOfferingServiceException(EMPTY_BRANCH_ID));
        checker.rejectIfNullOrEmpty(lecturerId, new CourseOfferingServiceException(EMPTY_LECTURER_ID));
        long courseIdLong = checker.parseUnsignedLong(courseId, new CourseOfferingServiceException(INVALID_COURSE_ID));
        long branchIdLong = checker.parseUnsignedLong(branchId, new CourseOfferingServiceException(INVALID_BRANCH_ID));
        long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new CourseOfferingServiceException(INVALID_LECTURER_ID));

        // Check if the course, the branch and the lecturer exist
        boolean isExisting = courseDao.checkExistenceById(courseIdLong);
        if (!isExisting)
            throw new CourseOfferingServiceException(COURSE_NOT_EXISTING);
        isExisting = branchDao.checkExistenceById(branchIdLong);
        if (!isExisting)
            throw new CourseOfferingServiceException(BRANCH_NOT_EXISTING);
        isExisting = lecturerDao.checkExistenceById(lecturerIdLong);
        if (!isExisting)
            throw new CourseOfferingServiceException(LECTURER_NOT_EXISTING);

        // Insert the offering
        CourseOffering offering = new CourseOffering();
        offering.setCourseId(courseIdLong);
        offering.setBranchId(branchIdLong);
        offering.setLecturerId(lecturerIdLong);

        courseOfferingDao.insert(offering);
        long lastId = generalDao.getLastInsertId();
        return lastId;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseOffering getById(String courseOfferingId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(courseOfferingId, new CourseOfferingServiceException(EMPTY_COURSE_OFFERING_ID));
        long courseOfferingIdLong = checker.parseUnsignedLong(courseOfferingId, new CourseOfferingServiceException(INVALID_COURSE_OFFERING_ID));

        CourseOffering offering = courseOfferingDao.getById(courseOfferingIdLong);
        if (offering == null)
            throw new CourseOfferingServiceException(COURSE_OFFERING_NOT_EXISTING);
        return offering;
    }

    @Override
    @Transactional(rollbackFor = { CourseOfferingServiceException.class, SQLException.class })
    public void update(String targetCourseOfferingId, String courseId, String branchId, String lecturerId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        // Check if the target ID is valid
        checker.rejectIfNullOrEmpty(targetCourseOfferingId, new CourseOfferingServiceException(EMPTY_COURSE_OFFERING_ID));
        long targetCourseOfferingIdLong = checker.parseUnsignedLong(targetCourseOfferingId, new CourseOfferingServiceException(INVALID_COURSE_OFFERING_ID));

        // Fetch the original offering
        CourseOffering offering = courseOfferingDao.getById(targetCourseOfferingIdLong);
        if (offering == null)
            throw new CourseOfferingServiceException(COURSE_OFFERING_NOT_EXISTING);

        if (courseId != null) {
            long courseIdLong = checker.parseUnsignedLong(courseId, new CourseOfferingServiceException(INVALID_COURSE_ID));
            boolean isExisting = courseDao.checkExistenceById(courseIdLong);
            if (!isExisting)
                throw new CourseOfferingServiceException(COURSE_NOT_EXISTING);
            offering.setCourseId(courseIdLong);
        }
        if (branchId != null) {
            long branchIdLong = checker.parseUnsignedLong(branchId, new CourseOfferingServiceException(INVALID_BRANCH_ID));
            boolean isExisting = branchDao.checkExistenceById(branchIdLong);
            if (!isExisting)
                throw new CourseOfferingServiceException(BRANCH_NOT_EXISTING);
            offering.setBranchId(branchIdLong);
        }
        if (lecturerId != null) {
            long lecturerIdLong = checker.parseUnsignedLong(lecturerId, new CourseOfferingServiceException(INVALID_LECTURER_ID));
            boolean isExisting = lecturerDao.checkExistenceById(lecturerIdLong);
            if (!isExisting)
                throw new CourseOfferingServiceException(LECTURER_NOT_EXISTING);
            offering.setLecturerId(lecturerIdLong);
        }

        // Update the offering
        courseOfferingDao.update(offering);
    }

    @Override
    @Transactional(rollbackFor = { CourseOfferingServiceException.class, SQLException.class })
    public void delete(String courseOfferingId) throws CourseOfferingServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(courseOfferingId, new CourseOfferingServiceException(EMPTY_COURSE_OFFERING_ID));
        long courseOfferingIdLong = checker.parseUnsignedLong(courseOfferingId, new CourseOfferingServiceException(INVALID_COURSE_OFFERING_ID));

        // Check if the offering exists
        boolean isExisting = courseOfferingDao.checkExistenceById(courseOfferingIdLong);
        if (!isExisting)
            throw new CourseOfferingServiceException(COURSE_OFFERING_NOT_EXISTING);

        // Delete
        courseOfferingDao.delete(courseOfferingIdLong);
    }
}
