package com.zzzz.service;

import com.zzzz.po.Administrator;

import java.sql.SQLException;

public interface AdministratorService {
    /**
     * Create a new system administrator account with the username and the password given.
     * @param username Username
     * @param password Password
     * @return The ID of the new account
     * @throws AdministratorServiceException An exception is thrown if the creation is unsuccessful.
     */
    long createSystemAccount(String username, String password) throws AdministratorServiceException, SQLException;

    /**
     * Create a new enterprise account with the username and the password given.
     * @param username Username
     * @param password Password
     * @return The ID of the new account
     * @throws AdministratorServiceException An exception is thrown if the creation is unsuccessful.
     */
    long createEnterpriseAccount(String username, String password) throws AdministratorServiceException, SQLException;

    /**
     * Log in an administrator account by its ID and password.
     * The administrator without its password will be returned as a result if the login is successful,
     * or an exception is thrown.
     * @param administratorId Administrator ID
     * @param password Password
     * @return The administrator without its password
     * @throws AdministratorServiceException An exception is thrown if the login is successful.
     */
    Administrator logIn(String administratorId, String password) throws AdministratorServiceException, SQLException;
}
