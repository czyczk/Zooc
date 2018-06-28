package com.zzzz.service;

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
}
