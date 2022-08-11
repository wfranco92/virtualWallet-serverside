package com.sofkaU.software.virtualWallet.collections;

/**
 * Esta clase Cuenta usa la anotación @Document para hacer saber a MongoDB que es una colección
 * @author: Marco Nino
 * */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "cuenta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

    @Id
    private String id;

    private String correoUsuario;

    private Long monto;

}
