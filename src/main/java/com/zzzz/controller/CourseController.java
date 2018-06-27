package com.zzzz.controller;

import com.zzzz.dto.CourseParam;
import com.zzzz.service.CourseService;
import com.zzzz.service.CourseServiceException;
import com.zzzz.vo.CourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * Create a new course.
     * @param enterpriseId The ID of the enterprise to which the course belong
     * @param courseParam name, detail, imgUrl, categoryId, price
     * @return Success: New course ID; Bad request: 400; Internal: 500
     */
    @PostMapping("/enterprise/{id}/course")
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 @RequestBody CourseParam courseParam) {
        // TODO authentication not implemented yet
        try {
            long courseId = courseService.insert(enterpriseId, courseParam.getName(), courseParam.getDetail(), courseParam.getImgUrl(), courseParam.getCategoryId(), new Date(), courseParam.getPrice());
            return ResponseEntity.ok(courseId);
        } catch (CourseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Get the detail of a course.
     * @param courseId Course ID
     * @return Success: Course detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/course/detail/{id}")
    public ResponseEntity getDetailById(@PathVariable("id") String courseId) {
        // TODO authentication not implemented yet
        try {
            CourseDetail result = courseService.getVoById(courseId);
            return ResponseEntity.ok(result);
        } catch (CourseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    @PutMapping("/course/{id}")
    public ResponseEntity update(@PathVariable("id") String targetCourseId,
                                 @RequestBody CourseParam courseParam) {
        // TODO authentication not implemented yet
        try {
            courseService.update(targetCourseId, courseParam.getName(), courseParam.getDetail(), courseParam.getImgUrl(), courseParam.getCategoryId(), courseParam.getReleaseTime(), courseParam.getPrice(), courseParam.getStatus());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CourseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
