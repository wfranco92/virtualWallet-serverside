package com.sofkaU.software.virtualWallet.useCase.transaccion;

import com.sofkaU.software.virtualWallet.collections.Transaccion;
import com.sofkaU.software.virtualWallet.mapper.Mapper;
import com.sofkaU.software.virtualWallet.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetAllTransaccionUseCaseTest {

    GetAllTransaccionUseCase getAllTransaccionUseCase;
    Mapper mapper = new Mapper();

    @Mock
    TransaccionRepository transaccionRepository;

    @BeforeEach
    public void setUp() {
        transaccionRepository = mock(TransaccionRepository.class);
        getAllTransaccionUseCase = new GetAllTransaccionUseCase(transaccionRepository, mapper);
    }

    @Test
    void GetAllTransaccionUseCaseTest(){

        var transaccion = new Transaccion();

        transaccion.setId("qwerty");
        transaccion.setFecha("27-06-2022");
        transaccion.setCorreoOrigen("correoOrigen@correo.com");
        transaccion.setCorreoDestino("correoDestino@correo.com");
        transaccion.setValor(1000L);

        var transaccion2 = new Transaccion();

        transaccion2.setId("ytrewq");
        transaccion2.setFecha("27-06-2022");
        transaccion2.setCorreoOrigen("correoOrigen1@correo.com");
        transaccion2.setCorreoDestino("correoDestino1@correo.com");
        transaccion2.setValor(2000L);

        Mockito.when(transaccionRepository.findAll()).thenReturn(Flux.just(transaccion, transaccion2));

        StepVerifier.create(getAllTransaccionUseCase.getAllTransaccion())
                .expectNext(mapper.mapEntityToTransaccion().apply(transaccion))
                .expectNext(mapper.mapEntityToTransaccion().apply(transaccion2))
                .verifyComplete();

        Mockito.verify(transaccionRepository).findAll();
    }

}