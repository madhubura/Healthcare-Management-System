
package com.example.onlinehealthcare.entity;

import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // <-- Add
    @Column
    private String phone;

    @Column
    private String address;
    
    @Column(nullable = false, unique = true)
    private String email;
     
//    @Column(nullable = false)
//    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
 
    // Constructors
    public Patient() {}

    public Patient(String phone, String address,String email,String name, User user ) {
        this.phone = phone;
        this.address = address;
        this.user = user;
        this.email=email;
//        this.password=password;
        this.name=name;
    }

    public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
//	 public String getPassword() {
//			return password;
//		}
//
//		public void setPassword(String password) {
//			this.password = password;
//		}

	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}




//package com.example.onlinehealthcare.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "patients")
//public class Patient {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column
//    private String phone;
//
//    @Column
//    private String address;
//
//    // Constructors
//    public Patient() {}
//
//    public Patient(String name, String email, String password, String phone, String address) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.phone = phone;
//        this.address = address;
//    }
//
//    // Getters and setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//}
