package com.zzzz.controller;

import com.zzzz.dto.LecturerParam;
import com.zzzz.po.Lecturer;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    /**
     * Create a new lecturer.
     * @param enterpriseId The ID of the enterprise to which the lecturer belong
     * @param lecturerParam name, photoUrl, introduction
     * @return Success: Lecturer ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value ="/course/{id}/lecturer")
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 @RequestBody LecturerParam lecturerParam) {
        // TODO authentication not implemented yet
        try {
            long lecturerId = lecturerService.insert(enterpriseId, lecturerParam.getName(), lecturerParam.getPhotoUrl(), lecturerParam.getIntroduction());
            return ResponseEntity.ok(lecturerId);
        } catch (LecturerServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Get a lecturer by its ID.
     * @param lecturerId Lecturer ID
     * @return Success: Lecturer; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/lecturer/{id}")
    // TODO authentication not implemented yet
    public ResponseEntity getById(@PathVariable("id") String lecturerId) {
        try {
            Lecturer lecturer = lecturerService.getById(lecturerId);
            return ResponseEntity.ok(lecturer);
        } catch (LecturerServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Update a lecturer. Don't pass in fields meant to be kept unchanged.
     * @param targetLecturerId Target lecturer ID
     * @param lecturerParam name, photoUrl, introduction
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/lecturer/{id}")
    public ResponseEntity update(@PathVariable("id") String targetLecturerId,
                                 @RequestBody LecturerParam lecturerParam) {
        // TODO authentication not implemented yet
        try {
            lecturerService.update(targetLecturerId, lecturerParam.getName(), lecturerParam.getPhotoUrl(), lecturerParam.getIntroduction());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (LecturerServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
