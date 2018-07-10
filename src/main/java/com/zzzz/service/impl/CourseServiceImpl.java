package com.zzzz.service.impl;

import com.zzzz.dao.CourseCategoryDao;
import com.zzzz.dao.CourseDao;
import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.po.Course;
import com.zzzz.po.CourseStatusEnum;
import com.zzzz.service.CourseService;
import com.zzzz.service.CourseServiceException;
import com.zzzz.service.util.PaginationUtil;
import com.zzzz.service.util.ParameterChecker;
import com.zzzz.vo.CourseDetail;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.zzzz.service.CourseServiceException.ExceptionTypeEnum.*;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;
    private final EnterpriseDao enterpriseDao;
    private final CourseCategoryDao courseCategoryDao;
    private final GeneralDao generalDao;
    private final ParameterChecker<CourseServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CourseServiceImpl(CourseDao courseDao, EnterpriseDao enterpriseDao, CourseCategoryDao courseCategoryDao, GeneralDao generalDao) {
        this.courseDao = courseDao;
        this.enterpriseDao = enterpriseDao;
        this.courseCategoryDao = courseCategoryDao;
        this.generalDao = generalDao;
    }

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

    @Override
    @Transactional(readOnly = true)
    public ListResult<CourseDetail> list(String usePagination, String targetPage, String pageSize, String enterpriseId, String courseId, String nameContaining, String categoryId, String priceMin, String priceMax, String status) throws CourseServiceException, SQLException {
        ListResult<CourseDetail> result = new ListResult<>();

        // Check if the parameters are valid
        boolean usePaginationBool = Boolean.parseBoolean(usePagination);
        if (usePaginationBool) {
            checker.rejectIfNullOrEmpty(targetPage, new CourseServiceException(EMPTY_TARGET_PAGE));
            checker.rejectIfNullOrEmpty(pageSize, new CourseServiceException(EMPTY_PAGE_SIZE));
        }
        checker.rejectIfNullOrEmpty(enterpriseId, new CourseServiceException(EMPTY_ENTERPRISE_ID));

        // Required fields
        Long targetPageLong = null;
        Long pageSizeLong = null;
        if (usePaginationBool) {
            targetPageLong = checker.parsePositiveLong(targetPage, new CourseServiceException(INVALID_TARGET_PAGE));
            pageSizeLong = checker.parsePositiveLong(pageSize, new CourseServiceException(INVALID_PAGE_SIZE));
        }
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CourseServiceException(INVALID_ENTERPRISE_ID));
        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CourseServiceException(ENTERPRISE_NOT_EXISTING);

        // Optional fields
        Long courseIdLong = null;
        if (courseId != null && !courseId.isEmpty())
            courseIdLong = checker.parseUnsignedLong(courseId, new CourseServiceException(INVALID_COURSE_ID));
        if (nameContaining != null && nameContaining.isEmpty())
            nameContaining = null;
        Long categoryIdLong = null;
        if (categoryId != null && !categoryId.isEmpty())
            categoryIdLong = checker.parseUnsignedLong(categoryId, new CourseServiceException(INVALID_CATEGORY_ID));
        BigDecimal priceMinBd = null;
        BigDecimal priceMaxBd = null;
        if (priceMin != null && !priceMin.isEmpty())
            priceMinBd = checker.parseUnsignedBigDecimal(priceMin, new CourseServiceException(INVALID_PRICE_RANGE));
        if (priceMax != null && !priceMax.isEmpty())
            priceMaxBd = checker.parseUnsignedBigDecimal(priceMax, new CourseServiceException(INVALID_PRICE_RANGE));
        CourseStatusEnum statusEnum = null;
        if (status != null) {
            if (status.isEmpty())
                status = null;
            else {
                try {
                    statusEnum = CourseStatusEnum.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new CourseServiceException(INVALID_STATUS);
                }
            }
        }

        // Process pagination info
        Long starting = null;
        if (usePaginationBool) {
            long totalNumItems = courseDao.countTotal(enterpriseIdLong, courseIdLong, nameContaining, categoryIdLong, priceMinBd, priceMaxBd, statusEnum);
            starting = PaginationUtil.getStartingIndex(targetPageLong, pageSizeLong, totalNumItems, result);

            // If the starting index exceeds the total number of items,
            // return a list result with an empty list
            if (starting == -1)
                return result;
        }

        List<CourseDetail> list = courseDao.list(usePaginationBool, starting, pageSizeLong, enterpriseIdLong, courseIdLong, nameContaining, categoryIdLong, priceMinBd, priceMaxBd, statusEnum);
        result.setList(list);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDetail> listLatest(String enterpriseId, String n) throws CourseServiceException, SQLException {
        // Check if the ID is valid
        checker.rejectIfNullOrEmpty(enterpriseId, new CourseServiceException(EMPTY_ENTERPRISE_ID));
        long enterpriseIdLong = checker.parseUnsignedLong(enterpriseId, new CourseServiceException(INVALID_ENTERPRISE_ID));
        int nInt = checker.parsePositiveInt(n, new CourseServiceException(INVALID_LATEST_NUMBER));

        // Check if the enterprise exists
        boolean isExisting = enterpriseDao.checkExistenceById(enterpriseIdLong);
        if (!isExisting)
            throw new CourseServiceException(ENTERPRISE_NOT_EXISTING);

        // Get the most recent N items
        List<CourseDetail> result = courseDao.listLatest(enterpriseIdLong, nInt);
        return result;
    }
}
