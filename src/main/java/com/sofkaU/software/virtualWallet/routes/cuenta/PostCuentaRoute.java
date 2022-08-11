package com.sofkaU.software.virtualWallet.routes.cuenta;

/**
 * Clase PostCuentaRoute contiene el método con la URL para crear la cuenta
 */

import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.useCase.cuenta.PostCuentaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Service
public class PostCuentaRoute {

    /**
     * El método crearCuenta recibe la clase UseCase
     * @param postCuenta
     * @return Mono después de aplanar la información entrante y convertirla en un DTO
     */

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createCuenta", summary = "Creación de una cuenta", tags = {"Cuenta"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = TransaccionDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> crearCuenta(PostCuentaUseCase postCuenta){
        return route(POST("/create/cuenta").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CuentaDto.class)
                        .flatMap(postCuenta::crearCuenta)
                        .flatMap(cuentaDto -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cuentaDto))
                        .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
