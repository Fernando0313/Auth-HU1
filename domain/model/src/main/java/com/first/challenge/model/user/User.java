package com.first.challenge.model.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String identityDocument;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    private String email;
    private BigDecimal baseSalary;

    public User() {
    }

    public User(String id,String firstName, String lastName,String identityDocument, LocalDate birthDate,
                String address, String phoneNumber, String email,
                BigDecimal baseSalary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.baseSalary = baseSalary;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getIdentityDocument() { return identityDocument; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public BigDecimal getBaseSalary() { return baseSalary; }


    public void setId(String id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setIdentityDocument(String identitiDocument) { this.identityDocument = identitiDocument; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User u)) return false;
        return Objects.equals(email, u.email);
    }

    @Override public int hashCode() {
        return Objects.hash(email);
    }
}