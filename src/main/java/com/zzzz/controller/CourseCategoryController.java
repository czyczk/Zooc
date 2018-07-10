package com.zzzz.controller;

import com.zzzz.po.CourseCategory;
import com.zzzz.service.CourseCategoryService;
import com.zzzz.service.CourseCategoryServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CourseCategoryController {
    private final CourseCategoryService courseCategoryService;

    @Autowired
    public CourseCategoryController(CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }

    /**
     * Create a new course category.
     * @param name Name
     * @return Success: New category ID; Bad request: 400; Internal: 500
     */
    @PostMapping
    public ResponseEntity<Long> create(String name) throws CourseCategoryServiceException, SQLException {
        Long lastId = courseCategoryService.insert(name);
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get a list of all course categories.
     * @return Success: List; Internal: 500
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<CourseCategory>> list() throws SQLException {
        return ResponseEntity.ok(courseCategoryService.list());
    }
}
