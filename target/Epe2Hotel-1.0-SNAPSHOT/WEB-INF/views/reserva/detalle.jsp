<%-- 
    Document   : detalle
    Created on : 12-04-2025, 17:41:36
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />

<c:if test="${empty sessionScope.usuario}">
    <div class="alert alert-danger">
        Debes iniciar sesión para ver los detalles de la reserva.
        <a href="${pageContext.request.contextPath}/app/usuarios?action=login" class="btn btn-primary">Iniciar Sesión</a>
    </div>
</c:if>

<c:if test="${empty reserva}">
    <div class="alert alert-danger">
        La reserva solicitada no existe o no tienes acceso a ella.
    </div>
</c:if>

<c:if test="${not empty sessionScope.usuario && not empty reserva}">
    <div class="card mb-4">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">Detalles de la Reserva</h2>
                <span class="badge bg-${reserva.estado == 'CONFIRMADA' ? 'success' : 
                                        reserva.estado == 'CANCELADA' ? 'danger' : 
                                        reserva.estado == 'COMPLETADA' ? 'info' : 'warning'} fs-6">
                    ${reserva.estado}
                </span>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <h4>Información de la Reserva</h4>
                    <p><strong>Número de Reserva:</strong> #${reserva.id}</p>
                    <p><strong>Fecha de Ingreso:</strong> <fmt:formatDate value="${reserva.fechaIngreso}" pattern="dd/MM/yyyy" /></p>
                    <p><strong>Fecha de Salida:</strong> <fmt:formatDate value="${reserva.fechaSalida}" pattern="dd/MM/yyyy" /></p>
                    <p><strong>Duración:</strong> ${reserva.duracionEnDias()} noches</p>
                    <p><strong>Fecha de Creación:</strong> <fmt:formatDate value="${reserva.fechaCreacion}" pattern="dd/MM/yyyy HH:mm" /></p>
                </div>
                <div class="col-md-6">
                    <h4>Detalles del Pago</h4>
                    <p><strong>Precio por noche:</strong> <fmt:formatNumber value="${reserva.habitacion.precio}" type="currency" currencySymbol="$" /></p>
                    <p><strong>Número de noches:</strong> ${reserva.duracionEnDias()}</p>
                    <p><strong>Subtotal:</strong> <fmt:formatNumber value="${reserva.precioTotal * 0.81}" type="currency" currencySymbol="$" /></p>
                    <p><strong>Impuestos (19%):</strong> <fmt:formatNumber value="${reserva.precioTotal * 0.19}" type="currency" currencySymbol="$" /></p>
                    <hr>
                    <h5><strong>Total:</strong> <fmt:formatNumber value="${reserva.precioTotal}" type="currency" currencySymbol="$" /></h5>
                </div>
            </div>
            
            <hr>
            
            <div class="row mt-3">
                <div class="col-md-6">
                    <h4>Información del Hotel</h4>
                    <p><strong>Hotel:</strong> ${reserva.habitacion.hotel.nombre} 
                        <c:forEach begin="1" end="${reserva.habitacion.hotel.estrellas}">
                            <i class="bi bi-star-fill text-warning"></i>
                        </c:forEach>
                    </p>
                    <p><strong>Dirección:</strong> ${reserva.habitacion.hotel.direccion}, ${reserva.habitacion.hotel.ciudad}, ${reserva.habitacion.hotel.pais}</p>
                    <p><strong>Servicios:</strong> ${reserva.habitacion.hotel.servicios}</p>
                </div>
                <div class="col-md-6">
                    <h4>Información de la Habitación</h4>
                    <p><strong>Número:</strong> ${reserva.habitacion.numero}</p>
                    <p><strong>Tipo:</strong> ${reserva.habitacion.tipo}</p>
                    <p><strong>Capacidad:</strong> ${reserva.habitacion.capacidad} personas</p>
                    <p><strong>Descripción:</strong> ${reserva.habitacion.descripcion}</p>
                </div>
            </div>
            
            <c:if test="${reserva.estado == 'CONFIRMADA' || reserva.estado == 'PENDIENTE'}">
                <hr>
                <div class="row mt-3">
                    <div class="col-md-12">
                        <div class="alert alert-warning">
                            <h5>Política de Cancelación</h5>
                            <p>Puedes cancelar tu reserva sin costo hasta 48 horas antes de la fecha de ingreso.</p>
                            <p>Las cancelaciones realizadas dentro de las 48 horas previas a la fecha de ingreso tendrán un cargo del 50% del valor total de la reserva.</p>
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#cancelarModal">
                                Cancelar Reserva
                            </button>
                        </div>
                        
                        <!-- Modal de Cancelación -->
                        <div class="modal fade" id="cancelarModal" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Cancelar Reserva</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>¿Estás seguro que deseas cancelar esta reserva?</p>
                                        <p><strong>Hotel:</strong> ${reserva.habitacion.hotel.nombre}</p>
                                        <p><strong>Fechas:</strong> <fmt:formatDate value="${reserva.fechaIngreso}" pattern="dd/MM/yyyy" /> - 
                                        <fmt:formatDate value="${reserva.fechaSalida}" pattern="dd/MM/yyyy" /></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                        <a href="${pageContext.request.contextPath}/app/reservas?action=cancelar&id=${reserva.id}" 
                                           class="btn btn-danger">Confirmar Cancelación</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    
    <div class="d-flex justify-content-between">
        <a href="${pageContext.request.contextPath}/app/reservas?action=mis-reservas" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Volver a Mis Reservas
        </a>
        <a href="${pageContext.request.contextPath}/app/hoteles?action=detalle&id=${reserva.habitacion.hotel.id}" class="btn btn-primary">
            Ver Hotel
        </a>
    </div>
</c:if>

<jsp:include page="../common/footer.jsp" />
