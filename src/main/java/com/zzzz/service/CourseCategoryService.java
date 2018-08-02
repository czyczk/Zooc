package com.zzzz.service;

import com.zzzz.po.CourseCategory;

import java.sql.SQLException;
import java.util.List;

public interface CourseCategoryService {
    /**
     * Insert a new course category. The ID of the new created one will be returned.
     * Redis:
     *   Save the new course category
     * @param name Name
     * @return New category ID
     * @throws CourseCategoryServiceException An exception is thrown if the insertion is not successful.
     */
    long insert(String name) throws CourseCategoryServiceException, SQLException;

    /**
     * List all course categories.
     * Redis:
     *   Fetch from the cache first
     *   On missing, get and cache all
     * @return A list of all course categories.
     */
    List<CourseCategory> list() throws SQLException;
}
