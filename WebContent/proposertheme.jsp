<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="fr" dir="ltr">
  <head>
    <link rel="stylesheet" href="resourse/style.css">
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Proposer un Thème de Stage </title>
  </head>
  <body> 
  <script type="text/javascript">
if(${requestScope.proposer}){
    alert("Thème en attente de validation")
    ${requestScope.proposer=false}
   // window.location.href="http://localhost:8080/ProjetFinEtude/proposer"
  }
</script>
  
 <%@include file="resourse/headbar.jsp" %>
 
 
 
    <div class="proposertheme">


<div class="themeform">
<h2>Proposer un Thème de Stage </h2>
  <form class="theme" method="post" action="proposer" style="overflow-y:scroll;">
    <div class="themeinput">
        <input type="text" name="titre" placeholder="Titre..." required autocomplete="off" spellcheck="true">
      </div>
    <div class="themeinput">
      <span>Categorie</span>
    <select style="width: 20vh;" class="categorie" name="categorie" required>
      <option value="" selected disabled hidden>choiser ici</option>
      
      <c:forEach items="${requestScope.categorie}" var="cat">
      <option value="${cat.getid()}">${cat.getnom()}</option>
      </c:forEach>
      
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
    <div class="themeinput">
      <textarea name="Description" rows="15" cols="80" placeholder="Descriptions..." required spellcheck="true"></textarea>
    </div>
    <input id="Proposer" type="submit" value="Proposer">

  </form>
</div>

    </div>


<%@include  file="resourse/menu.jsp" %>
  </body>
</html>