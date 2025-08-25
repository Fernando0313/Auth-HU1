package com.first.challenge.api;

import com.first.challenge.api.config.UserPath;
import com.first.challenge.api.dto.CreateUserDto;
import com.first.challenge.api.dto.UserExistsResponseDto;
import com.first.challenge.api.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
@Tag(
        name = "Usuarios",
        description = "Operaciones relacionadas con la gestión de usuarios, incluyendo registro, validación y consulta de información de usuarios"
)
public class RouterRest {
    private final UserPath userPath;
    private final Handler userHandler;

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    beanClass = Handler.class,
                    beanMethod = "listenSaveUser",
                    operation = @Operation(
                            operationId = "saveUser",
                            summary = "Registrar un nuevo usuario",
                            description = "Crea un nuevo usuario proporcionando sus datos personales básicos, incluyendo nombres y apellidos separados.",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = CreateUserDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CreateUserDto.class)
                                            )),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ErrorResponse.class)
                                            )),
                                    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ErrorResponse.class)
                                            ))
                            }
                    )
            ),

    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(userPath.getUsers()), handler::listenSaveUser);
//                .andRoute(GET(userPath.getUsers()), handler::listenGetUserByIdentityDocument);
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    beanClass = Handler.class,
                    beanMethod = "listenGetUserByIdentityDocument",
                    operation = @Operation(
                            operationId = "getUserByIdentityDocument",
                            summary = "Consultar usuario por documento",
                            description = "Obtiene la información de un usuario registrado a partir de su número de documento de identidad.",
                            parameters = {
                                    @io.swagger.v3.oas.annotations.Parameter(name = "identityDocument", description = "Número de documento del usuario", required = true)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = UserExistsResponseDto.class)
                                            )),
                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ErrorResponse.class)
                                            )),
                                    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ErrorResponse.class)
                                            ))
                            }
                    )
            )

    })
    @Bean
    public RouterFunction<ServerResponse> getUserRouter() {
        return route(GET(userPath.getUsers()), userHandler::listenGetUserByIdentityDocument);
    }
}
