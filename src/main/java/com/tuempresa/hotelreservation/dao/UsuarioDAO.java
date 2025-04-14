package com.tuempresa.hotelreservation.dao;

import com.tuempresa.hotelreservation.model.Usuario;
import java.util.List;

/**
 *
 * @author LFMG9
 */

public interface UsuarioDAO extends GenericDAO<Usuario> {
    
    /**
     * Busca un usuario por su correo electrónico
     * @param correo Correo electrónico del usuario
     * @return Usuario encontrado o null
     */
    Usuario buscarPorCorreo(String correo);
    
    /**
     * Verifica si las credenciales de inicio de sesión son válidas
     * @param correo Correo electrónico
     * @param contraseña Contraseña
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    Usuario validarCredenciales(String correo, String contraseña);
}



