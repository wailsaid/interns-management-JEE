<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>List des Stages</title>
 

    
  </head>
  <body>

 	 <%@include file="resourse/headbar.jsp" %>
 	 
 	 <div class="list">
 
 	 <div class="listbody">
 	  <h2>List des Stages:</h2>
 	<c:choose>	
 	 <c:when test="${requestScope.stagelist.size() eq 0 }">
 	 
 	 Il n'y a pas des thèmes, essayez de <a href="<c:url value='/proposer'/>">proposer un thème</a> 
 	 </c:when>
 	 

 	 
 	 <c:otherwise>
  <table border="1" style="border-collapse: collapse;">

     <tr>
        <th>Titre</th>
        <th>Categorie</th>
        <th>Etat</th>
      <c:forEach items="${requestScope.stagelist}" var="stages">
      <tr onclick="window.location.href='afficherprofile?q=${stages.getid()}'">
      <td>${stages.gettitre()} </td>
      <td>${stages.getcategorie()}</td>
      <td>${stages.getetat()}</td>
      
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