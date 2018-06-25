package com.zzzz.controller;

import com.zzzz.po.Lecturer;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    /**
     * Create a new lecturer.
     * @param enterpriseId The ID of the enterprise to which the lecturer belong
     * @param name Name
     * @param photoUrl Photo URL
     * @param introduction Introduction
     * @return Success: Lecturer ID; Bad request: 400; Internal: 500
     */
    @RequestMapping(value ="/course/{id}/lecturer", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 String name,
                                 String photoUrl,
                                 String introduction) {
        // TODO authentication not implemented yet
        try {
            long lecturerId = lecturerService.insert(enterpriseId, name, photoUrl, introduction);
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
    @RequestMapping(value = "/lecturer/{id}", method = RequestMethod.GET)
    // TODO authentication not implemented yet
    public ResponseEntity getById(String lecturerId) {
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
     * @param name New name
     * @param photoUrl New photo URL
     * @param introduction New introduction
     * @return Success: 204; Bad request: 400; Internal: 500
     */
    @RequestMapping(value = "/lecturer/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") String targetLecturerId,
                                 String name,
                                 String photoUrl,
                                 String introduction) {
        // TODO authentication not implemented yet
        try {
            lecturerService.update(targetLecturerId, name, photoUrl, introduction);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (LecturerServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
