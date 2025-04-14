package com.tuempresa.hotelreservation.dao.impl;

import com.tuempresa.hotelreservation.dao.ReservaDAO;
import com.tuempresa.hotelreservation.model.Reserva;
import com.tuempresa.hotelreservation.model.Reserva.EstadoReserva;
import com.tuempresa.hotelreservation.util.JPAUtil;
import java.util.Date;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author LFMG9
 */

public class ReservaDAOImpl implements ReservaDAO {

    @Override
    public Reserva guardar(Reserva reserva) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reserva);
            em.getTransaction().commit();
            return reserva;
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
    public Reserva actualizar(Reserva reserva) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            reserva = em.merge(reserva);
            em.getTransaction().commit();
            return reserva;
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
            Reserva reserva = em.find(Reserva.class, id);
            if (reserva != null) {
                em.remove(reserva);
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
    public Reserva buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reserva> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r", Reserva.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioId", Reserva.class);
            query.setParameter("usuarioId", usuarioId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reserva> buscarPorHabitacion(Long habitacionId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE r.habitacion.id = :habitacionId", Reserva.class);
            query.setParameter("habitacionId", habitacionId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reserva> buscarPorEstado(EstadoReserva estado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE r.estado = :estado", Reserva.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reserva> buscarPorRangoFechas(Date fechaInicio, Date fechaFin) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE " +
                    "(r.fechaIngreso BETWEEN :fechaInicio AND :fechaFin) OR " +
                    "(r.fechaSalida BETWEEN :fechaInicio AND :fechaFin) OR " +
                    "(r.fechaIngreso <= :fechaInicio AND r.fechaSalida >= :fechaFin)",
                    Reserva.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean verificarDisponibilidad(Long habitacionId, Date fechaIngreso, Date fechaSalida) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Buscar reservas que se superpongan con las fechas dadas
            String jpql = "SELECT COUNT(r) FROM Reserva r " +
                          "WHERE r.habitacion.id = :habitacionId " +
                          "AND r.estado != :estadoCancelada " +
                          "AND ((r.fechaIngreso <= :fechaSalida AND r.fechaSalida >= :fechaIngreso))";
            
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("habitacionId", habitacionId);
            query.setParameter("estadoCancelada", EstadoReserva.CANCELADA);
            query.setParameter("fechaIngreso", fechaIngreso);
            query.setParameter("fechaSalida", fechaSalida);
            
            Long count = query.getSingleResult();
            return count == 0; // Si no hay reservas superpuestas, la habitación está disponible
        } catch (NoResultException e) {
            return true; // Si no hay resultados, la habitación está disponible
        } finally {
            em.close();
        }
    }
}


