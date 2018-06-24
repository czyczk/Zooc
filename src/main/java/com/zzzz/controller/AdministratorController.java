package com.zzzz.controller;

import com.zzzz.service.AdministratorService;
import com.zzzz.service.AdministratorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    /**
     * Create a system account.
     * @param username Username
     * @param password Password
     * @return Success: Administrator ID; Bad request: 400; Internal: 500
     */
    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public ResponseEntity createSystemAccount(String username, String password) {
        return createAccount(username, password);
    }

    /**
     * Create an enterprise account.
     * @param username Username
     * @param password Password
     * @return Success: Administrator ID; Bad request: 400; Internal: 500
     */
    @RequestMapping(value = "/enterprise", method = RequestMethod.POST)
    public ResponseEntity createEnterpriseAccount(String username, String password) {
        return createAccount(username, password);
    }

    private ResponseEntity createAccount(String username, String password) {
        // TODO
        // Authentication not implemented yet.
        try {
            long administratorId = administratorService.createSystemAccount(username, password);
            return ResponseEntity.ok(administratorId);
        } catch (AdministratorServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
