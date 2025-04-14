package com.tuempresa.hotelreservation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

/**
 *
 * @author LFMG9
 */

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaIngreso;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaSalida;
    
    @Column(nullable = false)
    private BigDecimal precioTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    
    // Enum para el estado de la reserva
    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
    }
    
    // Constructores
    public Reserva() {
        this.fechaCreacion = new Date();
    }
    
    public Reserva(Date fechaIngreso, Date fechaSalida, BigDecimal precioTotal, EstadoReserva estado) {
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.fechaCreacion = new Date();
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    // Método para calcular la duración de la estancia en días
    public int getDuracionEnDias() {
        long diff = fechaSalida.getTime() - fechaIngreso.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
    
    // Método para calcular el precio total basado en la duración y el precio de la habitación
    public void calcularPrecioTotal() {
        if (habitacion != null) {
            int dias = getDuracionEnDias();
            this.precioTotal = habitacion.getPrecio().multiply(new BigDecimal(dias));
        }
    }
}


