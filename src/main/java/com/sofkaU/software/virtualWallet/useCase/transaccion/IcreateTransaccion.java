package com.sofkaU.software.virtualWallet.useCase.transaccion;

import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@FunctionalInterface
public interface IcreateTransaccion {

    Mono<TransaccionDTO> apply(@Valid TransaccionDTO transaccionDTO);
}
