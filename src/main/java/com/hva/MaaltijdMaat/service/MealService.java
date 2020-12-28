package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.Meal;
import com.hva.MaaltijdMaat.model.Suggestion;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.repository.MealRepository;
import com.hva.MaaltijdMaat.repository.SuggestRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final SuggestRepository suggestRepository;

    @Autowired
    public MealService(MealRepository mealRepository, SuggestRepository suggestRepository) {
        this.mealRepository = mealRepository;
        this.suggestRepository = suggestRepository;
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
     * @param groups the groups where the user is part of to check if the user is permitted to see the meal.
     * @return meal
     */
    public Meal findMealByGroupAndId(String mealId, Collection<Group> groups) {
        List<String> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());
        return this.mealRepository.findMealByGroupInAndId(groupIds, mealId);
    }

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

    /**
     * Update the meal
     *
     * @param meal the actual updated meal to persist.
     */
    public void updateMeal(Meal meal){
        mealRepository.save(meal);
    }

    /**
     * Create a suggestion and add it to the database.
     *
     * @param suggestion the suggestion object.
     * @return the persisted suggestion.
     */
    public Suggestion createSuggestion(Suggestion suggestion){
        return suggestRepository.insert(suggestion);
    }
}
