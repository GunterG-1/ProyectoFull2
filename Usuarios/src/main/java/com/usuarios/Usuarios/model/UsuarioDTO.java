package com.usuarios.Usuarios.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {

    private String emailUsuario;
    private String contrasenaUsuario;


    public String getEmailUsuario() {
        return emailUsuario;
    }   
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contraseñaUsuario) {
        this.contrasenaUsuario = contraseñaUsuario;
    }

}
