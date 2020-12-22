package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MealRepository extends MongoRepository<Meal, Integer> {
    List<Meal> findAllByGroupIn(Collection<String> groupIds);
}
