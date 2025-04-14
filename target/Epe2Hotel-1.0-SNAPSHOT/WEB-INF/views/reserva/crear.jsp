<%-- 
    Document   : crear
    Created on : 12-04-2025, 17:31:47
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />

<c:if test="${empty sessionScope.usuario}">
    <div class="alert alert-danger">
        Debes iniciar sesión para realizar una reserva.
        <a href="${pageContext.request.contextPath}/app/usuarios?action=login" class="btn btn-primary">Iniciar Sesión</a>
    </div>
</c:if>

<c:if test="${empty habitacion}">
    <div class="alert alert-danger">
        La habitación solicitada no existe o no está disponible.
    </div>
</c:if>

<c:if test="${not empty sessionScope.usuario && not empty habitacion}">
    <div class="card mb-4">
        <div class="card-header">
            <h2 class="mb-0">Confirmar Reserva</h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <h4>Detalles de la Habitación</h4>
                    <p><strong>Hotel:</strong> ${habitacion.hotel.nombre}</p>
                    <p><strong>Habitación:</strong> ${habitacion.numero} - ${habitacion.tipo}</p>
                    <p><strong>Capacidad:</strong> ${habitacion.capacidad} personas</p>
                    <p><strong>Precio por noche:</strong> <fmt:formatNumber value="${habitacion.precio}" type="currency" currencySymbol="$" /></p>
                    <p><strong>Descripción:</strong> ${habitacion.descripcion}</p>
                </div>
                <div class="col-md-6">
                    <h4>Detalles de la Reserva</h4>
                    <form action="${pageContext.request.contextPath}/app/reservas" method="post" id="reservaForm">
                        <input type="hidden" name="action" value="confirmar">
                        <input type="hidden" name="habitacionId" value="${habitacion.id}">
                        
                        <div class="mb-3">
                            <label for="fechaIngreso" class="form-label">Fecha de Ingreso</label>
                            <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso" 
                                   value="${param.fechaIngreso}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="fechaSalida" class="form-label">Fecha de Salida</label>
                            <input type="date" class="form-control" id="fechaSalida" name="fechaSalida" 
                                   value="${param.fechaSalida}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="numPersonas" class="form-label">Número de Personas</label>
                            <select class="form-select" id="numPersonas" name="numPersonas" required>
                                <c:forEach begin="1" end="${habitacion.capacidad}" var="i">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="comentarios" class="form-label">Comentarios o Solicitudes Especiales</label>
                            <textarea class="form-control" id="comentarios" name="comentarios" rows="3"></textarea>
                        </div>
                    </form>
                </div>
            </div>
            
            <hr>
            
            <div class="row mt-4">
                <div class="col-md-6">
                    <h4>Información del Cliente</h4>
                    <p><strong>Nombre:</strong> ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</p>
                    <p><strong>Correo:</strong> ${sessionScope.usuario.correo}</p>
                    <p><strong>Teléfono:</strong> ${sessionScope.usuario.telefono}</p>
                </div>
                <div class="col-md-6">
                    <h4>Resumen de Costos</h4>
                    <div id="resumenCostos">
                        <p><strong>Precio por noche:</strong> <fmt:formatNumber value="${habitacion.precio}" type="currency" currencySymbol="$" /></p>
                        <p><strong>Número de noches:</strong> <span id="numNoches">-</span></p>
                        <p><strong>Subtotal:</strong> <span id="subtotal">-</span></p>
                        <p><strong>Impuestos (19%):</strong> <span id="impuestos">-</span></p>
                        <hr>
                        <h5><strong>Total a pagar:</strong> <span id="total">-</span></h5>
                    </div>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-md-12">
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/app/habitaciones?action=detalle&id=${habitacion.id}" class="btn btn-secondary">
                            <i class="bi bi-arrow-left"></i> Volver
                        </a>
                        <button type="submit" form="reservaForm" class="btn btn-success btn-lg">
                            Confirmar Reserva <i class="bi bi-check-circle"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Establecer fecha mínima como hoy
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('fechaIngreso').min = today;
        document.getElementById('fechaSalida').min = today;
        
        // Validar que la fecha de salida sea posterior a la de ingreso
        document.getElementById('fechaIngreso').addEventListener('change', function() {
            document.getElementById('fechaSalida').min = this.value;
            
            // Si la fecha de salida es anterior a la nueva fecha de ingreso, actualizarla
            const fechaSalida = document.getElementById('fechaSalida');
            if (fechaSalida.value && fechaSalida.value < this.value) {
                fechaSalida.value = this.value;
            }
            
            calcularCostos();
        });
        
        document.getElementById('fechaSalida').addEventListener('change', calcularCostos);
        
        // Calcular costos iniciales
        calcularCostos();
        
        function calcularCostos() {
            const fechaIngreso = document.getElementById('fechaIngreso').value;
            const fechaSalida = document.getElementById('fechaSalida').value;
            
            if (fechaIngreso && fechaSalida) {
                const fechaInicio = new Date(fechaIngreso);
                const fechaFin = new Date(fechaSalida);
                const diffTime = Math.abs(fechaFin - fechaInicio);
                const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
                
                if (diffDays > 0) {
                    const precioPorNoche = ${habitacion.precio};
                    const subtotal = precioPorNoche * diffDays;
                    const impuestos = subtotal * 0.19;
                    const total = subtotal + impuestos;
                    
                    document.getElementById('numNoches').textContent = diffDays;
                    document.getElementById('subtotal').textContent = '$' + subtotal.toLocaleString('es-CL');
                    document.getElementById('impuestos').textContent = '$' + impuestos.toLocaleString('es-CL');
                    document.getElementById('total').textContent = '$' + total.toLocaleString('es-CL');
                }
            }
        }
    });
</script>

<jsp:include page="../common/footer.jsp" />
