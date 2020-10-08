package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {}
