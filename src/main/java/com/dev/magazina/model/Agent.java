package com.dev.magazina.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^([SC])$")
    private String type;

    @NotNull
    @Size(min = 3, max = 100, message = "Emri duhet te jete 3 - 100 karaktere!")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 100, message = "Mbiemri duhet te jete 3 - 100 karaktere!")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 100, message = "Emri tregtar duhet te jete 3 - 100 karaktere!")
    private String businessName;

    @Email(message = "Emaili nuk eshte i sakte!")
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 3, max = 100, message = "Adresa duhet te jete 3 - 100 karaktere!")
    private String address;

    @Range(min = -90, max = 90, message = "Vlera te gabuara per gjeresine gjeografike!")
    private String latitude;

    @Range(min = -180, max = 180, message = "Vlera te gabuara per gjatesine gjeografike!")
    private String longitude;

    public Agent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
