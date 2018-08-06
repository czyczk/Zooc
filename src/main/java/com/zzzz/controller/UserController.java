package com.zzzz.controller;

import com.zzzz.controller.util.CookieUtil;
import com.zzzz.dto.UserParam;
import com.zzzz.po.User;
import com.zzzz.service.UserService;
import com.zzzz.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
     * will also be returned along with Cookies with attributes `userId: long`
     * and `password: String` respectively.
     * @param req HttpServletRequest
     * @param userParam email, password, rememberMe: boolean
     * @return Success: User without the password; Bad request: 400; User not found: 404; Incorrect password: 401; Internal: 500
     */
    @PostMapping(value = "/login/email")
    public ResponseEntity<User> logInByEmail(HttpServletRequest req, HttpServletResponse resp,
                                             @RequestBody UserParam userParam) throws UserServiceException, SQLException {
        User user = userService.logInByEmail(userParam.getEmail(), userParam.getPassword());
        CookieUtil.addUserLoginCookie(req, resp,
                user.getUserId(), userParam.getPassword(), Boolean.parseBoolean(userParam.getRememberMe()));
        return ResponseEntity.ok(user);
    }

    /**
     * Log in using the user login cookie.
     * Triggered when a page attempts to log in using the stored cookie.
     * @param req HttpServletRequest
     * @return Success: User without the password; Bad request: 400; User not found: 404; Incorrect password: 401; Internal: 500
     */
    @PostMapping("/login/cookie")
    public ResponseEntity<User> logInByCookie(HttpServletRequest req) throws UserServiceException, SQLException {
        UserParam param = CookieUtil.parseUserLoginCookie(req);
        User user = userService.logInById(param.getUserId(), param.getPassword());
        return ResponseEntity.ok(user);
    }
}
