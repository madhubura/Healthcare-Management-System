package com.example.onlinehealthcare.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    Appointment saveAppointment(Appointment appointment);

    void deleteAppointment(Long id);

    List<Appointment> getAppointmentsByPatient(Patient patient);
    void save(Appointment appointment);
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
    void updateStatus(Long appointmentId, String status);
    Appointment findById(Long id);

    
//for appointment cancellation
   // List<Appointment> getActiveAppointmentsForPatient(Long patientId);
    List<Appointment> getAppointmentsByPatientAndStatusNot(Patient patient, String status);
    void cancelAppointment(Long appointmentId, Patient patient);
    List<Appointment> getAppointmentsForDoctorAndDate(Long doctorId, LocalDate date);


//	List<Appointment> getAllAppointments();
//
//    Appointment getAppointmentById(Long id);
//
//    Appointment saveAppointment(Appointment appointment);
//
//    void deleteAppointment(Long id);
//
//    void cancelAppointment(Long id);  // New method to cancel appointment
//   List<Appointment> findByPatientNameAndDoctorName(String patientName, String doctorName);
//    Page<Appointment> getAllAppointments(Pageable pageable);
//    Page<Appointment> findByPatientNameAndDoctorName(String patientName, String doctorName, Pageable pageable);
//

}

