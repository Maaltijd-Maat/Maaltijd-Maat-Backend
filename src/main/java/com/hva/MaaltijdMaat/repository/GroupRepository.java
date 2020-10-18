package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {}
