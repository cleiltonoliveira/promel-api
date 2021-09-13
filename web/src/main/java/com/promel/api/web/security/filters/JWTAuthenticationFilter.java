package com.promel.api.web.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.web.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private Environment environment;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserAuth userAuth = new ObjectMapper()
                    .readValue(request.getInputStream(), UserAuth.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuth.getEmail(),
                            userAuth.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("BEARER_TOKEN_SECRET_KEY")).compact();

        String bearerToken = SecurityConstants.TOKEN_PREFIX + token;

        response.setContentType("application/json");
        response.addHeader(SecurityConstants.HEADER_STRING, bearerToken);

        var body = new HashMap<String, String>();
        body.put("token", bearerToken);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}