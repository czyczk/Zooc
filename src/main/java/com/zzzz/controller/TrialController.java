package com.zzzz.controller;

import com.zzzz.dto.TrialParam;
import com.zzzz.service.TrialService;
import com.zzzz.service.TrialServiceException;
import com.zzzz.vo.TrialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class TrialController {
    @Autowired private TrialService trialService;

    /**
     * Create new trial.
     * @param branchId The ID of the branch to which the trial belongs
     * @param trialParam name, detail, imgUrl, categoryId, lecturerId
     * @return Success: New trial ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/branch/{id}/trial")
    public ResponseEntity<Long> create(@PathVariable("id") String branchId,
                                       @RequestBody TrialParam trialParam) throws TrialServiceException, SQLException {
        long lastId = trialService.insert(trialParam.getName(), trialParam.getDetail(),
                trialParam.getImgUrl(), trialParam.getCategoryId(), branchId,
                trialParam.getLecturerId(), new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get the detail of a trial.
     * @param trialId Trial ID
     * @return Success: Trial detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/trial/detail/{id}")
    public ResponseEntity<TrialDetail> getDetailById(@PathVariable("id") String trialId) throws TrialServiceException, SQLException {
        TrialDetail result = trialService.getVoById(trialId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a trial. Don't pass in fields meant to be left unchanged.
     * @param targetTrialId Target trial ID
     * @param trialParam name, detail, imgUrl, categoryId, branchId, lecturerId, releaseTime, status
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/trial/{id}")
    public ResponseEntity update(@PathVariable("id") String targetTrialId,
                                 @RequestBody TrialParam trialParam) throws TrialServiceException, SQLException {
        trialService.update(targetTrialId,
                trialParam.getName(),
                trialParam.getDetail(),
                trialParam.getImgUrl(),
                trialParam.getCategoryId(),
                trialParam.getBranchId(),
                trialParam.getLecturerId(),
                trialParam.getReleaseTime(),
                trialParam.getStatus());
        return ResponseEntity.noContent().build();
    }
}
