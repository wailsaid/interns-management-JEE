 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

 <div class="functionmenu">
      <ul> 
      <c:choose>
           <c:when test="${user.getusername() eq 'admin'}">
           <li><a href="<c:url value="listadmins"></c:url>">List des administrateurs</a></li>
           <li><a href="<c:url value="listdirections"></c:url>">List des directions</a></li>
           <li><a href="<c:url value="listcategoories"></c:url>">List des categories</a></li>
           </c:when>
           <c:otherwise>
        <li><a href="<c:url value="affichlistestage"></c:url>">List des Stages</a></li>

        <li> <a href="<c:url value="proposer"></c:url>">Proposer un Thème</a> </li>   
       
       
        <c:if test="${user.getusername() ne 'employe'}">
         <li><a href="<c:url value="affichlistestagiare"/>">List des Stagiares</a></li>
         <li><a href="<c:url value="affichlisteencadreur"/>">List des encadreur</a></li>
        <li><a href="<c:url value="affecter"/>">Affecter les Stages</a></li>
        <li><a href="<c:url value="evaluer"/>">Évaluer stagiaires</a></li>
        <c:url value=''></c:url>
        </c:if>
</c:otherwise>
</c:choose>


      </ul>

    </div>