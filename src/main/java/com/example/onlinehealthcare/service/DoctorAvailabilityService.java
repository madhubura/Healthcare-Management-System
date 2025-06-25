package com.example.onlinehealthcare.service;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface DoctorAvailabilityService {
    List<DoctorAvailability> getAvailabilityForDoctor(Long doctorId);
    List<DoctorAvailability> getAvailabilityForDoctorOnDay(Long doctorId, DayOfWeek dayOfWeek);
    DoctorAvailability saveAvailability(DoctorAvailability availability);
    void deleteAvailability(Long id);
    List<DoctorAvailability> findAll();
    
    
   // List<DoctorAvailability> findAll();
    DoctorAvailability findById(Long id);
  //  void saveAvailability(DoctorAvailability slot);
    void updateAvailability(Long id, Doctor doctor, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
    void deleteById(Long id);
    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);

    List<Map<String, String>> getAvailableTimeSlots(Long doctorId, LocalDate date, int slotMinutes);

}
