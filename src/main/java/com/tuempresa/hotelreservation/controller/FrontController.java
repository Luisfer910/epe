package com.tuempresa.hotelreservation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LFMG9
 */
@WebServlet(name = "FrontController", urlPatterns = {"/app/*"})
public class FrontController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response);
    }

    /**
     * Procesa las solicitudes y las dirige al controlador correspondiente
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if (path == null) {
            path = "/";
        }
        
        switch (path) {
            case "/":
                request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
                break;
            case "/hoteles":
                new HotelController().procesarSolicitud(request, response);
                break;
            case "/habitaciones":
                new HabitacionController().procesarSolicitud(request, response);
                break;
            case "/reservas":
                new ReservaController().procesarSolicitud(request, response);
                break;
            case "/usuarios":
                new UsuarioController().procesarSolicitud(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/app/");
                break;
        }
    }
}


