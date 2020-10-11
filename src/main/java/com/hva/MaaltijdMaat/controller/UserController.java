package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Dish;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            User _user = User.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
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

    @PostMapping(path = "/information")
    public ResponseEntity<?> getUserInformation(@RequestHeader(name = "Authorization") String token){
        String jwtToken = jwtTokenUtil.refactorToken(token);
        User retrievedLogin = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
        if (retrievedLogin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User responseUser = User.builder()
                .id(retrievedLogin.getId())
                .firstname(retrievedLogin.getFirstname())
                .lastname(retrievedLogin.getLastname())
                .email(retrievedLogin.getEmail())
                .avatar(retrievedLogin.getAvatar())
                .guest(retrievedLogin.isGuest())
                .Allergenen(retrievedLogin.getAllergenen())
                .build();

        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }
}
