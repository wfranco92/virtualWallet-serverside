package com.sofkaU.software.virtualWallet.routes.transaccion;

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.useCase.transaccion.CreateTransaccionUseCase;
import com.sofkaU.software.virtualWallet.useCase.transaccion.GetAllTransaccionByCorreoUseCase;
import com.sofkaU.software.virtualWallet.useCase.transaccion.GetAllTransaccionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class TransaccionRoute {

    /*http://localhost:8080/webjars/swagger-ui/index.html#/*/

    /**
     * Crea una nueva transacción
     *
     * @param createTransaccionUseCase Esto es un uso de caso que será ejecutado cuando el endpoint es llamado.
     * @return un RouterFunction que será usado para crear una nueva transacción.
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "createTransaccion", summary = "create a new Transaccion", tags = {"Transaccion"},
            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                    content = @Content(schema = @Schema(implementation = TransaccionDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))
    public RouterFunction<ServerResponse> createTransaccion(CreateTransaccionUseCase createTransaccionUseCase) {
        Function<TransaccionDTO, Mono<ServerResponse>> executor = transaccionDTO -> createTransaccionUseCase.apply(transaccionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/createTransaccion").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TransaccionDTO.class).flatMap(executor)
        );
    }

    /**
     * Una función router que retorna una respuesta del servidor
     *
     * @param getAllTransaccionUseCase  Esto es un uso de caso que será ejecutado cuando el endpoint es invocado
     * @return Un RouterFunction que es usado por la solicitud entrante en el método hanlder correspondiente
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllTransaccion", summary = "Find all transaccion in Wallet", tags = {"Transaccion"},
            /*parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id")},*/
            responses = {@ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransaccionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))
    public RouterFunction<ServerResponse> getAllTransaccion(GetAllTransaccionUseCase getAllTransaccionUseCase) {
        return route(GET("/getAllTransaccion"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTransaccionUseCase.getAllTransaccion(), TransaccionDTO.class))
        );
    }

    /**
     * Una función que retorna una lista de todas las transacciones hechas por el  usuario
     * A function that returns a list of all the transactions made by the user.
     *
     * @param getAllTransaccionByCorreo Este es el nombre del bean que será inyectado dentro de la función router.
     * @return un RouterFunction que es usado por la solicitud entrante en el método hanlder correspondiente
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllTransaccionEgresoByCorreo", summary = "Find all transaccion egrees in Wallet by user (email)", tags = {"Transaccion"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "correo", description = "account mail")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransaccionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))
    public RouterFunction<ServerResponse> getAllTransaccionEgresoByCorreo(GetAllTransaccionByCorreoUseCase getAllTransaccionByCorreo) {
        return route(GET("/getAllTransaccionEgreso/user/{correo}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTransaccionByCorreo.getAllTransaccionEgreso(request.pathVariable("correo")), TransaccionDTO.class))
        );
    }

    /**
     * Una función que retorna una lista de todas las transacciones hechas por el  usuario
     *
     * @param getAllTransaccionByCorreo  Este es el nombre del bean que será inyectado dentro de la función router.
     * @return una lista de Transacciones DTO
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllTransaccionIngresoByCorreo", summary = "Find all transaccion ingrees in Wallet by user (email)", tags = {"Transaccion"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "correo", description = "account mail")},
            responses = {@ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransaccionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))
    public RouterFunction<ServerResponse> getAllTransaccionIngresoByCorreo(GetAllTransaccionByCorreoUseCase getAllTransaccionByCorreo) {
        return route(GET("/getAllTransaccionIngreso/user/{correo}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTransaccionByCorreo.getAllTransaccionIngreso(request.pathVariable("correo")), TransaccionDTO.class))
        );
    }
}
