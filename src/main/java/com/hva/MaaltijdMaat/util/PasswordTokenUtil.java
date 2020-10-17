package com.hva.MaaltijdMaat.util;

import com.hva.MaaltijdMaat.model.PasswordResetToken;
import com.hva.MaaltijdMaat.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class PasswordTokenUtil {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    /**
     * General method that gives the result of validation and expiration.
     * @param token to check for validation.
     * @return a string with valid, invalidToken or expired string.
     */
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findAllByToken(token).get(0);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : "valid";
    }

    /**
     * Simple function to check if token object is found.
     * @param passToken the token object ot check.
     * @return boolean for found or not.
     */
    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    /**
     * Check if token is expired by checking the expiration date inside passwordResetToken object
     * @param passToken the passwordResetToken object.
     * @return boolean if the token is expired.
     */
    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
