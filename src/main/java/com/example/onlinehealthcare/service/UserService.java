package com.example.onlinehealthcare.service;

import com.example.onlinehealthcare.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

	User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    void save(User user);

    List<User> findAll();

   // void saveUser(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void deleteById(Long id);
   // boolean changePassword(User user, String currentPassword, String newPassword);

    
    
}
