<%-- 
    Document   : gestionar
    Created on : 12-04-2025, 17:31:58
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />

<h2 class="mb-4">Mis Reservas</h2>

<c:if test="${empty sessionScope.usuario}">
    <div class="alert alert-danger">
        Debes iniciar sesión para ver tus reservas.
        <a href="${pageContext.request.contextPath}/app/usuarios?action=login" class="btn btn-primary">Iniciar Sesión</a>
    </div>
</c:if>

<c:if test="${not empty sessionScope.usuario}">
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">
            ${mensaje}
        </div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
    </c:if>
    
    <div class="card mb-4">
        <div class="card-body">
            <ul class="nav nav-tabs" id="reservasTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="activas-tab" data-bs-toggle="tab" 
                            data-bs-target="#activas" type="button" role="tab" aria-selected="true">
                        Reservas Activas
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="historial-tab" data-bs-toggle="tab" 
                            data-bs-target="#historial" type="button" role="tab" aria-selected="false">
                        Historial
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="canceladas-tab" data-bs-toggle="tab" 
                            data-bs-target="#canceladas" type="button" role="tab" aria-selected="false">
                        Canceladas
                    </button>
                </li>
            </ul>
            
            <div class="tab-content pt-3" id="reservasTabsContent">
                <!-- Reservas Activas -->
                <div class="tab-pane fade show active" id="activas" role="tabpanel">
                    <c:choose>
                        <c:when test="${empty reservasActivas}">
                            <div class="alert alert-info">
                                No tienes reservas activas en este momento.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Hotel</th>
                                            <th>Habitación</th>
                                            <th>Fecha Ingreso</th>
                                            <th>Fecha Salida</th>
                                            <th>Estado</th>
                                            <th>Total</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservasActivas}">
                                            <tr>
                                                <td>${reserva.habitacion.hotel.nombre}</td>
                                                <td>${reserva.habitacion.tipo} (${reserva.habitacion.numero})</td>
                                                <td><fmt:formatDate value="${reserva.fechaIngreso}" pattern="dd/MM/yyyy" /></td>
                                                <td><fmt:formatDate value="${reserva.fechaSalida}" pattern="dd/MM/yyyy" /></td>
                                                <td>
                                                    <span class="badge bg-${reserva.estado == 'CONFIRMADA' ? 'success' : 'warning'}">
                                                        ${reserva.estado}
                                                    </span>
                                                </td>
                                                <td><fmt:formatNumber value="${reserva.precioTotal}" type="currency" currencySymbol="$" /></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/app/reservas?action=detalle&id=${reserva.id}" 
                                                       class="btn btn-sm btn-info">Ver</a>
                                                    <button type="button" class="btn btn-sm btn-danger" 
                                                            data-bs-toggle="modal" data-bs-target="#cancelarModal${reserva.id}">
                                                        Cancelar
                                                    </button>
                                                    
                                                    <!-- Modal de Cancelación -->
                                                    <div class="modal fade" id="cancelarModal${reserva.id}" tabindex="-1" aria-hidden="true">
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
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- Historial de Reservas -->
                <div class="tab-pane fade" id="historial" role="tabpanel">
                    <c:choose>
                        <c:when test="${empty reservasCompletadas}">
                            <div class="alert alert-info">
                                No tienes reservas completadas en tu historial.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Hotel</th>
                                            <th>Habitación</th>
                                            <th>Fecha Ingreso</th>
                                            <th>Fecha Salida</th>
                                            <th>Estado</th>
                                            <th>Total</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservasCompletadas}">
                                            <tr>
                                                <td>${reserva.habitacion.hotel.nombre}</td>
                                                <td>${reserva.habitacion.tipo} (${reserva.habitacion.numero})</td>
                                                <td><fmt:formatDate value="${reserva.fechaIngreso}" pattern="dd/MM/yyyy" /></td>
                                                <td><fmt:formatDate value="${reserva.fechaSalida}" pattern="dd/MM/yyyy" /></td>
                                                <td>
                                                    <span class="badge bg-info">
                                                        ${reserva.estado}
                                                    </span>
                                                </td>
                                                <td><fmt:formatNumber value="${reserva.precioTotal}" type="currency" currencySymbol="$" /></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/app/reservas?action=detalle&id=${reserva.id}" 
                                                       class="btn btn-sm btn-info">Ver</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- Reservas Canceladas -->
                <div class="tab-pane fade" id="canceladas" role="tabpanel">
                    <c:choose>
                        <c:when test="${empty reservasCanceladas}">
                            <div class="alert alert-info">
                                No tienes reservas canceladas.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Hotel</th>
                                            <th>Habitación</th>
                                            <th>Fecha Ingreso</th>
                                            <th>Fecha Salida</th>
                                            <th>Estado</th>
                                            <th>Total</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservasCanceladas}">
                                            <tr>
                                                <td>${reserva.habitacion.hotel.nombre}</td>
                                                <td>${reserva.habitacion.tipo} (${reserva.habitacion.numero})</td>
                                                <td><fmt:formatDate value="${reserva.fechaIngreso}" pattern="dd/MM/yyyy" /></td>
                                                <td><fmt:formatDate value="${reserva.fechaSalida}" pattern="dd/MM/yyyy" /></td>
                                                <td>
                                                    <span class="badge bg-danger">
                                                        ${reserva.estado}
                                                    </span>
                                                </td>
                                                <td><fmt:formatNumber value="${reserva.precioTotal}" type="currency" currencySymbol="$" /></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/app/reservas?action=detalle&id=${reserva.id}" 
                                                       class="btn btn-sm btn-info">Ver</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="../common/footer.jsp" />
