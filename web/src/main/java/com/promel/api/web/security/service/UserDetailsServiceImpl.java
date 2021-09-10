package com.promel.api.web.security.service;

import com.promel.api.usecase.authentication.UserAuthFinder;
import com.promel.api.usecase.role.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserAuthFinder userAuthFinder;

    public UserDetailsServiceImpl(UserAuthFinder userAuthFinder) {
        this.userAuthFinder = userAuthFinder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var userAuth = userAuthFinder.findByEmail(email);

        List<GrantedAuthority> authorityListAdmin = AuthorityUtils
                .createAuthorityList("ROLE_" + RoleType.ASSOCIATION_USER.name(), "ROLE_" + RoleType.ASSOCIATION_ADMIN.name());

        List<GrantedAuthority> authorityListUser = AuthorityUtils
                .createAuthorityList("ROLE_" + RoleType.ASSOCIATION_USER.name());

        var authorityList = userAuth.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(RoleType.ASSOCIATION_ADMIN.name())) ? authorityListAdmin : authorityListUser;

        return new User(
                userAuth.getEmail(),
                userAuth.getPassword(),
                authorityList);
    }
}