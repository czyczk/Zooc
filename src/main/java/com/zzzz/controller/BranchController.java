package com.zzzz.controller;

import com.zzzz.po.Branch;
import com.zzzz.service.BranchService;
import com.zzzz.service.BranchServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;

    /**
     * Create a new branch. The ID of the new branch is returned.
     * @param enterpriseId The ID of the enterprise to which the branch belong.
     * @param name Name
     * @param address Address
     * @param latitude Latitude
     * @param longitude Longitude
     * @param telephone Telephone
     * @return Success: Branch ID; Bad request: 400; Internal: 500
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(String enterpriseId,
                                 String name,
                                 String address,
                                 String latitude,
                                 String longitude,
                                 String telephone) {
        // TODO authentication not implemented yet
        try {
            long branchId = branchService.insert(enterpriseId, name, address, latitude, longitude, telephone);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable("id")String branchId) {
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
     * @param name New name
     * @param address New address
     * @param latitude New latitude
     * @param longitude New longitude
     * @param telephone New telephone
     * @return Success: 203; Bad request: 400; Internal: 500
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(String targetBranchId,
                                 String name,
                                 String address,
                                 String latitude,
                                 String longitude,
                                 String telephone) {
        // TODO authentication not implemented yet
        try {
            branchService.update(targetBranchId, name, address, latitude, longitude, telephone);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BranchServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
