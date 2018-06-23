package com.zzzz.dao;

import com.zzzz.po.Lecturer;

import java.sql.SQLException;

public interface LecturerDao {
    int insert(Lecturer lecturer) throws SQLException;
    boolean checkExistenceById(long lecturerId) throws SQLException;
    Lecturer getById(long lecturerId) throws SQLException;
    int update(Lecturer lecturer) throws SQLException;

    // Cascade when deleted. Implemented later.
}
