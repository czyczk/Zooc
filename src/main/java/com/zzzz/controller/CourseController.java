package com.zzzz.controller;

import com.zzzz.dto.CourseParam;
import com.zzzz.service.CourseService;
import com.zzzz.service.CourseServiceException;
import com.zzzz.vo.CourseDetail;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Create a new course.
     * @param enterpriseId The ID of the enterprise to which the course belong
     * @param courseParam name, detail, imgUrl, categoryId, price
     * @return Success: New course ID; Bad request: 400; Internal: 500
     */
    @PostMapping("/enterprise/{id}/course")
    public ResponseEntity<Long> create(@PathVariable("id") String enterpriseId,
                                 @RequestBody CourseParam courseParam) throws SQLException, CourseServiceException {
        // TODO authentication not implemented yet
        long courseId = courseService.insert(enterpriseId, courseParam.getName(), courseParam.getDetail(), courseParam.getImgUrl(), courseParam.getCategoryId(), new Date(), courseParam.getPrice());
        return ResponseEntity.ok(courseId);
    }

    /**
     * Get the detail of a course.
     * @param courseId Course ID
     * @return Success: Course detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/course/detail/{id}")
    public ResponseEntity<CourseDetail> getDetailById(@PathVariable("id") String courseId) throws SQLException, CourseServiceException {
        // TODO authentication not implemented yet
        CourseDetail result = courseService.getVoById(courseId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a course. Don't pass in fields meant to be left unchanged.
     * @param targetCourseId Target course ID
     * @param courseParam name, detail, imgUrl, categoryId, releaseTime, price, status
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/course/{id}")
    public ResponseEntity update(@PathVariable("id") String targetCourseId,
                                 @RequestBody CourseParam courseParam) throws SQLException, CourseServiceException {
        // TODO authentication not implemented yet
        courseService.update(targetCourseId, courseParam.getName(), courseParam.getDetail(), courseParam.getImgUrl(), courseParam.getCategoryId(), courseParam.getReleaseTime(), courseParam.getPrice(), courseParam.getStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Get a list containing all items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param courseId Course ID (optional)
     * @param nameContaining Name containing (optional)
     * @param categoryId Category ID (optional)
     * @param priceMin Lower bound of the price range (optional)
     * @param priceMax Upper bound of the price range (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/course/list")
    public ResponseEntity<ListResult<CourseDetail>> list(@PathVariable("id") String enterpriseId,
                                                         String usePagination, String targetPage, String pageSize,
                                                         String courseId, String nameContaining, String categoryId,
                                                         String priceMin, String priceMax,
                                                         String status) throws SQLException, CourseServiceException {
        // TODO authentication not implemented yet
        ListResult<CourseDetail> result = courseService.list(usePagination, targetPage, pageSize, enterpriseId, courseId, nameContaining, categoryId, priceMin, priceMax, status);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of the most recent N available courses of the enterprise.
     * The actual number of items can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param n The number of most recent items to list (positive integer)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/course/recent")
    public ResponseEntity<List<CourseDetail>> listRecent(@PathVariable("id") String enterpriseId,
                                                               String n) throws SQLException, CourseServiceException {
        // TODO authentication not implemented yet
        List<CourseDetail> result = courseService.listLatest(enterpriseId, n);
        return ResponseEntity.ok(result);
    }
}
