package com.example.onlinehealthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.Prescription;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.service.AppointmentService;
import com.example.onlinehealthcare.service.DoctorService;
import com.example.onlinehealthcare.service.PrescriptionService;
import com.example.onlinehealthcare.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
    private  UserService userService;
    @Autowired
    private DoctorService doctorService; 
    @Autowired
    private AppointmentService appointmentService;
    

    // Doctor dashboard
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "doctor/dashboard";
//    }
    
//    @GetMapping("/dashboard")
//    public String doctorDashboard(HttpSession session, Model model) {
//        // Retrieve the logged-in user from session
//    	Doctor doctor = (Doctor) session.getAttribute("doctor");
//
//        if (doctor == null) {
//            // Not logged in or not a doctor, redirect to login
//            return "redirect:/auth/login";
//        }
//        // Add the user to the model as 'doctor'
//        model.addAttribute("doctor", doctor);
//        // Return the view for the doctor dashboard
//        return "doctor/dashboard";
//    }


    @GetMapping("/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        // Retrieve the logged-in user from session
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }

        // Fetch the Doctor entity using the User object
        Doctor doctor = doctorService.getDoctorByUser(user).orElse(null);
        if (doctor == null) {
            // Handle missing doctor profile (should not happen if data is correct)
            model.addAttribute("error", "Doctor profile not found. Please contact admin.");
            return "auth/login";
        }

        model.addAttribute("doctor", doctor);
        return "doctor/dashboard";
    }

       
    // Show doctor's own appointments
    @GetMapping("/appointments")
    public String appointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }
        Doctor doctor = doctorService.getDoctorByUser(user).orElse(null);
        if (doctor == null) {
            model.addAttribute("error", "Doctor profile not found. Please contact admin.");
            return "auth/login";
        }
        // Get all appointments for this doctor
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor);
 // or use appointmentService.getAppointmentsByDoctor(doctor)
        model.addAttribute("appointments", appointments);
        return "doctor/appointments";
    }

    // Show doctor's own prescriptions
    @GetMapping("/prescriptions")
    public String prescriptions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }
        Doctor doctor = doctorService.getDoctorByUser(user).orElse(null);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctor);
        model.addAttribute("prescriptions", prescriptions);
        return "doctor/prescriptions";
    }

    
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/prescriptions/new")
    public String newPrescription(@RequestParam("appointmentId") Long appointmentId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }
        Optional<Appointment> appointmentOpt = appointmentService.getAppointmentById(appointmentId);
        if (appointmentOpt.isEmpty() || !appointmentOpt.get().getDoctor().getUser().getId().equals(user.getId())) {
            model.addAttribute("error", "Invalid appointment or access denied.");
            return "doctor/appointments";
        }
        Appointment appointment = appointmentOpt.get();
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        model.addAttribute("prescription", prescription);
        model.addAttribute("appointment", appointment);
        return "doctor/new-prescription";
    }

    @PostMapping("/prescriptions/save")
    public String savePrescription(@ModelAttribute Prescription prescription,
                                  @RequestParam("appointmentId") Long appointmentId,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }

        // Fetch the full appointment from DB using the ID
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            // handle error gracefully
            return "redirect:/doctor/appointments?error=AppointmentNotFound";
        }

        // Link the prescription to the appointment
        prescription.setAppointment(appointment);
        prescriptionService.savePrescription(prescription);

        // Update appointment status to PRESCRIBED
        appointment.setStatus("PRESCRIBED");
        appointmentService.saveAppointment(appointment);

        return "redirect:/doctor/appointments";
    }

    
    // last used method 
//    @PostMapping("/prescriptions/save")
//    public String savePrescription(@ModelAttribute Prescription prescription, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null || !"DOCTOR".equals(user.getRole())) {
//            return "redirect:/auth/login";
//        }
//        prescriptionService.savePrescription(prescription);
//
//        // Update the appointment status to PRESCRIBED
//        Appointment appointment = prescription.getAppointment();
//        if (appointment != null) {
//            appointment.setStatus("PRESCRIBED");
//            appointmentService.saveAppointment(appointment); // Make sure you have this method
//        }
//
//        return "redirect:/doctor/appointments";
//    }


//    // View or edit doctor's own profile
//    @GetMapping("/profile/{id}")
//    public String editDoctorProfile(@PathVariable Long id, Model model) {
//        Optional<Doctor> doctor = doctorService.getDoctorById(id);
//        if (doctor.isPresent()) {
//            model.addAttribute("doctor", doctor.get());
//            return "doctor/profile";
//        } else {
//            return "redirect:/doctor/dashboard";
//        }
//    }

    @GetMapping("/profile/{id}")
    public String editDoctorProfile(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }

        Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
        if (doctorOpt.isEmpty() || !doctorOpt.get().getUser().getId().equals(user.getId())) {
            // Prevent access to other doctors' profiles
            return "redirect:/doctor/dashboard";
        }

        model.addAttribute("doctor", doctorOpt.get());
        return "doctor/profile";
    }    
    
    @PostMapping("/appointments/{id}/accept")
    public String acceptAppointment(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }
        appointmentService.updateStatus(id, "CONFIRMED");
        return "redirect:/doctor/appointments";
    }

    @PostMapping("/appointments/{id}/reject")
    public String rejectAppointment(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }
        appointmentService.updateStatus(id, "REJECTED");
        return "redirect:/doctor/appointments";
    }

    
    @PostMapping("/update/{id}")
    public String updateDoctorProfile(@PathVariable Long id, @ModelAttribute("doctor") Doctor updatedDoctor) {
        doctorService.updateDoctor(id, updatedDoctor);
        return "redirect:/doctor/dashboard";
    }
    
//    @GetMapping("/profile")
//    public String profile(HttpSession session, Model model) {
//    	Doctor doctor = (Doctor) session.getAttribute("doctor");
//        if (doctor == null ) {
//            return "redirect:/auth/login";
//        }
//        model.addAttribute("doctor", doctor);
//        return "doctor/profile";
//    }
//
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }

        Doctor doctor = doctorService.getDoctorByUser(user).orElse(null);
        if (doctor == null) {
            return "redirect:/auth/login"; // or show error page
        }

        model.addAttribute("doctor", doctor);
        return "doctor/profile";
    }

    
    
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("doctor") Doctor updatedDoctor, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"DOCTOR".equals(user.getRole())) {
            return "redirect:/auth/login";
        }

        Doctor doctor = doctorService.getDoctorByUser(user).orElse(null);
        if (doctor == null) {
            return "redirect:/auth/login";
        }

        // Update allowed fields
        doctor.setName(updatedDoctor.getName());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPhone(updatedDoctor.getPhone());

        doctorService.saveDoctor(doctor);

        model.addAttribute("doctor", doctor);
        model.addAttribute("success", "Profile updated successfully!");
        return "doctor/profile";
    }
    
//    @PostMapping("/profile/update")
//    public String updateProfile(@ModelAttribute("doctor") User doctor, HttpSession session, Model model) {
//        // Update logic: fetch user from DB by email or id, update fields, save
//    	Doctor sessionDoctor = (Doctor) session.getAttribute("doctor");
//        if (sessionDoctor  == null ) {
//            return "redirect:/auth/login";
//        }
//        // Only allow updating allowed fields
//        sessionDoctor .setName(doctor.getName());
//        sessionDoctor .setSpecialization(doctor.getSpecialization());
//        sessionDoctor.setEmail(doctor.getEmail());
//        sessionDoctor.setPhone(doctor.getPhone());
//
//        // Save updated user (assumes you have a userService or userRepository)
//        doctorService.saveDoctor(sessionDoctor);
//        session.setAttribute("doctor", sessionDoctor); // update session
//        model.addAttribute("doctor", sessionDoctor);
//        model.addAttribute("success", "Profile updated successfully!");
//        return "doctor/profile";
//    }

}