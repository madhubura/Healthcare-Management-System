package com.example.onlinehealthcare.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.service.AppointmentService;
import com.example.onlinehealthcare.service.DoctorService;
import com.example.onlinehealthcare.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // List all appointments (admin/doctor use case)
    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointments/list"; // Create this Thymeleaf page under templates/appointments/list.html
    }

    // Show appointment details by id
    @GetMapping("/{id}")
    public String getAppointmentDetails(@PathVariable Long id, Model model) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            model.addAttribute("appointment", appointment.get());
            return "appointments/details"; // Create this view too
        }
        return "redirect:/appointments";
    }

    // Form to create new appointment (for admin/doctor or patient)
    @GetMapping("/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        // You can add doctors list as well similarly
        return "appointments/new"; // Form page for appointment creation
    }

    // Save appointment (handle form POST)
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
    	appointment.setStatus("PENDING");   // Set status before saving (choose your default, e.g. "PENDING")

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    // Delete appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}