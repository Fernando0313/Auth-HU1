package com.first.challenge.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;


import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "CreateUserDto", description = "DTO para crear un nuevo usuario en el sistema")
public record CreateUserDto(
        @Schema(description = "Primer nombre del usuario", example = "Fernando", required = true)
        String firstName,

        @Schema(description = "Apellido del usuario", example = "Perez", required = true)
        String lastName,

        @Schema(description = "Número de documento de identidad", example = "111222334", required = true)
        String identityDocument,

        @Schema(description = "Fecha de nacimiento del usuario", example = "1990-05-14")
        LocalDate birthDate,

        @Schema(description = "Dirección del usuario", example = "Av. Siempre Viva 742, Lima")
        String address,

        @Schema(description = "Número de teléfono del usuario", example = "+51 987654321")
        String phoneNumber,

        @Schema(description = "Correo electrónico del usuario", example = "juan.perez233@example.com", required = true)
        String email,

        @Schema(description = "Salario base del usuario", example = "3500.75", required = true)
        BigDecimal baseSalary
) {}