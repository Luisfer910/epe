package com.tuempresa.hotelreservation.dao;

import java.util.List;

/**
 *
 * @author LFMG9
 */
public interface GenericDAO<T> {
    
    /**
     * Guarda una entidad en la base de datos
     * @param entity Entidad a guardar
     * @return Entidad guardada
     */
    T guardar(T entity);
    
    /**
     * Actualiza una entidad existente
     * @param entity Entidad a actualizar
     * @return Entidad actualizada
     */
    T actualizar(T entity);
    
    /**
     * Elimina una entidad por su ID
     * @param id ID de la entidad a eliminar
     */
    void eliminar(Long id);
    
    /**
     * Busca una entidad por su ID
     * @param id ID de la entidad
     * @return Entidad encontrada o null
     */
    T buscarPorId(Long id);
    
    /**
     * Obtiene todas las entidades
     * @return Lista de entidades
     */
    List<T> listarTodos();
}


