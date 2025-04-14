<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" />

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-2 bg-dark text-white py-3 min-vh-100">
            <h4 class="text-center mb-4">Panel Admin</h4>
            <div class="nav flex-column">
                <a href="${pageContext.request.contextPath}/app/admin/dashboard" 
                   class="nav-link text-white">
                    <i class="bi bi-speedometer2 me-2"></i>
                    Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/app/admin/usuarios" 
                   class="nav-link text-white">
                    <i class="bi bi-people me-2"></i>
                    Usuarios
                </a>
                <a href="${pageContext.request.contextPath}/app/admin/hoteles" 
                   class="nav-link text-white">
                    <i class="bi bi-building me-2"></i>
                    Hoteles
                </a>
                <a href="${pageContext.request.contextPath}/app/admin/reservas" 
                   class="nav-link text-white">
                    <i class="bi bi-calendar-check me-2"></i>
                    Reservas
                </a>
            </div>
        </div>

        <!-- Contenido principal -->
        <div class="col-md-10 py-4">
            <h2 class="mb-4">Dashboard</h2>
            
            <!-- Tarjetas de resumen -->
            <div class="row g-4 mb-4">
                <div class="col-md-3">
                    <div class="card bg-primary text-white">
                        <div class="card-body">
                            <h5 class="card-title">Total Usuarios</h5>
                            <h2 class="card-text">${totalUsuarios}</h2>
                            <i class="bi bi-people position-absolute bottom-0 end-0 mb-3 me-3 fs-1 opacity-50"></i>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card bg-success text-white">
                        <div class="card-body">
                            <h5 class="card-title">Total Hoteles</h5>
                            <h2 class="card-text">${totalHoteles}</h2>
                            <i class="bi bi-building position-absolute bottom-0 end-0 mb-3 me-3 fs-1 opacity-50"></i>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card bg-warning text-dark">
                        <div class="card-body">
                            <h5 class="card-title">Reservas Activas</h5>
                            <h2 class="card-text">${reservasActivas}</h2>
                            <i class="bi bi-calendar-check position-absolute bottom-0 end-0 mb-3 me-3 fs-1 opacity-50"></i>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3">
                    <div class="card bg-info text-white">
                        <div class="card-body">
                            <h5 class="card-title">Ingresos Totales</h5>
                            <h2 class="card-text">$${ingresosTotales}</h2>
                            <i class="bi bi-cash position-absolute bottom-0 end-0 mb-3 me-3 fs-1 opacity-50"></i>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Últimas reservas -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Últimas Reservas</h5>
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Usuario</th>
                                <th>Hotel</th>
                                <th>Fecha Ingreso</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reserva" items="${ultimasReservas}">
                                <tr>
                                    <td>${reserva.id}</td>
                                    <td>${reserva.usuario.nombre}</td>
                                    <td>${reserva.habitacion.hotel.nombre}</td>
                                    <td>${reserva.fechaIngreso}</td>
                                    <td>
                                        <span class="badge bg-success">Activa</span>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm btn-info">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
