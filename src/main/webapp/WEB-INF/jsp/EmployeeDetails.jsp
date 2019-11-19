<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML>
<html>
<head>
<title><spring:message code="saveEmployee"/></title>
<style type="text/css">
    @import url("<c:url value="/css/main.css"/>");</style>
</head>
<body>
<div id="global">
    <h4>The employee details have been saved.</h4>
    <p>
        <h5><spring:message code="details"/>:</h5>
            <spring:message code="firstName"/>: ${employee.firstName}<br/>
            <spring:message code="lastName"/>: ${employee.lastName}<br/>
            <spring:message code="dateOfBirth"/>: ${employee.birthDate}
    </p>
             <form action="employee_input" method="get">
           <input id="submit" type="submit"  
                value="<spring:message code="addNew"/>">
	</form>
    
</div>
</body>
</html>
