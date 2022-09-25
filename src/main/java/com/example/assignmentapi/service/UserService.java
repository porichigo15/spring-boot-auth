package com.example.assignmentapi.service;

import com.example.assignmentapi.model.ResponseBodyModel;
import com.example.assignmentapi.model.User;
import com.example.assignmentapi.repository.UserRepository;
import com.example.assignmentapi.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertUtil convertUtil;

    public ResponseBodyModel<User> register(User user) {
        ResponseBodyModel<User> responseBodyModel = new ResponseBodyModel<>();
        User found = userRepository.findByEmail(user.getEmail());
        if (found == null) {
            LocalDateTime now = LocalDateTime.now();

            user.setUserId(UUID.randomUUID().toString());
            user.setCreatedDate(now);
            user.setPassword(convertUtil.passwordEncoder().encode(user.getPassword()));
            user.setEnable(true);
            user.setRoles("ROLE_USER");

            User saved = userRepository.save(user);

            responseBodyModel.setObjectValue(saved);
        } else {
            responseBodyModel.setMessage("USER_EXIST");
        }
        return responseBodyModel;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserDetails() {
        User user = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getDetails();
        }
        return user;
    }
}
