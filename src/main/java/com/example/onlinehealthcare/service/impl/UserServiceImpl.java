package com.example.onlinehealthcare.service.impl;
import org.springframework.beans.factory.annotation.Autowired;


//import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.UserRepository;
import com.example.onlinehealthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
   


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

//    @Override
//    public void save1(User user) {
//        userRepository.save(user);
//    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);

	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);

	}

	@Override
	public void save(User user) {
		userRepository.save(user);

		
	}

//	@Override
//    public boolean changePassword(User user, String currentPassword, String newPassword) {
//        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
//            return false;
//        }
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        return true;
//    }
}
