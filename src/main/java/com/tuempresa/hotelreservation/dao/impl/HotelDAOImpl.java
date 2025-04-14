package com.tuempresa.hotelreservation.dao.impl;

import com.tuempresa.hotelreservation.dao.HotelDAO;
import com.tuempresa.hotelreservation.model.Hotel;
import com.tuempresa.hotelreservation.util.JPAUtil;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author LFMG9
 */

public class HotelDAOImpl implements HotelDAO {

    @Override
    public Hotel guardar(Hotel hotel) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return hotel;
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
    public Hotel actualizar(Hotel hotel) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            hotel = em.merge(hotel);
            em.getTransaction().commit();
            return hotel;
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
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel != null) {
                em.remove(hotel);
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
    public Hotel buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Hotel.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Hotel> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h", Hotel.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Hotel> buscarPorCiudad(String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery(
                    "SELECT h FROM Hotel h WHERE h.ciudad = :ciudad", Hotel.class);
            query.setParameter("ciudad", ciudad);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Hotel> buscarPorEstrellas(Integer estrellas) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery(
                    "SELECT h FROM Hotel h WHERE h.estrellas = :estrellas", Hotel.class);
            query.setParameter("estrellas", estrellas);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Hotel> buscarHotelesDisponiblesEnCiudad(String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery(
                    "SELECT DISTINCT h FROM Hotel h JOIN h.habitaciones hab " +
                    "WHERE h.ciudad = :ciudad AND hab.disponible = true", Hotel.class);
            query.setParameter("ciudad", ciudad);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}


