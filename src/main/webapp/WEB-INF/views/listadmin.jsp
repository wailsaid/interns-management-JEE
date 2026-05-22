<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>List des Administrateurs </title>
    

    
  </head>
  <body>
 	 <%@include file="/WEB-INF/views/fragments/headbar.jsp" %>
<div class="app-main">
  <div class="card">
 <div Style='margin-bottom: 5vh;'>
    <h2 Style='display: inline;'>List des Administrateurs :</h2>
    <c:if test="${requestScope.listadmin.size() ne 0 }">
  <button onclick="window.location.href ='ajouteradmin'">Ajouter</button></c:if>
  
</div>

<c:choose>
<c:when test="${requestScope.listadmin.size() eq 0 }">

Il n'y a pas des administrateurs, éssayez de <a href="<c:url value='ajouteradmin'/>">ajouter un administrateur.</a>

</c:when>


<c:otherwise>
  <table class="data-table">

     <tr>
        <th >Nom Prenom / Nom d'Utilisateur</th>
        <th >Categorie</th>
     <th >Direction</th> 
      <c:forEach items="${requestScope.listadmin}" var="admin">
      <tr id="row-${admin.getid()}" onclick="window.location.href='afficheradminprofile?q=${admin.getid()}'">
      <td>${admin.getnom()}  ${admin.getprenom()} / ${admin.getusername()}</td>
      <td>${admin.getcategorie()}</td>
      <td>${admin.getdirection()}</td> 
      
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
