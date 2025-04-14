package com.tuempresa.hotelreservation.controller;

import com.tuempresa.hotelreservation.dao.UsuarioDAO;
import com.tuempresa.hotelreservation.dao.impl.UsuarioDAOImpl;
import com.tuempresa.hotelreservation.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author LFMG9
 */
public class UsuarioController {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    public void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "login";
        }
        
        switch (action) {
            case "login":
                mostrarFormularioLogin(request, response);
                break;
            case "autenticar":
                autenticarUsuario(request, response);
                break;
            case "registro":
                mostrarFormularioRegistro(request, response);
                break;
            case "registrar":
                registrarUsuario(request, response);
                break;
            case "logout":
                cerrarSesion(request, response);
                break;
            default:
                mostrarFormularioLogin(request, response);
                break;
        }
    }
    
    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/usuario/login.jsp").forward(request, response);
    }
    
    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");
        
        // Para un proyecto educativo, podemos simplificar la autenticación
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            response.sendRedirect(request.getContextPath() + "/app/");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("/WEB-INF/views/usuario/login.jsp").forward(request, response);
        }
    }
    
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/usuario/registro.jsp").forward(request, response);
    }
    
    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");
        String telefono = request.getParameter("telefono");
        
        // Verificar si el correo ya está registrado
        if (usuarioDAO.buscarPorCorreo(correo) != null) {
            request.setAttribute("error", "El correo ya está registrado");
            request.getRequestDispatcher("/WEB-INF/views/usuario/registro.jsp").forward(request, response);
            return;
        }
        
        // Crear y guardar el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setContraseña(contraseña); // Para un proyecto educativo, no hasheamos la contraseña
        usuario.setTelefono(telefono);
        
        usuarioDAO.guardar(usuario);
        
        request.setAttribute("mensaje", "Usuario registrado correctamente");
        request.getRequestDispatcher("/WEB-INF/views/usuario/login.jsp").forward(request, response);
    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/app/");
    }
}


