package com.sofkaU.software.virtualWallet.useCase.cuenta;

/**
 * La clase PostCuentaUseCase es el caso de uso que permite crear una cuenta
 * @author: Marcos Nino
 * */

import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.mapper.CuentaMapper;
import com.sofkaU.software.virtualWallet.repository.ICuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PostCuentaUseCase {

    private final ICuentaRepository cuentaRepo;

    private final CuentaMapper mapper;

    /**
     * el método validarCamposCuenta valida que cada campo del formulario Cuenta sea llenado
     * @param cuentaDto
     * @return a boolean que es true si todos los campos no fueron ingresados como nulos
     */

    private boolean validarCamposCuenta(CuentaDto cuentaDto) {
        return cuentaDto.getCorreoUsuario() != null && cuentaDto.getMonto() != null;
    }

    /**
     * filtrarCuentaConCamposNoNulos como su nombre lo expresa, filtra el método anterior
     * @param cuentaDto
     * @return Mono de Cuenta DTO que fueron retornados como true en el método anterior
     * De lo contrario, retorna un error
     */

    private Mono<CuentaDto> filtrarCuentaConCamposNoNulos(CuentaDto cuentaDto){
        return Mono.just(cuentaDto)
                .filter(this::validarCamposCuenta)
                .switchIfEmpty(Mono.error(() -> new Throwable("Algunos campos están vacíos")));
    }

    /**
     *
     * @param cuentaDto
     * @return el método filtrarCuentaConCamposNoNulos que a su vez retorna un Mono.
     * Este método recorre cada entrada y la convierte en colección, para luego retornar
     * la información como un Mono DTO
     */

    public Mono<CuentaDto> crearCuenta(CuentaDto cuentaDto){
        return filtrarCuentaConCamposNoNulos(cuentaDto)
                .flatMap(cuentaDto1 -> cuentaRepo.save(mapper.toCuentaCollection(cuentaDto1)))
                .map(cuenta -> mapper.toCuentaDto(cuenta));
    }
}
