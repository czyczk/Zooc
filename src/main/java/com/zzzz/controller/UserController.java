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
import java.sql.SQLException;

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
    public ResponseEntity create(@RequestBody UserParam userParam) throws UserServiceException, SQLException {
        userService.insert(userParam.getUsername(), userParam.getPassword(), userParam.getEmail(), userParam.getMobile(), userParam.getAvatarUrl());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Get a user by ID.
     * @param userId Target user ID
     * @return Success: user; User not found: 404; Internal: 500
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String userId) throws UserServiceException, SQLException {
        // TODO
        // Authentication not implemented yet.
        User result = userService.getById(userId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a user. A field should not be provided if it's not to be modified.
     * @param targetId ID of the target user
     * @param userParam username, password, email, mobile, avatarUrl
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") String targetId,
                                 @RequestBody UserParam userParam) throws UserServiceException, SQLException {
        // TODO
        // Authentication not implemented yet.
        userService.update(targetId, userParam.getUsername(), userParam.getPassword(), userParam.getEmail(), userParam.getMobile(), userParam.getAvatarUrl());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Log in. After a successful login, the user ID will be returned.
     * The state of login and the ID of the authenticated user
     * will also be stored in the session with attributes `isLoggedIn: boolean`
     * and `userId: long` respectively.
     * @param req HttpServletRequest
     * @param userParam email, password
     * @return Success: userId; User not found: 404; Incorrect password: 401
     */
    @PostMapping(value = "/login/email")
    public ResponseEntity logInByEmail(HttpServletRequest req,
                                       @RequestBody UserParam userParam) throws UserServiceException, SQLException {
        long userId = userService.logInByEmail(userParam.getEmail(), userParam.getPassword());
        HttpSession session = req.getSession();
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("userId", userId);
        return ResponseEntity.ok(userId);
    }
}
