package com.tuempresa.hotelreservation.controller;

import com.tuempresa.hotelreservation.dao.HabitacionDAO;
import com.tuempresa.hotelreservation.dao.impl.HabitacionDAOImpl;
import com.tuempresa.hotelreservation.model.Habitacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author LFMG9
 */
public class HabitacionController {
    
    private final HabitacionDAO habitacionDAO;
    
    public HabitacionController() {
        this.habitacionDAO = new HabitacionDAOImpl();
    }
    
    public void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "detalle";
        }
        
        switch (action) {
            case "detalle":
                mostrarDetalleHabitacion(request, response);
                break;
            case "seleccionar":
                seleccionarHabitacion(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                break;
        }
    }
    
    private void mostrarDetalleHabitacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            Long id = Long.parseLong(idStr);
            Habitacion habitacion = habitacionDAO.buscarPorId(id);
            
            if (habitacion == null) {
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                return;
            }
            
            request.setAttribute("habitacion", habitacion);
            request.getRequestDispatcher("/WEB-INF/views/habitacion/detalle.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
        }
    }
    
    private void seleccionarHabitacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String habitacionIdStr = request.getParameter("id");
        
        if (habitacionIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            Long habitacionId = Long.parseLong(habitacionIdStr);
            Habitacion habitacion = habitacionDAO.buscarPorId(habitacionId);
            
            if (habitacion == null) {
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                return;
            }
            
            // Guardar información en la sesión para el proceso de reserva
            HttpSession session = request.getSession();
            session.setAttribute("habitacionSeleccionada", habitacion);
            
            // Redirigir al formulario de reserva
            response.sendRedirect(request.getContextPath() + "/app/reservas?action=crear");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
        }
    }
}


