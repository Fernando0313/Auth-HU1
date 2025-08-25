package com.first.challenge.r2dbc;

import com.first.challenge.model.user.User;
import com.first.challenge.model.user.gateways.UserRepository;
import com.first.challenge.r2dbc.entity.UserEntity;
import com.first.challenge.r2dbc.helper.ReactiveAdapterOperations;

import org.reactivecommons.utils.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
    String,
        UserReactiveRepository
> implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserReactiveRepositoryAdapter.class);


    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.Map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return super.repository.existsByEmail(email)
                .doOnSuccess(exists -> logger.info("[UserReactiveRepositoryAdapter.existsByEmail] el email={} existe={}", email, exists))
                .doOnError(error -> logger.error("[UserReactiveRepositoryAdapter.existsByEmail] Error al consultar email={} - Causa: {}", email, error.getMessage(), error));
    }

    @Override
    public Mono<Boolean> existsByIdentityDocument(String identityDocument) {
        return super.repository.existsByIdentityDocument(identityDocument)
                .doOnSuccess(exists -> logger.info("[UserReactiveRepositoryAdapter.existsByIdentityDocument] el identityDocument={} existe={}", identityDocument, exists))
                .doOnError(error -> logger.error("[UserReactiveRepositoryAdapter.existsByIdentityDocument] Error al consultar email={} - Causa: {}", identityDocument, error.getMessage(), error));
    }

    @Transactional
    @Override
    public Mono<User> save(User user) {
        logger.info("[UserReactiveRepositoryAdapter] Guardando usuario email={} salario={} ", user.getEmail(), user.getBaseSalary());
        return super.save(user)
                .doOnSuccess(saved -> logger.info("[UserReactiveRepositoryAdapter.save] Usuario registrado id={} email={}", saved.getId(), saved.getEmail()))
                .doOnError(error -> logger.error("[UserReactiveRepositoryAdapter.save] Error registrando usuario email={} - Causa: {}", user.getEmail(), error.getMessage(), error))
                .doFinally(signal -> logger.info("[UserReactiveRepositoryAdapter.save] Finalizó flujo con señal={}", signal));
    }
    @Override
    public Mono<User> findById(String id) {
        return super.findById(id);
    }
}
