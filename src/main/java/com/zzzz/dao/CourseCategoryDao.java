package com.zzzz.dao;

import com.zzzz.po.CourseCategory;

import java.sql.SQLException;
import java.util.List;

public interface CourseCategoryDao {
    int insert(CourseCategory courseCategory) throws SQLException;
    boolean checkExistenceById(long categoryId) throws SQLException;
    boolean checkExistenceByName(String name) throws SQLException;
    CourseCategory getById(long categoryId) throws SQLException;
    int update(CourseCategory courseCategory) throws SQLException;
    List<CourseCategory> list() throws SQLException;
}
