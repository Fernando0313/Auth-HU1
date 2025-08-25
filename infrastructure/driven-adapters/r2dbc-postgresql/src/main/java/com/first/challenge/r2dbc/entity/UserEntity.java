package com.first.challenge.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("identity_document")
    private String identityDocument;
    @Column("birth_date")
    private LocalDate birthDate;
    private String address;
    @Column("phone_number")
    private String phoneNumber;
    private String email;
    @Column("base_salary")
    private BigDecimal baseSalary;

}
