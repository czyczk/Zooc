package com.zzzz.controller;

import com.zzzz.dto.CourseOfferingParam;
import com.zzzz.po.CourseOffering;
import com.zzzz.service.CourseOfferingService;
import com.zzzz.service.CourseOfferingServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
public class CourseOfferingController {
    @Autowired
    private CourseOfferingService courseOfferingService;

    /**
     * Create a new offering for a course.
     * @param courseId The ID of the course
     * @param courseOfferingParam branchId, lecturerId
     * @return Success: New offering ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/course/{id}/offering")
    public ResponseEntity<Long> create(@PathVariable("id") String courseId,
                                       @RequestBody CourseOfferingParam courseOfferingParam) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        long courseOfferingId = courseOfferingService.insert(courseId,
                                                             courseOfferingParam.getBranchId(),
                                                             courseOfferingParam.getLecturerId());
        return ResponseEntity.ok(courseOfferingId);
    }

    /**
     * Get a course offering by its ID.
     * @param courseOfferingId Course offering ID
     * @return Success: Course offering; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/offering/{id}")
    public ResponseEntity<CourseOffering> getById(@PathVariable("id") String courseOfferingId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        CourseOffering result = courseOfferingService.getById(courseOfferingId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a course offering. Don't pass in fields meant to be left unchanged.
     * @param targetCourseOfferingId Target course offering ID
     * @param courseOfferingParam courseId, branchId, lecturerId
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/offering/{id}")
    public ResponseEntity update(@PathVariable("id") String targetCourseOfferingId,
                                 @RequestBody CourseOfferingParam courseOfferingParam) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet
        courseOfferingService.update(targetCourseOfferingId, courseOfferingParam.getCourseId(),
                courseOfferingParam.getBranchId(), courseOfferingParam.getLecturerId());
        return ResponseEntity.noContent().build();
    }


}
