package com.tuempresa.hotelreservation.dao.impl;

import com.tuempresa.hotelreservation.dao.HabitacionDAO;
import com.tuempresa.hotelreservation.model.Habitacion;
import com.tuempresa.hotelreservation.util.JPAUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author LFMG9
 */

public class HabitacionDAOImpl implements HabitacionDAO {

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(habitacion);
            em.getTransaction().commit();
            return habitacion;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Habitacion actualizar(Habitacion habitacion) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            habitacion = em.merge(habitacion);
            em.getTransaction().commit();
            return habitacion;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Habitacion habitacion = em.find(Habitacion.class, id);
            if (habitacion != null) {
                em.remove(habitacion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Habitacion buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Habitacion.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Habitacion> query = em.createQuery("SELECT h FROM Habitacion h", Habitacion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> buscarPorHotel(Long hotelId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Habitacion> query = em.createQuery(
                    "SELECT h FROM Habitacion h WHERE h.hotel.id = :hotelId", Habitacion.class);
            query.setParameter("hotelId", hotelId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> buscarDisponiblesPorHotel(Long hotelId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Habitacion> query = em.createQuery(
                    "SELECT h FROM Habitacion h WHERE h.hotel.id = :hotelId AND h.disponible = true", 
                    Habitacion.class);
            query.setParameter("hotelId", hotelId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> buscarPorTipo(String tipo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Habitacion> query = em.createQuery(
                    "SELECT h FROM Habitacion h WHERE h.tipo = :tipo", Habitacion.class);
            query.setParameter("tipo", tipo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Habitacion> query = em.createQuery(
                    "SELECT h FROM Habitacion h WHERE h.precio BETWEEN :precioMin AND :precioMax AND h.disponible = true", 
                    Habitacion.class);
            query.setParameter("precioMin", precioMin);
            query.setParameter("precioMax", precioMax);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Habitacion> buscarDisponiblesPorFechas(Long hotelId, Date fechaIngreso, Date fechaSalida) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Esta consulta es más compleja: busca habitaciones que no tengan reservas que se superpongan con las fechas dadas
            String jpql = "SELECT h FROM Habitacion h WHERE h.hotel.id = :hotelId AND h.disponible = true " +
                          "AND h.id NOT IN (" +
                          "SELECT r.habitacion.id FROM Reserva r " +
                          "WHERE (r.fechaIngreso <= :fechaSalida AND r.fechaSalida >= :fechaIngreso) " +
                          "AND r.estado != com.tuempresa.hotelreservation.model.Reserva$EstadoReserva.CANCELADA)";
            
            TypedQuery<Habitacion> query = em.createQuery(jpql, Habitacion.class);
            query.setParameter("hotelId", hotelId);
            query.setParameter("fechaIngreso", fechaIngreso);
            query.setParameter("fechaSalida", fechaSalida);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}