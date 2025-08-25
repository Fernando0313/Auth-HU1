package com.first.challenge.usecase.user;

import com.first.challenge.model.user.User;
import com.first.challenge.model.user.exceptions.BusinessException;
import com.first.challenge.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;


public class UserUseCase implements  IUserUseCase{

    private final UserRepository userRepository;
    public UserUseCase(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Mono<User> saveUser(User user) {
        return Mono.just(user)
                .map(this::validateMandatory)
                .map(this::validateFormats)
                .flatMap(validUser -> userRepository.existsByEmail(validUser.getEmail())
                        .flatMap(exists -> {
                            if (Boolean.TRUE.equals(exists)) {
                                return Mono.error(new BusinessException("EMAIL_ALREADY_EXISTS", "El correo_electronico ya está registrado"));
                            }
                            return Mono.just(validUser);
                        }))
                .flatMap(u -> {
                    return userRepository.save(u);
                });
    }


    @Override
    public Mono<Boolean> existsByIdentityDocument(String identityDocument) {
        return userRepository.existsByIdentityDocument(identityDocument);
    }

    private User validateMandatory(User u) {
        if (isBlank(u.getFirstName()))
            throw new BusinessException("MANDATORY", "El campo nombres es obligatorio");
        if (isBlank(u.getLastName()))
            throw new BusinessException("MANDATORY", "El campo apellidos es obligatorio");
        if (isBlank(u.getEmail()))
            throw new BusinessException("MANDATORY", "El campo correo_electronico es obligatorio");
        if (Objects.isNull(u.getBaseSalary()))
            throw new BusinessException("MANDATORY", "El campo salario_base es obligatorio");
        return u;
    }


    private User validateFormats(User u) {
        if (!EMAIL.matcher(u.getEmail()).matches())
            throw new BusinessException("INVALID_EMAIL", "Formato de correo_electronico inválido");
        BigDecimal s = u.getBaseSalary();
        if (s.compareTo(BigDecimal.ZERO) < 0 || s.compareTo(new BigDecimal("15000000")) > 0)
            throw new BusinessException("INVALID_SALARY", "El salario_base debe estar entre 0 y 15000000");
        return u;
    }

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
