package com.zzzz.dao;

import com.zzzz.po.Course;
import com.zzzz.vo.CourseDetail;

import java.sql.SQLException;

public interface CourseDao {
    long insert(Course course) throws SQLException;
    boolean checkExistenceById(long courseId) throws SQLException;
    Course getById(long courseId) throws SQLException;
    CourseDetail getVoById(long courseId) throws SQLException;
    int update(Course course) throws SQLException;

    // TODO Cascade when deleted.
}
