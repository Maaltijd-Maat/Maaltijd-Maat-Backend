package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Suggestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestRepository extends MongoRepository<Suggestion, Integer> {}
