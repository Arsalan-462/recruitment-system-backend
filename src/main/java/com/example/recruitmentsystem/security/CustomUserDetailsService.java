package com.example.recruitmentsystem.security;

import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserType().toUpperCase());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(),
                Collections.singleton(authority));
    }
}
