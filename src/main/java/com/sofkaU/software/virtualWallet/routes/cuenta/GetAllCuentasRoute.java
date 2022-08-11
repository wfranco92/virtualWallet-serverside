package com.sofkaU.software.virtualWallet.routes.cuenta;

/**
 * Clase GetAllCuentasRoute retorna una respuesta por parte del servidor. En este caso, la respuesta es OK y
 * muestra todas las cuentas si el usuario está en la URL destinada
 */

import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.useCase.cuenta.GetAllCuentasUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GetAllCuentasRoute {

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllCuentas", summary = "Encontrar todas las cuentas creadas.", tags = {"Cuenta"},
            /*parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id")},*/
            responses = {@ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))
    public RouterFunction<ServerResponse> allCuentas(GetAllCuentasUseCase allCuentas){
        return route(GET("/get/all/cuentas"), request -> ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(allCuentas.todasLasCuentas(), CuentaDto.class)));
    }
}
