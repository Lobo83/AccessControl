<%@page import="org.apache.taglibs.standard.tag.el.core.OutTag"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,org.personaltrainer.web.beans.rutine.RecordRutine"
	session="true"%>
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
<body >
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
	<script type="text/javascript">
	function setExercisesList(valueIndex) {	
		
		<%int numRecord=((List<RecordRutine>)request.getAttribute("userExercisesList")).size();%>
		var numExercises =<%=numRecord%>;
		var exercisesValues = new Array(numExercises);
		for ( var i = 0; i < numExercises; i++) {
			exercisesValues[i] = new Array(3);
		}
		<%List<RecordRutine> userExercisesList=(List<RecordRutine>)request.getAttribute("userExercisesList");
		  int indexExercisesList=0;
		%>
		<%for(int j=0; j<numRecord;j++){%>
			exercisesValues[<%=j%>][0] =<%=userExercisesList.get(indexExercisesList).getIdRutine()%>;
			exercisesValues[<%=j%>][1] =<%=userExercisesList.get(indexExercisesList).getIdRecordRutine()%>;
			exercisesValues[<%=j%>][2] ='<%=userExercisesList.get(indexExercisesList).getExercisesName()%>';
		<% indexExercisesList++;
		}%>
	
		//Inicializacion del combo de ejercicios una vez se ha seleccionado una rutina 

		var eSelect = document.getElementById("exercise");
		//Se borra de momento todo el contenido del listado de ejercicios
		while (eSelect.options.length > 0) {
			eSelect.remove(0);
		}

		var newOption = document.createElement("option");
		//seteando el primer valor
		newOption.value = "NONE";
		newOption.text = "--- Seleccione una rutina primero ---";
		try {
			eSelect.add(newOption); // will fail in DOM browsers but needed for IE 
		} catch (e) {
			eSelect.appendChild(newOption);
		}
		for ( var i = 0; i < <%=numRecord%>; i++) {
			
			
			if (exercisesValues[i][0] == valueIndex) {
				
				var newOption1 = document.createElement("option");
				newOption1 = document.createElement("option");
				newOption1.value = exercisesValues[i][1];
				newOption1.text =  exercisesValues[i][2];
				//alert(' valor de array '+exercisesValues[i][1]+' i vale '+i);
				try {
					eSelect.add(newOption1); // will fail in DOM browsers but needed for IE 
				} catch (e) {
					eSelect.appendChild(newOption1);
				}
			}
		}
	}
	
	</script>
	<form:form modelAttribute="statisticForm" action="RutineDataStatitic"
		method="POST" target="_blank">
		<table align="center">
			<tr />
			<th>Rutina</th>
			<th>Ejercicio</th>
			<th>Medida</th>

			<tr />

			<tr>
				<td><form:select path="idRutine" id="rutine"	onchange="setExercisesList(this.value)">
						<form:option value="NONE" label="--- Seleccione una ---" />
						<form:options items="${userRutinesList}" />
					</form:select>
				</td>
				<td>
					<!-- La idea es que cuando se seleccione una rutina se carguen automaticamente los ejercicios -->
					<form:select path="idExercise" id="exercise">
						<form:option value="NONE" label="--- Seleccione una rutina primero ---" />
						
					</form:select>
				</td>
				<td>
					<!-- La idea es que cuando se seleccione una rutina se carguen automaticamente los ejercicios -->
					<form:select path="measure" id="measure">
						<form:option value="NONE" label="--- Seleccione una medida ---" />
						<form:options items="${measureList}" />
					</form:select>
				</td>
				<td>Fecha Desde:<form:input path="dateFrom" /></td>
				<td>Fecha Hasta:<form:input path="dateTo" /></td>
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