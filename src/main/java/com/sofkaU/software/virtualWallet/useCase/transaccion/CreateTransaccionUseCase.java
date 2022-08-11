package com.sofkaU.software.virtualWallet.useCase.transaccion;

/**
 * La clase CreateTransaccionUseCase es el caso de uso que permite crear una transacción
 * @author: William Franco
 * */

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.mapper.Mapper;
import com.sofkaU.software.virtualWallet.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public class CreateTransaccionUseCase implements IcreateTransaccion{

    private final TransaccionRepository transaccionRepository;
    private final Mapper mapper;

    public CreateTransaccionUseCase(Mapper mapper, TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
        this.mapper = mapper;
    }

    /**
     * La clase apply guarda la transacción luego de convertirla en DTO
     * @param transaccionDTO
     * @return un Mono de DTO
     */

    @Override
    public Mono<TransaccionDTO> apply(@Valid TransaccionDTO transaccionDTO) {
        return transaccionRepository
                .save(mapper.mapperToTransaccion(transaccionDTO.getId())
                        .apply(transaccionDTO))
                .map(entity -> mapper.mapEntityToTransaccion().apply(entity));
    }
}
