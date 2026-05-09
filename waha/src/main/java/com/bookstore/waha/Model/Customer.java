package com.bookstore.waha.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerID")
    private long customerID;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "streetNumber")
    private String streetNumber;

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "Province")
    private String Province;

    @Column(name = "Country")
    private String Country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Email
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;




    public Long getCustomerID() {
        return customerID;
    }


    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
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
