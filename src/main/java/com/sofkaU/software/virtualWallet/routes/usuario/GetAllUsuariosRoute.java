package com.sofkaU.software.virtualWallet.routes.usuario;

/**
 * Clase allUserRoute retorna una respuesta por parte del servidor. En este caso, la respuesta es OK y
 * muestra todas los usuarios en la URL destinada para dicha acción
 */

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.useCase.usuario.GetAllUsuariosUseCase;
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



import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class GetAllUsuariosRoute {


    @Bean
    @RouterOperation(operation = @Operation(operationId = "Obtener todos los usuarios", summary = "Encontrar todos los usuarios registrados.", tags = {"Usuario"},
            /*parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id")},*/
            responses = {@ApiResponse(responseCode = "200", description = "successful operation.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Server not found")}))

    public RouterFunction<ServerResponse> allUserRoute(GetAllUsuariosUseCase useCase){
        return route(GET("/getall/users"),
                request -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromProducer(useCase.getAllUsers(), UsuarioDto.class)));
    }
}
