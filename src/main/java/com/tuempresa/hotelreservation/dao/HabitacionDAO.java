package com.tuempresa.hotelreservation.dao;

import com.tuempresa.hotelreservation.model.Habitacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LFMG9
 */

public interface HabitacionDAO extends GenericDAO<Habitacion> {
    
    /**
     * Busca habitaciones por hotel
     * @param hotelId ID del hotel
     * @return Lista de habitaciones del hotel
     */
    List<Habitacion> buscarPorHotel(Long hotelId);
    
    /**
     * Busca habitaciones disponibles por hotel
     * @param hotelId ID del hotel
     * @return Lista de habitaciones disponibles
     */
    List<Habitacion> buscarDisponiblesPorHotel(Long hotelId);
    
    /**
     * Busca habitaciones por tipo
     * @param tipo Tipo de habitación
     * @return Lista de habitaciones del tipo especificado
     */
    List<Habitacion> buscarPorTipo(String tipo);
    
    /**
     * Busca habitaciones disponibles por rango de precio
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de habitaciones en el rango de precio
     */
    List<Habitacion> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax);
    
    /**
     * Busca habitaciones disponibles para un período específico
     * @param hotelId ID del hotel
     * @param fechaIngreso Fecha de ingreso
     * @param fechaSalida Fecha de salida
     * @return Lista de habitaciones disponibles
     */
    List<Habitacion> buscarDisponiblesPorFechas(Long hotelId, Date fechaIngreso, Date fechaSalida);
}


