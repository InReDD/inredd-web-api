package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Sex;
import java.time.LocalDate;

public class PatientCreateDTO {

    private String fullName;
    private LocalDate dateOfBirth;
    private Sex sex;
    private String address;

    // No-argument constructor
    public PatientCreateDTO() {
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}