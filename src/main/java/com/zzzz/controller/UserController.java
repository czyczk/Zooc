package com.zzzz.controller;

import com.zzzz.po.User;
import com.zzzz.service.UserService;
import com.zzzz.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Create a new user.
     * @param username Username
     * @param password Password
     * @param email Email address
     * @param mobile Mobile
     * @param avatarUrl Avatar URL (Nullable)
     * @return Success: 201; Bad request: 400; Internal: 500
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(String username,
                                 String password,
                                 String email,
                                 String mobile,
                                 @Nullable String avatarUrl) {
        try {
            userService.insert(username, password, email, mobile, avatarUrl);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getMessage());
        }
    }

    /**
     * Get a user by ID.
     * @param userId Target user ID
     * @return Success: user; User not found: 404; Internal: 500
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable("id") String userId) {
        // TODO
        // Authentication not implemented yet.
        try {
            User result = userService.getById(userId);
            return ResponseEntity.ok(result);
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getMessage());
        }
    }

    /**
     * Update a user. A field should not be provided if it's not to be modified.
     * @param targetId ID of the target user
     * @param username New username
     * @param password New password
     * @param email New email address
     * @param mobile New mobile
     * @param avatarUrl New avatar URL
     * @return Success: 204; Bad request: 400; Internal: 500
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") String targetId,
                                 @Nullable String username,
                                 @Nullable String password,
                                 @Nullable String email,
                                 @Nullable String mobile,
                                 @Nullable String avatarUrl) {
        // TODO
        // Authentication not implemented yet.
        try {
            userService.update(targetId, username, password, email, mobile, avatarUrl);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getMessage());
        }
    }

    /**
     * Log in. After a successful login, the user ID will be returned.
     * The state of login and the ID of the authenticated user
     * will also be stored in the session with attributes `isLoggedIn: boolean`
     * and `userId: long` respectively.
     * @param req HttpServletRequest
     * @param email Email
     * @param password Password
     * @return Success: userId; User not found: 404; Incorrect password: 401
     */
    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public ResponseEntity logInByEmail(HttpServletRequest req,
                                String email,
                                String password) {
        try {
            long userId = userService.logInByEmail(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userId", userId);
            return ResponseEntity.ok(userId);
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getMessage());
        }
    }
}
