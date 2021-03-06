package com.rftdevgroup.transporthub.controller.rest;

import com.rftdevgroup.transporthub.data.dto.user.UserDTO;
import com.rftdevgroup.transporthub.data.dto.user.UserUpdateDTO;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.service.UserService;
import com.rftdevgroup.transporthub.validator.ValidationErrors;
import com.rftdevgroup.transporthub.validator.Validators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * REST Controller class for providing endpoints to {@link User} related operations.
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    @Autowired
    private UserService userService;

    @Autowired
    private Validators validators;

    @Secured(ADMIN)
    @RequestMapping(value = "/users", method = GET)
    public List<UserDTO> users() {
        log.debug("Admin requested the user list.");
        return userService.listUsers();
    }

    @Secured(ADMIN)
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") long userId, Principal principal) {
        //delete user
        if (userService.deleteUser(userId)) {
            String message = principal.getName() + " deleted user id: " + userId;
            log.debug(message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "Something went wrong!";
            log.error("User({}) deletion by {} failed.",userId, principal.getName());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @Secured(USER)
    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSelf(Principal principal, @RequestBody UserUpdateDTO updateDTO) {
        Optional<ValidationErrors> errors = validators.validate(updateDTO);
        if (errors.isPresent()) {
            log.warn("User update data has field errors.");
            return new ResponseEntity<>(errors.get().getErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<UserDTO> activeUser = userService.findAndMapUser(principal.getName(), UserDTO.class);
        if (activeUser.isPresent()) {
            UserDTO updatedUser = userService.updateUser(activeUser.get().getId(), updateDTO);
            if (updateDTO != null) {
                log.debug("User({}) updated successfully.",updateDTO.getUserName());
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                log.error("User update failed.");
                return new ResponseEntity<>("Update failed!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("User is missing to update.");
            return new ResponseEntity<>("No active user is present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured(ADMIN)
    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserUpdateDTO updateDTO) {
        Optional<ValidationErrors> errors = validators.validate(updateDTO);
        if (errors.isPresent()) {
            log.warn("User update data has field errors.");
            return new ResponseEntity<>(errors.get().getErrors(), HttpStatus.BAD_REQUEST);
        }
        UserDTO updatedUser = userService.updateUser(id, updateDTO);
        if (updatedUser == null) {
            log.error("No user found with given id({})",id);
            return new ResponseEntity<>("User not found with given id.", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.debug("User({}) successfully updated by admin.",id);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    @Secured(USER)
    @RequestMapping(value = "/user", method = GET)
    public ResponseEntity<?> getSelf(Principal principal) {
        String username = principal.getName();
        Optional<UserDTO> userDTO = userService.findAndMapUser(username, UserDTO.class);
        if (!userDTO.isPresent()){
            log.error("No active user found.");
            return new ResponseEntity<>("No user found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("User({}) details served.", principal.getName());
        return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
    }

    @Secured(ADMIN)
    @RequestMapping(value = "/user/{id}", method = GET)
    public ResponseEntity<?> adminGetUser(@PathVariable("id") long id) {
        Optional<UserDTO> userDTO = userService.findAndMapUser(id, UserDTO.class);
        if (!userDTO.isPresent()) return new ResponseEntity<>("No user found with given id.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
    }

}
