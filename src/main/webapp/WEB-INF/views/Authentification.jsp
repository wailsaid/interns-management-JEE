<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Connexion</title>
</head>
<body class="login-page">
  <div class="login-header">
    <img src="${pageContext.request.contextPath}/img/logo.svg" alt="Logo">
  </div>
  <div class="login-wrapper">
    <div class="login-card">
      <h1>Connexion</h1>
      <p class="subtitle">Gestion des stages et stagiaires</p>
      <c:if test="${erreur}">
        <div class="alert alert-error">Nom d'utilisateur ou mot de passe invalide.</div>
      </c:if>
      <form action="${pageContext.request.contextPath}/Authentifier" method="post">
        <div class="form-group">
          <label for="username">Nom d'utilisateur</label>
          <input type="text" id="username" name="username" placeholder="Nom d'utilisateur" required autocomplete="username">
        </div>
        <div class="form-group">
          <label for="password">Mot de passe</label>
          <input type="password" id="password" name="password" placeholder="Mot de passe" required autocomplete="current-password">
        </div>
        <button type="submit" class="btn btn-primary">Se connecter</button>
      </form>
      <div class="login-footer">
        <a href="${pageContext.request.contextPath}/employee">Accès employé (sans authentification)</a>
      </div>
    </div>
  </div>
</body>
</html>
