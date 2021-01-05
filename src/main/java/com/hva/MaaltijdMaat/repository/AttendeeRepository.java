package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Attendee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttendeeRepository extends MongoRepository<Attendee, Integer>{}
