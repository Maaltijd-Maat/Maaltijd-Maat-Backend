package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishRepository extends MongoRepository<Dish, String> {}
