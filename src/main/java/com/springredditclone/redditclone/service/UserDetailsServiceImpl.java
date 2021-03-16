package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        // this exception provided by spring
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("NO user found with username" + username));

        // this class implement user details interface
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
