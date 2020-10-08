package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(User user) {
        this.userRepository.save(user);
    }

    public User getUserInformation(String email){
        try{
            return userRepository.findUserByEmail(email).get(0);
        }catch (Exception e){
            System.out.println("Authentication problem.");
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            User user = userRepository.findUserByEmail(email).get(0);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    new ArrayList<>());
        }catch (Exception e){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
