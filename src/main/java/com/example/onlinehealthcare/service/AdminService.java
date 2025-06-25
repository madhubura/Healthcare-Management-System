package com.example.onlinehealthcare.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;

public interface AdminService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long id);
    void saveDoctor(Doctor doctor);
    
    void deleteDoctor(Long id);

    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    void deletePatient(Long id);

    List<Appointment> getAllAppointments();
    long countByStatus(String status); // <-- Add this

}