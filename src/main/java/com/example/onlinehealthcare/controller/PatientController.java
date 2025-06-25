package com.example.onlinehealthcare.controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.UserRepository;
import com.example.onlinehealthcare.service.AppointmentService;
import com.example.onlinehealthcare.service.DoctorAvailabilityService;
import com.example.onlinehealthcare.service.DoctorService;
import com.example.onlinehealthcare.service.PatientService;
import com.example.onlinehealthcare.service.PrescriptionService;

import jakarta.servlet.http.HttpSession;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private DoctorService doctorService; // or DoctorRepository doctorRepository;



   //  Patient dashboard
//    @GetMapping("/patient/dashboard")
//    public String dashboard(Model model, HttpSession session) {
//        // Example: get logged in user from session
//        User user = (User) session.getAttribute("loggedInUser");
//        if (user == null) {
//            return "redirect:/auth/login";
//        }
//        
//        model.addAttribute("user", user);
//        // Add any other required data
//        
//        return "patient/dashboard";  // must match templates/patient/dashboard.html
//    }

    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("PATIENT")) {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", user);
     // Fetch appointments for this patient (assuming user.id is patient id)
        Patient patient = patientService.getPatientByUserId(user.getId());
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient);

        model.addAttribute("appointments", appointments);

        return "patient/dashboard";
    }


    // Show all patients (for admin or doctor use)
    @GetMapping("/list")
    public String listPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    // View profile - example by id param
    @GetMapping("/profile/{id}")
    public String patientProfile(@PathVariable Long id, Model model) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if(patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            return "patient/profile";
        }
        return "redirect:/patient/dashboard";
    }

    // Edit patient form
    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if(patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            return "patient/edit";
        }
        return "redirect:/patient/dashboard";
    }

    // Save patient details after edit
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patient/dashboard";
    }
    
    @GetMapping("/my-appointments")
    public String myAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/auth/login";
        }
        Patient patient = patientService.findByUserEmail(user.getEmail());
        if (patient == null) {
            return "redirect:/patient/dashboard";
        }
        // Only show appointments that are not cancelled
        model.addAttribute("appointments", appointmentService.getAppointmentsByPatientAndStatusNot(patient, "Cancelled"));
        return "patient/my-appointments";
    }


    @PostMapping("/cancel-appointment/{id}")
    public String cancelAppointment(@PathVariable("id") Long appointmentId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/auth/login";
        }
        Patient patient = patientService.findByUserEmail(user.getEmail());
        if (patient == null) {
            return "redirect:/patient/dashboard";
        }
        try {
            appointmentService.cancelAppointment(appointmentId, patient);
            redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patient/my-appointments";
    }

    @GetMapping("/prescriptions")
    public String myPrescriptions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
            // If not logged in as patient, redirect to login
            return "redirect:/auth/login";
        }
        // Fetch the patient entity using the user's email
        Patient patient = patientService.findByUserEmail(user.getEmail());
        if (patient == null) {
            // If patient not found, redirect to dashboard (or show error)
            return "redirect:/patient/dashboard";
        }
        // Add the list of prescriptions for this patient to the model
       model.addAttribute("prescriptions", prescriptionService.getPrescriptionsByPatient(patient));
        
       // Render the patient/prescriptions.html Thymeleaf template
        return "patient/prescriptions";
    }


    
    
 // List my appointments (patient logged in assumed to be fetched)
//    @GetMapping("/my-appointments")
//    public String myAppointments(Model model) {
//        // For demo, fetching patient with id=1, replace with logged-in patient logic
//        Patient patient = patientService.getPatientById(1L).orElse(null);
//        if (patient == null) {
//            return "redirect:/patient/dashboard";
//        }
//        model.addAttribute("appointments", appointmentService.getAppointmentsByPatient(patient));
//        return "patient/my-appointments";
//    }
//
//    // List my prescriptions
//    @GetMapping("/prescriptions")
//    public String myPrescriptions(Model model) {
//        Patient patient = patientService.getPatientById(1L).orElse(null);
//        if (patient == null) {
//            return "redirect:/patient/dashboard";
//        }
//        model.addAttribute("prescriptions", prescriptionService.getPrescriptionsByPatient(patient));
//        return "patient/prescriptions";
//    }
    
    
    @GetMapping("/book-appointment")
    public String showBookAppointmentPage(
            @RequestParam(value = "doctorId", required = false) Long doctorId,
            @RequestParam(value = "date", required = false) String date,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/auth/login";
        }

        model.addAttribute("doctors", doctorService.getAllDoctors());

        if (doctorId != null && date != null && !date.isEmpty()) {
            java.time.DayOfWeek javaDayOfWeek = java.time.LocalDate.parse(date).getDayOfWeek();
            com.example.onlinehealthcare.enums.DayOfWeek myDayOfWeek =
                com.example.onlinehealthcare.enums.DayOfWeek.valueOf(javaDayOfWeek.name());
            List<DoctorAvailability> availableSlots = doctorAvailabilityService.findByDoctorIdAndDayOfWeek(doctorId, myDayOfWeek);
            model.addAttribute("availableSlots", availableSlots);
            model.addAttribute("selectedDoctorId", doctorId);
            model.addAttribute("selectedDate", date);
        }

        return "patient/book-appointment";
    }


    
    //before last used method(before adding doctor avialabi;ity timings)
//    @GetMapping("/book-appointment")
//    public String showBookAppointmentPage(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
//            return "redirect:/auth/login";
//        }
////        model.addAttribute("doctors", doctorService.getAllDoctors());
//     // If you have userService or userRepository available:
//      //  List<Doctor> doctors = doctorService.getAllDoctors();
//        List<User> doctors = userRepository.findByRole("DOCTOR");
//        
//      //  model.addAttribute("doctors", doctors);
////        model.addAttribute("doctors", userRepository.findByRole("DOCTOR"));
//        model.addAttribute("doctors", doctorService.getAllDoctors());
//
//        return "patient/book-appointment";
//    }
//        
    @PostMapping("/book-appointment")
    public String bookAppointment(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("reason") String reason,   // <-- add this line
            HttpSession session,
            Model model )
    {
    	
        User user = (User) session.getAttribute("user");
        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/auth/login";
        }
        
     // 2. Fetch the patient entity for the logged-in user
//        Patient patient = patientService.findByUserEmail(user.getEmail());
        Patient patient = patientService.findByUser(user);
     // Add debug prints here!
        System.out.println("Patient: " + patient);
        System.out.println("Patient ID: " + (patient != null ? patient.getId() : null));

        if (patient == null) {
            model.addAttribute("error", "Patient record not found.");
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "patient/book-appointment";
        }
        
     // 3. Fetch the doctor entity by doctorId
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(doctorId);
        if (doctorOpt.isEmpty()) {
            model.addAttribute("error", "Selected doctor not found.");
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "patient/book-appointment";
        }
        Doctor doctor = doctorOpt.get();
     // 4. Parse date and time safely
        LocalDate appointmentDate;
        LocalTime appointmentStartTime;
        LocalTime appointmentEndTime;
        try {
            appointmentDate = LocalDate.parse(date);
            // Split the time slot (e.g., "09:00-09:30")
            String[] times = time.split("-");
            appointmentStartTime = LocalTime.parse(times[0].trim());
            appointmentEndTime = LocalTime.parse(times[1].trim());
        } catch (Exception e) {
            model.addAttribute("error", "Invalid date or time format.");
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "patient/book-appointment";
        }
        
     // 5. Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(appointmentDate);
       // appointment.setTime(appointmentTime);
        appointment.setStartTime(appointmentStartTime);
        appointment.setEndTime(appointmentEndTime);

        appointment.setStatus("PENDING"); // <-- ADD THIS LINE
        appointment.setReason(reason); // <-- set the reason
        appointmentService.save(appointment);
        
     // 6. Show success and reload doctors for the form
        model.addAttribute("success", "Appointment booked successfully!");
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "patient/book-appointment";
    }


//
//    @PostMapping("/book-appointment")
//    public String bookAppointment(
//            @RequestParam("doctorId") Long doctorId,
//            @RequestParam("date") String date,
//            @RequestParam("time") String time,
//            HttpSession session,
//            Model model
//    ) {
//        User user = (User) session.getAttribute("user");
//        if (user == null || !"PATIENT".equalsIgnoreCase(user.getRole())) {
//            return "redirect:/auth/login";
//        }
//
//        Patient patient = patientService.findByUserEmail(user.getEmail());
//        if (patient == null) {
//            model.addAttribute("error", "Patient record not found.");
//            model.addAttribute("doctors", doctorService.getAllDoctors());
//            return "patient/book-appointment";
//        }
//
//        Optional<Doctor> doctorOpt = doctorService.getDoctorById(doctorId);
//        if (doctorOpt.isEmpty()) {
//            model.addAttribute("error", "Selected doctor not found.");
//            model.addAttribute("doctors", doctorService.getAllDoctors());
//            return "patient/book-appointment";
//        }
//        Doctor doctor = doctorOpt.get();
//
//        Appointment appointment = new Appointment();
//        appointment.setPatient(patient);
//        appointment.setDoctor(doctor);
//        appointment.setDate(LocalDate.parse(date));
//        appointment.setTime(LocalTime.parse(time));
//
//        appointmentService.save(appointment);
//
//        model.addAttribute("success", "Appointment booked successfully!");
//        model.addAttribute("doctors", doctorService.getAllDoctors());
//        return "patient/book-appointment";
//    }

}