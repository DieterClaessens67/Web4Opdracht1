<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="SignUp" />
</jsp:include>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="SignUp" />
</jsp:include>
<main>
    <c:if test="${errors.size()>0 }">
        <div class="danger">
            <ul>
                <c:forEach var="error" items="${errors }">
                    <li>${error }</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</main>
<form action="Controller?action=SignUp" method="post" >
    <p>
        <label for="naam">Name:</label>
        <input type="text" id="naam" name="naam">
    </p>
    <p>
        <label for="voorNaam">FirstName:</label>
        <input type="text" id="voorNaam" name="voorNaam">
    </p>
    <p>
        <label for="emailAdres">Email:</label>
        <input type="email" id="emailAdres" name="emailAdres">
    </p>
    <p>
        <label for="geslacht">Gender:</label>
        <select id="geslacht" name="geslacht">
            <option value="male">male</option>
            <option value="female">female</option>
        </select>
    </p>
    <p>
        <label for="paswoord">Password:</label>
        <input type="text" name="paswoord" id="paswoord">
    </p>
   <p>
       <label for="herhaalPaswoord">Repeat Password:</label>
       <input type="text" name="herhaalpaswoord" id="herhaalPaswoord">
   </p>
   <p>
       <label for="leeftijd">Age:</label>
       <input type="number" id="leeftijd" name="leeftijd">
   </p>
    <p id="signUpButton">
        <input type="submit" value="Submit" id="signUpInputButton">
    </p>
</form>
<jsp:include page="footer.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
</body>
</html>