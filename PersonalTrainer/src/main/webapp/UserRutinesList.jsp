<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PersonalTrainer</title>
</head>
<%
		if (session.getAttribute("user") == null) {
			pageContext.setAttribute("errorRutineList", "Sesión cerrada, autentifiquese");
		}
	%>
<body>
	<center>
		<table>
			<tr>
				<td><img src="images/HeaderpersonalTrainer.jpg" /></td>
			</tr>
		</table>
	</center>
	
	<table align="center" bgcolor="#C0C0C0" style="width: 1220px;">
		<tr>
			<td style="width: 200px;"><a href="/PersonalTrainer/WelcomeUser.jsp">${user}</a></td>
			<td style="width: 200px;"><a href="/PersonalTrainer/UserPage">Datos Personales</a></td>
			<td style="width: 200px;"><a href="/PersonalTrainer/UserRutinesList">Tablas</a></td>
			<td style="width: 200px;"><a href="/PersonalTrainer/HistoricPage.jsp">Evolucion</a></td>
			<td style="width: 100px;"><a href="/PersonalTrainer/LogOut.jsp">Cerrar Sesión</a></td>
		</tr>
	</table>
	<% if(null==pageContext.getAttribute("errorRutineList")){ %>
	<h2>
		<c:out value="${errorRutineList}" />
	</h2>

	

	<table border="1" align="center">

		<tr>
			<th>Nombre</th>
			<th>Descripción</th>
			<th>Acción</th>
		</tr>
		
		<c:forEach items="${ rutineList.table}" var="rutine" varStatus="rutineListStatus">
			
			<tr>
		
				<td>${rutine.name} </td>
				<td>${rutine.description}</td>
				<td><a href=/PersonalTrainer/editRutine?idRutine=${rutine.idRutine}>Comenzar</a></td>
				


			</tr>
		
		</c:forEach>
<tr><td></td><td></td><td><a href="/PersonalTrainer/UserRutinesPage">Crear nueva</a></td></tr>

	</table>

<%}else{%>
<h2 ><c:out value="${errorRutineList}"/><a href="/PersonalTrainer/index.jsp"> aquí</a></h2>
<%} %>
</body>
</html>