package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DishRepository extends MongoRepository<Dish, String> {
    @Query("{ 'author._id' : ?0 }")
    List<Dish> findDishesByUser(String userId);
}
