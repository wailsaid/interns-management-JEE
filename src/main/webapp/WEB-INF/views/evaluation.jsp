<%@ taglib uri = "jakarta.tags.core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    
    
    <title>Evaluation</title>
    

    
  </head>
  <body>

 	 <%@include file="/WEB-INF/views/fragments/headbar.jsp" %>
 	  <div class="app-main">
 
 	 <div class="card">
 	  <h2>List des Stages:</h2>
 	<c:choose>	
 	 <c:when test="${requestScope.stages.size() eq 0 }">
 	 
 	 Il n'y a pas des Satge attribué, essayez de <a href="<c:url value="affecter"/>">affecter un Stages</a> 
 	 </c:when>
 	 

 	 
 	 <c:otherwise>
 	 
 	 
 	   <table class="data-table">

     <tr>
        <th>Titre</th>
        <th>Categorie</th>
        <th style="width:80px;"></th>

      <c:forEach items="${requestScope.stages}" var="stage">
      <tr>
      <td>${stage.gettitre()} </td>
      <td>${stage.getcategorie()}</td>
  <td><button onclick="eval('${stage.getid()}')">Evaluer</button></td>
      
      </tr>
      </c:forEach>

  </table>
 	 
 	 </c:otherwise>
 	 </c:choose>
 	 
 	 
 	 
 	 
 </div>
 </div>	 
 

 

 	 
 	 <%@include file="/WEB-INF/views/fragments/menu.jsp" %>	 
 	 
 	 <div id="page_evaluation" class="modal-overlay">
 	 <div id="evaluation_content" class="modal-content">
 	 <h3>Page de evaluation</h3><br>
 	
 	 </div>
 	 </div>
 	 
 <script type="text/javascript">
var modal = document.getElementById("page_evaluation");

//var btn = document.getElementById("evalui");


window.onclick = function(event) {
if (event.target == modal) {
  modal.classList.remove("open");
}
}
function eval(id){
	modal.classList.add("open");
	xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("evaluation_content").innerHTML = this.responseText;
	    }
	  };
	  xhttp.open("GET", "getstagiares?q="+id, true);
	  xhttp.send();
}

 </script>
</body>
</html>
