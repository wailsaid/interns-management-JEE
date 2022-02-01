<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resourse/style.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Profile d'un Stage</title>
</head>
<body>
 <%@include file="resourse/headbar.jsp" %>

 <div class="profil_stage">
 
 <div class="profil_body">
 <div> 
<h3><c:out value='${requestScope.profile_stage.gettitre()}'/></h3>

<c:if test="${sessionScope.user.getcategorie() eq requestScope.profile_stage.getcategorie()}">
<c:if test="${requestScope.profile_stage.getetat() ne 'Terminer' and requestScope.profile_stage.getetat() ne 'effectué'}">
   <button onclick="window.location.href ='modifier?q=${requestScope.profile_stage.getid()}'"> Modifier</button>

 <button onclick="window.location.href ='refuser?q=${requestScope.profile_stage.getid()}'">Refuser</button>
   <button onclick="window.location.href ='valider?q=${requestScope.profile_stage.getid()}'"> Valider</button>
</c:if>
</c:if>
</div>

<hr>
Categorie :<c:out value='${requestScope.profile_stage.getcategorie()}'/>
<hr>
Niveau : <c:out value="${requestScope.profile_stage.getniveau()}"/>
<hr>
L'état : <c:out value="${requestScope.profile_stage.getetat()}"/>
<hr>
<c:if test="${requestScope.profile_stage.getetat() eq 'effectué'}">
date de début des travaux : <c:out value="${requestScope.profile_stage.getdateD()}"/>
<hr>
date de fin des travaux : <c:out value="${requestScope.profile_stage.getdateF()}"/>
<hr>
</c:if>
<c:if test="${requestScope.profile_stage.getetat() eq 'Terminer' }">
<a style="text-decoration: none; color:#0000ff" href="<c:url value='/telechegertravail?q=${requestScope.profile_stage.getid()}'/>" target="_blank">Travail Livrer</a>
<hr>
</c:if>
<section style="white-space: pre-wrap;">
<c:out value="${requestScope.profile_stage.getdescription()}"/>
</section>

 </div>
     
    </div>
 
 <%@include file="resourse/menu.jsp" %>	
 
</body>
</html>