package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            User _user = User.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .Allergenen(user.getAllergenen())
                    .avatar(user.getAvatar())
                    .guest(user.isGuest())
                    .build();

            userService.registerUser(_user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
