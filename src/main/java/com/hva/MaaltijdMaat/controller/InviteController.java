package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.service.InviteService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/invite")
public class InviteController {
    private final InviteService inviteService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public InviteController(InviteService inviteService,
                           UserService userService,
                           JwtTokenUtil jwtTokenUtil) {
        this.inviteService = inviteService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
