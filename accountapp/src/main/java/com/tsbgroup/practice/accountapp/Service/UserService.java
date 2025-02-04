package com.tsbgroup.practice.accountapp.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tsbgroup.practice.accountapp.Model.User;
import com.tsbgroup.practice.accountapp.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findByUserId(userId);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
