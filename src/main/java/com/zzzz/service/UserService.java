package com.zzzz.service;

import com.zzzz.po.User;

import java.sql.SQLException;

public interface UserService {
    void insert(String username, String password, String email, String mobile, String avatarUrl) throws UserServiceException, SQLException;
    User getById(String userId) throws UserServiceException, SQLException;

    /**
     * Update a user. A field should be left null if no modification is to be made.
     * @param targetUserId The ID of the user to be modified
     * @param username New username
     * @param password New password
     * @param email New email address
     * @param mobile New mobile
     * @param avatarUrl New avatar URL
     * @throws UserServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetUserId,
                String username,
                String password,
                String email,
                String mobile,
                String avatarUrl) throws UserServiceException, SQLException;

    /**
     * Log in by the user's email. Check the validity of this login activity
     * and return the user without the password if the login is successful.
     * @param email Email address
     * @param password Password
     * @return The user without the password
     * @throws UserServiceException An exception is thrown if the login is not successful.
     */
    User logInByEmail(String email, String password) throws UserServiceException, SQLException;

    /**
     * Log in by the user's mobile. Check the validity of this login activity
     * and return the user without its password if the login is successful.
     * @param mobile Mobile number
     * @param verificationCode Verification code
     * @return The user without its password
     * @throws UserServiceException An exception is thrown if the login is not successful.
     */
    User logInByMobile(String mobile, String verificationCode) throws UserServiceException;

    /**
     * Log in by the user's ID. Check the validity of this login activity
     * and return the user without the password if the login is successful.
     * @param userId User ID
     * @param password Password
     * @return The user without the password
     * @throws UserServiceException An exception is thrown if the login is not successful.
     */
    User logInById(String userId, String password) throws UserServiceException, SQLException;
}
