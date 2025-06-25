package com.example.onlinehealthcare.service.impl;

import java.util.List;

import java.util.Optional;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.Prescription;
import com.example.onlinehealthcare.repository.PrescriptionRepository;
import com.example.onlinehealthcare.service.PrescriptionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

//    @Override
//    public List<Prescription> getPrescriptionsByPatient(Patient patient) {
//        return prescriptionRepository.findByPatient(patient);
//    }

	@Override
	public List<Prescription> getPrescriptionsByDoctor(Doctor doctor) {
		return prescriptionRepository.findByAppointment_Doctor(doctor);

	}

	@Override
	public List<Prescription> getPrescriptionsByPatient(Patient patient) {
	    return prescriptionRepository.findByAppointment_Patient(patient);
	}

}
