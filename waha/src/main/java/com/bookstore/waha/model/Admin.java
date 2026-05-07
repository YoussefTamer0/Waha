package com.bookstore.waha.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="admins")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="adminID")
	private Long adminID;

	@NotBlank(message = "First name is required")
	@Column(name="firstName")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column(name="lastName")
	private String lastName;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Column(name="email")
	private String email;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotBlank(message = "Password is required")
	@Column(name="password")
	private String password;
	public Long getAdminID() {
		return adminID;
	}
	public void setAdminID(Long adminID) {
		this.adminID = adminID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public Admin(Long adminID, String firstName, String lastName, String email, String password) {
		this.adminID = adminID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	public Admin(){}
}
