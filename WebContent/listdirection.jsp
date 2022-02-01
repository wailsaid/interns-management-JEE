<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style type="text/css">
	input[type="text"]{
    width: 400px;
    padding: 10px;
}
#formul{
  display: none;
}
	</style>
    <meta charset="utf-8">
<title>List des Directions</title>
</head>
<body>
 	 <%@include file="resourse/headbar.jsp" %>
 	 <div class="list">
  <div class="listbody">
 <div Style='margin-bottom: 5vh;'>
    <h2 Style='display: inline;'>List des Directions :</h2>
    
  <button  onclick="showform()">Ajouter</button>
  
</div>

<form id="formul" action="listdirections" method="post">
<div class="themeinput"> <input name="nom" type="text" placeholder="Nom de un Direction..." required> <input type="submit" value="ajouter"></div>

</form>
<c:choose>
 	<c:when test="${requestScope.directions.size() eq 0 }">

Il n'y a pas des Direction.

	</c:when>


<c:otherwise>
  <table border="1" style="border-collapse: collapse;">

     <tr>
        <th style="width:150vh;" >Directions</th>
        <th></th>

      <c:forEach items="${requestScope.directions}" var="dir">
      <tr id="row-${dir.getid()}">
      <td>${dir.getnom()}</td>
   
      <td><button onclick="suprimer(${dir.getid()})">supprimer</button> </td> 
      
      </tr>
      </c:forEach>

  </table>
</c:otherwise>
</c:choose>


  </div>

</div>
	 <%@include file="resourse/menu.jsp" %>
<script type="text/javascript">
function showform(){
	document.getElementById("formul").style.display="block";
}
function suprimer(id){
//	alert(id);
	var conf = confirm("Attention: \n vous allez supprimer tous les information de ce direction");
	if(conf == true){
		window.location.href='supdir?nom=direction&q='+id;
		}
}

</script>
</body>
</html>