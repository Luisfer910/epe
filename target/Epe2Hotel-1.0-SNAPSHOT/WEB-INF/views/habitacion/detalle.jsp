<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />

<div class="container py-4">
    <c:if test="${empty habitacion}">
        <div class="alert alert-danger shadow-sm">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            La habitación solicitada no existe.
        </div>
    </c:if>

    <c:if test="${not empty habitacion}">
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-primary text-white py-3">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="mb-0">
                        <i class="bi bi-door-open me-2"></i>
                        Habitación ${habitacion.numero}
                    </h2>
                    <span class="badge bg-light text-primary">${habitacion.tipo}</span>
                </div>
            </div>
            <div class="card-body">
                <div class="row g-4">
                    <!-- Columna Principal -->
                    <div class="col-md-8">
                        <!-- Información Principal -->
                        <div class="p-3 bg-light rounded mb-4">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="d-flex align-items-center">
                                        <i class="bi bi-people-fill fs-4 me-2 text-primary"></i>
                                        <div>
                                            <small class="text-muted">Capacidad</small>
                                            <p class="mb-0 fw-bold">${habitacion.capacidad} personas</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="d-flex align-items-center">
                                        <i class="bi bi-cash fs-4 me-2 text-success"></i>
                                        <div>
                                            <small class="text-muted">Precio por noche</small>
                                            <p class="mb-0 fw-bold">
                                                <fmt:formatNumber value="${habitacion.precio}" type="currency" currencySymbol="$" />
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Descripción -->
                        <div class="mb-4">
                            <h5 class="border-bottom pb-2">
                                <i class="bi bi-info-circle me-2"></i>
                                Descripción
                            </h5>
                            <p class="lead">${habitacion.descripcion}</p>
                        </div>

                        <!-- Características -->
                        <div class="mb-4">
                            <h5 class="border-bottom pb-2">
                                <i class="bi bi-check-circle me-2"></i>
                                Características
                            </h5>
                            <div class="d-flex flex-wrap gap-2">
                                <span class="badge bg-secondary"><i class="bi bi-snow me-1"></i>Aire acondicionado</span>
                                <span class="badge bg-secondary"><i class="bi bi-tv me-1"></i>TV de pantalla plana</span>
                                <span class="badge bg-secondary"><i class="bi bi-cup-straw me-1"></i>Minibar</span>
                                <span class="badge bg-secondary"><i class="bi bi-safe me-1"></i>Caja fuerte</span>
                                <span class="badge bg-secondary"><i class="bi bi-droplet me-1"></i>Baño privado</span>
                                <span class="badge bg-secondary"><i class="bi bi-wifi me-1"></i>WiFi gratuito</span>
                            </div>
                        </div>

                        <!-- Información del Hotel -->
                        <div class="card bg-light">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <i class="bi bi-building me-2"></i>
                                    Información del Hotel
                                </h5>
                                <hr>
                                <p class="mb-2">
                                    <strong>${habitacion.hotel.nombre}</strong>
                                    <span class="ms-2">
                                        <c:forEach begin="1" end="${habitacion.hotel.estrellas}">
                                            <i class="bi bi-star-fill text-warning"></i>
                                        </c:forEach>
                                    </span>
                                </p>
                                <p class="mb-2">
                                    <i class="bi bi-geo-alt me-2"></i>
                                    ${habitacion.hotel.direccion}, ${habitacion.hotel.ciudad}, ${habitacion.hotel.pais}
                                </p>
                                <p class="mb-0">
                                    <i class="bi bi-list-check me-2"></i>
                                    ${habitacion.hotel.servicios}
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Columna de Reserva -->
                    <div class="col-md-4">
                        <div class="card shadow border-primary">
                            <div class="card-header bg-primary text-white">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-calendar-check me-2"></i>
                                    Reservar esta habitación
                                </h5>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/app/reservas" method="get">
                                    <input type="hidden" name="action" value="crear">
                                    <input type="hidden" name="habitacionId" value="${habitacion.id}">
                                    
                                    <div class="mb-3">
                                        <label for="fechaIngreso" class="form-label">
                                            <i class="bi bi-calendar-plus me-1"></i>
                                            Fecha de Ingreso
                                        </label>
                                        <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso" required>
                                    </div>
                                    
                                    <div class="mb-3">
                                        <label for="fechaSalida" class="form-label">
                                            <i class="bi bi-calendar-minus me-1"></i>
                                            Fecha de Salida
                                        </label>
                                        <input type="date" class="form-control" id="fechaSalida" name="fechaSalida" required>
                                    </div>
                                    
                                    <c:choose>
                                        <c:when test="${empty sessionScope.usuario}">
                                            <div class="alert alert-warning">
                                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                                Debes <a href="${pageContext.request.contextPath}/app/usuarios?action=login" class="alert-link">iniciar sesión</a> para reservar.
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn btn-success w-100">
                                                <i class="bi bi-check-circle me-2"></i>
                                                Reservar Ahora
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Botones de Navegación -->
        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/app/hoteles?action=detalle&id=${habitacion.hotel.id}" 
               class="btn btn-outline-primary">
                <i class="bi bi-arrow-left me-2"></i>
                Volver al Hotel
            </a>
            <a href="${pageContext.request.contextPath}/app/hoteles" 
               class="btn btn-outline-secondary">
                <i class="bi bi-building me-2"></i>
                Ver Todos los Hoteles
            </a>
        </div>
    </c:if>
</div>

<style>
.card {
    transition: all 0.3s ease;
}

.badge {
    font-weight: 500;
    padding: 0.5em 1em;
}

.card:hover {
    transform: translateY(-5px);
}

.btn {
    transition: all 0.3s ease;
}

.btn:hover {
    transform: translateY(-2px);
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const fechaIngreso = document.getElementById('fechaIngreso');
    const fechaSalida = document.getElementById('fechaSalida');
    
    // Establecer fecha mínima como hoy
    const today = new Date().toISOString().split('T')[0];
    fechaIngreso.min = today;
    fechaSalida.min = today;
    
    // Validar fechas
    fechaIngreso.addEventListener('change', function() {
        fechaSalida.min = this.value;
        if (fechaSalida.value && fechaSalida.value < this.value) {
            fechaSalida.value = this.value;
        }
    });
});
</script>

<jsp:include page="../common/footer.jsp" />
