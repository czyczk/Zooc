package com.zzzz.service;

import com.zzzz.po.CourseCategory;

import java.sql.SQLException;
import java.util.List;

public interface CourseCategoryService {
    /**
     * Insert a new course category. The ID of the new created one will be returned.
     * @param name Name
     * @return New category ID
     * @throws CourseCategoryServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String name) throws CourseCategoryServiceException, SQLException;
    List<CourseCategory> list() throws SQLException;
}
