<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Accueil</title>
</head>
<body>
  <%@include file="/WEB-INF/views/fragments/headbar.jsp" %>
  <%@include file="/WEB-INF/views/fragments/menu.jsp" %>
  <main class="app-main">
    <div class="card welcome-card">
      <h1>Bienvenue</h1>
      <p>Utilisez le menu pour gérer les stagiaires, encadreurs et stages.</p>
    </div>
  </main>
</body>
</html>
