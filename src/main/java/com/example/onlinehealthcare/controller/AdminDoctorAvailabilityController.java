package com.example.onlinehealthcare.controller;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek;
import com.example.onlinehealthcare.service.DoctorAvailabilityService;
import com.example.onlinehealthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin/doctor-availability")
public class AdminDoctorAvailabilityController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    // Show the form and all availabilities
 // AdminDoctorAvailabilityController.java

    @GetMapping
    public String showAvailabilityForm(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        List<DoctorAvailability> allAvailabilities = doctorAvailabilityService.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("availability", new DoctorAvailability());
        model.addAttribute("allAvailabilities", allAvailabilities);
        return "admin/set_availability";
    }

    @PostMapping("/add")
    public String addAvailability(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            RedirectAttributes redirectAttributes
    ) {
        Doctor doctor = doctorService.findById(doctorId);
        DoctorAvailability slot = new DoctorAvailability(doctor, dayOfWeek, startTime, endTime);
        doctorAvailabilityService.saveAvailability(slot);
        redirectAttributes.addFlashAttribute("success", "Availability added successfully!");
        return "redirect:/admin/doctor-availability";
    }
    
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DoctorAvailability slot = doctorAvailabilityService.findById(id);
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("availability", slot);
        model.addAttribute("doctors", doctors);
        return "admin/edit_availability";
    }

    @PostMapping("/edit/{id}")
    public String updateAvailability(
            @PathVariable Long id,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            RedirectAttributes redirectAttributes
    ) {
        Doctor doctor = doctorService.findById(doctorId);
        doctorAvailabilityService.updateAvailability(id, doctor, dayOfWeek, startTime, endTime);
        redirectAttributes.addFlashAttribute("success", "Availability updated successfully!");
        return "redirect:/admin/doctor-availability";
    }

    @GetMapping("/delete/{id}")
    public String deleteAvailability(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        doctorAvailabilityService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Availability deleted successfully!");
        return "redirect:/admin/doctor-availability";
    }
}