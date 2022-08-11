package com.sofkaU.software.virtualWallet.routes.usuario;

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.useCase.usuario.PostUsuarioUseCase;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PostUsuarioRoute {

    /**
     * método postUsuario recibe su correspondiente UseCase de usuario para crear un usuario
     * @param useCase
     * @return una respuesta del servidor luego de aplanar toda la información entrante para convertirla en DTO
     */

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createUsuario", summary = "crear un nuevo Usuario", tags = {"Usuario"},
            requestBody = @RequestBody(required = true, description = "Ingresar el cuerpo del objeto en formato JSON",
                    content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> postUsuario(PostUsuarioUseCase useCase) {
        return route(POST("/post/user").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(UsuarioDto.class)
                        .flatMap(useCase::createUsuario)
                        .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dto))
                        .onErrorResume((error) -> ServerResponse.status(HttpStatus.BAD_REQUEST).build())
        );
    }
}
