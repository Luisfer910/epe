<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />

<div class="container py-4">
    <!-- Mensaje de error si no hay hotel -->
    <c:if test="${empty hotel}">
        <div class="alert alert-danger">
            El hotel solicitado no existe.
        </div>
    </c:if>

    <!-- Información del hotel -->
    <c:if test="${not empty hotel}">
        <div class="card mb-4">
            <div class="card-header">
                <h2>${hotel.nombre}</h2>
                <div class="text-warning">
                    <c:forEach begin="1" end="${hotel.estrellas}">
                        <i class="bi bi-star-fill"></i>
                    </c:forEach>
                </div>
            </div>
            
            <div class="card-body">
                <div class="row">
                    <!-- Información principal -->
                    <div class="col-md-8">
                        <p><i class="bi bi-geo-alt"></i> <strong>Dirección:</strong> ${hotel.direccion}, ${hotel.ciudad}, ${hotel.pais}</p>
                        <p><strong>Descripción:</strong> ${hotel.descripcion}</p>
                        <p><strong>Servicios:</strong> ${hotel.servicios}</p>
                    </div>
                    
                    <!-- Información de contacto -->
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <h5>Información de Contacto</h5>
                                <p><i class="bi bi-telephone"></i> +56 2 1234 5678</p>
                                <p><i class="bi bi-envelope"></i> info@hotel.com</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Formulario de búsqueda -->
        <div class="card mb-4">
            <div class="card-body">
                <h5>Buscar Disponibilidad</h5>
                <form action="${pageContext.request.contextPath}/app/hoteles" method="get">
                    <input type="hidden" name="action" value="detalle">
                    <input type="hidden" name="id" value="${hotel.id}">
                    
                    <div class="row g-3">
                        <div class="col-md-5">
                            <label for="fechaIngreso" class="form-label">Fecha de Ingreso</label>
                            <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso" required>
                        </div>
                        
                        <div class="col-md-5">
                            <label for="fechaSalida" class="form-label">Fecha de Salida</label>
                            <input type="date" class="form-control" id="fechaSalida" name="fechaSalida" required>
                        </div>
                        
                        <div class="col-md-2">
                            <label class="form-label">&nbsp;</label>
                            <button type="submit" class="btn btn-primary w-100">Buscar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Lista de habitaciones -->
        <h3>Habitaciones Disponibles</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Número</th>
                        <th>Tipo</th>
                        <th>Capacidad</th>
                        <th>Precio por Noche</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="habitacion" items="${habitaciones}">
                        <tr>
                            <td>${habitacion.numero}</td>
                            <td>${habitacion.tipo}</td>
                            <td>${habitacion.capacidad} personas</td>
                            <td><fmt:formatNumber value="${habitacion.precio}" type="currency" currencySymbol="$" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/habitaciones?action=detalle&id=${habitacion.id}" 
                                   class="btn btn-sm btn-info">Ver Detalles</a>
                                <c:if test="${not empty sessionScope.usuario}">
                                    <a href="${pageContext.request.contextPath}/app/reservas?action=crear&habitacionId=${habitacion.id}" 
                                       class="btn btn-sm btn-success">Reservar</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Establecer fecha mínima como hoy
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('fechaIngreso').min = today;
    document.getElementById('fechaSalida').min = today;
    
    // Validar fechas
    document.getElementById('fechaIngreso').addEventListener('change', function() {
        document.getElementById('fechaSalida').min = this.value;
    });
});
</script>

<jsp:include page="../common/footer.jsp" />
