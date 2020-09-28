package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(User user) {
        this.userRepository.save(user);
    }
}
