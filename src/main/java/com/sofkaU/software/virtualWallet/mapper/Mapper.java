package com.sofkaU.software.virtualWallet.mapper;

/**
 * Esta clase Mapper permite realizar los cambios entre la colección Transaccion y el DTO TransaccionDTO
 * para el recibimiento y/o envío de información del usuario en cada transacción
 * @author: William Franco
 * */

import com.sofkaU.software.virtualWallet.collections.Transaccion;
import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Mapper {

    public Function<TransaccionDTO, Transaccion> mapperToTransaccion(String id){
        return updateTransaccion -> {
            var transaccion = new Transaccion();
            transaccion.setId(id);
            transaccion.setFecha(updateTransaccion.getFecha());
            transaccion.setCorreoOrigen(updateTransaccion.getCorreoOrigen());
            transaccion.setCorreoDestino(updateTransaccion.getCorreoDestino());
            transaccion.setValor(updateTransaccion.getValor());
            return transaccion;
        };
    }

    public Function<Transaccion, TransaccionDTO> mapEntityToTransaccion() {
        return entity -> new TransaccionDTO(
                entity.getId(), entity.getFecha(), entity.getCorreoOrigen(), entity.getCorreoDestino(), entity.getValor()
        );
    }
}
