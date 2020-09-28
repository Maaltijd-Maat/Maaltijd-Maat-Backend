package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

}