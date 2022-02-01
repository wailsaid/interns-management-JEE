<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
<h2>Page de Évaluation</h2>
<h4>Les Stagiares concernés :</h4>
 <form action="evaluer"method="post" enctype="multipart/form-data">
 
 <ul>
 	 <c:forEach items="${requestScope.list}" var="stagiare">
 	<li> <c:out value="${stagiare.getnom()} ${stagiare.getprenom()}"/></li>
 	<input type="hidden" name="stagiares" value="${stagiare.getid() }">
 	<input type="hidden" name="stage" value="${stagiare.gettravail()}">
 	 </c:forEach> 
 	</ul>
 	<hr>
 	<h4>L'encadreur responsable :</h4>
 	<div ><ul><li><c:out value="${requestScope.encadreur.getnom()} ${requestScope.encadreur.getprenom()}" /></li></ul></div>
 	<hr>
 	<div><span>la date de debut :</span> <c:out value="${requestScope.date.getdateD()}"/></div>
 	<hr>
 <div><span>la date de fin :</span>	<c:out value="${requestScope.date.getdateF()}"/></div>
<hr>

 <h4>Télécharger le travail demandé :</h4>
  <div class="themeinput"><input type="file" name="travail" required><br></div>
  <h4>Entrez votre évaluation </h4>
  <div class="themeinput"><textarea rows="5" cols="100" required placeholder="Évaluation..." name="evaluation"></textarea></div>
  <input type="submit" value="Évaluer">
 </form>
 
 </div>
 