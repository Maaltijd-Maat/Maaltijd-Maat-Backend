package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends MongoRepository<Dish, String> {
    @Query("{ 'author._id' : ?0 }")
    List<Dish> findDishesByAuthor(String userId);

    @Query("{ '_id' : ?0, 'author._id' : ?1 }")
    Optional<Dish> findDishByAuthor(String dishId, String userId);
}
