package com.example.onlinehealthcare.service.impl;
import com.example.onlinehealthcare.exception.EmailAlreadyExistsException;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.repository.AppointmentRepository;
import com.example.onlinehealthcare.repository.DoctorRepository;
import com.example.onlinehealthcare.repository.PatientRepository;
import com.example.onlinehealthcare.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
 
    public void saveDoctor(Doctor doctor) {
        List<Doctor> existingDoctors = doctorRepository.findByEmail(doctor.getEmail());
        boolean isDuplicate = existingDoctors.stream()
            .anyMatch(d -> !d.getId().equals(doctor.getId())); // Exclude self in edit
        if (isDuplicate) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }
        doctorRepository.save(doctor);
    }



    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        return optionalDoctor.orElse(null);
    }


    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        return optionalPatient.orElse(null);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }



	@Override
	public long countByStatus(String status) {
		return appointmentRepository.countByStatus(status);

	}
}