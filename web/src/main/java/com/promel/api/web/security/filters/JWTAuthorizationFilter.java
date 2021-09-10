package com.promel.api.web.security.filters;

import com.promel.api.web.security.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.promel.api.web.security.SecurityConstants.HEADER_STRING;
import static com.promel.api.web.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetails) {
        super(authManager);
        this.userDetailsService = userDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);

        } catch (ExpiredJwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
        } catch (MalformedJwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e + ". The JWT token is not a valid token");
        } catch (UsernameNotFoundException e) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, e.toString());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token == null) {
            return null;
        }

        String username = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();

        var userDetails = userDetailsService.loadUserByUsername(username);

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()) : null;
    }
}