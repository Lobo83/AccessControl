<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- Con esto digo que puedo invocar a la funcionalidad de
jsp/jstl/core usando el prefijo c. Con esto puedo manejar variables en scope y puedo hacer iteraciones
y condiciones sobre el contenido de la página. MOLA. Ojo, requiere configuracion en web.xml -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PersonalTrainer</title>
</head>
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
			<td style="width: 200px;"><a
				href="/PersonalTrainer/WelcomeUser.jsp"><c:out value="${user}" /></a></td>
			<td style="width: 200px;"><a href="/PersonalTrainer/UserPage">Datos
					Personales</a></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/UserRutinesList">Tablas</a></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/HistoricPage.jsp">Evolucion</a></td>
			<td style="width: 100px;"></td>
			<td><a href="/PersonalTrainer/LogOut.jsp">Cerrar Sesión</a></td>
		</tr>
	</table>
	<table align="center" bgcolor="#A1FFFF" style="width: 1220px;">
		<tr>

			<td style="width: 400px;"></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/UserDataStatisticInput">Estadísticas
					Personales</a></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/UserRutinesStatisticInput">Estadísticas
					de Ejercicios</a></td>
			<td style="width: 200px;"></td>
			<td style="width: 100px;"></td>
			<td></td>
		</tr>
	</table>
	<%
		if (session.getAttribute("user") == null) {
			pageContext.setAttribute("errorUser",
					"Sesión cerrada, autentifiquese");
		}
	%>

	<%
		if (null == pageContext.getAttribute("errorUser")) {
	%>
	<form:form modelAttribute="statisticForm" action="UserDataStatistic" method="POST" target="_blank" onsubmit="window.open(/PersonalTrainer/UserDataStatistics,Informe de usuario)">
		<table align="center">
		<tr/>
		<th>Medida Biométrica</th>
		<tr/>
		
			<tr>
				<td>Medida: 
				<form:select path="measure">

						<form:option value="NONE" label="--- Seleccione una ---" />
						<form:options items="${userMeasuresList}" />
					</form:select>
				</td>
				<td>Fecha Desde:<form:input path="dateFrom"/></td>
				<td>Fecha Hasta:<form:input path="dateTo"/></td>
				<td><input type="submit" value="Generar Estadísticas" /></td>
			</tr>
		</table>
	</form:form>
	<%
		} else {
	%>
	<h2>
		<c:out value="${errorUser}" />
		<a href="/PersonalTrainer/index.jsp"> aquí</a>
	</h2>
	<%
		}
	%>

</body>
</html>