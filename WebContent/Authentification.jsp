<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr" dir="ltr">
  <head>
    <link rel="stylesheet" href="resourse/style.css">
    <meta charset="utf-8">   
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page de Authentification</title>
  </head>
  <body>
  <script>
  if(${requestScope.erreur}){
    alert("Vous avez entré un nom d'utilisateur ou un mot de passe invalide")
    ${requestScope.erreur=false}
    window.location.href="http://localhost:8080/ProjetFinEtude/"
  }
</script>
  
    <div class="headerbar">
<img src="resourse/logo.png" alt="logo">
    </div>
    
<div class="formcontainer">

    <form class="loginform" action="Authentifier" method="post">

  <div class="logininput">   <input type="text" name="username" placeholder="Nom d'Utilisateur..." required autocomplete="off"> </div>
  <div class="logininput">    <input type="password" name="password" placeholder="Mot de Pass..." required autocomplete="off">  </div>
  <div class="logininput">    <input type="submit" name="submit" value="Connecter">  <a id="entreemploye" href="employee">accès sans authentif</a> </div>
    </form>

</div>

<script type="text/javascript">

</script>
  </body>
</html>
