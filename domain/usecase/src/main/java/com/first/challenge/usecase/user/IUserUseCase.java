package com.first.challenge.usecase.user;

import com.first.challenge.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserUseCase {
    Mono<User> saveUser(User user);
    Mono<User> GetUserById(String id);
    Mono<Boolean> existsByIdentityDocument(String identityDocument);
}
