package com.zzzz.repo;

import com.zzzz.po.CourseCategory;

import java.util.List;

public interface CourseCategoryRepo {
    /**
     * Save a course category to cache.
     * Triggered when a new course category is added, but not when a single category is read.
     * For the solution to reading a single course category, see {@link #saveAll(List)}
     * @see #saveAll(List)
     * @param courseCategory Course category
     */
    void save(CourseCategory courseCategory);

    /**
     * Save all course categories to cache.
     * Triggered when reading a single category from cache fails
     * or when reading the list of all categories from the cache fails.
     * @param courseCategories All course categories
     */
    void saveAll(List<CourseCategory> courseCategories);

    /**
     * Get a course category from cache by its ID.
     * @param courseCategoryId Course category ID
     * @return Course category
     */
    CourseCategory getById(long courseCategoryId);

    /**
     * Get a course category from cache by its name.
     * @param name Course category name
     * @return Course category
     */
    CourseCategory getByName(String name);

    /**
     * Get all course categories from cache.
     * The design is to make Redis cache all categories,
     * so when this method is called, it should always return
     * all the categories available.
     * @return All available course categories
     */
    List<CourseCategory> getAll();

    /**
     * Check if a course category is cached.
     * @param courseCategoryId Course category ID
     * @return `true` if the category is cached or `false` otherwise
     */
    boolean isCached(long courseCategoryId);
}
