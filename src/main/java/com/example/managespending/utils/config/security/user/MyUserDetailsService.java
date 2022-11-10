package com.example.managespending.utils.config.security.user;

import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = repository.findAccountByAccountUsername(username);

        if(account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new MyUserDetails(account);
    }

}
