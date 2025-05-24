package com.usuarios.Usuarios.controller;

import com.usuarios.Usuarios.model.Direccion;
import com.usuarios.Usuarios.model.UsuarioDTO;
import com.usuarios.Usuarios.model.Tarjeta;
import com.usuarios.Usuarios.model.Usuario;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usuarios.Usuarios.services.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;

    public boolean esAdmin(Usuario usuario) {               //verifica si el usuario tiene el rol de ADMIN
        if (usuario.getRoles().contains("ADMIN")) {
            return true;
        }
    }

    @PostMapping("/api/admin/CrearUsuario")
    public void crearUsuarioConRol(@RequestBody Usuario usuario,@RequestBody Long id_rol ) { //para crear un usuario con rol ADMIN
        if (esAdmin(usuario)) { 
            usuarioService.                      
            usuarioService.save(usuario);
        } else {
            throw new RuntimeException("No tienes permiso para crear un usuario");
        }

    }



    @PostMapping("/registro")                                   //para registrar un usuario nuevo
    public void crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
    }


    @PostMapping("/registro/{idUsuario}/tarjeta")                //para asignar una tarjeta a un usuario
    public void registrarTarjeta(@RequestBody Tarjeta tarjeta, @PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario != null) {
            tarjeta.setUsuario(usuario);
            usuario.getTarjetas_usuario().add(tarjeta);
            usuarioService.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
            
        }
    }
    @PostMapping("/registro/{idUsuario}/direccion")             //para asignar una direccion a un usuario
    public void registrarDireccion(@RequestBody Direccion direccion, @PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario != null) {
            usuario.getDirecciones_usuario().add(direccion);
            usuarioService.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
            
        }
    }
    
    @PostMapping("/login")                              //para iniciar sesion/validar usuario
    public ResponseEntity<?> login(@RequestBody UsuarioDTO request) {
        Optional<Usuario> usuarioOpcional = usuarioService.login(
            request.getEmailUsuario(),
            request.getContrasenaUsuario()
        );

        if (usuarioOpcional.isPresent()) {
            Usuario usuario = usuarioOpcional.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
                             
        }
    }



    @GetMapping("/get")                           //para obtener todos los usuarios
    public List<Usuario> getUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/delete")

    







    
    

    
    
}
