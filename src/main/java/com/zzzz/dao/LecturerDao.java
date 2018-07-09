package com.zzzz.dao;

import com.zzzz.po.Lecturer;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface LecturerDao {
    int insert(Lecturer lecturer) throws SQLException;
    boolean checkExistenceById(long lecturerId) throws SQLException;
    Lecturer getById(long lecturerId) throws SQLException;
    int update(Lecturer lecturer) throws SQLException;

    /**
     * Count the total number of items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the lecturer belongs
     * @param lecturerId Lecturer ID (optional)
     * @param name Name (optional)
     * @return The total number of items meeting the requirements.
     */
    long countTotal(@Param("enterpriseId") long enterpriseId,
                    @Param("lecturerId") Long lecturerId,
                    @Param("name") String name) throws SQLException;

    /**
     * Query all items meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the lecturer belongs
     * @param lecturerId Lecturer ID (optional)
     * @param name Name (optional)
     * @return A list containing all items meeting the requirements
     */
    List<Lecturer> list(@Param("usePagination") boolean usePagination,
                        @Param("starting") Long starting,
                        @Param("pageSize") Long pageSize,
                        @Param("enterpriseId") long enterpriseId,
                        @Param("lecturerId") Long lecturerId,
                        @Param("name") String name) throws SQLException;
}
