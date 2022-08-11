package com.sofkaU.software.virtualWallet.routes.usuario;

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.useCase.usuario.FindByCorreoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class GetByCorreoRoute {

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getOneUserByCorreo", summary = "Obtener un usuario por correo dado.", tags = {"Usuario"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "correo", description = "account mail")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransaccionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> GetOneUser(FindByCorreoUseCase useCase){
        return route(GET("/getbyemail/user/{correo}"),
                request -> useCase.findByCorreo(request.pathVariable("correo"))
                        .flatMap(usuarioDto -> ServerResponse.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(usuarioDto))
                        .onErrorResume(throwable -> {
                            System.out.println(throwable.getMessage());
                            return ServerResponse.status(HttpStatus.NOT_FOUND).build();
                        }));
    }
}
