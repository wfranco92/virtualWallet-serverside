package com.sofkaU.software.virtualWallet.collections;

/**
 * Esta clase Transaccion usa la anotación @Document para hacer saber a MongoDB que es una colección
 * Transaccion contiene los atributos pertinentes para realizar y caracterizar cada transacción
 * @author: William Franco
 * */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Transaccion {

    @Id
    private String id;
    private String fecha;
    private String correoOrigen;
    private String correoDestino;
    private Long valor;
}
