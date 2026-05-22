<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Profile d'un Encadreur</title>
    

    
  </head>
  <body>
	<%@include file="/WEB-INF/views/fragments/headbar.jsp" %>

<div class="app-main">
 <div class="card">
  
  <div> 
<h3>Profile d'un Encadreur :</h3>

<c:if test="${sessionScope.user.getcategorie() eq requestScope.encadreur.getcategorie()}">
 <button onclick="window.location.href ='modiferEncadreur?q=${requestScope.encadreur.getid()}'">Modifer</button>
 <c:if test="${requestScope.suivi ne 1}">  <button onclick="supprimer()"> Supprimer</button></c:if>
</c:if>
</div>
<span>Nom : </span><c:out value="${requestScope.encadreur.getnom()}"/>
<hr>
 <span>Prénom : </span><c:out value="${requestScope.encadreur.getprenom()}"/>
<hr>
 <span>Email : </span><c:out value="${requestScope.encadreur.getemail()}"/>
 <hr>
 <span>Direction : </span><c:out value="${requestScope.encadreur.getdirection()}"/>
 <hr>
 <span>Categorie : </span><c:out value="${requestScope.encadreur.getcategorie()}"/>


 </div>


</div>

	 <%@include file="/WEB-INF/views/fragments/menu.jsp" %>
	 
	<script>
function supprimer() {
  var r = confirm("êtes-vous sûr de vouloir supprimer ce Encadreur ?");
  if (r == true) {
	  window.location.href ='suprimerEncadreur?q=${requestScope.encadreur.getid()}';
  }
  
}
</script>

</body>
</html>
