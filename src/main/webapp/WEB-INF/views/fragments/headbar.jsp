<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<header class="app-header">
  <div class="app-header__brand">
    <img src="${pageContext.request.contextPath}/img/logo.svg" alt="Logo">
    <span>Gestion des stages</span>
  </div>
  <div class="app-header__user">
    <c:if test="${not empty sessionScope.user}">
      <span>Connecté : <strong>${sessionScope.user.username}</strong></span>
      <a href="${pageContext.request.contextPath}/deconnecter" class="btn btn-secondary" style="color:#0f172a;padding:0.4rem 0.9rem;">Déconnexion</a>
    </c:if>
  </div>
</header>
