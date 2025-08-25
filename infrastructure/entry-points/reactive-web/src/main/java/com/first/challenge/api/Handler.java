package com.first.challenge.api;

import com.first.challenge.api.dto.CreateUserDto;
import com.first.challenge.api.mapper.UserDtoMapper;
import com.first.challenge.model.user.User;
import com.first.challenge.usecase.user.IUserUseCase;
import com.first.challenge.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;


@RequiredArgsConstructor
@Component
public class Handler {
    private final IUserUseCase userUseCase;
    private  final UserDtoMapper userDtoMapper;




    public Mono<ServerResponse> listenGetUserById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return userUseCase.GetUserById(id)
                .flatMap(task -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(task))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDto.class)
                .map(userDtoMapper::toEntity) // convert DTO -> User entity
                .flatMap(userUseCase::saveUser) // save User
                .map(userDtoMapper::toResponse) // convert back User -> DTO
                .flatMap(savedDto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedDto));
    }
    public Mono<ServerResponse> listenGetUserByIdentityDocument(ServerRequest serverRequest) {
        String identityDocument = serverRequest.queryParam("identityDocument")
                .orElse(""); // Si no se pasa el query param

        if (identityDocument.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("Falta el parámetro 'identityDocument'");
        }

        return userUseCase.existsByIdentityDocument(identityDocument)
                .flatMap(exists -> {
                    if (exists) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("exists", true));
                    } else {
                        return ServerResponse.noContent().build();
                    }
                });
    }


}
