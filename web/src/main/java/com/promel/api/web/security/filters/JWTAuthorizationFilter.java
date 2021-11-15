package com.promel.api.web.security.filters;

import com.promel.api.usecase.exception.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.promel.api.web.security.SecurityConstants.AUTHORIZATION_HEADER;
import static com.promel.api.web.security.SecurityConstants.BEARER_TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private Environment environment;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetails, Environment environment) {
        super(authManager);
        this.userDetailsService = userDetails;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith(BEARER_TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);

        } catch (ExpiredJwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is expired");
        } catch (MalformedJwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is not a valid token");
        } catch (SignatureException e){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
        } catch (UnsupportedJwtException e){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is unsupported");
        } catch (ResourceNotFoundException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null) {
            return null;
        }

        String username = Jwts.parser().setSigningKey(environment.getProperty("BEARER_TOKEN_SECRET_KEY"))
                .parseClaimsJws(token.replace(BEARER_TOKEN_PREFIX, "")).getBody().getSubject();

        var userDetails = userDetailsService.loadUserByUsername(username);

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()) : null;
    }
}