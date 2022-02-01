<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Profile d'un Encadreur</title>
    

    
  </head>
  <body>
	<%@include file="resourse/headbar.jsp" %>

<div class="profil_stage">
 <div class="profil_body">
  
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

	 <%@include file="resourse/menu.jsp" %>
	 
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