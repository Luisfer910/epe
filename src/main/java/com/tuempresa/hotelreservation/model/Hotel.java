package com.tuempresa.hotelreservation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

/**
 * Esta clase representa un hotel en nuestro sistema de reservaciones
 * @author LFMG9
 */

// Esto le dice a Java que esta clase se guardará en la base de datos
@Entity
// Le damos nombre a la tabla en la base de datos
@Table(name = "hoteles")
public class Hotel implements Serializable {
    
    // Esto es para cuando guardamos objetos en archivos
    private static final long serialVersionUID = 1L;
    
    // id único que identifica a cada hotel
    @Id
    // Hace que el ID se genere automáticamente cuando creamos un nuevo hotel
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // El nombre del hotel
    @Column(nullable = false, length = 100)
    private String nombre;
    
    
    @Column(nullable = false, length = 200)
    private String direccion;
    
   
    @Column(nullable = false, length = 50)
    private String ciudad;
    

    @Column(nullable = false, length = 50)
    private String pais;
    
    
    @Column(nullable = false)
    private Integer estrellas;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(length = 200)
    private String servicios;
    
    // Esta es la relación con las habitaciones - un hotel tiene muchas habitaciones
    // Si borramos el hotel, se borran todas sus habitaciones 
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones = new ArrayList<>();
    
    // Constructor vacío
    public Hotel() {
   
    }
    
    // Constructor con los datos básicos obligatorios
    public Hotel(String nombre, String direccion, String ciudad, String pais, Integer estrellas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estrellas = estrellas;
    }
    
    // Métodos para obtener y cambiar los valores (getters y setters)
    // Estos son necesarios para acceder a las propiedades desde otras clases
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
    
    // Método útil para añadir una habitación al hotel
    // También actualiza la relación en la habitación
    public void addHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        habitacion.setHotel(this);
    }
}
