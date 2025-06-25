package com.example.onlinehealthcare.service;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.User;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(Long id);
    Doctor saveDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor updatedDoctor);
    void deleteDoctor(Long id);
    Optional<Doctor> authenticateDoctor(String email, String password);
    Optional<Doctor> getDoctorByUser(User user);

    List<Doctor> getAllDoctorsWithUser();
    Doctor findById(Long doctorId);
    List<Doctor> findAll();

}

