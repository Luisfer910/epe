package com.tuempresa.hotelreservation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

/**
 * Esta clase guarda la info de los usuarios que pueden hacer reservas en el sistema
 * @author LFMG9
 */
@Entity // Esto dice que la clase se guardará como una tabla en la base de datos
@Table(name = "users") // Le ponemos nombre "users" a la tabla en la BD
public class Usuario implements Serializable { // Serializable es para poder guardar los objetos en archivos
    
    // Un número para identificar la versión de la clase, no te compliques con esto
    private static final long serialVersionUID = 1L;
    
    // El número único que identifica a cada usuario
    @Id
    // Hace que el ID se cree solo cuando registramos un usuario nuevo
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(nullable = false, length = 50)
    private String apellido;
    
    @Column(nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(nullable = false)
    private String contraseña;
    
    @Column(length = 20)
    private String telefono;
    
    // Lista de todas las reservas que ha hecho este usuario
    // Si borramos al usuario, se borran todas sus reservas (por el cascade)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    // Constructor vacío que necesita JPA para crear usuarios
    public Usuario() {
    }

    // Constructor con todos los datos para crear un usuario completo
    public Usuario(Long id, String nombre, String apellido, String correo, String contraseña, String telefono, List<Reserva> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.reservas = reservas;
    }

    // A partir de aquí son los getters y setters
    // Sirven para leer y modificar los datos del usuario de forma segura
    
    // Obtener el ID del usuario
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Obtener el nombre
    public String getNombre() {
        return nombre;
    }

    // Cambiar el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Obtener el apellido
    public String getApellido() {
        return apellido;
    }

    // Cambiar el apellido
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Obtener el correo electrónico
    public String getCorreo() {
        return correo;
    }

    // Cambiar el correo electrónico
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    // Cambiar la contraseña
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    // Cambiar el número de teléfono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Obtener la lista de todas las reservas del usuario
    public List<Reserva> getReservas() {
        return reservas;
    }

    // Cambiar toda la lista de reservas
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Método útil para añadir una nueva reserva al usuario
    // También actualiza la relación en la reserva para que sepa a qué usuario pertenece
    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setUsuario(this);
    }
}
