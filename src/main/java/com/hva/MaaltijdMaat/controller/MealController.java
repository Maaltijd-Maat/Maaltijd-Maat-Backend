package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.*;
import com.hva.MaaltijdMaat.service.GroupService;
import com.hva.MaaltijdMaat.service.MealService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/meal")
@CrossOrigin
public class MealController {
    private final MealService mealService;
    private final GroupService groupService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public MealController(MealService mealService, GroupService groupService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.mealService = mealService;
        this.groupService = groupService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestHeader(name = "Authorization") String token,
                                           @RequestBody CreateMeal mealRequest) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User creator = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            // Retrieve group and check if the creator is a member of the group
            Group group = groupService.findGroup(mealRequest.getGroupId(), creator.getId());

            Meal meal = Meal.builder()
                    .title(mealRequest.getTitle())
                    .group(group)
                    .createdBy(creator)
                    .start(mealRequest.getStart())
                    .end(mealRequest.getEnd())
                    .description(mealRequest.getDescription())
                    .build();

            return new ResponseEntity<>(mealService.createNewMeal(meal), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getMeals(@RequestHeader(name = "Authorization") String token) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<Group> groups = groupService.findGroups(user.getId());
            List<Meal> meals = mealService.findMealsByGroups(groups);

            return new ResponseEntity<>(meals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
