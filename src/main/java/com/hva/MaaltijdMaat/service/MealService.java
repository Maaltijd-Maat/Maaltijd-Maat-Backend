package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.*;
import com.hva.MaaltijdMaat.repository.AttendeeRepository;
import com.hva.MaaltijdMaat.repository.MealRepository;
import com.hva.MaaltijdMaat.repository.SuggestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     */
    public void createSuggestion(Suggestion suggestion){
        suggestRepository.insert(suggestion);
    }

    /**
     * Create new Attendee
     *
     * @param attendee object with the status and references to user and meal
     */
    public void createOrUpdateAttendee(Attendee attendee){
        attendeeRepository.save(attendee);
    }

    /**
     * Get single attendee by User.
     *
     * @return Attendee if exists.
     */
    public Attendee getAttendeeByUserAndMeal(User user, Meal meal){
        return attendeeRepository.findAttendeeByAttendeeAndMeal(user, meal);
    }

    /**
     * Get a list of all group members that had given their status for the meal.
     *
     * @param mealId object used for searching in the attendee DB.
     * @return A list of attendees that had given their availability.
     */
    public List<Attendee> getAttendeesByMealId(String mealId){
        return attendeeRepository.findAttendeesByMealId(mealId);
    }

    /**
     * Initialize and create all the group members as attendees with no status
     *
     * @param insertedMeal the meal where the attendees could attend.
     */
    public void createAttendees(Meal insertedMeal) {
        List<Attendee> attendees = new ArrayList<>();
            for (User member : insertedMeal.getGroup().getMembers()){
                Attendee attendee = Attendee.builder()
                        .status("Not given availability yet.")
                        .attendee(member)
                        .meal(insertedMeal)
                        .build();
                attendees.add(attendee);
            }
        attendeeRepository.saveAll(attendees);
    }
}
