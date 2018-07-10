package com.zzzz.dao;

import com.zzzz.po.CourseOffering;
import com.zzzz.vo.CourseOfferingDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface CourseOfferingDao {
    int insert(CourseOffering courseOffering) throws SQLException;
    boolean checkExistenceById(long courseOfferingId) throws SQLException;
    CourseOffering getById(long courseOfferingId) throws SQLException;
    CourseOfferingDetail getVoById(long courseOfferingId) throws SQLException;
    int update(CourseOffering courseOffering) throws SQLException;
    int delete(long courseOfferingId) throws SQLException;

    /**
     * Count the number of course offerings meeting the requirements.
     * @param courseId The ID of the course to which the offerings belong
     * @param courseOfferingId Course offering ID (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param lecturerId Lecturer ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @return The number of course offerings meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    long countTotal(@Param("courseId") long courseId,
                    @Param("courseOfferingId") Long courseOfferingId,
                    @Param("branchId") Long branchId,
                    @Param("branchNameContaining") String branchNameContaining,
                    @Param("lecturerId") Long lecturerId,
                    @Param("lecturerNameContaining") String lecturerNameContaining) throws SQLException;

    /**
     * Get a list containing course offerings meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param courseId The ID of the course to which the offerings belong
     * @param courseOfferingId Course offering ID (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param lecturerId Lecturer ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @return A list containing course offerings meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<CourseOffering> list(@Param("usePagination") boolean usePagination,
                              @Param("starting") Long starting,
                              @Param("pageSize") Long pageSize,
                              @Param("courseId") long courseId,
                              @Param("courseOfferingId") Long courseOfferingId,
                              @Param("branchId") Long branchId,
                              @Param("branchNameContaining") String branchNameContaining,
                              @Param("lecturerId") Long lecturerId,
                              @Param("lecturerNameContaining") String lecturerNameContaining) throws SQLException;
}
