package com.first.challenge.usecase.user;


import com.first.challenge.model.user.User;
import com.first.challenge.model.user.exceptions.BusinessException;
import com.first.challenge.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private UserRepository userRepository;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userUseCase = new UserUseCase(userRepository);
    }

    // Caso exitoso
    @Test
    void saveUser_success() {
        User user =  new User(
                null,
                "John",
                "Doe",
                "12345678",
                null,
                null,
                null,
                "john@test.com",
                BigDecimal.valueOf(5000)
        );

        when(userRepository.existsByEmail(any())).thenReturn(Mono.just(false));
        when(userRepository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNextMatches(saved -> saved.getEmail().equals("john@test.com"))
                .verifyComplete();

        verify(userRepository).existsByEmail("john@test.com");
        verify(userRepository).save(user);
    }

    // Validación de campo obligatorio
    @Test
    void saveUser_missingFirstName_throwsBusinessException() {
        User user =  new User(
                null,
                null,   // firstName faltante
                "Doe",
                "12345678",
                null,
                null,
                null,
                "john@test.com",
                BigDecimal.valueOf(5000)
        );

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(e -> e instanceof BusinessException &&
                        e.getMessage().equals("El campo nombres es obligatorio") &&
                        ((BusinessException) e).getCode().equals("MANDATORY"))
                .verify();
    }

    @Test
    void saveUser_invalidEmail_throwsBusinessException() {
        User user =  new User(
                null,
                "John",
                "Doe",
                "12345678",
                null,
                null,
                null,
                "invalid-email",  // correo inválido
                BigDecimal.valueOf(5000)
        );

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(e -> e instanceof BusinessException &&
                        e.getMessage().equals("Formato de correo_electronico inválido") &&
                        ((BusinessException) e).getCode().equals("INVALID_EMAIL"))
                .verify();
    }

    @Test
    void saveUser_salaryTooHigh_throwsBusinessException() {
        User user =  new User(
                null,
                "John",
                "Doe",
                "12345678",
                null,
                null,
                null,
                "john@test.com",
                BigDecimal.valueOf(20000000)
        );

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(e -> e instanceof BusinessException &&
                        e.getMessage().equals("El salario_base debe estar entre 0 y 15000000") &&
                        ((BusinessException) e).getCode().equals("INVALID_SALARY"))
                .verify();
    }

    @Test
    void saveUser_emailAlreadyExists_throwsBusinessException() {
        User user =  new User(
                null,
                "John",
                "Doe",
                "12345678",
                null,
                null,
                null,
                "john@test.com",
                BigDecimal.valueOf(5000)
        );

        when(userRepository.existsByEmail(any())).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(e -> e instanceof BusinessException &&
                        e.getMessage().equals("El correo_electronico ya está registrado") &&
                        ((BusinessException) e).getCode().equals("EMAIL_ALREADY_EXISTS"))
                .verify();

        verify(userRepository).existsByEmail("john@test.com");
        verify(userRepository, never()).save(any());
    }

    // existsByIdentityDocument
    @Test
    void existsByIdentityDocument_success() {
        when(userRepository.existsByIdentityDocument("12345678")).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.existsByIdentityDocument("12345678"))
                .expectNext(true)
                .verifyComplete();

        verify(userRepository).existsByIdentityDocument("12345678");
    }
}