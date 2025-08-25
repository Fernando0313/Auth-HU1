package com.first.challenge.model.user.gateways;

import com.first.challenge.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdentityDocument(String identityDocument);
    Mono<User> save(User user);
    Mono<User> findById(String id);
}
