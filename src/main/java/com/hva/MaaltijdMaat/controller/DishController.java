package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Dish;
import com.hva.MaaltijdMaat.service.DishService;
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

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable("id") String id) {
        try {
            Optional<Dish> dish = dishService.findDish(id);

            return dish.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getDishes() {
        try {
            List<Dish> dishes = dishService.getAllDishes();
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HttpStatus> createDish(@RequestBody Dish dish) {
        try {
            Dish _dish = Dish.builder()
                    .name(dish.getName())
                    .amountOfPeople(dish.getAmountOfPeople())
                    .ingredients(dish.getIngredients())
                    .instructions(dish.getInstructions())
                    .build();

            dishService.createDish(_dish);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDish(@PathVariable String id, @RequestBody Dish dish) {
        try {
            Optional<Dish> dishData = dishService.findDish(id);

            if (dishData.isPresent()) {
                Dish _dish = Dish.builder()
                        .id(id)
                        .name(dish.getName())
                        .amountOfPeople(dish.getAmountOfPeople())
                        .ingredients(dish.getIngredients())
                        .instructions(dish.getInstructions())
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
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable String id) {
        try {
            Optional<Dish> dishData = dishService.findDish(id);

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
