package com.first.challenge.api;

import com.first.challenge.api.config.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class RouterRest {
    private final UserPath userPath;
    private final Handler userHandler;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(userPath.getUsers()), handler::listenSaveUser)
                .andRoute(GET(userPath.getUsers()), handler::listenGetUserByIdentityDocument);
    }
}
