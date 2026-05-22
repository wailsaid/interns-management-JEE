<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Profile d'un Administrateur</title>
    

    
  </head>
  <body>
	<%@include file="/WEB-INF/views/fragments/headbar.jsp" %>

<div class="app-main">
 <div class="card">
 
  <div> 
<h3>Profile d'un Administrateur :</h3>



 <button onclick="window.location.href ='modiferAdmin?q=${requestScope.admin.getid()}'">Modifer</button>
   <button onclick="supprimer()"> Supprimer</button>
</div>
<span>Nom : </span><c:out value="${requestScope.admin.getnom()}"/>
<hr>
 <span>Prénom : </span><c:out value="${requestScope.admin.getprenom()}"/>
<hr>
 <span>Nom d'Utilisateur : </span><c:out value="${requestScope.admin.getusername()}"/>
 <hr>
 <span>Direction : </span><c:out value="${requestScope.admin.getdirection()}"/>
<hr>
 <span>Categorie : </span><c:out value="${requestScope.admin.getcategorie()}"/>
 <hr>
 <span>Mot de Pass : </span><c:out value="${requestScope.admin.getpassword()}"/>
 
 </div>


</div>

	 <%@include file="/WEB-INF/views/fragments/menu.jsp" %>
	 
	<script>
function supprimer() {
  var r = confirm("êtes-vous sûr de vouloir supprimer ce administrateur ?");
  if (r == true) {
	  window.location.href ='suprimerAdmin?q=${requestScope.admin.getid()}';
  }
  
}
</script>
</body>
</html>
