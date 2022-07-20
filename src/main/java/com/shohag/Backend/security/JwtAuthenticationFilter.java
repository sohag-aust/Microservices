package com.shohag.Backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JWTTokenHelper jwtTokenHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenFromRequest = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if(tokenFromRequest != null && tokenFromRequest.startsWith("Bearer ")) {
            jwtToken = tokenFromRequest.substring(7);

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("==** Unable to get JWT Token **==");
            } catch (ExpiredJwtException expiredJwtException) {
                System.out.println("==** JWT Token has expired **==");
            } catch (MalformedJwtException malformedJwtException) {
                System.out.println("==** Invalid JWT Token **==");
            }
        } else {
            System.out.println("==** JWT Token does not begin with Bearer **==");
        }


        // now validating jwtToken
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(this.jwtTokenHelper.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("==** Invalid JWT Token **==");
            }
        } else {
            System.out.println("==** Invalid username or securityContext already set **==");
        }

        filterChain.doFilter(request, response);
    }
}
