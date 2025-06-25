package com.example.onlinehealthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.PatientRepository;
import com.example.onlinehealthcare.repository.UserRepository;
import com.example.onlinehealthcare.service.PatientService;
import com.example.onlinehealthcare.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientService patientService;
    

    @Autowired
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login") // ✅ Corrected mapping
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "auth/login";
        }
        session.setAttribute("user", user);
        if ("ADMIN".equals(user.getRole())) return "redirect:/admin/dashboard";
        if ("DOCTOR".equals(user.getRole())) return "redirect:/doctor/dashboard";
        return "redirect:/patient/dashboard";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user, Model model) {
//        if (userRepository.existsByEmail(user.getEmail())) {
//            model.addAttribute("error", "Email already registered");
//            return "auth/register";
//        }
//        user.setRole("PATIENT");
//        userRepository.save(user);
//
//        // Save Patient
//        Patient patient = new Patient();
//        patient.setUser(user);
//        patient.setPhone(user.getPhone());
//        patient.setEmail(user.getEmail());
//        patient.setPassword(user.getPassword()); // <-- THIS LINE FIXES YOUR ERROR
//
//        System.out.println("Saving patient: " + patient.getEmail()); // For debugging
//        patientRepository.save(patient);
//
//        model.addAttribute("success", "Registration successful. Please login.");
//        return "auth/login";
//    }

//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user, Model model) {
//        // Save user as PATIENT
//        user.setRole("PATIENT");
//        userRepository.save(user);
//
//        // ALSO: Save a Patient record linked to this user
//        Patient patient = new Patient();
//        patient.setUser(user); // if you have a User field in Patient
//        patient.setName(user.getName());
//        patient.setEmail(user.getEmail());
//        patient.setPhone(user.getPhone());
//        // ... set other fields as needed ...
//        patientRepository.save(patient);
//     // Add this debug line
//        System.out.println("Saved patient: " + patient.getEmail() + ", user_id: " + user.getId());
//
//        return "redirect:/auth/login";
//    }
    
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "auth/register";
        }
        user.setRole("PATIENT");
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setName(user.getName());
        patient.setEmail(user.getEmail());
        patient.setPhone(user.getPhone());
        patientService.savePatient(patient);

        model.addAttribute("success", "Registration successful. Please login.");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
    
    
 // Optional: central dashboard redirect
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";

        switch (user.getRole()) {
            case "ADMIN":
                return "redirect:/admin/dashboard";
            case "DOCTOR":
                return "redirect:/doctor/dashboard";
            case "PATIENT":
                return "redirect:/patient/dashboard";
            default:
                return "error/unauthorized"; // optional error view
        }
    }
    
}


//package com.example.onlinehealthcare.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.onlinehealthcare.entity.User;
//import com.example.onlinehealthcare.repository.UserRepository;
//import com.example.onlinehealthcare.service.UserService;
//import org.springframework.ui.Model; // ✅ CORRECT
//
//import jakarta.servlet.http.HttpSession;
//@Controller
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final UserService userService;
//    private UserRepository userRepository;
//
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
// // show login page
//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }
// handle login form submission
//    @PostMapping("/auth/login")
//    public String loginUser(@RequestParam String email,
//                        @RequestParam String password,
//                        HttpSession session,
//                        Model model) {
//       // User user = userService.findByEmailAndPassword(email, password);
//        User user = userRepository.findByEmailAndPassword(email, password);
//
//        if (user == null) {
//            model.addAttribute("error", "Invalid email or password");
//            return "auth/login";
//        }
//
//        session.setAttribute("user", user);
//        if ("ADMIN".equals(user.getRole())) return "redirect:/admin/dashboard";
//        if ("DOCTOR".equals(user.getRole())) return "redirect:/doctor/dashboard";
//        return "redirect:/patient/dashboard";
//    }
//          
//          
//        // Redirect based on role
//        
//       if (user.getRole().equals("ADMIN")) {
//            return "redirect:/admin/dashboard";
//       } else if (user.getRole().equals("DOCTOR")) {
//            return "redirect:/doctor/dashboard";
//        } else {
//            return "redirect:/patient/dashboard";
//        }
//
//   }
//        
//        
//        
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String email,
//                            @RequestParam String password,
//                            HttpSession session,
//                            Model model) {
//        User user = userService.findByEmailAndPassword(email, password);
//        if (user == null) {
//            model.addAttribute("error", "Invalid credentials");
//            return "auth/login";
//        }
//        session.setAttribute("user", user);
////        // Redirect to home or dashboard (no security restriction)
////       // return "redirect:/";
////        return "redirect:/auth/dashboard";
////
////    }
//    
//  // show registration page
//    @GetMapping("/register")
//    public String registerPage(Model model) {
//        model.addAttribute("user", new User());
//        return "auth/register";
//    }
//
//    
// // Handle registration form submission
//
//    @PostMapping("/register")
//    public String registerUser(@RequestParam String name,
//                               @RequestParam String email,
//                               @RequestParam String password,
//                               Model model) {
//        if (userService.existsByEmail(email)) {
//            model.addAttribute("error", "Email already registered");
//            return "auth/register";
//        }
//        User newUser = new User();
//        newUser.setName(name);
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//       // newUser.setRole("PATIENT");  // Default role
//
//        userService.save(newUser);
//        model.addAttribute("success", "Registration successful. Please login.");
//        return "auth/login";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/auth/login";
//    }
//    
// // Role-based dashboard
//    @GetMapping("/dashboard")
//    public String showDashboard(HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/auth/login";
//        }
//        String role = user.getRole();
//        if ("PATIENT".equalsIgnoreCase(role)) {
//            return "patient/dashboard";
//        } else if ("DOCTOR".equalsIgnoreCase(role)) {
//            return "doctor/dashboard";
//        } else if ("ADMIN".equalsIgnoreCase(role)) {
//            return "admin/dashboard";
//        } else {
//            return "error/unauthorized";
//        }
//    }
//}




//@Controller
//@RequestMapping("/auth")
//
//public class AuthController {
//	
//	@Autowired
//	private UserService userService;	
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "auth/login"; // Will load templates/auth/login.html
//    }
//    @PostMapping("/login")
//    public String processLogin(@RequestParam String email,
//                               @RequestParam String password,
//                               HttpSession session,
//                               Model model) {
//        // Authentication logic...
//        return "redirect:/dashboard"; // Change as needed
//    }
//
//
//    @GetMapping("/register")
//    public String showRegisterPage(org.springframework.ui.Model model) {
//        model.addAttribute("user", new User());
//        return "auth/register";
//    }
//    @PostMapping("/register")
//    public String processRegister(@ModelAttribute("user") User user) {
//        userService.saveUser(user); // <-- Make sure this line is present!
//        return "redirect:/auth/login";
//    }
//
//    @GetMapping("/dashboard")
//	public String showDashboard() {
//	    return "dashboard"; // This will load templates/dashboard.html
//	}
//
//}
