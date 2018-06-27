package com.zzzz.controller;

import com.zzzz.dto.BranchParam;
import com.zzzz.po.Branch;
import com.zzzz.service.BranchService;
import com.zzzz.service.BranchServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BranchController {
    @Autowired
    private BranchService branchService;

    /**
     * Create a new branch. The ID of the new branch is returned.
     * @param enterpriseId The ID of the enterprise to which the branch belong.
     * @param branchParam name, address, latitude, longitude, telephone
     * @return Success: Branch ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value = "/course/{id}/branch")
    public ResponseEntity create(@PathVariable("id") String enterpriseId,
                                 @RequestBody BranchParam branchParam) {
        // TODO authentication not implemented yet
        try {
            long branchId = branchService.insert(enterpriseId, branchParam.getName(), branchParam.getAddress(), branchParam.getLatitude(), branchParam.getLongitude(), branchParam.getTelephone());
            return ResponseEntity.ok(branchId);
        } catch (BranchServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Get a branch by its ID.
     * @param branchId The target branch ID
     * @return Success: Branch; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/branch/{id}")
    public ResponseEntity getById(@PathVariable("id") String branchId) {
        // TODO authentication not implemented yet
        try {
            Branch branch = branchService.getById(branchId);
            return ResponseEntity.ok(branch);
        } catch (BranchServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Update a branch. Don't pass in fields meant to be left unchanged.
     * @param targetBranchId Target branch ID
     * @param branchParam name, address, latitude, longitude, telephone
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/branch/{id}")
    public ResponseEntity update(@PathVariable("id") String targetBranchId,
                                 @RequestBody BranchParam branchParam) {
        // TODO authentication not implemented yet
        try {
            branchService.update(targetBranchId, branchParam.getName(), branchParam.getAddress(), branchParam.getLatitude(), branchParam.getLongitude(), branchParam.getTelephone());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BranchServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
