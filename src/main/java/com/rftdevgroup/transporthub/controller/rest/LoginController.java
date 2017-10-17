package com.rftdevgroup.transporthub.controller.rest;

import com.rftdevgroup.transporthub.data.dto.UserDTO;
import com.rftdevgroup.transporthub.data.model.user.Address;
import com.rftdevgroup.transporthub.data.model.user.Role;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.data.model.user.UserDetails;
import com.rftdevgroup.transporthub.data.repository.RoleRepository;
import com.rftdevgroup.transporthub.data.repository.UserRepository;
import com.rftdevgroup.transporthub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

/**
 * Custom REST controller for testing purposes.
 * To be removed.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Authentication login(Authentication auth) {
        return auth;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public UserDTO test() {
        return userService.findUser("rftuser").get();
    }
}
