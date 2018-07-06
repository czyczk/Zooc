package com.zzzz.service.impl;

import com.zzzz.dao.CourseCategoryDao;
import com.zzzz.dao.CourseDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.po.Course;
import com.zzzz.po.CourseStatusEnum;
import com.zzzz.service.CourseService;
import com.zzzz.service.CourseServiceException;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.CourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import static com.zzzz.service.CourseServiceException.ExceptionTypeEnum.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private CourseCategoryDao courseCategoryDao;

    @Autowired
    private GeneralDao generalDao;

    private ParameterChecker<CourseServiceException> checker = new ParameterChecker<>();

    @Override
    @Transactional(rollbackFor = { CourseServiceException.class, SQLException.class })
    public long insert(String enterpriseId, String name, String detail, String imgUrl, String categoryId, Date releaseTime, String price) throws CourseServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(enterpriseId, new CourseServiceException(EMPTY_ENTERPRISE_ID));
        checker.rejectIfNullOrEmpty(name, new CourseServiceException(EMPTY_NAME));
        checker.rejectIfNullOrEmpty(detail, new CourseServiceException(EMPTY_DETAIL));
        checker.rejectIfNullOrEmpty(imgUrl, new CourseServiceException(EMPTY_IMG_URL));
        checker.rejectIfNullOrEmpty(categoryId, new CourseServiceException(EMPTY_CATEGORY_ID));
        checker.rejectIfNullOrEmpty(price, new CourseServiceException(EMPTY_PRICE));

        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CourseServiceException(INVALID_ENTERPRISE_ID));
        long categoryIdLong = checker.parseUnsignedLong(categoryId, new CourseServiceException(INVALID_CATEGORY_ID));
        BigDecimal priceBd = checker.parseUnsignedBigDecimal(price, new CourseServiceException(INVALID_PRICE));

        // Check if the enterprise exists
        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CourseServiceException(ENTERPRISE_NOT_EXISTING);
        // Check if the category exists
        isExisting = courseCategoryDao.checkExistenceById(categoryIdLong);
        if (!isExisting)
            throw new CourseServiceException(CATEGORY_NOT_EXISTING);
        // Insert
        Course course = new Course();
        course.setEnterpriseId(enterpriseIdLong);
        course.setName(name);
        course.setDetail(detail);
        course.setImgUrl(imgUrl);
        course.setCategoryId(categoryIdLong);
        course.setReleaseTime(releaseTime);
        course.setPrice(priceBd);
        // TODO a course is currently AVAILABLE by default before the reviewing modules are implemented
        course.setStatus(CourseStatusEnum.AVAILABLE);
        courseDao.insert(course);
        // Fetch the last ID
        long courseId = generalDao.getLastInsertId();
        return courseId;
    }

    @Override
    @Transactional(readOnly = true)
    public Course getById(String courseId) throws CourseServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(courseId, new CourseServiceException(EMPTY_COURSE_ID));
        long courseIdLong = checker.parseUnsignedLong(courseId, new CourseServiceException(INVALID_COURSE_ID));

        // Fetch the course from the database
        Course result = courseDao.getById(courseIdLong);
        // Check if the course exists
        if (result == null)
            throw new CourseServiceException(COURSE_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetail getVoById(String courseId) throws CourseServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(courseId, new CourseServiceException(EMPTY_COURSE_ID));
        long courseIdLong = checker.parseUnsignedLong(courseId, new CourseServiceException(INVALID_COURSE_ID));

        // Fetch the course from the database
        CourseDetail result = courseDao.getVoById(courseIdLong);
        // Check if the course exists
        if (result == null)
            throw new CourseServiceException(COURSE_NOT_EXISTING);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { CourseServiceException.class, SQLException.class })
    public void update(String targetCourseId, String name, String detail, String imgUrl, String categoryId, String releaseTime, String price, String status) throws CourseServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(targetCourseId, new CourseServiceException(EMPTY_COURSE_ID));
        long targetCourseIdLong = checker.parseUnsignedLong(targetCourseId, new CourseServiceException(INVALID_COURSE_ID));

        // Fetch the old course
        Course course = courseDao.getById(targetCourseIdLong);
        if (course == null)
            throw new CourseServiceException(COURSE_NOT_EXISTING);
        if (name != null) {
            if (name.isEmpty())
                throw new CourseServiceException(EMPTY_NAME);
            else
                course.setName(name);
        }
        if (detail != null) {
            if (detail.isEmpty())
                throw new CourseServiceException(EMPTY_DETAIL);
            else
                course.setDetail(detail);
        }
        if (imgUrl != null) {
            if (imgUrl.isEmpty())
                throw new CourseServiceException(EMPTY_IMG_URL);
            else
                course.setImgUrl(imgUrl);
        }
        if (categoryId != null) {
            long categoryIdLong = checker.parseUnsignedLong(categoryId, new CourseServiceException(INVALID_CATEGORY_ID));
            course.setCategoryId(categoryIdLong);
        }
        if (releaseTime != null) {
            long releaseTimeLong = checker.parseUnsignedLong(releaseTime, new CourseServiceException(INVALID_RELEASE_TIME));
            course.setReleaseTime(new Date(releaseTimeLong));
        }
        if (price != null) {
            BigDecimal priceDb = checker.parseUnsignedBigDecimal(price, new CourseServiceException(INVALID_PRICE));
            course.setPrice(priceDb);
        }
        if (status != null) {
            try {
                CourseStatusEnum statusEnum = CourseStatusEnum.valueOf(status);
                course.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new CourseServiceException(INVALID_STATUS);
            }
        }
        // Update
        courseDao.update(course);
    }
}
