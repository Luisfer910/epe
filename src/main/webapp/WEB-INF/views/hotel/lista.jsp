<%-- 
    Document   : lista
    Created on : 12-04-2025, 17:31:13
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" />

<div class="container mt-4">
    <!-- Banner superior -->
    <div class="card mb-4 bg-primary text-white">
        <div class="card-body p-4">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h1 class="display-5 fw-bold mb-2">Descubre nuestros hoteles</h1>
                    <p class="lead mb-0">Encuentra el alojamiento perfecto para tu próxima aventura</p>
                </div>
                <div class="col-md-4 text-md-end">
                    <span class="badge bg-light text-primary fs-5 p-2">
                        <i class="bi bi-building"></i> ${hoteles.size()} hoteles disponibles
                    </span>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Formulario de filtrado -->
    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h5 class="card-title mb-0"><i class="bi bi-funnel"></i> Filtrar hoteles</h5>
                <button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#filtroCollapse" aria-expanded="false" aria-controls="filtroCollapse">
                    <i class="bi bi-sliders"></i> Mostrar/Ocultar filtros
                </button>
            </div>
            
            <div class="collapse show" id="filtroCollapse">
                <form action="${pageContext.request.contextPath}/app/hoteles" method="get" class="row g-3">
                    <input type="hidden" name="action" value="filtrar">
                    
                    <div class="col-md-4">
                        <label for="ciudad" class="form-label">Ciudad</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                            <input type="text" class="form-control" id="ciudad" name="ciudad" value="${param.ciudad}" placeholder="Ej: Santiago">
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <label for="estrellas" class="form-label">Estrellas mínimas</label>
                        <select class="form-select" id="estrellas" name="estrellas">
                            <option value="">Todas</option>
                            <option value="1" ${param.estrellas == '1' ? 'selected' : ''}>1 estrella o más</option>
                            <option value="2" ${param.estrellas == '2' ? 'selected' : ''}>2 estrellas o más</option>
                            <option value="3" ${param.estrellas == '3' ? 'selected' : ''}>3 estrellas o más</option>
                            <option value="4" ${param.estrellas == '4' ? 'selected' : ''}>4 estrellas o más</option>
                            <option value="5" ${param.estrellas == '5' ? 'selected' : ''}>5 estrellas</option>
                        </select>
                    </div>
                    
                    <div class="col-md-3 align-self-end">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Filtrar
                        </button>
                    </div>
                    
                    <div class="col-md-2 align-self-end">
                        <a href="${pageContext.request.contextPath}/app/hoteles" class="btn btn-outline-secondary w-100">
                            <i class="bi bi-x-circle"></i> Limpiar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Resultados y ordenación -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <p class="mb-0">Mostrando <strong>${hoteles.size()}</strong> resultados</p>
        <div class="btn-group">
            <button type="button" class="btn btn-outline-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-sort-down"></i> Ordenar por
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/app/hoteles?action=ordenar&criterio=nombre&orden=asc">Nombre (A-Z)</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/app/hoteles?action=ordenar&criterio=nombre&orden=desc">Nombre (Z-A)</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/app/hoteles?action=ordenar&criterio=estrellas&orden=desc">Estrellas (Mayor a menor)</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/app/hoteles?action=ordenar&criterio=estrellas&orden=asc">Estrellas (Menor a mayor)</a></li>
            </ul>
        </div>
    </div>

    <!-- Tarjetas de hoteles -->
    <div class="row row-cols-1 row-cols-md-3 g-4 mb-4">
        <c:forEach var="hotel" items="${hoteles}">
            <div class="col">
                <div class="card h-100 shadow-sm hotel-card">
                    <!-- Imagen del hotel con badge de estrellas -->
                    <div class="position-relative">
                        <img src="${pageContext.request.contextPath}/assets/img/hotel-default.jpg" 
                             class="card-img-top" alt="${hotel.nombre}">
                        <div class="position-absolute top-0 end-0 m-2">
                            <span class="badge bg-warning text-dark p-2">
                                ${hotel.estrellas} <i class="bi bi-star-fill"></i>
                            </span>
                        </div>
                    </div>
                    
                    <div class="card-body">
                        <h5 class="card-title">${hotel.nombre}</h5>
                        <p class="card-text">
                            <i class="bi bi-geo-alt-fill text-primary"></i> ${hotel.ciudad}
                            <br>
                            <span class="text-warning">
                                <c:forEach begin="1" end="${hotel.estrellas}">
                                    <i class="bi bi-star-fill"></i>
                                </c:forEach>
                                <c:forEach begin="${hotel.estrellas + 1}" end="5">
                                    <i class="bi bi-star"></i>
                                </c:forEach>
                            </span>
                            <!-- Servicios destacados (simulados) -->
                            <div class="mt-2 d-flex flex-wrap gap-1">
                                <span class="badge bg-light text-dark"><i class="bi bi-wifi"></i> WiFi</span>
                                <span class="badge bg-light text-dark"><i class="bi bi-p-circle"></i> Parking</span>
                                <span class="badge bg-light text-dark"><i class="bi bi-cup-hot"></i> Desayuno</span>
                            </div>
                        </p>
                    </div>
                    <div class="card-footer bg-white border-top-0 d-flex justify-content-between align-items-center">
                        <span class="text-success fw-bold">Desde $99.99/noche</span>
                        <a href="${pageContext.request.contextPath}/app/hoteles?action=detalle&id=${hotel.id}" 
                           class="btn btn-primary">
                           <i class="bi bi-info-circle"></i> Ver Detalle
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <!-- Paginación -->
    <nav aria-label="Navegación de páginas">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Anterior</a>
            </li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Siguiente</a>
            </li>
        </ul>
    </nav>
</div>



<jsp:include page="../common/footer.jsp" />
