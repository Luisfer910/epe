package com.tuempresa.hotelreservation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

/**
 * Clase para manejar la info de las habitaciones en el sistema de reservas
 * @autor LFMG9
 */

@Entity // Indica que la clase se guardará como una tabla en la base de datos
@Table(name = "habitaciones") // Nombre de la tabla en la BD
public class Habitacion implements Serializable { // Serializable permite guardar objetos en archivos
    
    // Un número para identificar la versión de la clase, no te preocupes por esto
    private static final long serialVersionUID = 1L;
    
    // ID único para cada habitación
    @Id
    // El ID se genera automáticamente cuando se crea una nueva habitación
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Número de habitación, obligatorio
    @Column(nullable = false)
    private String numero;
    
    // Tipo de habitación (ej. simple, doble), obligatorio
    @Column(nullable = false)
    private String tipo;
    
    // Capacidad de personas, obligatorio
    @Column(nullable = false)
    private Integer capacidad;
    
    // Precio por noche, obligatorio
    @Column(nullable = false)
    private BigDecimal precio;
    
    // Si la habitación está disponible o no, obligatorio
    @Column(nullable = false)
    private Boolean disponible;
    
    // Descripción de la habitación, opcional y hasta 500 caracteres
    @Column(length = 500)
    private String descripcion;
    
    // Relación con el hotel al que pertenece la habitación, obligatorio
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
    
    // Lista de reservas para esta habitación
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();
    
    // Constructor por defecto, inicializa la habitación como disponible
    public Habitacion() {
        this.disponible = true;
    }
    
    // Constructor con los datos básicos necesarios
    public Habitacion(String numero, String tipo, Integer capacidad, BigDecimal precio) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precio = precio;
        this.disponible = true;
    }
    
    // Getters y setters para acceder y modificar los datos de manera segura
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    // Método para añadir una reserva y actualizar la relación
    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setHabitacion(this);
    }

}


