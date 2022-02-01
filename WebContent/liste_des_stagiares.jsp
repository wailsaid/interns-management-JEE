<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>List des Stagiares </title>
    

    
  </head>
  <body>
 	 <%@include file="resourse/headbar.jsp" %>

<div class="list">
  <div class="listbody">
 <div Style='margin-bottom: 5vh;'>
    <h2 Style='display: inline;'>List des Stagiares :</h2>
    <c:if test="${requestScope.list_satgiare.size() ne 0 }">
  <c:if test="${user.getusername() ne 'employe'}"> <button onclick="window.location.href ='ajouterstagire'">Ajouter</button></c:if>
   </c:if>
</div>

<c:choose>
<c:when test="${requestScope.list_satgiare.size() eq 0 }">

Il n'y a pas des stagiares, éssayez de <a href="<c:url value='/ajouterstagire'/>">ajouter un stagiare</a>

</c:when>


<c:otherwise>
  <table border="1" style="border-collapse: collapse;">

     <tr>
        <th >Nom Prenom</th>
        <th>Direction</th>
        <th >Categorie</th>
     <th >Etat</th> 
      <c:forEach items="${requestScope.list_satgiare}" var="stagiare">
      <tr id="row-${stagiare.getid()}" onclick="window.location.href='afficherstagiareprofile?q=${stagiare.getid()}'">
      <td>${stagiare.getnom()}  ${stagiare.getprenom()}</td>
      <td>${stagiare.getdirection()}</td>
      <td>${stagiare.getcategorie()}</td>
      <td>${stagiare.getetat()}</td> 
      
      </tr>
      </c:forEach>

  </table>
</c:otherwise>
</c:choose>


  </div>

</div>

	 <%@include file="resourse/menu.jsp" %>

</body>
</html>