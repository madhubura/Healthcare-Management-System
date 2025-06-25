package com.example.onlinehealthcare.repository;


import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

// List<Prescription> findByAppointmentPatientId(Long patientId);

    //List<Prescription> findByAppointmentDoctorId(Long doctorId);
	
   // List<Prescription> findByPatient(Patient patient);
    List<Prescription> findByAppointment_Doctor(Doctor doctor);
    List<Prescription> findByAppointment_Patient(Patient patient);
    
    @Query("SELECT p FROM Prescription p JOIN FETCH p.appointment a JOIN FETCH a.doctor d WHERE a.patient = :patient")
    List<Prescription> findByPatientWithDoctor(@Param("patient") Patient patient);



   // List<Prescription> findByPatient(Patient patient);

}
