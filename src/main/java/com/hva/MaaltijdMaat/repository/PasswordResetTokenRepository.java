package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.PasswordResetToken;
import com.hva.MaaltijdMaat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, Integer> {
    List<PasswordResetToken> findAllByToken(String token);
    void deleteAllByUser(User user);
    void deleteAllByExpiryDateBefore(Date date);
}
