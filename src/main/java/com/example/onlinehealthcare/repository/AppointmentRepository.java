package com.example.onlinehealthcare.repository;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
//	List<Appointment> findByDoctorId(Long doctorId);
	List<Appointment> findByPatient(Patient patient);
	List<Appointment> findByDoctor(Doctor doctor);
	//List<Appointment> findByPatientIdAndStatusNot(Long patientId, String status);
	List<Appointment> findByPatientAndStatusNot(Patient patient, String status);
	long countByStatus(String status);

	@Query("SELECT a FROM Appointment a JOIN FETCH a.doctor d JOIN FETCH d.user WHERE a.patient = :patient")
    List<Appointment> findByPatientWithDoctor(@Param("patient") Patient patient);

	List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);


//    // Find appointments by patient id
//    List<Appointment> findByPatientId(Long patientId);
//
//    // Find appointments by doctor id
//    List<Appointment> findByDoctorId(Long doctorId);
//
//    // Find appointments by doctor id and date
//    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
//    
//    @Query("SELECT a FROM Appointment a WHERE " +
//            "LOWER(a.patient.name) LIKE LOWER(CONCAT('%', :patientName, '%')) AND " +
//            "LOWER(a.doctor.name) LIKE LOWER(CONCAT('%', :doctorName, '%'))")
//     Page<Appointment> findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCase(
//         @Param("patientName") String patientName,
//         @Param("doctorName") String doctorName,
//         Pageable pageable
//     );

}

