package com.sofkaU.software.virtualWallet.routes.cuenta;

import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.useCase.cuenta.UpdateCuentaUseCase;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UpdateCuentaRoute {

    /**
     * método  actualizarCuenta recibe correspondiente UseCase
     * @param updCuenta
     * @return una respuesta del servidor y aplana la información entrante que luego servirá
     * para actualizar los datos. Dicha información es convertida en DTO. Finalmente, de no hallarse el dato
     * a editar, retorna como respuesta del servidor un error 400
     */

    @Bean
    @RouterOperation(operation = @Operation(operationId = "updateCuenta", summary = "Actualización de una cuenta existe", tags = {"Cuenta"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = TransaccionDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> actualizarCuenta(UpdateCuentaUseCase updCuenta){
        return route(PUT("/update/cuenta").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CuentaDto.class)
                        .flatMap(updCuenta::updateCuenta)
                        .flatMap(cuentaDto -> ServerResponse.status(HttpStatus.ACCEPTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cuentaDto))
                        .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
