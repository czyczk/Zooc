package com.zzzz.controller;

import com.zzzz.dto.AdministratorParam;
import com.zzzz.po.Administrator;
import com.zzzz.service.AdministratorService;
import com.zzzz.service.AdministratorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    /**
     * Create a system account.
     * @param administratorParam username, password
     * @return Success: Administrator ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value = "/system")
    public ResponseEntity<Long> createSystemAccount(@RequestBody AdministratorParam administratorParam) throws SQLException, AdministratorServiceException {
        return createAccount(administratorParam.getUsername(), administratorParam.getPassword());
    }

    /**
     * Create an enterprise account.
     * @param administratorParam username, password
     * @return Success: Administrator ID; Bad request: 400; Internal: 500
     */
    @PostMapping(value = "/enterprise")
    public ResponseEntity<Long> createEnterpriseAccount(@RequestBody AdministratorParam administratorParam) throws SQLException, AdministratorServiceException {
        return createAccount(administratorParam.getUsername(), administratorParam.getPassword());
    }

    private ResponseEntity<Long> createAccount(String username, String password) throws SQLException, AdministratorServiceException {
        // TODO Authentication not implemented yet.
        long administratorId = administratorService.createSystemAccount(username, password);
        return ResponseEntity.ok(administratorId);
    }


    /**
     * Get an administrator by its ID.
     * @param administratorId Administrator ID
     * @return Success: Administrator; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getById(@PathVariable("id") String administratorId) throws AdministratorServiceException, SQLException {
        // TODO Authentication not implemented yet.
        Administrator result = administratorService.getById(administratorId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update an administrator. Don't pass in parameters meant to be left unchanged.
     * @param targetAdministratorId Target administrator ID
     * @param administratorParam username, password
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String targetAdministratorId,
                                 @RequestBody AdministratorParam administratorParam) throws AdministratorServiceException, SQLException {
        // TODO Authentication not implemented yet.
        administratorService.update(targetAdministratorId, administratorParam.getUsername(), administratorParam.getPassword());
        return ResponseEntity.noContent().build();
    }

    /**
     * Log in an administrator account.
     * @param administratorParam administratorId, password
     * @return Success: Administrator without its password; Bad request: 400; Incorrect password: 401; Administrator not found: 404; Internal: 500
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Administrator> logIn(HttpServletRequest req,
                                               @RequestBody AdministratorParam administratorParam
    ) throws SQLException, AdministratorServiceException {
        Administrator result = administratorService.logIn(administratorParam.getAdministratorId(), administratorParam.getPassword());
        HttpSession session = req.getSession();
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("administratorId", administratorParam.getAdministratorId());
        return ResponseEntity.ok(result);
    }
}
