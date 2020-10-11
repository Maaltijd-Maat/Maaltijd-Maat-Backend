package com.hva.MaaltijdMaat.config;

import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that is executed once per request.
 * It will check if the token is in the request and it will check for validity.
 */
@Component
public class RequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = jwtTokenUtil.refactorToken(requestTokenHeader);
        try {
            if (jwtToken != null) email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }catch (IllegalArgumentException e){
            System.out.println("Unable to get JWT token, try again.");
        }catch (ExpiredJwtException e){
            System.out.println("JWT token has expired");
        }

        //Validation
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = (UserDetails) this.userService.getUserInformation(email);
            if (jwtTokenUtil.validateToken(jwtToken, (User) userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, httpServletResponse);
    }
}
