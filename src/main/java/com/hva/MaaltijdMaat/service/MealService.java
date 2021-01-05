package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Attendee;
import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.Meal;
import com.hva.MaaltijdMaat.model.Suggestion;
import com.hva.MaaltijdMaat.repository.AttendeeRepository;
import com.hva.MaaltijdMaat.repository.MealRepository;
import com.hva.MaaltijdMaat.repository.SuggestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final SuggestRepository suggestRepository;
    private final AttendeeRepository attendeeRepository;

    @Autowired
    public MealService(MealRepository mealRepository, SuggestRepository suggestRepository, AttendeeRepository attendeeRepository) {
        this.mealRepository = mealRepository;
        this.suggestRepository = suggestRepository;
        this.attendeeRepository = attendeeRepository;
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

    public void setAttendee(Attendee attendee){
        attendeeRepository.save(attendee);
    }
}
