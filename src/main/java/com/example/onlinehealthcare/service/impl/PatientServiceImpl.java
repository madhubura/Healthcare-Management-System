package com.example.onlinehealthcare.service.impl;

import java.util.List;

import java.util.Optional;

import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.PatientRepository;
import com.example.onlinehealthcare.repository.UserRepository;
import com.example.onlinehealthcare.service.PatientService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

	@Override
	public Patient findByUser_Id(Long userId) {
		return patientRepository.findByUser_Id(userId).orElse(null);
	}

	

	@Override
	public Patient findByUserEmail(String email) {
		return patientRepository.findByEmail(email).orElse(null);
	}

	@Override
	public void registerPatient(User user, Patient patient) {
		// Save User first
        userRepository.save(user);

        // Link Patient to User
        patient.setUser(user);

        // Save Patient
        patientRepository.save(patient);

		
	}

	@Override
	public Patient findByUser(User user) {
		return patientRepository.findByUser(user).orElse(null);
	}

	@Override
	public Patient getPatientByUserId(Long userId) {
		return patientRepository.findByUserId(userId);
	}
	

//	@Override
//	public Patient findByUser_Id(Long userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}



	//@Override
//	public Patient updatePatient(Long id, Patient patient) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
