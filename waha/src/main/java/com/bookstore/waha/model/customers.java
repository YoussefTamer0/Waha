package com.bookstore.waha.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="customers")
public class customers{
	@Id
	@Column(name="customerID")
	private Integer customerID;
	@Column(name="firstName")
	private String Firstname;
	@Column(name="lastName")
	private String Lastname;
	@Column(name="streetNumber")
	private String streetNumber;
	@Column(name="streetName")
	private String streetName;
	@Column(name="postalCode")
	private String postalCode;
	@Column(name="Province")
	private String Province;
	@Column(name="Country")
	private String Country;
	@Column(name="phoneNumber")
	private String phoneNumber;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;

	public customers(Integer customerID, String firstname, String lastname, String streetNumber, String streetName,
			String postalCode, String province, String country, String phoneNumber, String email, String password) {
		
		this.customerID = customerID;
		Firstname = firstname;
		Lastname = lastname;
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.postalCode = postalCode;
		Province = province;
		Country = country;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public String getFirstname() {
		return Firstname;
	}

	public void setFirstname(String firstname) {
		Firstname = firstname;
	}

	public String getLastname() {
		return Lastname;
	}

	public void setLastname(String lastname) {
		Lastname = lastname;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	
}
