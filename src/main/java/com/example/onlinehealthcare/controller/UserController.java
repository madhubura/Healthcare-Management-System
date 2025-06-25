package com.example.onlinehealthcare.controller;

import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "auth/list";
    }

//    @GetMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
//        return "auth/register";
//    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "auth/edit";
        } else {
            return "redirect:/users";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
    
    
//    @GetMapping("/change-password")
//    public String showChangePasswordForm() {
//        return "change-password";
//    }
//
//    @PostMapping("/change-password")
//    public String changePassword(
//            @RequestParam String currentPassword,
//            @RequestParam String newPassword,
//            @RequestParam String confirmPassword,
//            HttpSession session,
//            Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/auth/login";
//        }
//        if (!newPassword.equals(confirmPassword)) {
//            model.addAttribute("error", "New passwords do not match.");
//            return "change-password";
//        }
//        // Check current password, update password logic here...
//        boolean changed = userService.changePassword(user, currentPassword, newPassword);
//        if (changed) {
//            model.addAttribute("success", "Password updated successfully.");
//        } else {
//            model.addAttribute("error", "Current password is incorrect.");
//        }
//        return "change-password";
//    }

}
