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

import java.time.LocalDateTime;

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
                                             @RequestBody LocalDateTime plannedFor, @RequestParam String groupId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User creator = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            // Retrieve group and check if inviter is member of the group
            Group group = groupService.findGroup(groupId, creator.getId());

            Meal meal = Meal.builder()
                    .group(group)
                    .createdBy(creator)
                    .plannedFor(plannedFor)
                    .build();

            return new ResponseEntity<>(mealService.createNewMeal(meal), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
