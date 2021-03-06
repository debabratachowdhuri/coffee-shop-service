package com.coffeeshop.domain.user.staff;

import com.coffeeshop.constant.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Chandan Vishwakarma
 */
public class StaffRegisterRequestDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String contactNumber;
    private String name;
    private GenderType gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
