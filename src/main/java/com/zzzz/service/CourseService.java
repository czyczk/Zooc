package com.zzzz.service;

import com.zzzz.po.Course;
import com.zzzz.vo.CourseDetail;

import java.util.Date;

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
                String price) throws CourseServiceException;

    Course getById(String courseId) throws CourseServiceException;

    CourseDetail getVoById(String courseId) throws CourseServiceException;

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
                String status) throws CourseServiceException;
}
