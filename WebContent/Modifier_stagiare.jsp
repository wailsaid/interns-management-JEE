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
    <title>Modifier le profile d'un Stagiare</title>
    

    
  </head>
  <body>
 	 <%@include file="resourse/headbar.jsp" %>


 <div class="list">
 	 <div class="stagiareform">
 	 <h2>Modifier le profile d'un Stagiare :</h2>
 	 
 	 <form action="modiferStagire?q=${requestScope.stagiare.getid()}" method="Post">
 	<div class="themeinput"> <input name="nom" type="text" placeholder="Nom..." required value="${requestScope.stagiare.getnom()}"></div>
 	<div class="themeinput"> <input name="prenom" type="text" placeholder="Prenom..." required value="${requestScope.stagiare.getprenom()}"></div>
 	<div class="themeinput"> <input name="email" type="email" placeholder="exemple@gamil.com" required value="${requestScope.stagiare.getemail()}"></div>
 	
 	<div class="themeinput"> 
 	<span>Direction</span>
 	  <select style="width: 20vh;" class="direction" name="direction" required >
      <option value="${requestScope.direction.getid()}" selected >${requestScope.direction.getnom()} </option>
 	 </select>
 	 </div> 
 	
 	
 	
 	<div class="themeinput"> 
 	<span>Categorie</span>
 	  <select style="width: 20vh;" class="categorie" name="categorie" required >
      <option value="${requestScope.categorie.getid()}" selected >${requestScope.categorie.getnom()} </option>
 	 </select>
 	 </div>
 	 
 	 <div class="themeinput">
 	 <span>Niveau</span>
 	 <select style="width: 20vh;" class="niveau" name="niveau" required>
      <option value="" selected disabled hidden>choiser ici</option>
      
      <c:forEach items="${requestScope.niveau}" var="niv">
      <option value="${niv.getid()}">${niv.getnom()}</option>
      </c:forEach>
 	 
 	 </select>
 	 </div>
 	 <div class="themeinput"><input name="etabalissement" placeholder="Etabalissement..."type="text" value="${requestScope.stagiare.getetabalissement()}"></div>
 	 <input type="submit" value="Sauvgarder" Style="margin: 10px 15px ;">
 	 
 	 </form>
 	 
 	 
 	 </div>
 	 	  	 
 	 </div>


	 <%@include file="resourse/menu.jsp" %>

</body>
</html>