package com.bookstore.waha.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerID")
    private Long customerID;

    @NotBlank(message = "First name is required")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "streetNumber")
    private String streetNumber;

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "Province")
    private String province;

    @Column(name = "Country")
    private String country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Long getCustomerID() { return customerID; }
    public void setCustomerID(Long customerID) { this.customerID = customerID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getStreetNumber() { return streetNumber; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }

    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
