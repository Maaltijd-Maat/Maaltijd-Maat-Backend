package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.PasswordResetToken;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.repository.PasswordResetTokenRepository;
import com.hva.MaaltijdMaat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(User user) {
        this.userRepository.save(user);
    }

    /**
     * Get the user information from the database.
     * @param email the email where to search with.
     * @return A user object with the collected information.
     */
    public User getUserInformation(String email){
        try{
            return userRepository.findUserByEmail(email).get(0);
        }catch (Exception e){
            System.out.println("Authentication problem.");
            return null;
        }
    }

    /**
     * Class for getting user by username this is mandatory for the JWT token.
     * @param email address for searching.
     * @return UserDetails object part of User object.
     * @throws UsernameNotFoundException When user is not found.
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            //Search user in user database.
            User user = userRepository.findUserByEmail(email).get(0);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    new ArrayList<>());
        }catch (Exception e){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    /**
     * Set expiration date for password reset token and persist into database.
     * @param user The user who requested a token.
     * @param token The string UUID token.
     */
    public void createPasswordResetTokenForUser(User user, String token) {
        //Get the current calendar date.
        final Calendar cal = Calendar.getInstance();
        //Add 1 hour by the calender date.
        cal.add(Calendar.HOUR_OF_DAY, 1);
        //Build object with the collected data and save into database.
        PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(cal.getTime())
                .build();
        passwordResetTokenRepository.save(myToken);
    }

    /**
     * Get stored user inside token object by password reset token from the database.
     * @param token the token where to search with.
     * @return User object that is stored in token object.
     */
    public User getUserByPasswordResetToken(String token){
        return passwordResetTokenRepository.findAllByToken(token).get(0).getUser();
    }

    /**
     * Update the user password into the database.
     * @param user The changed user object.
     */
    public void updatePassword(User user){
        userRepository.save(user);
    }

    /**
     * Deletes all expired and already used password reset tokens in the database.
     * @param user user object that recently used the password reset token.
     */
    public void deleteUserPasswordTokens(User user){
        //Create calender object and roll 1 hour to check all expired tokens.
        Calendar calender = Calendar.getInstance();
        calender.roll(Calendar.HOUR_OF_DAY, 1);
        //Delete all by recently used.
        passwordResetTokenRepository.deleteAllByUser(user);
        //Delete all expired tokens.
        passwordResetTokenRepository.deleteAllByExpiryDateBefore(calender.getTime());
    }
}