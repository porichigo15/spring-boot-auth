package com.example.assignmentapi.service;

import com.example.assignmentapi.model.MyUserDetails;
import com.example.assignmentapi.model.User;
import com.example.assignmentapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        return new MyUserDetails(user);
    }
}
