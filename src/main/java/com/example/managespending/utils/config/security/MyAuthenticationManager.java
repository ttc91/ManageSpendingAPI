package com.example.managespending.utils.config.security;

import com.example.managespending.utils.config.security.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationManager implements AuthenticationManager {

    @Autowired
    MyUserDetailsService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final UserDetails userDetails = service.loadUserByUsername(authentication.getPrincipal().toString());
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
            throw new BadCredentialsException("Wrong password !");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

    }

}
