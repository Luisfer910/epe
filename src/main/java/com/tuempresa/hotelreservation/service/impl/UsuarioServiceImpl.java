package com.tuempresa.hotelreservation.service.impl;

import com.tuempresa.hotelreservation.dao.UsuarioDAO;
import com.tuempresa.hotelreservation.dao.impl.UsuarioDAOImpl;
import com.tuempresa.hotelreservation.model.Usuario;
import com.tuempresa.hotelreservation.service.UsuarioService;
import com.tuempresa.hotelreservation.util.SeguridadUtil;
import java.util.List;

/**
 *
 * @author LFMG9
 */

public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Hashear la contraseña antes de guardar
        String contraseñaHash = SeguridadUtil.hashContraseña(usuario.getContraseña());
        usuario.setContraseña(contraseñaHash);
        return usuarioDAO.guardar(usuario);
    }
    
    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        // Si la contraseña ha cambiado, hashearla
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getId());
        if (!usuario.getContraseña().equals(usuarioExistente.getContraseña())) {
            String contraseñaHash = SeguridadUtil.hashContraseña(usuario.getContraseña());
            usuario.setContraseña(contraseñaHash);
        }
        return usuarioDAO.actualizar(usuario);
    }
    
    @Override
    public void eliminarUsuario(Long id) {
        usuarioDAO.eliminar(id);
    }
    
    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }
    
    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDAO.buscarPorCorreo(correo);
    }
    
    @Override
    public Usuario iniciarSesion(String correo, String contraseña) {
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        if (usuario != null && SeguridadUtil.verificarContraseña(contraseña, usuario.getContraseña())) {
            return usuario;
        }
        return null;
    }
}


