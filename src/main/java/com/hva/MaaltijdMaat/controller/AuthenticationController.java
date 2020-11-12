package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User login) throws Exception{
        authenticate(login.getEmail(), login.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> isTokenValid(@RequestHeader(name = "Authorization") String token){
        //Remove the word bearer from the token.
        String jwtToken = jwtTokenUtil.refactorToken(token);
        User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
        Boolean valid = jwtTokenUtil.validateToken(jwtToken, user);
        if (!valid) valid = false;
        return new ResponseEntity(valid, HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception{
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
