package com.sofkaU.software.virtualWallet.mapper;

/**
 * Esta clase CuentaMapper permite realizar los cambios entre la colección Cuenta y el DTO CuentaDto
 * para el recibimiento y/o envío de información del usuario a través de su cuenta
 * @author: Marco Nino
 * */

import com.sofkaU.software.virtualWallet.collections.Cuenta;
import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toCuentaCollection(CuentaDto cuentaDto){
        Cuenta cuenta = new Cuenta();

        cuenta.setId(cuentaDto.getId());
        cuenta.setCorreoUsuario(cuentaDto.getCorreoUsuario());
        cuenta.setMonto(cuentaDto.getMonto());

        return cuenta;

    }

    public CuentaDto toCuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId(cuenta.getId());
        cuentaDto.setCorreoUsuario(cuenta.getCorreoUsuario());
        cuentaDto.setMonto(cuenta.getMonto());

        return cuentaDto;

    }

}
