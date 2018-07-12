package com.zzzz.controller;

import com.zzzz.dto.TrialParam;
import com.zzzz.service.TrialService;
import com.zzzz.service.TrialServiceException;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.TrialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TrialController {
    private final TrialService trialService;

    @Autowired
    public TrialController(TrialService trialService) {
        this.trialService = trialService;
    }

    /**
     * Create new trial.
     * @param branchId The ID of the branch to which the trial belongs
     * @param trialParam name, detail, imgUrl, categoryId, lecturerId
     * @return Success: New trial ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/branch/{id}/trial")
    public ResponseEntity<Long> create(@PathVariable("id") String branchId,
                                       @RequestBody TrialParam trialParam) throws TrialServiceException, SQLException {
        // TODO authentication not implemented yet
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
        // TODO authentication not implemented yet
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
        // TODO authentication not implemented yet
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

    /**
     * Get a list of trials meeting the requirements
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param usePagination Use pagination (`false`) by default
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param trialId Trial ID (optional)
     * @param nameContaining Name containing (optional)
     * @param branchId The ID of the branch to which the trials belong (optional)
     * @param branchNameContaining Branch name containing (optional)
     * @param categoryId Category ID (optional)
     * @param lecturerNameContaining Lecturer name containing (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/trial/list")
    public ResponseEntity<ListResult<TrialDetail>> list(@PathVariable("id") String enterpriseId,
                                                        String usePagination,
                                                        String targetPage,
                                                        String pageSize,
                                                        String trialId,
                                                        String nameContaining,
                                                        String branchId,
                                                        String branchNameContaining,
                                                        String categoryId,
                                                        String lecturerNameContaining,
                                                        String status) throws TrialServiceException, SQLException {
        // TODO authentication not implemented yet
        ListResult<TrialDetail> result = trialService.list(usePagination, targetPage, pageSize,
                enterpriseId, trialId, nameContaining, branchId, branchNameContaining, categoryId, lecturerNameContaining, status);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of N latest available trials of the branch.
     * The actual number of items can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the trials belong
     * @param n The number of items to list
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/trial/latest")
    public ResponseEntity<List<TrialDetail>> list(@PathVariable("id") String enterpriseId,
                                                  String n) throws TrialServiceException, SQLException {
        // TODO authentication not implemented yet
        List<TrialDetail> result = trialService.listLatest(enterpriseId, n);
        return ResponseEntity.ok(result);
    }
}
