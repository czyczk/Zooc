package com.zzzz.service;

import com.zzzz.po.Course;
import com.zzzz.vo.CourseDetail;
import com.zzzz.vo.ListResult;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CourseService {
    /**
     * Insert a new course.
     * @param enterpriseId The ID of the enterprise to which the course belong
     * @param name Name
     * @param detail Detail
     * @param imgUrl Image URL
     * @param categoryId Category ID
     * @param releaseTime Release time (from controllers)
     * @param price Price
     * @return The ID of the new course
     * @throws CourseServiceException An exception is thrown if the insertion is unsuccessful.
     */
    long insert(String enterpriseId,
                String name,
                String detail,
                String imgUrl,
                String categoryId,
                Date releaseTime,
                String price) throws CourseServiceException, SQLException;

    Course getById(String courseId) throws CourseServiceException, SQLException;

    CourseDetail getVoById(String courseId) throws CourseServiceException, SQLException;

    /**
     * Update a course. A field will be left unchanged if the corresponding parameter is null.
     * @param targetCourseId The target course ID
     * @param name New name
     * @param detail New detail
     * @param imgUrl New image URL
     * @param categoryId New category ID
     * @param releaseTime New release time
     * @param price New price
     * @param status New status
     * @throws CourseServiceException An exception is thrown if the update is unsuccessful.
     */
    void update(String targetCourseId,
                String name,
                String detail,
                String imgUrl,
                String categoryId,
                String releaseTime,
                String price,
                String status) throws CourseServiceException, SQLException;

    /**
     * Fetch all items meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param courseId Course ID (optional)
     * @param nameContaining Name containing (optional)
     * @param categoryId Category ID (optional)
     * @param priceMin Lower bound of the price range (optional)
     * @param priceMax Upper bound of the price range (optional)
     * @param status Status (optional)
     * @return A list containing all items meeting the requirements
     * @throws CourseServiceException An exception is thrown if the query is not successful.
     */
    ListResult<CourseDetail> list(String usePagination,
                                  String targetPage,
                                  String pageSize,
                                  String enterpriseId,
                                  String courseId,
                                  String nameContaining,
                                  String categoryId,
                                  String priceMin,
                                  String priceMax,
                                  String status) throws CourseServiceException, SQLException;

    /**
     * List the latest N available courses of the enterprise.
     * The actual number of result can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param n The number of items to list
     * @return The latest N available courses
     * @throws CourseServiceException An exception is thrown if the query is not successful.
     */
    List<CourseDetail> listLatest(String enterpriseId, String n) throws CourseServiceException, SQLException;
}
