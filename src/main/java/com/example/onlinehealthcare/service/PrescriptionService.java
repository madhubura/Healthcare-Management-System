package com.example.onlinehealthcare.service;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
	
    List<Prescription> getAllPrescriptions();

    Optional<Prescription> getPrescriptionById(Long id);

    Prescription savePrescription(Prescription prescription);

    void deletePrescription(Long id);

    List<Prescription> getPrescriptionsByPatient(Patient patient);
    List<Prescription> getPrescriptionsByDoctor(Doctor doctor);


	
//    Prescription savePrescription(Prescription prescription);
//    List<Prescription> getAllPrescriptions();
//    Optional<Prescription> getPrescriptionById(Long id);
//    void deletePrescription(Long id);
//
//    List<Prescription> getPrescriptionsByPatient(Long patientId);
//    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
}
