<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resourse/style.css">   
    <style type="text/css">
   input[type="text"], input[type="email"],select{
  	width: 400px;
 	padding: 10px;
   }
    </style> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <title>Modifier le profile d'un Encadreur</title>
    

    
  </head>
  <body>
 	 <%@include file="resourse/headbar.jsp" %>
 	  <div class="list">
 	 <div class="stagiareform">
 	 <h2>Modifier le profile d'un Encadreur :</h2>
 	 
 	 <form action="modiferAdmin?q=${requestScope.admin.getid() }" method="Post">
 	<div class="themeinput"> <input name="nom" type="text" placeholder="Nom..." required value="${requestScope.admin.getnom() }"></div>
 	<div class="themeinput"> <input name="prenom" type="text" placeholder="Prenom..." required value="${requestScope.admin.getprenom() }"></div>
 	<div class="themeinput"> <input name="username" type="text" placeholder="Nom d'Utilisateur..." required value="${requestScope.admin.getusername() }"></div>
 	<div class="themeinput"> <input name="password" type="text" placeholder="Mot de Pass..." required value="${requestScope.admin.getpassword() }"></div>
 	 <div class="themeinput">
 	 <span>Direction</span>
 	 <select style="width: 20vh;" class="niveau" name="direction" required>
      <option value="" selected disabled hidden>choiser ici</option>
      
      <c:forEach items="${requestScope.direction}" var="dir">
      <option value="${dir.getid()}">${dir.getnom()}</option>
      </c:forEach>
 	 
 	 </select>
 	 </div>
 	<div class="themeinput"> 
 	<span>Categorie</span>
 	 <select style="width: 20vh;" class="niveau" name="categorie" required>
      <option value="" selected disabled hidden>choiser ici</option>
      
      <c:forEach items="${requestScope.categorie}" var="cat">
      <option value="${cat.getid()}">${cat.getnom()}</option>
      </c:forEach>
 	 
 	 </select>
 	 </div>
 	
 	 <input type="submit" value="Sauvgarder" Style="margin: 10px 15px ;">
 	 
 	 </form>
 	 
 	 
 	 </div>
	 
 	 </div>
 	 	 <%@include file="resourse/menu.jsp" %>
 	 
 	 
</body>
</html>