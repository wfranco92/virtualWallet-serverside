package com.sofkaU.software.virtualWallet.dto;

/**
 * Esta clase CuentaDto, como su nombre lo expresa, contiene todas las anotaciones y validaciones que
 * Permiten usarla como un DTO para recibir toda la información proveniente del usuario.
 * @author: Maria Lamilla
 * */

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
public class UsuarioDto {

        private String id;
        @NotBlank(message = "Por favor ingrese su nombre")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$", message = "Este campo no coincide con un formato de nombre valido")
        private String nombre;
        @NotBlank(message = "Por favor ingrese su correo")
        @Indexed(unique = true)
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Este campo no coincide con un formato de correo electronico valido")
        private String correo;

        @NotBlank(message = "Por favor ingrese una contraseña")
        @Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?/_'’+>!\"$%±&¿³¡²?;µ.:§ª¨·,¶¹<=»«´¯({ñ¥Ñ|£°¬}`~)!¢÷‰@^*ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ€≠≤≥√Ω-]).{8,}$", message = "formato de contrasenia invalido")
        private String contrasena;
        private String rol;
        private boolean estaActivo;
        private boolean correoVerificado;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                UsuarioDto that = (UsuarioDto) o;
                return estaActivo == that.estaActivo && correoVerificado == that.correoVerificado && Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(correo, that.correo) && Objects.equals(rol, that.rol);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, nombre, correo, rol, estaActivo, correoVerificado);
        }
}
