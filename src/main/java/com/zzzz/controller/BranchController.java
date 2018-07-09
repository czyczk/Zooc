package com.zzzz.controller;

import com.zzzz.dto.BranchParam;
import com.zzzz.po.Branch;
import com.zzzz.service.BranchService;
import com.zzzz.service.BranchServiceException;
import com.zzzz.vo.ListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    /**
     * Create a new branch. The ID of the new branch is returned.
     * @param enterpriseId The ID of the enterprise to which the branch belong.
     * @param branchParam name, address, latitude, longitude, telephone
     * @return Success: Branch ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value = "/enterprise/{id}/branch")
    public ResponseEntity<Long> create(@PathVariable("id") String enterpriseId,
                                 @RequestBody BranchParam branchParam) throws BranchServiceException, SQLException {
        // TODO authentication not implemented yet
        long branchId = branchService.insert(enterpriseId, branchParam.getName(), branchParam.getAddress(), branchParam.getLatitude(), branchParam.getLongitude(), branchParam.getTelephone());
        return ResponseEntity.ok(branchId);
    }

    /**
     * Get a branch by its ID.
     * @param branchId The target branch ID
     * @return Success: Branch; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/branch/{id}")
    public ResponseEntity<Branch> getById(@PathVariable("id") String branchId) throws BranchServiceException, SQLException {
        // TODO authentication not implemented yet
        Branch branch = branchService.getById(branchId);
        return ResponseEntity.ok(branch);
    }

    /**
     * Update a branch. Don't pass in fields meant to be left unchanged.
     * @param targetBranchId Target branch ID
     * @param branchParam name, address, latitude, longitude, telephone
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/branch/{id}")
    public ResponseEntity update(@PathVariable("id") String targetBranchId,
                                 @RequestBody BranchParam branchParam) throws BranchServiceException, SQLException {
        // TODO authentication not implemented yet
        branchService.update(targetBranchId, branchParam.getName(), branchParam.getAddress(), branchParam.getLatitude(), branchParam.getLongitude(), branchParam.getTelephone());
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a branch. The branch will be disabled and no more interactions can be made to it.
     * @param branchId Branch ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping(value = "/branch/{id}")
    public ResponseEntity delete(@PathVariable("id") String branchId) throws BranchServiceException, SQLException {
        // TODO authentication not implemented yet
        branchService.disable(branchId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list containing items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the branch belong
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (Required when using pagination)
     * @param pageSize Page size (Required when using pagination)
     * @param branchId Branch ID (Optional)
     * @param nameContaining Name containing (Optional)
     * @param addressContaining Address containing (Optional)
     * @return Success: list; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/enterprise/{id}/branch/list")
    public ResponseEntity<ListResult<Branch>> list(@PathVariable("id") String enterpriseId,
                                                   String usePagination,
                                                   String targetPage,
                                                   String pageSize,
                                                   String branchId,
                                                   String nameContaining,
                                                   String addressContaining) throws BranchServiceException, SQLException {
        // TODO authentication not implemented yet
        ListResult<Branch> result = branchService.list(usePagination, targetPage, pageSize, enterpriseId, branchId, nameContaining, addressContaining);
        return ResponseEntity.ok(result);
    }
}
