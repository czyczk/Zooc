package com.zzzz.controller;

import com.zzzz.dto.CourseOfferingParam;
import com.zzzz.po.CourseOffering;
import com.zzzz.service.CourseOfferingService;
import com.zzzz.service.CourseOfferingServiceException;
import com.zzzz.vo.CourseOfferingDetail;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
public class CourseOfferingController {
    private final CourseOfferingService courseOfferingService;

    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

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
     * Get the detail of a course offering by its ID.
     * @param courseOfferingId Course offering ID
     * @return Success: Course offering detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/offering/detail/{id}")
    public ResponseEntity<CourseOfferingDetail> getDetailById(@PathVariable("id") String courseOfferingId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet

        CourseOfferingDetail result = courseOfferingService.getVoById(courseOfferingId);
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

    /**
     * Delete a course offering.
     * @param courseOfferingId Course offering ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping("/offering/{id}")
    public ResponseEntity delete(@PathVariable("id") String courseOfferingId) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet
        courseOfferingService.delete(courseOfferingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list containing course offerings meeting the requirements.
     * @param courseId The ID of the course to which the offerings belong
     * @param usePagination Use pagination
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param courseOfferingId Course offering ID (optional)
     * @param branchId Branch ID (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param lecturerId Lecturer ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/course/{id}/offering/list")
    public ResponseEntity<ListResult<CourseOfferingDetail>> list(@PathVariable("id") String courseId,
                                                                 String usePagination,
                                                                 String targetPage,
                                                                 String pageSize,
                                                                 String courseOfferingId,
                                                                 String branchId, String branchNameContaining,
                                                                 String lecturerId, String lecturerNameContaining) throws CourseOfferingServiceException, SQLException {
        // TODO authentication not implemented yet
        ListResult<CourseOfferingDetail> result = courseOfferingService.list(usePagination, targetPage, pageSize, courseId, courseOfferingId, branchId, branchNameContaining, lecturerId, lecturerNameContaining);
        return ResponseEntity.ok(result);
    }
}
