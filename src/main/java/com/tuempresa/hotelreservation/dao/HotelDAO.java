package com.tuempresa.hotelreservation.dao;

import com.tuempresa.hotelreservation.model.Hotel;
import java.util.List;

/**
 *
 * @author LFMG9
 */

public interface HotelDAO extends GenericDAO<Hotel> {
    
    /**
     * Busca hoteles por ciudad
     * @param ciudad Ciudad donde se buscan hoteles
     * @return Lista de hoteles en la ciudad especificada
     */
    List<Hotel> buscarPorCiudad(String ciudad);
    
    /**
     * Busca hoteles por número de estrellas
     * @param estrellas Número de estrellas
     * @return Lista de hoteles con el número de estrellas especificado
     */
    List<Hotel> buscarPorEstrellas(Integer estrellas);
    
    /**
     * Busca hoteles disponibles en una ciudad específica
     * @param ciudad Ciudad donde se buscan hoteles
     * @return Lista de hoteles con habitaciones disponibles
     */
    List<Hotel> buscarHotelesDisponiblesEnCiudad(String ciudad);
}


