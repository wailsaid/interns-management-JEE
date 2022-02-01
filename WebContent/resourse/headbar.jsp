<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
   <style type="text/css">
    a {
	text-decoration: none; 
	color:#0eb0ff;
}
    </style>
<div class="headerbar">

    <img src="resourse/logo.png" alt="logo">
    <c:if test="${SessionScope.user.getusername() ne 'employe'}">
    <span>${sessionScope.user.getcategorie() } <a href="<c:url value='/deconnecter'/>">déconnecter</a></span>
    </c:if>
    
    
    
 
    </div>