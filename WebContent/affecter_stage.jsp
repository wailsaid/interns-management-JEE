<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="fr" dir="ltr">
  <head>
    <link rel="stylesheet" href="resourse/style.css">
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	

	<style type="text/css">
	input{
	display:none;
	}
	input[type="date"]{
		display: inline-block;
	}
	label{
	display: inline-block;
	width: 150px;
	
	}
	a{
	text-decoration: none;
	 color:#0000ff;
	 }
	</style>
    <title>Affecter les Stages</title>
  </head>
  <body> 
   <%@include file="resourse/headbar.jsp" %>
    <div class="list">
  <div class="listbody">
  
  
  
  <form action="affecter" method="post" id="affectaction">
  <input id="stage" name="stage" type="checkbox" value="" required>
  <c:forEach items="${requestScope.list_satgiare}" var="stagiare">
<input type="checkbox" name="stagiares" id="${stagiare.getid()}" value="${stagiare.getid()}">
      </c:forEach>
   <input id="encadreur" name="encadreur" type="checkbox" value="" required >
      

  <div class="tab" >
  <h3>Selectionner un Stage :</h3>
  <c:choose>	
 	 <c:when test="${requestScope.stagelist.size() eq 0 }">
 	 
 	<br> Il n'y a pas des thèmes Valide, essayez de <a href="<c:url value='/proposer'/>">proposer un thème</a> 
 	 </c:when> 
 	 <c:otherwise>
  <table border="1" style="border-collapse: collapse;">

     <tr>
        <th>Titre</th>
        <th>Niveau</th>
        <th>Categorie</th>
        <th>Etat</th>
      <c:forEach items="${requestScope.stagelist}" var="stages">
      <tr id="stagerow-${stages.getid()}" onclick="selectstage('${stages.getid()}','${stages.getniveau()}')">
      <td>${stages.gettitre()} </td>
      <td>${stages.getniveau()}</td>
      <td>${stages.getcategorie()}</td>
      <td>${stages.getetat()}</td> 
      </tr>
   
      </c:forEach>

  </table>
  </c:otherwise>
</c:choose>

  </div>
<div class="tab">
  <h3>Selectionner des Stagiares :</h3>
<c:choose>
<c:when test="${requestScope.list_satgiare.size() eq 0 }">

<br>Il n'y a pas des stagiares<c:if test="${user.getusername() ne 'employe'}">,essayez de <a href="<c:url value='/ajouterstagire'/>">ajouter un stagiare</a>
</c:if>
</c:when>


<c:otherwise>
  <table id="stagiare" border="1" style="border-collapse: collapse;">
     <tr>
        <th Style="">Nom Prenom</th>
        <th>Niveau</th>
        <th Style="">Categorie</th>
     <th Style="">Etat</th> 
      <c:forEach items="${requestScope.list_satgiare}" var="stagiare">
      <tr id="stagiarerow-${stagiare.getid()}" onclick="selectstagiares('${stagiare.getid()}')">
      <td>${stagiare.getnom()}  ${stagiare.getprenom()}</td>
       <td>${stagiare.getniveau()}</td>
      <td>${stagiare.getcategorie()}</td>
      <td>${stagiare.getetat()}</td> 
      
      </tr>
    
      </c:forEach>

  </table> 
  <button onclick="showTab(2)">Suivant</button>
 <button  onclick="showTab(0)">Précident</button>
 

</c:otherwise>
</c:choose>


</div>  
  
  
  <div class="tab">
  <h3>Selectionner un Encadreur :</h3>
  
  <c:choose>
<c:when test="${requestScope.list_encadreur.size() eq 0 }">

<br>Il n'y a pas des encadreur<c:if test="${user.getusername() ne 'employe'}">,essayez de <a href="<c:url value='/ajouterencadreur'/>">ajouter un encadreur</a>
</c:if>
</c:when>
<c:otherwise>
  <table border="1" style="border-collapse: collapse;" id ="Tencad">

     <tr>
        <th Style="width:150vh;'">Nom Prenom</th>
        <th Style="">Categorie</th>
    
      <c:forEach items="${requestScope.list_encadreur}" var="encadreur">
      <tr id="encadreurrow-${encadreur.getid()}" onclick="selectencadreur('${encadreur.getid()}')">
      <td>${encadreur.getnom()}  ${encadreur.getprenom()}</td>
      <td>${encadreur.getcategorie()}</td>
	 </tr>
	 
      </c:forEach>

  </table>
  <input type="submit" onclick="formsubmit()">
   <button onclick="formsubmit()">Terminé</button>
 <button  onclick="showTab(1)">Précident</button>
 
 <div class="themeinput"> <label for="debut">Date de Début 		:</label>
 <input	type="date" id="debut" name="debut" required></div>
  <div class="themeinput"> <label for="fin">Date de Fin 	:</label>
 <input	type="date" id="fin" name="fin" required></div>
</c:otherwise>
</c:choose>
  
  </div>
 </form>
  
  </div>
  </div>
<script type="text/javascript">
var currentTab = 0; 
showTab(currentTab);
var nivo ;
function selectstage(id,niv){
	nivo = niv;
//	document.getElementById("stagerow-"+id).style.background="#1eb3d8";
	 document.getElementById("stage").setAttribute('value', id);
	 document.getElementById("stage").checked = true;
	 showTab(1);
	 filter();
}
function selectstagiares(id){
	//alert("row-"+id);
	
	var x =	document.getElementById("stagiarerow-"+id);
	var y =	document.getElementById(id);
	
	if(y.checked){
	
		x.style.background="";
		y.checked = false;
	}else{
	x.style.background="#1eb3d8";
	y.checked = true;
	}
	//document.getElementById("stagiarerow-"+id).style.background="#1eb3d8";
	//document.getElementById(id).checked = true;
	
}
function selectencadreur(id){
	
	 var x = document.getElementById("encadreur");
	 var y = document.getElementById("encadreurrow-"+id);
	 var tab = document.getElementById("Tencad");
	 for(let row of tab.rows){
		// console.log(row.id);
		 if(row.id != y.id ){
			 row.style.background="";
		 }else {
			 if(x.checked){
				 y.style.background="";
				 x.checked=false;
			 }else{
				 y.style.background="#1eb3d8";
				 x.checked=true;
				 x.setAttribute('value', id);
			 } 
		 }
		 
	 }
}

function showTab(n) {
	  var x = document.getElementsByClassName("tab");
	  if(n == 0){
	  x[0].style.display = "block";
	  x[1].style.display = "none";
	  x[2].style.display = "none";
	  }else if(n == 1){
		  x[0].style.display = "none";
		  x[1].style.display = "block";
		  x[2].style.display = "none";
	  }else if(n==2){
		  x[0].style.display = "none";
		  x[1].style.display = "none";
		  x[2].style.display = "block";
	  }

	}
function formsubmit(){
	
	var x = document.getElementsByName("stagiares");
	let ok = false;
	var p = document.getElementsByName("encadreur");
	var debut = document.getElementsByName("debut");
	var fin = document.getElementsByName("fin");
	//alert(p[0].value);
	for(let i=0; i < x.length;i++){
		if(x[i].checked == true){
			ok=true;	
		}
	}
	if(ok == false ){
			alert('Veuillez remplir correctement tous les champs(stagiares)');
			showTab(1);			
	}

	else if(p[0].value == '' ){
		alert('Veuillez remplir correctement tous les champs(encadreur)');
		
	}
	
	//alert(debut[0].value);
	else if(debut[0].value==''){
		alert('Veuillez remplir correctement tous les champs(les dates)');
	}
	//alert(fin[0].value);
	else if(fin[0].value ==''){
		alert('Veuillez remplir correctement tous les champs(les dates)');
	}

	else{
	document.getElementById("myForm").submit();
	}
}	
function filter() {
	  
	  var input, filter, table, tr, td, i, txtValue;

	  filter = nivo;
	  table = document.getElementById("stagiare");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[1];
	    if (td) {
	      txtValue = td.textContent || td.innerText;
	      if (txtValue.indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}
</script>
   <c:if test="${requestScope.done == true}">
   <script type="text/javascript">
   alert('Le stage a été attribué ');
   ${requestScope.done = false}
   window.location.href= window.location.href;
   </script>
   
   </c:if>
   
<%@include  file="resourse/menu.jsp" %>
  </body>
</html>