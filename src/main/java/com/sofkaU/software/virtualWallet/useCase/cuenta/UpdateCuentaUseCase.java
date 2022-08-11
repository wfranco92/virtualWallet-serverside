package com.sofkaU.software.virtualWallet.useCase.cuenta;

import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.mapper.CuentaMapper;
import com.sofkaU.software.virtualWallet.repository.ICuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateCuentaUseCase {

    private final ICuentaRepository cuentaRepo;

    private final CuentaMapper mapper;

    /**
     * Método para actualizar la Cuenta
     * @param cuentaDto
     * @return un Mono luego de guardar cada dato de entrada como un DTO
     */

    public Mono<CuentaDto> updateCuenta(CuentaDto cuentaDto){
        return cuentaRepo.save(mapper.toCuentaCollection(cuentaDto))
                .map(mapper::toCuentaDto);
    }

}
