package com.example.onlinehealthcare.service.impl;

import com.example.onlinehealthcare.entity.Appointment;
import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.DoctorAvailability;
import com.example.onlinehealthcare.enums.DayOfWeek;
import com.example.onlinehealthcare.repository.DoctorAvailabilityRepository;
import com.example.onlinehealthcare.service.AppointmentService;
import com.example.onlinehealthcare.service.DoctorAvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository availabilityRepository;
    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;
    @Autowired
    private AppointmentService appointmentService; // <-- Add this line


    @Override
    public List<DoctorAvailability> getAvailabilityForDoctor(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<DoctorAvailability> getAvailabilityForDoctorOnDay(Long doctorId, DayOfWeek dayOfWeek) {
        return availabilityRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek);
    }

    @Override
    public DoctorAvailability saveAvailability(DoctorAvailability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

	@Override
	public List<DoctorAvailability> findAll() {
		return doctorAvailabilityRepository.findAll();

	}

	@Override
	public DoctorAvailability findById(Long id) {
        Optional<DoctorAvailability> opt = doctorAvailabilityRepository.findById(id);
        return opt.orElse(null);
	}

	@Override
	public void updateAvailability(Long id, Doctor doctor, DayOfWeek dayOfWeek, LocalTime startTime,
			LocalTime endTime) {
		DoctorAvailability slot = findById(id);
        if (slot != null) {
            slot.setDoctor(doctor);
            slot.setDayOfWeek(dayOfWeek);
            slot.setStartTime(startTime);
            slot.setEndTime(endTime);
            doctorAvailabilityRepository.save(slot);
        }		
	}

	@Override
	public void deleteById(Long id) {
		doctorAvailabilityRepository.deleteById(id);		
	}

	@Override
	public List<DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek) {
		return doctorAvailabilityRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek);

	}

	@Override
	public List<Map<String, String>> getAvailableTimeSlots(Long doctorId, LocalDate date, int slotMinutes) {
	    DayOfWeek dayOfWeek = DayOfWeek.valueOf(date.getDayOfWeek().name());
	    List<DoctorAvailability> availabilities = doctorAvailabilityRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek);

	    // Get already booked slots for this doctor and date
	    List<Appointment> bookedAppointments = appointmentService.getAppointmentsForDoctorAndDate(doctorId, date);

	    List<Map<String, String>> slots = new ArrayList<>();
	    for (DoctorAvailability availability : availabilities) {
	        LocalTime slotStart = availability.getStartTime();
	        LocalTime slotEnd = availability.getEndTime();

	        while (slotStart.plusMinutes(slotMinutes).compareTo(slotEnd) <= 0) {
	            LocalTime nextSlot = slotStart.plusMinutes(slotMinutes);

	            // Fix: assign to a final variable for lambda use
	            final LocalTime currentSlotStart = slotStart;
	            final LocalTime currentSlotEnd = nextSlot;

	            // Check if this slot is already booked
	            boolean isBooked = bookedAppointments.stream().anyMatch(app ->
	                app.getStartTime().equals(currentSlotStart) && app.getEndTime().equals(currentSlotEnd)
	            );

	            if (!isBooked) {
	                slots.add(Map.of(
	                    "startTime", currentSlotStart.toString(),
	                    "endTime", currentSlotEnd.toString()
	                ));
	            }
	            slotStart = nextSlot;
	        }
	    }
	    return slots;
	}

}
