package com.example.onlinehealthcare.repository;


import com.example.onlinehealthcare.entity.Patient;
import com.example.onlinehealthcare.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	Optional<Patient> findByEmail(String email);
	
	Optional<Patient> findByUserEmail(String email);
	Optional<Patient> findByUser(User user);


	// Assuming Patient has a field: private User user;
	Optional<Patient> findByUser_Id(Long userId); // Fix here
	
	//Patient findByUserEmail(String email);

	// Finds a patient by the user_id foreign key
    Patient findByUserId(Long userId);

}
