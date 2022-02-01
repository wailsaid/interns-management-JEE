<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resourse/style.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Modifier un thème de Stage</title>
</head>
<body>
 <%@include file="resourse/headbar.jsp" %>
 <div class="profil_stage">
 <div class="profil_body">
 <h3>Modification :</h3>
 
  <form class="theme" method="POST" action="Sauvgarder" >
  <input type="hidden" name="q"value="${requestScope.profile_stage.getid()}">
    <div class="themeinput">
        <input type="text" name="titre" value="${requestScope.profile_stage.gettitre() }" required autocomplete="off" spellcheck="true">
      </div>
    <div class="themeinput">
       <span>Categorie</span>
    <select style="width: 20vh;" class="categorie" name="categorie" required disabled>
      <option value="" selected hidden>${requestScope.profile_stage.getcategorie()}</option>
      
      <c:forEach items="${requestScope.categorie}" var="cat">
      <option value="${cat.getid()}">${cat.getnom()}</option>
      </c:forEach>
      
    </select>
    </div>

    <div class="themeinput">
      <span>Niveau</span>
    <select style="width: 20vh;" class="niveau" name="niveau" required>
      <option value="" selected  hidden >${requestScope.profile_stage.getniveau()}</option>
      
      
      <c:forEach items="${requestScope.niveau}" var="niv">
      <option value="${niv.getid()}">${niv.getnom()}</option>
      </c:forEach>

      
    </select>
    </div>
    <div class="themeinput">
      <textarea name="Description" rows="15" cols="90" required spellcheck="true">
      ${requestScope.profile_stage.getdescription()}
      </textarea>
    </div>
    <input Style="margin: 10px 15px" type="submit" value="Sauvgarder" >

  </form>
</div>

    </div>

 </div>
 
<%@include file="resourse/menu.jsp" %>	
</body>
</html>