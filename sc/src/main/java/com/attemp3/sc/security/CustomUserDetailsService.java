package com.attemp3.sc.security;


import com.attemp3.sc.entities.User;
import com.attemp3.sc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        String roleName = userFound.getRole().getName();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        if ("ADMIN".equals(roleName)) {
            authorities.add(new SimpleGrantedAuthority("read"));
            authorities.add(new SimpleGrantedAuthority("write"));
            authorities.add(new SimpleGrantedAuthority("create"));
            authorities.add(new SimpleGrantedAuthority("delete"));
        } else if ("MOD".equals(roleName)) {
            authorities.add(new SimpleGrantedAuthority("read"));
            authorities.add(new SimpleGrantedAuthority("write"));
        } else {
            authorities.add(new SimpleGrantedAuthority("read"));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(userFound.getUsername())
                .password(userFound.getPassword())
                .authorities(authorities)
                .build();
    }
}

