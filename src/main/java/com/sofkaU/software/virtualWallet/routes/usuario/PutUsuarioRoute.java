package com.sofkaU.software.virtualWallet.routes.usuario;

import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.useCase.usuario.PutUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PutUsuarioRoute {

    /**
     *
     * @param useCase
     * @return una respuesta del servidor y aplana la información entrante que luego servirá
     * para actualizar los datos del usuario. Dicha información es convertida en DTO. Finalmente,
     * de no hallarse el usuario a editar, retorna como respuesta del servidor un error 400
     */

    @Bean
    @RouterOperation(operation = @Operation(operationId = "updateUsuario", summary = "actualizar un usuario existente", tags = {"Usuario"},
            requestBody = @RequestBody(required = true, description = "Ingresar el cuerpo del objeto en formato JSON",
                    content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> putRoute(PutUsuarioUseCase useCase){

        return route(PUT("/put/user").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(UsuarioDto.class)
                        .flatMap(useCase::updateUsuarioUseCase)
                        .flatMap(usuarioDto -> ServerResponse.status(HttpStatus.ACCEPTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(usuarioDto))
                        .onErrorResume(throwable -> {
                            System.out.println(throwable.getMessage());
                            return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
                        }));
    }
}
