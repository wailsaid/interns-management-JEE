<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
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
<title>List des Categories</title>
</head>
<body>
 	 <%@include file="/WEB-INF/views/fragments/headbar.jsp" %>
 	 <div class="app-main">
  <div class="card">
 <div Style='margin-bottom: 5vh;'>
    <h2 Style='display: inline;'>List des Categories :</h2>
    
  <button  onclick="showform()">Ajouter</button>
  
</div>

<form id="formul" action="listcategoories" method="post">
<div class="themeinput"> <input name="nom" type="text" placeholder="Nom de un Categorie..." required> <input type="submit" value="ajouter"></div>

</form>
<c:choose>
 	<c:when test="${requestScope.Categories.size() eq 0 }">

Il n'y a pas des Categories.

	</c:when>


<c:otherwise>
  <table class="data-table">

     <tr>
        <th style="width:150vh;" >Categories</th>
        <th></th>

      <c:forEach items="${requestScope.Categories}" var="cat">
      <tr id="row-${cat.getid()}">
      <td>${cat.getnom()}</td>
   
      <td><button onclick="suprimer(${cat.getid()})">supprimer</button> </td> 
      
      </tr>
      </c:forEach>

  </table>
</c:otherwise>
</c:choose>


  </div>

</div>
	 <%@include file="/WEB-INF/views/fragments/menu.jsp" %>
<script type="text/javascript">
function showform(){
	document.getElementById("formul").style.display="block";
}
function suprimer(id){
//	alert(id);
	var conf = confirm("Attention: \n vous allez supprimer tous les information de ce categorie");
	if(conf == true){
	window.location.href='supcat?nom=categorie&q='+id;
	}
}

</script>
</body>
</html>
