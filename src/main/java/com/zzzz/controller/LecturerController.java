package com.zzzz.controller;

import com.zzzz.dto.LecturerParam;
import com.zzzz.po.Lecturer;
import com.zzzz.service.LecturerService;
import com.zzzz.service.LecturerServiceException;
import com.zzzz.vo.ListResult;
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
    @PostMapping(value ="/enterprise/{id}/lecturer")
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

    /**
     * Delete a lecturer. The lecturer will be disabled and no interactions can be made to it.
     * @param lecturerId Lecturer ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping(value = "/lecturer/{id}")
    public ResponseEntity delete(@PathVariable("id") String lecturerId) throws SQLException, LecturerServiceException {
        // TODO authentication not implemented yet
        lecturerService.disable(lecturerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list containing items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the lecturer belongs
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param lecturerId Lecturer ID (optional)
     * @param name Name (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/enterprise/{id}/lecturer/list")
    public ResponseEntity<ListResult<Lecturer>> list(@PathVariable("id") String enterpriseId,
                                                     String usePagination,
                                                     String targetPage,
                                                     String pageSize,
                                                     String lecturerId,
                                                     String name) throws LecturerServiceException, SQLException {
        // TODO authentication not implemented yet
        ListResult<Lecturer> result = lecturerService.list(usePagination, targetPage, pageSize, enterpriseId, lecturerId, name);
        return ResponseEntity.ok(result);
    }
}
