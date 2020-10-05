package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Dish;
import com.hva.MaaltijdMaat.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Optional<Dish> findDish(String id) {
        return dishRepository.findById(id);
    }

    public void createDish(Dish dish) {
        this.dishRepository.insert(dish);
    }

    public void updateDish(Dish dish) {
        this.dishRepository.save(dish);
    }

    public void deleteDish(String id) {
        this.dishRepository.deleteById(id);
    }
}
