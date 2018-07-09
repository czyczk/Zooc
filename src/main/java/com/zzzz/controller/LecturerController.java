package com.zzzz.controller;

import com.zzzz.dto.LecturerParam;
import com.zzzz.po.Lecturer;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
public class LecturerController {
    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    /**
     * Create a new lecturer.
     * @param enterpriseId The ID of the enterprise to which the lecturer belong
     * @param lecturerParam name, photoUrl, introduction
     * @return Success: Lecturer ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value ="/course/{id}/lecturer")
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 @RequestBody LecturerParam lecturerParam) throws SQLException, LecturerServiceException {
        // TODO authentication not implemented yet
        long lecturerId = lecturerService.insert(enterpriseId, lecturerParam.getName(), lecturerParam.getPhotoUrl(), lecturerParam.getIntroduction());
        return ResponseEntity.ok(lecturerId);
    }

    /**
     * Get a lecturer by its ID.
     * @param lecturerId Lecturer ID
     * @return Success: Lecturer; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/lecturer/{id}")
    // TODO authentication not implemented yet
    public ResponseEntity<Lecturer> getById(@PathVariable("id") String lecturerId) throws SQLException, LecturerServiceException {
        Lecturer lecturer = lecturerService.getById(lecturerId);
        return ResponseEntity.ok(lecturer);
    }

    /**
     * Update a lecturer. Don't pass in fields meant to be kept unchanged.
     * @param targetLecturerId Target lecturer ID
     * @param lecturerParam name, photoUrl, introduction
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/lecturer/{id}")
    public ResponseEntity update(@PathVariable("id") String targetLecturerId,
                                 @RequestBody LecturerParam lecturerParam) throws SQLException, LecturerServiceException {
        // TODO authentication not implemented yet
        lecturerService.update(targetLecturerId, lecturerParam.getName(), lecturerParam.getPhotoUrl(), lecturerParam.getIntroduction());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/lecturer/{id}")
    public ResponseEntity delete(@PathVariable("id") String lecturerId) throws SQLException, LecturerServiceException {
        // TODO authentication not implemented yet
        lecturerService.disable(lecturerId);
        return ResponseEntity.noContent().build();
    }
}
