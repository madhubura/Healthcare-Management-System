package com.example.onlinehealthcare.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.onlinehealthcare.entity.Prescription;
import com.example.onlinehealthcare.service.AppointmentService;
import com.example.onlinehealthcare.service.PatientService;
import com.example.onlinehealthcare.service.PrescriptionService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    // List all prescriptions (admin/doctor use case)
    @GetMapping
    public String listPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        return "prescriptions/list"; // Create this Thymeleaf page under templates/prescriptions/list.html
    }

    // Show prescription details by id
    @GetMapping("/{id}")
    public String getPrescriptionDetails(@PathVariable Long id, Model model) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if (prescription.isPresent()) {
            model.addAttribute("prescription", prescription.get());
            return "prescriptions/details"; // Create this view too
        }
        return "redirect:/prescriptions";
    }

    // Form to create new prescription
    @GetMapping("/new")
    public String newPrescriptionForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("patients", patientService.getAllPatients());
        // Add doctors list if needed
        return "prescriptions/new"; // Form page for prescription creation
    }

    // Save prescription (handle form POST)
    @PostMapping("/save")
    public String savePrescription(@ModelAttribute("prescription") Prescription prescription) {
        prescriptionService.savePrescription(prescription);
        return "redirect:/prescriptions";
    }

    // Delete prescription
    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/prescriptions";
    }
}