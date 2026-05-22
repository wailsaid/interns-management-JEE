<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<aside class="app-sidebar">
  <div class="app-sidebar__title">Navigation</div>
  <nav>
    <ul>
      <li><a href="${pageContext.request.contextPath}/page_d_acceil">Accueil</a></li>
      <li><a href="${pageContext.request.contextPath}/affichlistestagiare">Stagiaires</a></li>
      <li><a href="${pageContext.request.contextPath}/affichlisteencadreur">Encadreurs</a></li>
      <li><a href="${pageContext.request.contextPath}/affichlistestage">Stages</a></li>
      <li><a href="${pageContext.request.contextPath}/proposer">Proposer un thème</a></li>
      <li><a href="${pageContext.request.contextPath}/affecter">Affecter</a></li>
      <li><a href="${pageContext.request.contextPath}/evaluer">Évaluation</a></li>
      <c:if test="${sessionScope.user.username eq 'admin'}">
        <li class="app-sidebar__title" style="margin-top:1rem;">Administration</li>
        <li><a href="${pageContext.request.contextPath}/listadmins">Administrateurs</a></li>
        <li><a href="${pageContext.request.contextPath}/listcategoories">Catégories</a></li>
        <li><a href="${pageContext.request.contextPath}/listdirections">Directions</a></li>
      </c:if>
    </ul>
  </nav>
</aside>
