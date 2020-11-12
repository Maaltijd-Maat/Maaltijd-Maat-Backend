package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.PasswordResetToken;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.EmailService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import com.hva.MaaltijdMaat.util.PasswordTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordTokenUtil passwordTokenUtil;

    /**
     * Register newly created user.
     * @param user object from body, with all the necessary information to register the user.
     * @return HTTP status response entity.
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            User _user = User.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .allergies(user.getAllergies())
                    .avatar(user.getAvatar())
                    .guest(user.isGuest())
                    .allergies(user.getAllergies())
                    .diets(user.getDiets())
                    .build();

            userService.registerUser(_user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get user object with information by using JWT token.
     * @param token JWT token.
     * @return The user if found and HTTP response status entity.
     */
    @GetMapping(path = "/information")
    public ResponseEntity<?> getUserInformation(@RequestHeader(name = "Authorization") String token){
        //Remove the word bearer from the token.
        String jwtToken = jwtTokenUtil.refactorToken(token);
        //Try to get the information by using the token.
        User retrievedLogin = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
        //If not found return not found http status code.
        if (retrievedLogin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //Build user object with all information that is collected.
        User responseUser = User.builder()
                .id(retrievedLogin.getId())
                .firstname(retrievedLogin.getFirstname())
                .lastname(retrievedLogin.getLastname())
                .email(retrievedLogin.getEmail())
                .secondEmail(retrievedLogin.getSecondEmail())
                .avatar(retrievedLogin.getAvatar())
                .guest(retrievedLogin.isGuest())
                .allergies(retrievedLogin.getAllergies())
                .diets(retrievedLogin.getDiets())
                .build();
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    /**
     * Endpoint for requesting password reset token by mail.
     * @param credentials exists of only the email.
     * @return HTTP response entity status.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User credentials) {
        String email = credentials.getEmail();
        //Check if user exists
        User user = userService.getUserInformation(email);
        //Return not found if user email of specific user is not found int the user database.
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //Generate random UUID called token for passwordResetToken model.
        String token = UUID.randomUUID().toString();
        //Create new password reset token object with user and token.
        userService.createPasswordResetTokenForUser(user, token);
        //Email token to email address of user.
        emailService.constructResetTokenEmail(token, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update the user information.
     * @param token JWT token.
     * @param user The user with changes that are requested.
     * @return HTTP status code.
     */
    @PutMapping("/update-information")
    public ResponseEntity<?> updateUserInformation(@RequestHeader(name = "Authorization") String token, @RequestBody User user){
        //Remove the word bearer from the token.
        String jwtToken = jwtTokenUtil.refactorToken(token);
        //Try to get the information by using the token.
        User retrievedUser = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
        //Check which value to use for persisting user object.
        String[] allergens = user.getAllergies().length > 0 ? user.getAllergies() : retrievedUser.getAllergies();
        String[] diets = user.getDiets().length > 0 ? user.getDiets() : retrievedUser.getDiets();
        String email = !user.getEmail().isEmpty() ? user.getEmail() : retrievedUser.getEmail();
        String secondEmail = !user.getSecondEmail().isEmpty() ? user.getSecondEmail() : retrievedUser.getSecondEmail();
        String firstname = !user.getFirstname().isEmpty() ? user.getFirstname() : retrievedUser.getFirstname();
        String lastname = !user.getLastname().isEmpty() ? user.getLastname() : retrievedUser.getLastname();
        //Create persisting user with requested changes.
        User persistingUser = User.builder()
                .id(retrievedUser.getId())
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .secondEmail(secondEmail)
                .password(retrievedUser.getPassword())
                .avatar(retrievedUser.getAvatar())
                .guest(retrievedUser.isGuest())
                .allergies(allergens)
                .diets(diets)
                .build();
        userService.updateUser(persistingUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint for changing password with password reset token.
     * @param credentials exists of the password reset token and the new password.
     * @return HTTP response entity status.
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User credentials){
        //Put the token and new password in the correct object.
        PasswordResetToken tokenObject = PasswordResetToken.builder()
                .token(credentials.getEmail())
                .build();
        //Check if token is valid and not expired.
        String response = passwordTokenUtil.validatePasswordResetToken(tokenObject.getToken());
        if(response.equals("valid")){
            //Check if user exists by passwordResetToken
            User retrievedUser = userService.getUserByPasswordResetToken(tokenObject.getToken());
            //Return not found http status if user is not found.
            if (retrievedUser == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //Build the new user with the encoded password.
            User user = User.builder()
                    .id(retrievedUser.getId())
                    .firstname(retrievedUser.getFirstname())
                    .lastname(retrievedUser.getLastname())
                    .email(retrievedUser.getEmail())
                    .password(passwordEncoder.encode(credentials.getPassword()))
                    .avatar(retrievedUser.getAvatar())
                    .guest(retrievedUser.isGuest())
                    .allergies(retrievedUser.getAllergies())
                    .diets(retrievedUser.getDiets())
                    .build();
            //Update the user into the database.
            userService.updateUser(user);
            //Delete all tokens from user and all token that are expired.
            userService.deleteUserPasswordTokens(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else if(response.equals("expired")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
