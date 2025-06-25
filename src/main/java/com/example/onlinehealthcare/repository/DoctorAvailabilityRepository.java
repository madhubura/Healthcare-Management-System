package com.example.onlinehealthcare.repository;

import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek; // <-- Correct import!
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctorId(Long doctorId);
    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);
}
