package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Dish;
import com.hva.MaaltijdMaat.service.DishService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.UserService;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dish")
@CrossOrigin
public class DishController {
    private final DishService dishService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public DishController(DishService dishService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.dishService = dishService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@RequestHeader(name = "Authorization") String token,
                                                 @RequestBody Dish dish) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Dish _dish = Dish.builder()
                    .name(dish.getName())
                    .author(user)
                    .amountOfPeople(dish.getAmountOfPeople())
                    .build();

            dishService.createDish(_dish);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishByAuthor(@RequestHeader(name = "Authorization") String token,
                                                @PathVariable("id") String id) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Optional<Dish> dish = dishService.findDishByAuthor(id, user.getId());

            return dish.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getDishesByAuthor(@RequestHeader(name = "Authorization") String token) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<Dish> dishes = dishService.findDishesByAuthor(user.getId());
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDish(@RequestHeader(name = "Authorization") String token,
                                                 @PathVariable String id,
                                                 @RequestBody Dish dish) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Optional<Dish> dishData = dishService.findDishByAuthor(id, user.getId());

            if (dishData.isPresent()) {
                Dish _dish = dishData.get().toBuilder()
                        .name(dish.getName())
                        .amountOfPeople(dish.getAmountOfPeople())
                        .build();

                dishService.updateDish(_dish);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@RequestHeader(name = "Authorization") String token,
                                                 @PathVariable String id) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Optional<Dish> dishData = dishService.findDishByAuthor(id, user.getId());

            if (dishData.isPresent()) {
                dishService.deleteDish(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
