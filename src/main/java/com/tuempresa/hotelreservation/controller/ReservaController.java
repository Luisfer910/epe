package com.tuempresa.hotelreservation.controller;

import com.tuempresa.hotelreservation.dao.ReservaDAO;
import com.tuempresa.hotelreservation.dao.impl.ReservaDAOImpl;
import com.tuempresa.hotelreservation.model.Habitacion;
import com.tuempresa.hotelreservation.model.Reserva;
import com.tuempresa.hotelreservation.model.Reserva.EstadoReserva;
import com.tuempresa.hotelreservation.model.Usuario;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author LFMG9
 */
public class ReservaController {
    
    private final ReservaDAO reservaDAO;
    private final SimpleDateFormat dateFormat;
    
    public ReservaController() {
        this.reservaDAO = new ReservaDAOImpl();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "mis-reservas";
        }
        
        switch (action) {
            case "crear":
                mostrarFormularioReserva(request, response);
                break;
            case "guardar":
                guardarReserva(request, response);
                break;
            case "mis-reservas":
                listarMisReservas(request, response);
                break;
            case "cancelar":
                cancelarReserva(request, response);
                break;
            default:
                listarMisReservas(request, response);
                break;
        }
    }
    
    private void mostrarFormularioReserva(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            // Usuario no autenticado, redirigir a login
            response.sendRedirect(request.getContextPath() + "/app/usuarios?action=login");
            return;
        }
        
        Habitacion habitacion = (Habitacion) session.getAttribute("habitacionSeleccionada");
        
        if (habitacion == null) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        request.setAttribute("habitacion", habitacion);
        request.getRequestDispatcher("/WEB-INF/views/reserva/crear.jsp").forward(request, response);
    }
    
    private void guardarReserva(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/app/usuarios?action=login");
            return;
        }
        
        Habitacion habitacion = (Habitacion) session.getAttribute("habitacionSeleccionada");
        
        if (habitacion == null) {
            response.sendRedirect(request.getContextPath() + "/app/hoteles");
            return;
        }
        
        try {
            // Obtener datos del formulario
            String fechaIngresoStr = request.getParameter("fechaIngreso");
            String fechaSalidaStr = request.getParameter("fechaSalida");
            
            Date fechaIngreso = dateFormat.parse(fechaIngresoStr);
            Date fechaSalida = dateFormat.parse(fechaSalidaStr);
            
            // Calcular precio total
            int dias = calcularDias(fechaIngreso, fechaSalida);
            BigDecimal precioTotal = habitacion.getPrecio().multiply(new BigDecimal(dias));
            
            // Crear la reserva
            Reserva reserva = new Reserva();
            reserva.setFechaIngreso(fechaIngreso);
            reserva.setFechaSalida(fechaSalida);
            reserva.setPrecioTotal(precioTotal);
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            reserva.setUsuario(usuario);
            reserva.setHabitacion(habitacion);
            reserva.setFechaCreacion(new Date());
            
            // Guardar la reserva
            reserva = reservaDAO.guardar(reserva);
            
            // Limpiar datos de sesión
            session.removeAttribute("habitacionSeleccionada");
            
            // Redirigir a la página de confirmación
            request.setAttribute("reserva", reserva);
            request.getRequestDispatcher("/WEB-INF/views/reserva/confirmacion.jsp").forward(request, response);
            
        } catch (ParseException e) {
            request.setAttribute("error", "Error en el formato de las fechas");
            request.getRequestDispatcher("/WEB-INF/views/reserva/crear.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al guardar la reserva");
            request.getRequestDispatcher("/WEB-INF/views/reserva/crear.jsp").forward(request, response);
        }
    }
    
    private void listarMisReservas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/app/usuarios?action=login");
            return;
        }
        
        List<Reserva> reservas = reservaDAO.buscarPorUsuario(usuario.getId());
        request.setAttribute("reservas", reservas);
        
        request.getRequestDispatcher("/WEB-INF/views/reserva/mis-reservas.jsp").forward(request, response);
    }
    
    private void cancelarReserva(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/app/usuarios?action=login");
            return;
        }
        
        String idStr = request.getParameter("id");
        
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/app/reservas?action=mis-reservas");
            return;
        }
        
        try {
            Long id = Long.parseLong(idStr);
            Reserva reserva = reservaDAO.buscarPorId(id);
            
            if (reserva == null || !reserva.getUsuario().getId().equals(usuario.getId())) {
                response.sendRedirect(request.getContextPath() + "/app/reservas?action=mis-reservas");
                return;
            }
            
            // Cancelar la reserva
            reserva.setEstado(EstadoReserva.CANCELADA);
            reservaDAO.actualizar(reserva);
            
            response.sendRedirect(request.getContextPath() + "/app/reservas?action=mis-reservas");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/reservas?action=mis-reservas");
        }
    }
    
    private int calcularDias(Date fechaIngreso, Date fechaSalida) {
        long diff = fechaSalida.getTime() - fechaIngreso.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
}


