package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.*;
import com.hva.MaaltijdMaat.service.DishService;
import com.hva.MaaltijdMaat.service.GroupService;
import com.hva.MaaltijdMaat.service.MealService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    private final DishService dishService;

    @Autowired
    public MealController(MealService mealService,
                          GroupService groupService,
                          UserService userService,
                          JwtTokenUtil jwtTokenUtil,
                          DishService dishService) {
        this.mealService = mealService;
        this.groupService = groupService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<Object> createMeal(@RequestHeader(name = "Authorization") String token,
                                           @RequestBody CreateMeal mealRequest) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User creator = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            // Retrieve group and check if the creator is a member of the group
            Group group = groupService.findGroup(mealRequest.getGroupId(), creator.getId());

            // Check if the end date is after the start date
            if (mealRequest.getEnd().isBefore(mealRequest.getStart())) {
                return new ResponseEntity<>("End date is before start date", HttpStatus.BAD_REQUEST);
            }

            //Create new meal object
            Meal meal = Meal.builder()
                    .title(mealRequest.getTitle())
                    .group(group)
                    .createdBy(creator)
                    .start(ZonedDateTime.of(mealRequest.getStart(), ZoneId.systemDefault()))
                    .end(ZonedDateTime.of(mealRequest.getEnd(), ZoneId.systemDefault()))
                    .description(mealRequest.getDescription())
                    .suggestions(new ArrayList<>())
                    .build();

            //Inserted meal that we have to use for new attendee object
            Meal insertedMeal = mealService.createNewMeal(meal);

            //create new attendees for meal with default availability
            try{
                mealService.createAttendees(insertedMeal);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(insertedMeal, HttpStatus.OK);
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

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMeal(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("id") String mealId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<Group> groups = groupService.findGroups(user.getId());
            Meal meal = mealService.findMealByGroupAndId(mealId, groups);

            return new ResponseEntity<>(meal, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/suggestion")
    public ResponseEntity<?> createSuggestion(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody Suggestion suggestionRequest){
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
            Dish dish = dishService.findDishById(suggestionRequest.getDish().getId());
            Suggestion suggestion = Suggestion.builder()
                    .suggester(user)
                    .title(suggestionRequest.getTitle())
                    .description(suggestionRequest.getDescription())
                    .dish(dish)
                    .build();
            mealService.createSuggestion(suggestion);

            return new ResponseEntity<>(suggestion, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/attendee")
    public ResponseEntity<?> createOrUpdateAttendee(@RequestHeader(name = "Authorization") String token, @RequestBody Attendee attendee){
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            //Check if attendee already exists
            Attendee existingAttendee = mealService.getAttendeeByUserAndMeal(user, attendee.getMeal());
            if(existingAttendee != null) {
                //Update existing attendee
                attendee = Attendee.builder()
                        .id(existingAttendee.getId())
                        .status(attendee.getStatus())
                        .meal(attendee.getMeal())
                        .attendee(user)
                        .build();
            }else{
                //Insert new attendee
                attendee = Attendee.builder()
                        .status(attendee.getStatus())
                        .meal(attendee.getMeal())
                        .attendee(user)
                        .build();
            }

            mealService.createOrUpdateAttendee(attendee);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMeal(@RequestBody Meal meal){
        try {
            Meal persistingMeal = Meal.builder()
                    .id(meal.getId())
                    .createdBy(meal.getCreatedBy())
                    .description(meal.getDescription())
                    .group(meal.getGroup())
                    .end(meal.getEnd())
                    .start(meal.getStart())
                    .suggestions(meal.getSuggestions())
                    .build();
            mealService.updateMeal(persistingMeal);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/attendee/{id}")
    public ResponseEntity<List<Attendee>> getAttendees(@PathVariable("id") String mealId) {
        try {
            List<Attendee> attendees = mealService.getAttendeesByMealId(mealId);
            return new ResponseEntity<>(attendees, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
