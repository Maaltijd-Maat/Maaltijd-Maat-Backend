package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    List<User> findUserByEmail(String email);
}