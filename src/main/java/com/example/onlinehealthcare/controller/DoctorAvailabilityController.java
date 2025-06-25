package com.example.onlinehealthcare.controller;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek;
import com.example.onlinehealthcare.service.DoctorAvailabilityService;
import com.example.onlinehealthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctors/{doctorId}/availability")
public class DoctorAvailabilityController {

    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    @Autowired
    private DoctorService doctorService;
    
    @PostMapping("/add")
    public String addAvailability(
            @PathVariable Long doctorId,
            @RequestParam("dayOfWeek") DayOfWeek dayOfWeek,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime
    ) {
        Doctor doctor = doctorService.findById(doctorId);
        DoctorAvailability slot = new DoctorAvailability(doctor, dayOfWeek, startTime, endTime);
        doctorAvailabilityService.saveAvailability(slot);
        return "redirect:/doctors/" + doctorId + "/availability";
    }

    // You can add methods for listing, editing, and deleting slots as needed
}
