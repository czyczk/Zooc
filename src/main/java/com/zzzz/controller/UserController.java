package com.zzzz.controller;

import com.zzzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletRequest req,
                                 String username,
                                 String password,
                                 String email,
                                 String telephone,
                                 String avatarUrl) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        System.out.println(telephone);
        System.out.println(avatarUrl);
        return ResponseEntity.ok("OK");
    }
}
