package com.sofkaU.software.virtualWallet.bcrypt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptMapper {

    private final PasswordEncoder encoder;

    public BcryptMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encode(String contrasena) {
        return encoder.encode(contrasena);
    }

    public Boolean compare(String contrasena, String hashContrasena) {
        return encoder.matches(contrasena, hashContrasena);
    }

}
