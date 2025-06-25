package com.example.onlinehealthcare.service.impl;

import java.util.List;

import java.util.Optional;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.User;
import com.example.onlinehealthcare.repository.DoctorRepository;
import com.example.onlinehealthcare.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {
	
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setName(updatedDoctor.getName());
            existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
            existingDoctor.setEmail(updatedDoctor.getEmail());
            existingDoctor.setPhone(updatedDoctor.getPhone());
           // existingDoctor.setPassword(updatedDoctor.getPassword()); // Add this if you want to update password
            return doctorRepository.save(existingDoctor);
        } else {
            return null;
        }
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

	@Override
	public Optional<Doctor> authenticateDoctor(String email, String password) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Doctor> getDoctorByUser(User user) {
		return doctorRepository.findByUser(user);

	}

	@Override
	public List<Doctor> getAllDoctorsWithUser() {
		return doctorRepository.findAllWithUser();

	}

	@Override
	public Doctor findById(Long doctorId) {
		return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
	}

	@Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }


//	@Override
//	public Optional<Doctor> authenticateDoctor(String email, String password) {
//		return doctorRepository.findByEmailAndPassword(email, password);
//
//	}
}