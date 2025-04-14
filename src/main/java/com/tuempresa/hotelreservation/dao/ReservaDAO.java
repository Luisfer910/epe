package com.tuempresa.hotelreservation.dao;

import com.tuempresa.hotelreservation.model.Reserva;
import com.tuempresa.hotelreservation.model.Reserva.EstadoReserva;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LFMG9
 */

public interface ReservaDAO extends GenericDAO<Reserva> {
    
    /**
     * Busca reservas por usuario
     * @param usuarioId ID del usuario
     * @return Lista de reservas del usuario
     */
    List<Reserva> buscarPorUsuario(Long usuarioId);
    
    /**
     * Busca reservas por habitación
     * @param habitacionId ID de la habitación
     * @return Lista de reservas para la habitación
     */
    List<Reserva> buscarPorHabitacion(Long habitacionId);
    
    /**
     * Busca reservas por estado
     * @param estado Estado de la reserva
     * @return Lista de reservas con el estado especificado
     */
    List<Reserva> buscarPorEstado(EstadoReserva estado);
    
    /**
     * Busca reservas por rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de reservas en el rango de fechas
     */
    List<Reserva> buscarPorRangoFechas(Date fechaInicio, Date fechaFin);
    
    /**
     * Verifica si una habitación está disponible para un rango de fechas
     * @param habitacionId ID de la habitación
     * @param fechaIngreso Fecha de ingreso
     * @param fechaSalida Fecha de salida
     * @return true si la habitación está disponible, false en caso contrario
     */
    boolean verificarDisponibilidad(Long habitacionId, Date fechaIngreso, Date fechaSalida);
}


