package com.example.onlinehealthcare.service;


import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);
    Patient findByUser_Id(Long userId);

    Patient findByUser(User user);

    Patient savePatient(Patient patient);

//    Patient updatePatient(Long id, Patient patient);

    void deletePatient(Long id);
//    Patient findByUserEmail(String email);

//	Optional<Patient> getPatientByEmail(String email);
	void registerPatient(User user, Patient patient);

	Patient getPatientByUserId(Long userId);

	Patient findByUserEmail(String email);

	Optional<Patient> getPatientByEmail(String email);

}

