<%-- 
    Document   : index
    Created on : 12-04-2025, 17:12:48
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp" />

<div class="jumbotron">
    <h1 class="display-4">¡Bienvenido al Sistema de Reservas de Hotel!</h1>
    <p class="lead">Encuentra el hotel perfecto para tus vacaciones o viajes de negocios.</p>
    <hr class="my-4">
    <p>Busca entre nuestra amplia selección de hoteles en diferentes ciudades.</p>
    
    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title">Buscar Hoteles</h5>
            <form action="${pageContext.request.contextPath}/app/hoteles" method="get">
                <input type="hidden" name="action" value="buscar">
                
                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="ciudad" class="form-label">Ciudad</label>
                        <input type="text" class="form-control" id="ciudad" name="ciudad" required>
                    </div>
                    
                    <div class="col-md-3">
                        <label for="fechaIngreso" class="form-label">Fecha de Ingreso</label>
                        <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso" required>
                    </div>
                    
                    <div class="col-md-3">
                        <label for="fechaSalida" class="form-label">Fecha de Salida</label>
                        <input type="date" class="form-control" id="fechaSalida" name="fechaSalida" required>
                    </div>
                    
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Buscar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="row mt-5">
    <div class="col-md-4">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Amplia Selección</h5>
                <p class="card-text">Encuentra hoteles de todas las categorías, desde económicos hasta de lujo.</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Reservas Fáciles</h5>
                <p class="card-text">Proceso de reserva simple y rápido, con confirmación inmediata.</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Gestión de Reservas</h5>
                <p class="card-text">Gestiona tus reservas en cualquier momento, con opciones de modificación y cancelación.</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
