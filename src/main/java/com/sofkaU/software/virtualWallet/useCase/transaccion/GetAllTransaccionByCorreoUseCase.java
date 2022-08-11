package com.sofkaU.software.virtualWallet.useCase.transaccion;

/**
 * Esta clase GetAllCuentasUseCase es el caso de uso que permite mostrar en pantalla todas las transacciones
 * que han sido generadas. Estas transacciones son buscadas por medio de un correo electrónico
 * @author: William Franco
 * */

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.mapper.Mapper;
import com.sofkaU.software.virtualWallet.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAllTransaccionByCorreoUseCase {

    private final TransaccionRepository transaccionRepository;
    private final Mapper mapper;

    public GetAllTransaccionByCorreoUseCase(TransaccionRepository transaccionRepository, Mapper mapper) {
        this.transaccionRepository = transaccionRepository;
        this.mapper = mapper;
    }

    /**
     * El método getAllTransaccion retorna un Flux de DTO con todos las transacciones salientes
     * luego de convertir cada colección de transacción en DTO
     * */

    public Flux<TransaccionDTO> getAllTransaccionEgreso(String correo) {
        return transaccionRepository.findByCorreoOrigen(correo)
                .map(mapper.mapEntityToTransaccion());
    }

    /**
     * El método getAllTransaccion retorna un Flux de DTO con todos las transacciones entrantes
     * luego de convertir cada colección de transacción en DTO
     * */

    public Flux<TransaccionDTO> getAllTransaccionIngreso(String correo) {
        return transaccionRepository.findByCorreoDestino(correo)
                .map(mapper.mapEntityToTransaccion());
    }
}
