package com.example.onlinehealthcare.repository;

import com.example.onlinehealthcare.entity.Doctor;
import com.example.onlinehealthcare.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	//Optional<Doctor> findByEmail(String email);

	List<Doctor> findByEmail(String email);
	//Optional<Doctor> findByEmailAndPassword(String email, String password);
    Optional<Doctor> findByUser(User user);
	// Find doctor by email (useful for admin lookups)
   // Optional<Doctor> findByEmail(String email);

    // Find doctor by User reference (for linking user and doctor)
   // Optional<Doctor> findByUser(User user);

    // Find all doctors (already provided by JpaRepository, but you can add custom filters)
    List<Doctor> findAll();

    // Find doctor by specialization (optional, if you want to filter by specialization)
    List<Doctor> findBySpecialization(String specialization);
    Doctor findByUserId(Long userId);

 // Fetch all doctors with their user (for name)
    @Query("SELECT d FROM Doctor d JOIN FETCH d.user")
    List<Doctor> findAllWithUser();


}
