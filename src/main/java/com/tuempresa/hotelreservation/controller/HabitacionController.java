package com.tuempresa.hotelreservation.controller;

import com.tuempresa.hotelreservation.dao.HabitacionDAO;
import com.tuempresa.hotelreservation.dao.impl.HabitacionDAOImpl;
import com.tuempresa.hotelreservation.model.Habitacion;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para manejar acciones relacionadas con habitaciones
 * @autor LFMG9
 */
public class HabitacionController {
    
    private final HabitacionDAO habitacionDAO;
    
    // Constructor que inicializa el DAO de habitaciones
    public HabitacionController() {
        this.habitacionDAO = new HabitacionDAOImpl();
    }
    
    // Método principal para procesar solicitudes relacionadas con habitaciones
    public void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action"); // Obtiene la acción a realizar
        
        if (action == null) {
            action = "detalle"; // Acción por defecto si no se especifica ninguna
        }
        
        // Decide qué método llamar según la acción
        switch (action) {
            case "detalle":
                mostrarDetalleHabitacion(request, response);
                break;
            case "seleccionar":
                seleccionarHabitacion(request, response);
                break;
            default:
                // Redirige a la lista de hoteles si la acción no es reconocida
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                break;
        }
    }
    
    // Muestra los detalles de una habitación específica
    private void mostrarDetalleHabitacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id"); // Obtiene el ID de la habitación
        
        if (idStr == null) {
            // Redirige si no se proporciona un ID
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            Long id = Long.parseLong(idStr); // Convierte el ID a Long
            Habitacion habitacion = habitacionDAO.buscarPorId(id); // Busca la habitación
            
            if (habitacion == null) {
                // Redirige si no se encuentra la habitación
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                return;
            }
            
            // Añade la habitación al request y muestra la vista de detalles
            request.setAttribute("habitacion", habitacion);
            request.getRequestDispatcher("/WEB-INF/views/habitacion/detalle.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Redirige si el ID no es un número válido
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
        }
    }
    
    // Selecciona una habitación para el proceso de reserva
    private void seleccionarHabitacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String habitacionIdStr = request.getParameter("id"); // Obtiene el ID de la habitación
        
        if (habitacionIdStr == null) {
            // Redirige si no se proporciona un ID
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            Long habitacionId = Long.parseLong(habitacionIdStr); // Convierte el ID a Long
            Habitacion habitacion = habitacionDAO.buscarPorId(habitacionId); // Busca la habitación
            
            if (habitacion == null) {
                // Redirige si no se encuentra la habitación
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                return;
            }
            
            // Guarda la habitación en la sesión para el proceso de reserva
            HttpSession session = request.getSession();
            session.setAttribute("habitacionSeleccionada", habitacion);
            
            // Redirige al formulario de reserva
            response.sendRedirect(request.getContextPath() + "/app/reservas?action=crear");
        } catch (NumberFormatException e) {
            // Redirige si el ID no es un número válido
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
        }
    }
}
