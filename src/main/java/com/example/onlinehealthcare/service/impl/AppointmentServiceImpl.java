package com.example.onlinehealthcare.service.impl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.repository.AppointmentRepository;
import com.example.onlinehealthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.onlinehealthcare.*;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
    	return appointmentRepository.findByPatientWithDoctor(patient);
    }

	@Override
	public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
		return appointmentRepository.findByDoctor(doctor);

	}

	@Override
	public void updateStatus(Long appointmentId, String status) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
		        .orElseThrow(() -> new RuntimeException("Appointment not found"));
		    appointment.setStatus(status);
		    appointmentRepository.save(appointment);
		
	}

//	@Override
//	public List<Appointment> getActiveAppointmentsForPatient(Long patientId) {
//		return appointmentRepository.findByPatientIdAndStatusNot(patientId, "Cancelled");
//	}

	@Override
	public void cancelAppointment(Long appointmentId, Patient patient) {
	    Appointment appointment = appointmentRepository.findById(appointmentId)
	        .orElseThrow(() -> new RuntimeException("Appointment not found"));
	    if (!appointment.getPatient().getId().equals(patient.getId())) {
	        throw new RuntimeException("Unauthorized cancellation");
	    }
	    appointment.setStatus("Cancelled");
	    appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> getAppointmentsByPatientAndStatusNot(Patient patient, String status) {
		return appointmentRepository.findByPatientAndStatusNot(patient, status);

	}

	@Override
	public Appointment findById(Long id) {
		return appointmentRepository.findById(id).orElse(null);

	}

	@Override
	public List<Appointment> getAppointmentsForDoctorAndDate(Long doctorId, LocalDate date) {
		return appointmentRepository.findByDoctorIdAndDate(doctorId, date);

	}
}

    


//@Service
//public class AppointmentServiceImpl implements AppointmentService {
//
//    @Autowired
//    private AppointmentRepository appointmentRepository;
//
//    @Override
//    public List<Appointment> getAllAppointments() {
//        return appointmentRepository.findAll();
//    }
//
//    @Override
//    public Appointment getAppointmentById(Long id) {
//        return appointmentRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public Appointment saveAppointment(Appointment appointment) {
//        return appointmentRepository.save(appointment);
//    }
//
//    @Override
//    public void deleteAppointment(Long id) {
//        appointmentRepository.deleteById(id);
//    }
//
          //    @Override
//    public void cancelAppointment(Long id) {
//        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
//        if (appointmentOpt.isPresent()) {
//            Appointment appointment = appointmentOpt.get();
//            appointment.setStatus(Appointment.Status.CANCELLED);
//            appointmentRepository.save(appointment);
//        }
        //    }
//    
//   @Override
//    public List<Appointment> findByPatientNameAndDoctorName(String patientName, String doctorName) {
//        if ((patientName == null || patientName.isEmpty()) && (doctorName == null || doctorName.isEmpty())) {
//            return appointmentRepository.findAll();
//        }
//
//       // You can implement this either via custom JPQL or using Criteria API, for simplicity:
//        return appointmentRepository.findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCase(
//            patientName != null ? patientName : "",
//            doctorName != null ? doctorName : ""
//        );
//    }
//
//    
//    @Override
//    public Page<Appointment> getAllAppointments(Pageable pageable) {
//        return appointmentRepository.findAll(pageable);
//    }
//
//    @Override
//    public Page<Appointment> findByPatientNameAndDoctorName(String patientName, String doctorName, Pageable pageable) {
//        if ((patientName == null || patientName.isEmpty()) && (doctorName == null || doctorName.isEmpty())) {
//            return appointmentRepository.findAll(pageable);
//        }
//        return appointmentRepository.findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCase(
//            patientName != null ? patientName : "",
//            doctorName != null ? doctorName : "",
//            pageable
//        );
//    }
//
//	@Override
//	public List<Appointment> findByPatientNameAndDoctorName(String patientName, String doctorName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
