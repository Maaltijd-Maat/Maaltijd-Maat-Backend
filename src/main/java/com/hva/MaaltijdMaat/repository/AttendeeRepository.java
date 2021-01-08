package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Attendee;
import com.hva.MaaltijdMaat.model.Meal;
import com.hva.MaaltijdMaat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendeeRepository extends MongoRepository<Attendee, Integer>{
    Attendee findAttendeeByAttendeeAndMeal(User user, Meal meal);
    List<Attendee> findAttendeesByMealId(String mealId);
}
