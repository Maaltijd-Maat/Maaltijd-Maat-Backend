package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends MongoRepository<Dish, String> {
    /**
     * Find all dishes by the author's ID.
     * @param userId ID of the author.
     * @return List with dishes
     */
    List<Dish> findDishesByAuthor_Id(String userId);

    /**
     * Find a specified dish by ID and author's ID.
     * @param id ID of the dish
     * @param authorId ID of the author
     * @return Optional Dish
     */
    Optional<Dish> findDishByIdAndAuthor_Id(String id, String authorId);
}
