package com.promel.api.web.security;

import com.promel.api.usecase.role.RoleType;
import com.promel.api.web.security.filters.JWTAuthenticationFilter;
import com.promel.api.web.security.filters.JWTAuthorizationFilter;
import com.promel.api.web.security.service.UserDetailsServiceImpl;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private Environment environment;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, Environment environment) {
        this.userDetailsService = userDetailsService;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/admin/**").hasRole(RoleType.ASSOCIATION_ADMIN.name())
                .antMatchers("/**/protected/**").hasRole(RoleType.ASSOCIATION_USER.name())
                .and()
                .addFilter(getJWTAuthenticationFilter())
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService, environment))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    public JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        var jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(), environment);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/users/login");

        return jwtAuthenticationFilter;
    }
}