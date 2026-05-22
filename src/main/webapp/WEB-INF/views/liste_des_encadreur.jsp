<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>List des Encadreur</title>
    

    
  </head>
  <body>
 	 <%@include file="/WEB-INF/views/fragments/headbar.jsp" %>
 	 <div class="app-main">
  <div class="card">
 <div Style='margin-bottom: 5vh;'>
    <h2 Style='display: inline;'>List des Encadreur :</h2>
    <c:if test="${requestScope.list_encadreur.size() ne 0 }">
  <c:if test="${user.getusername() ne 'employe'}"> <button onclick="window.location.href ='ajouterencadreur'">Ajouter</button></c:if>
   </c:if>
</div>

<c:choose>
<c:when test="${requestScope.list_encadreur.size() eq 0 }">

Il n'y a pas des encadreur, éssayez de <a  href="<c:url value='/ajouterencadreur'/>">ajouter un encadreur</a>
</c:when>


<c:otherwise>
  <table class="data-table">

     <tr>
        <th >Nom Prenom</th>
        <th>Direction</th>
        <th >Categorie</th>
    
      <c:forEach items="${requestScope.list_encadreur}" var="encadreur">
      <tr id="row-${encadreur.getid()}" onclick="window.location.href='afficherencadreurprofile?q=${encadreur.getid()}'">
      <td>${encadreur.getnom()}  ${encadreur.getprenom()}</td>
      <td>${encadreur.getdirection()}</td>
      <td>${encadreur.getcategorie()}</td>
     
      
      </tr>
      </c:forEach>

  </table>
</c:otherwise>
</c:choose>





</div>
</div>

	 <%@include file="/WEB-INF/views/fragments/menu.jsp" %>

</body>
</html>
