package com.zzzz.controller;

import com.zzzz.dto.UserParam;
import com.zzzz.po.User;
import com.zzzz.service.UserService;
import com.zzzz.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * At least one of the email address or the mobile should be provided.
     * Avatar URL is optional.
     * @param userParam username, password, email, mobile, avatarUrl
     * @return Success: 201; Bad request: 400; Internal: 500
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody UserParam userParam) {
        try {
            userService.insert(userParam.getUsername(), userParam.getPassword(), userParam.getEmail(), userParam.getMobile(), userParam.getAvatarUrl());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Get a user by ID.
     * @param userId Target user ID
     * @return Success: user; User not found: 404; Internal: 500
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") String userId) {
        // TODO
        // Authentication not implemented yet.
        try {
            User result = userService.getById(userId);
            return ResponseEntity.ok(result);
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Update a user. A field should not be provided if it's not to be modified.
     * @param targetId ID of the target user
     * @param userParam username, password, email, mobile, avatarUrl
     * @return Success: 204; Bad request: 400; Internal: 500
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") String targetId,
                                 @RequestBody UserParam userParam) {
        // TODO
        // Authentication not implemented yet.
        try {
            userService.update(targetId, userParam.getUsername(), userParam.getPassword(), userParam.getEmail(), userParam.getMobile(), userParam.getAvatarUrl());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserServiceException e) {
            ResponseEntity result = ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
            System.out.println(result);
            return result;
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
    @PostMapping(value = "/login/email")
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
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
