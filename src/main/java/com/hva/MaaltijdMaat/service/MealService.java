package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.Meal;
import com.hva.MaaltijdMaat.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MongoTemplate mongoTemplate;
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MongoTemplate mongoTemplate, MealRepository mealRepository) {
        this.mongoTemplate = mongoTemplate;
        this.mealRepository = mealRepository;
    }

    /**
     * Creates a new meal.
     *
     * @param meal meal to be added to the database
     * @return The created meal including id
     */
    public Meal createNewMeal(Meal meal) {
        return mealRepository.insert(meal);
    }

    /**
     * Finds an already existing meal.
     *
     * @param mealId id of the requested meal
     * @return meal
     */
    public Meal findMeal(String mealId) { return null;}

    /**
     * Finds all meals by groups.
     *
     * @param groups list of groups
     * @return list of meals
     */
    public List<Meal> findMealsByGroups(Collection<Group> groups) {
        List<String> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());
        return mealRepository.findAllByGroupIn(groupIds);
    }
}
