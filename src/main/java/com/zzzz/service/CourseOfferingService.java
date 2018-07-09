package com.zzzz.service;

import com.zzzz.po.CourseOffering;

import java.sql.SQLException;

public interface CourseOfferingService {
    /**
     * Insert a new course offering.
     * @param courseId The ID of the course to which the offering belongs
     * @param branchId Branch ID
     * @param lecturerId Lecturer ID
     * @return The ID of the new offering
     * @throws CourseOfferingServiceException An exception is thrown if the insertion is unsuccessful.
     */
    long insert(String courseId,
                String branchId,
                String lecturerId) throws CourseOfferingServiceException, SQLException;

    CourseOffering getById(String courseOfferingId) throws CourseOfferingServiceException, SQLException;

    /**
     * Update a course offering. A field will be left unchanged if the corresponding parameter is null.
     * @param targetCourseOfferingId The target course offering ID
     * @param courseId New course ID
     * @param branchId New branch ID
     * @param lecturerId New lecturer ID
     * @throws CourseOfferingServiceException An exception is thrown if the update is unsuccessful.
     */
    void update(String targetCourseOfferingId,
                String courseId,
                String branchId,
                String lecturerId) throws CourseOfferingServiceException, SQLException;

    /**
     * Delete a course offering.
     * @param courseOfferingId Course offering ID
     * @throws CourseOfferingServiceException An exception is thrown if the deletion is unsuccessful.
     */
    void delete(String courseOfferingId) throws CourseOfferingServiceException, SQLException;
}
