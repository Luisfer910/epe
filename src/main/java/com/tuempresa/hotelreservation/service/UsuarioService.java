package com.tuempresa.hotelreservation.service;

import com.tuempresa.hotelreservation.model.Usuario;
import java.util.List;


/**
 *
 * @author LFMG9
 */
public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    Usuario buscarPorId(Long id);
    List<Usuario> listarTodos();
    Usuario buscarPorCorreo(String correo);
    Usuario iniciarSesion(String correo, String contraseña);
}


