<%-- 
    Document   : perfil
    Created on : 12-04-2025, 17:32:33
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header">
                <h2 class="text-center">Mi Perfil</h2>
            </div>
            <div class="card-body">
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success" role="alert">
                        ${mensaje}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                
                <div class="row">
                    <div class="col-md-6">
                        <h4>Información Personal</h4>
                        <form action="${pageContext.request.contextPath}/app/usuarios" method="post">
                            <input type="hidden" name="action" value="actualizar">
                            
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" 
                                       value="${sessionScope.usuario.nombre}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" id="apellido" name="apellido" 
                                       value="${sessionScope.usuario.apellido}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="correo" class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="correo" name="correo" 
                                       value="${sessionScope.usuario.correo}" readonly>
                                <div class="form-text">El correo electrónico no se puede cambiar.</div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="telefono" class="form-label">Teléfono</label>
                                <input type="tel" class="form-control" id="telefono" name="telefono" 
                                       value="${sessionScope.usuario.telefono}">
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Actualizar Información</button>
                        </form>
                    </div>
                    
                    <div class="col-md-6">
                        <h4>Cambiar Contraseña</h4>
                        <form action="${pageContext.request.contextPath}/app/usuarios" method="post">
                            <input type="hidden" name="action" value="cambiarContraseña">
                            
                            <div class="mb-3">
                                <label for="contraseñaActual" class="form-label">Contraseña Actual</label>
                                <input type="password" class="form-control" id="contraseñaActual" 
                                       name="contraseñaActual" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="nuevaContraseña" class="form-label">Nueva Contraseña</label>
                                <input type="password" class="form-control" id="nuevaContraseña" 
                                       name="nuevaContraseña" required minlength="6">
                                <div class="form-text">La contraseña debe tener al menos 6 caracteres.</div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="confirmarNuevaContraseña" class="form-label">Confirmar Nueva Contraseña</label>
                                <input type="password" class="form-control" id="confirmarNuevaContraseña" 
                                       name="confirmarNuevaContraseña" required minlength="6">
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
                        </form>
                    </div>
                </div>
                
                <hr>
                
                <h4 class="mt-4">Mis Reservas Recientes</h4>
                <c:choose>
                    <c:when test="${empty sessionScope.usuario.reservas}">
                        <div class="alert alert-info">
                            No tienes reservas recientes.
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
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="reserva" items="${sessionScope.usuario.reservas}">
                                        <tr>
                                            <td>${reserva.habitacion.hotel.nombre}</td>
                                            <td>${reserva.habitacion.tipo} (${reserva.habitacion.numero})</td>
                                            <td>${reserva.fechaIngreso}</td>
                                            <td>${reserva.fechaSalida}</td>
                                            <td>${reserva.estado}</td>
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

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const cambiarContraseñaForm = document.querySelector('form[action$="cambiarContraseña"]');
        cambiarContraseñaForm.addEventListener('submit', function(event) {
            const password = document.getElementById('nuevaContraseña').value;
            const confirmPassword = document.getElementById('confirmarNuevaContraseña').value;
            
            if (password !== confirmPassword) {
                event.preventDefault();
                alert('Las contraseñas no coinciden');
            }
        });
    });
</script>

<jsp:include page="../common/footer.jsp" />