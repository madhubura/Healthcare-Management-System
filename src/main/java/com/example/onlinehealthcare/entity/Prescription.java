package com.example.onlinehealthcare.entity;

import jakarta.persistence.*;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String medicineDetails;

    @Column(nullable = true)
    private String notes;

    public Prescription() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public String getMedicineDetails() { return medicineDetails; }
    public void setMedicineDetails(String medicineDetails) { this.medicineDetails = medicineDetails; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}



//@Entity
////@Table(name = "prescriptions")
//public class Prescription {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "patient_id", nullable = false)
//    private Patient patient;
//
//    @ManyToOne
//    @JoinColumn(name = "doctor_id", nullable = false)
//    private Doctor doctor;
//
//    @Column(nullable = false)
//    private String medicineDetails;
//
//    @Column(nullable = true)
//    private String notes;
//    @ManyToOne
//    @JoinColumn(name = "appointment_id")
//    private Appointment appointment;
//
//
//    public Prescription() {}
//
//    // Getters and Setters
// // Getter
//    public Appointment getAppointment() {
//        return appointment;
//    }
//
//    // Setter
//    public void setAppointment(Appointment appointment) {
//        this.appointment = appointment;
//    }
//
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public Patient getPatient() { return patient; }
//    public void setPatient(Patient patient) { this.patient = patient; }
//
//    public Doctor getDoctor() { return doctor; }
//    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
//
//    public String getMedicineDetails() { return medicineDetails; }
//    public void setMedicineDetails(String medicineDetails) { this.medicineDetails = medicineDetails; }
//
//    public String getNotes() { return notes; }
//    public void setNotes(String notes) { this.notes = notes; }
//}
