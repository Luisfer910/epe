package com.tuempresa.hotelreservation.controller;

import com.tuempresa.hotelreservation.dao.HotelDAO;
import com.tuempresa.hotelreservation.dao.HabitacionDAO;
import com.tuempresa.hotelreservation.dao.impl.HotelDAOImpl;
import com.tuempresa.hotelreservation.dao.impl.HabitacionDAOImpl;
import com.tuempresa.hotelreservation.model.Hotel;
import com.tuempresa.hotelreservation.model.Habitacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author LFMG9
 */
public class HotelController {
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private final HotelDAO hotelDAO;
    private final HabitacionDAO habitacionDAO;
    
    public HotelController() {
        this.hotelDAO = new HotelDAOImpl();
        this.habitacionDAO = new HabitacionDAOImpl();
        try {
            emf = Persistence.createEntityManagerFactory("hotelPU");
            em = emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo apropiado del error
        }
    }
    
    public void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }
        
        switch (action) {
            case "listar":
                listarHoteles(request, response);
                break;
            case "buscar":
                buscarHoteles(request, response);
                break;
            case "detalle":
                mostrarDetalleHotel(request, response);
                break;
            default:
                listarHoteles(request, response);
                break;
        }
    }
    
    private void listarHoteles(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Hotel> hoteles = hotelDAO.listarTodos();
        request.setAttribute("hoteles", hoteles);
        request.getRequestDispatcher("/WEB-INF/views/hotel/lista.jsp").forward(request, response);
    }
    
    private void buscarHoteles(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String ciudad = request.getParameter("ciudad");
        
        if (ciudad == null || ciudad.trim().isEmpty()) {
            listarHoteles(request, response);
            return;
        }
        
        List<Hotel> hoteles = hotelDAO.buscarPorCiudad(ciudad);
        
        request.setAttribute("hoteles", hoteles);
        request.setAttribute("ciudad", ciudad);
        request.getRequestDispatcher("/WEB-INF/views/hotel/resultados.jsp").forward(request, response);
    }
    
    private void mostrarDetalleHotel(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            Long id = Long.parseLong(idStr);
            Hotel hotel = hotelDAO.buscarPorId(id);
            
            if (hotel == null) {
                response.sendRedirect(request.getContextPath() + "/app/hoteles");
                return;
            }
            
            request.setAttribute("hotel", hotel);
            
            // Obtener las habitaciones del hotel
            List<Habitacion> habitaciones = habitacionDAO.buscarPorHotel(id);
            request.setAttribute("habitaciones", habitaciones);
            
            request.getRequestDispatcher("/WEB-INF/views/hotel/detalle.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
        }
    }
}


