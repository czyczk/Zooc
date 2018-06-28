package com.zzzz.controller;

import com.zzzz.service.AdministratorService;
import com.zzzz.service.AdministratorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

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
    public ResponseEntity<Long> createSystemAccount(String username, String password) throws SQLException, AdministratorServiceException {
        return createAccount(username, password);
    }

    /**
     * Create an enterprise account.
     * @param username Username
     * @param password Password
     * @return Success: Administrator ID; Bad request: 400; Internal: 500
     */
    @RequestMapping(value = "/enterprise", method = RequestMethod.POST)
    public ResponseEntity<Long> createEnterpriseAccount(String username, String password) throws SQLException, AdministratorServiceException {
        return createAccount(username, password);
    }

    private ResponseEntity<Long> createAccount(String username, String password) throws SQLException, AdministratorServiceException {
        // TODO
        // Authentication not implemented yet.
        long administratorId = administratorService.createSystemAccount(username, password);
        return ResponseEntity.ok(administratorId);
    }
}
