package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepository extends MongoRepository<Meal, Integer> {
}
