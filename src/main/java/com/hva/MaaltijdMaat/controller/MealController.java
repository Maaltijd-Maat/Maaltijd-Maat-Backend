package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.service.MealService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/meal")
@CrossOrigin
public class MealController {
    private final MealService mealService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public MealController(MealService mealService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.mealService = mealService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
