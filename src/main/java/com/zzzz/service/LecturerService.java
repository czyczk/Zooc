package com.zzzz.service;

import com.zzzz.po.Lecturer;
import com.zzzz.vo.ListResult;

import java.sql.SQLException;

public interface LecturerService {
    /**
     * Create a new lecturer
     * @param enterpriseId The ID of the enterprise to which the lecturer belong
     * @param name Name
     * @param photoUrl Photo URL
     * @param introduction Introduction
     * @return The ID of the new lecturer
     * @throws LecturerServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String enterpriseId, String name, String photoUrl, String introduction) throws LecturerServiceException, SQLException;

    boolean checkExistenceById(String lecturerId) throws LecturerServiceException, SQLException;

    Lecturer getById(String lecturerId) throws LecturerServiceException, SQLException;

    /**
     * Update a lecturer. Fields will be left unchanged if the corresponding parameter is null.
     * @param targetLecturerId Target lecturer ID
     * @param name New name
     * @param photoUrl New photo URL
     * @param introduction New introduction
     * @throws LecturerServiceException An exception is thrown if the update is unsuccessful.
     */
    void update(String targetLecturerId, String name, String photoUrl, String introduction) throws LecturerServiceException, SQLException;

    /**
     * Disable a lecturer. The lecturer will be invisible from now on.
     * @param lecturerId Lecturer ID
     * @throws LecturerServiceException An exception is thrown if the operation is unsuccessful.
     */
    void disable(String lecturerId) throws LecturerServiceException, SQLException;

    /**
     * Get all items meeting the requirements.
     * @param usePagination Use pagination or not
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the lecturer belongs
     * @param lecturerId Lecturer ID (optional)
     * @param name Name (optional)
     * @return All items meeting the requirements
     * @throws LecturerServiceException An exception is thrown if the query is unsuccessful.
     */
    ListResult<Lecturer> list(String usePagination,
                              String targetPage,
                              String pageSize,
                              String enterpriseId,
                              String lecturerId,
                              String name) throws LecturerServiceException, SQLException;
}
