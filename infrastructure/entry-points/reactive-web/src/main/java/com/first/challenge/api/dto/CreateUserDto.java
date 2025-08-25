package com.first.challenge.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserDto (String firstName, String lastName, String identityDocument, LocalDate birthDate,
                             String address, String phoneNumber, String email, BigDecimal baseSalary){}