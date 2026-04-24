package com.bookstore.waha.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="admins")
public class admin {
 @Id
 @Column(name="adminID")
 private Integer adminID;
 @Column(name="firstName")
 private String firstName;
 @Column(name="lastName")
 private String lastName;
 @Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	public Integer getAdminID() {
		return adminID;
	}
	public void setAdminID(Integer adminID) {
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
	public admin(){}
	public admin(Integer adminID, String firstName, String lastName, String email, String password) {
		
		this.adminID = adminID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

 
}
