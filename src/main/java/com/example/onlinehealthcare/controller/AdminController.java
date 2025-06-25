package com.example.onlinehealthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.DoctorRepository;
import com.example.onlinehealthcare.repository.UserRepository;
import com.example.onlinehealthcare.service.AdminService;
import com.example.onlinehealthcare.service.DoctorService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired 
    private DoctorService doctorService;

    // Admin dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // Manage doctors - list
//    @GetMapping("/doctors")
//    public String listDoctors(Model model) {
//        List<Doctor> doctors = adminService.getAllDoctors();
//        model.addAttribute("doctors", doctors);
//        return "admin/manage-doctors";
//    }
    
    
//    @GetMapping("/doctors")
//    public String listDoctors(Model model) {
//        model.addAttribute("doctors", adminService.getAllDoctors());
//        model.addAttribute("doctor", new Doctor());
//        return "admin/manage-doctors";
//    }

    @GetMapping("/doctors")
    public String showDoctors(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin/manage-doctors";
    }

    
    // last used method this only 
//    @GetMapping("/doctors")
//    public String listDoctors(Model model) {
//        List<User> doctors = userRepository.findByRole("DOCTOR");
//        model.addAttribute("doctors", doctors);
//        model.addAttribute("doctor", new Doctor());
//        return "admin/manage-doctors";
//    }


    // Show form to add new doctor
//    @GetMapping("/doctors/new")
//    public String showDoctorForm(Model model) {
//        model.addAttribute("doctor", new Doctor());
//        return "admin/manage-doctors";
//    }
    
    @GetMapping("/doctors/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());  // <-- This is required
        return "admin/manage-doctors";
    }
    
//    // Save or update doctor
//    @PostMapping("/doctors/save")
//    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
//        adminService.saveDoctor(doctor);
//        return "redirect:/admin/doctors";
//    }
    
    
//    @PostMapping("/doctors/save")
//    public String saveDoctor(@ModelAttribute Doctor doctor, Model model) {
//        // Create a User for this doctor
//        User user = new User();
//        user.setName(doctor.getName());
//        user.setEmail(doctor.getEmail());
//        user.setPassword(doctor.getPassword());
//        user.setRole("DOCTOR");
//        user.setSpecialization(doctor.getSpecialization()); // if you have this field
//        userRepository.save(user);
//
//        // Optionally, save doctor-specific info in doctor table
//        // doctorRepository.save(doctor);
//
//        return "redirect:/admin/doctors";
//    }
    
    
    
    // last used method this only

    // Save or update doctor
//    @PostMapping("/doctors/save")
//    public String saveDoctor(@ModelAttribute("doctor") User doctor, Model model) {
//        if (doctor.getId() != null) {
//            // Editing existing doctor
//            User existing = userRepository.findById(doctor.getId()).orElse(null);
//            if (existing != null) {
//                existing.setName(doctor.getName());
//                existing.setEmail(doctor.getEmail());
//                existing.setSpecialization(doctor.getSpecialization());
//                existing.setPassword(doctor.getPassword());
//                // Do not change role
//              
//                userRepository.save(existing);
//             // Update Doctor entity if needed
//                Doctor doc = doctorRepository.findByUser(existing).orElse(null);
//                if (doc != null) {
//                    doc.setName(existing.getName());
//                    doc.setEmail(existing.getEmail());
//                    doc.setPhone(existing.getPhone());
//                    doc.setSpecialization(existing.getSpecialization());
//                    doctorRepository.save(doc);
//                }
//
//            }
//        } else {
//            // Adding new doctor
//            doctor.setRole("DOCTOR");
//            userRepository.save(doctor);
//            
//            // Also create Doctor entity!
//            Doctor doc = new Doctor();
//            doc.setUser(doctor);
//            doc.setName(doctor.getName());
//            doc.setEmail(doctor.getEmail());
//            doc.setPhone(doctor.getPhone());
//            doc.setSpecialization(doctor.getSpecialization());
//            doctorRepository.save(doc);
//        }
//        return "redirect:/admin/doctors";
//    }

    
    // last used method this only
//    @PostMapping("/doctors/save")
//    public String saveDoctor(@ModelAttribute("user") User user,
//            @RequestParam("specialization") String specialization,
//            Model model)  {
//        user.setRole("DOCTOR");
//        userRepository.save(user);
//
//        Doctor doctor = new Doctor();
//        doctor.setUser(user);
//        doctor.setName(user.getName());
//        doctor.setEmail(user.getEmail());
//        doctor.setPhone(user.getPhone());
//        doctor.setSpecialization(specialization);
//       // doctor.setSpecialization(user.getSpecialization());
//        doctorService.saveDoctor(doctor);
//
//        model.addAttribute("success", "Doctor registered successfully!");
//        return "redirect:/admin/doctors";
//    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute("user") User user,
                             @RequestParam("specialization") String specialization,
                             Model model) {
        user.setRole("DOCTOR");
        userRepository.save(user); // This will update if user.id is present

        // Try to find an existing doctor for this user
        Doctor doctor = doctorRepository.findByUserId(user.getId());
        if (doctor == null) {
            // If not found, create new
            doctor = new Doctor();
            doctor.setUser(user);
        }
        // Update doctor details
        doctor.setName(user.getName());
        doctor.setEmail(user.getEmail());
        doctor.setPhone(user.getPhone());
        doctor.setSpecialization(specialization);

        doctorService.saveDoctor(doctor);

        model.addAttribute("success", "Doctor saved successfully!");
        return "redirect:/admin/doctors";
    }

    

    // Edit doctor form
    @GetMapping("/doctors/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
//        Doctor doctor = adminService.getDoctorById(id);
    	//User doctor = userRepository.findById(id).orElse(null);
    	User user = userRepository.findById(id).orElse(null);
        Doctor doctor = doctorRepository.findByUserId(id);
        if (user == null || doctor==null) {
            return "redirect:/admin/doctors";
        }
       // model.addAttribute("doctor", doctor);
        model.addAttribute("user", user);
        model.addAttribute("specialization", doctor.getSpecialization());
        model.addAttribute("doctors", doctorService.getAllDoctors());
       // model.addAttribute("doctors", userRepository.findByRole("DOCTOR"));
        return "admin/manage-doctors";
    }

    
    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        // Delete doctor by user id
        Doctor doctor = doctorRepository.findByUserId(id);
        if (doctor != null) {
            doctorRepository.delete(doctor);
        }
        userRepository.deleteById(id);
        return "redirect:/admin/doctors";
    }

    
    // Delete doctor
//    @GetMapping("/doctors/delete/{id}")
//    public String deleteDoctor(@PathVariable Long id) {
////        adminService.deleteDoctor(id);
//    	userRepository.deleteById(id);
//        return "redirect:/admin/doctors";
//    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        List<Patient> patients = adminService.getAllPatients();
        model.addAttribute("patients", patients);
        return "admin/manage-patients"; // Make sure this matches your admin template!
    }

    
    // Manage patients - list
//    @GetMapping("/patients")
//    public String listPatients(Model model) {
//        List<Patient> patients = adminService.getAllPatients();
//        model.addAttribute("patients", patients);
//        return "admin/manage-patients";
//    }

    // Delete patient
    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        adminService.deletePatient(id);
        return "redirect:/admin/patients";
    }

    // View all appointments
    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        List<Appointment> appointments = adminService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        // Add status counts
        model.addAttribute("pendingCount", adminService.countByStatus("PENDING"));
        model.addAttribute("acceptedCount", adminService.countByStatus("ACCEPTED"));
        model.addAttribute("prescribedCount", adminService.countByStatus("PRESCRIBED"));

        return "admin/view-appointments";
    }
}
