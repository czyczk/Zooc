package com.zzzz.service;

import com.zzzz.po.CourseOffering;
import com.zzzz.vo.CourseOfferingDetail;
import com.zzzz.vo.ListResult;

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

    /**
     * Get a course offering by its ID.
     * @param courseOfferingId Course offering ID
     * @return The course offering
     * @throws CourseOfferingServiceException An exception is thrown if the query is unsuccessful.
     */
    CourseOffering getById(String courseOfferingId) throws CourseOfferingServiceException, SQLException;

    /**
     * Get the detail (VO) of a course offering by its ID.
     * @param courseOfferingId Course offering ID
     * @return The detail (VO) of the course offering
     * @throws CourseOfferingServiceException An exception is thrown if the query is unsuccessful.
     */
    CourseOfferingDetail getVoById(String courseOfferingId) throws CourseOfferingServiceException, SQLException;

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

    /**
     * Get a list of course offerings meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param courseId The ID of the course to which the offering belong
     * @param courseOfferingId Course offering ID (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param lecturerId Lecturer ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @return A list containing course offerings meeting the requirements
     * @throws CourseOfferingServiceException An exception is thrown if the query is unsuccessful.
     */
    ListResult<CourseOfferingDetail> list(String usePagination,
                                          String targetPage, String pageSize,
                                          String courseId,
                                          String courseOfferingId,
                                          String branchId, String branchNameContaining,
                                          String lecturerId, String lecturerNameContaining)
            throws CourseOfferingServiceException, SQLException;
}
