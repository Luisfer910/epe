package com.tuempresa.hotelreservation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador principal que maneja todas las solicitudes entrantes
 * @autor LFMG9
 */
@WebServlet(name = "FrontController", urlPatterns = {"/app/*"})
public class FrontController extends HttpServlet {

    // Maneja las solicitudes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response); // Llama al método que procesa las solicitudes
    }

    // Maneja las solicitudes POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response); // Llama al mismo método para procesar
    }

    // Método para procesar las solicitudes y dirigirlas al controlador adecuado
    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo(); // Obtiene el camino después de "/app"
        
        if (path == null) {
            path = "/"; // Si no hay un camino específico, usa la raíz
        }
        
        // Dependiendo del camino, dirige a diferentes controladores o vistas
        switch (path) {
            case "/":
                // Muestra la página principal
                request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
                break;
            case "/hoteles":
                // Llama al controlador de hoteles
                new HotelController().procesarSolicitud(request, response);
                break;
            case "/habitaciones":
                // Llama al controlador de habitaciones
                new HabitacionController().procesarSolicitud(request, response);
                break;
            case "/reservas":
                // Llama al controlador de reservas
                new ReservaController().procesarSolicitud(request, response);
                break;
            case "/usuarios":
                // Llama al controlador de usuarios
                new UsuarioController().procesarSolicitud(request, response);
                break;
            default:
                // Si el camino no coincide con ninguno, redirige a la página principal
                response.sendRedirect(request.getContextPath() + "/app/");
                break;
        }
    }
}
