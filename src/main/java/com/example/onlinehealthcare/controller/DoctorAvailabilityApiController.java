package com.example.onlinehealthcare.controller;

import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek;
import com.example.onlinehealthcare.service.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availability")
public class DoctorAvailabilityApiController {

    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;
    
    
    @GetMapping("/doctor/{doctorId}/date/{date}")
    public List<Map<String, String>> getAvailableTimes(
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        int slotMinutes = 30; // Change to 60 for 1-hour slots if needed
        return doctorAvailabilityService.getAvailableTimeSlots(doctorId, date, slotMinutes);
    }


//    @GetMapping("/doctor/{doctorId}/date/{date}")
//    public List<java.util.Map<String, String>> getAvailableTimes(
//            @PathVariable Long doctorId,
//            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
//    ) {
//        DayOfWeek dayOfWeek = DayOfWeek.valueOf(date.getDayOfWeek().name());
//        List<DoctorAvailability> slots = doctorAvailabilityService.getAvailabilityForDoctorOnDay(doctorId, dayOfWeek);
//        return slots.stream()
//                .map(slot -> java.util.Map.of(
//                        "startTime", slot.getStartTime().toString(),
//                        "endTime", slot.getEndTime().toString()))
//                .collect(Collectors.toList());
//    }

}
