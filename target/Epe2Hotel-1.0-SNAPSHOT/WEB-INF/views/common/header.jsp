<%-- 
    Document   : header
    Created on : 12-04-2025, 17:29:30
    Author     : LFMG9
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sistema de Reservas de Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/app/">Reservas Hotel</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/hoteles">Hoteles</a>
                    </li>
                    <c:if test="${not empty sessionScope.usuario}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/reservas?action=mis-reservas">Mis Reservas</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${empty sessionScope.usuario}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/usuarios?action=login">Iniciar Sesión</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/usuarios?action=registro">Registrarse</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/usuarios?action=perfil">
                                    Hola, ${sessionScope.usuario.nombre}
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/usuarios?action=logout">Cerrar Sesión</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
