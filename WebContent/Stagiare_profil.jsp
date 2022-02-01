<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Profile d'un Stagiare</title>
    

    
  </head>
  <body>
	<%@include file="resourse/headbar.jsp" %>

<div class="profil_stage">
 <div class="profil_body">
 
  <div> 
<h3>Profile d'un Stagiare :</h3>

<c:if test="${sessionScope.user.getcategorie() eq requestScope.stagiare.getcategorie()}">
 <button onclick="window.location.href ='modiferStagire?q=${requestScope.stagiare.getid()}'">Modifer</button>
 <c:if test="${requestScope.stagiare.getetat() ne 'travailler en stage'}"> <button onclick="supprimer()"> Supprimer</button></c:if> 
</c:if>
</div>
<span>Nom : </span><c:out value="${requestScope.stagiare.getnom()}"/>
<hr>
 <span>Prénom : </span><c:out value="${requestScope.stagiare.getprenom()}"/>
<hr>
 <span>Email : </span><c:out value="${requestScope.stagiare.getemail()}"/>
 <hr>
 <span>Direction : </span><c:out value="${requestScope.stagiare.getdirection()}"/>
 <hr>
 <span>Categorie : </span><c:out value="${requestScope.stagiare.getcategorie()}"/>
 <hr>
  <span>Niveau : </span><c:out value="${requestScope.stagiare.getniveau()}"/>
<hr>
 <span>Etabalissement : </span><c:out value="${requestScope.stagiare.getetabalissement()}"/>
 <hr>
 <span>Etat : </span><c:out value="${requestScope.stagiare.getetat()}"/>
 
 <hr>
 <span>Travail demandé : </span><c:out value="${requestScope.stagiare.gettravail() }"/>
<c:if test="${requestScope.stagiare.getetat() eq 'travailler en stage' }">
  <hr>
 <span>Encadreur : </span><c:out value="${requestScope.stagiare.getencadreur() }"/>
  </c:if>
 
 
 <c:if test="${requestScope.stagiare.getetat() eq 'fini le travail'}">
 <hr>
 <span>Evaluation :</span><c:out value=" ${requestScope.stagiare.getevaluation()}"></c:out>
 </c:if>
 
 
 </div>


</div>

	 <%@include file="resourse/menu.jsp" %>
	 
	<script>
function supprimer() {
  var r = confirm("êtes-vous sûr de vouloir supprimer ce stagiaire ?");
  if (r == true) {
	  window.location.href ='suprimerStagiare?q=${requestScope.stagiare.getid()}';
  }
  
}
</script>

</body>
</html>