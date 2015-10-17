<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,org.personaltrainer.web.beans.rutine.TableRutine"
	session="true"%>
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
		pageContext.setAttribute("errorRutine",
				"Sesión cerrada, autentifiquese");
	}
%>
<script type="text/javascript">
		function showValidation(){
			
			var message="<%=(String) request.getAttribute("errorValidation")%>";
			if(message!='null'){
				alert(message);	
			}
		}
	</script>
<body onload="showValidation();">
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
				href="/PersonalTrainer/WelcomeUser.jsp">${user}</a></td>
			<td style="width: 200px;"><a href="/PersonalTrainer/UserPage">Datos
					Personales</a></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/UserRutinesList">Tablas</a></td>
			<td style="width: 200px;"><a
				href="/PersonalTrainer/HistoricPage.jsp">Evolucion</a></td>
			<td style="width: 100px;"><a href="/PersonalTrainer/LogOut.jsp">Cerrar
					Sesión</a></td>
		</tr>
	</table>
	<%
		if (null == pageContext.getAttribute("errorRutine")) {
			String disable = ((Boolean) request.getAttribute("disable"))
					.toString();
			Boolean bDisable = (Boolean) request.getAttribute("disable");
	%>
	<script type="text/javascript">
		function activateForm(disable){
			var numRecord=<%=((TableRutine) request.getAttribute("rutineTable"))
						.getTable().size()%>;
			for(var i=0; i<numRecord; i++){
				document.getElementById("table["+i+"].idExercise").disabled=disable;
				document.getElementById("table["+i+"].seriesNumber").disabled=disable;
				document.getElementById("table["+i+"].duration").disabled=disable;
				document.getElementById("table["+i+"].measures").disabled=disable;
				document.getElementById("table["+i+"].weight").disabled=disable;
				document.getElementById("table["+i+"].order").disabled=disable;
				
			}
			document.getElementById("rutineName").disabled=disable;
			document.getElementById("rutineDescription").disabled=disable;
			document.getElementById("addRow").disabled=disable;
			document.getElementById("save").disabled=disable;
			document.getElementById("cancel").disabled=disable;
			document.getElementById("edit").disabled=!disable;
		}
	</script>

	<script type="text/javascript">
		function removeLine(line,link){

			
			if(document.getElementById("table["+line+"].idRecordRutine").value==0){
				document.getElementById("table["+line+"].idExercise").style.display = 'none';
				document.getElementById("table["+line+"].seriesNumber").style.display = 'none';
				document.getElementById("table["+line+"].duration").style.display = 'none';
				document.getElementById("table["+line+"].measures").style.display = 'none';
				document.getElementById("table["+line+"].weight").style.display = 'none';
				document.getElementById("table["+line+"].order").style.display = 'none';
				link.style.display='none';
				
			}else{
				window.location.href="/PersonalTrainer/removeRutineExercise?idRecordRutine="+document.getElementById("table["+line+"].idRecordRutine").value;
			}
		}
	</script>
	<script type="text/javascript">
	/* Como mejora lo que habría que hacer es abrir la página con el formato ya prestablecido
	 y que dicha página ya recuperase los datos para simplemente imprimir
	
	*/
	function imprSelec(){
		var rutinaName = document.getElementById("rutineName");
		var rutinaDescription=document.getElementById("rutineDescription");
		//Se abre una nueva ventana que contendrá la información a imprimir
		var ventimp = window.open('','popimpr');
		//se empieza a construir la información
		var numRecord=<%=((TableRutine) request.getAttribute("rutineTable")).getTable().size()%>;
		//puesto que se está escribiendo HTML, el contenido debe tener las etiquetas adecuadas según se quiera presentar la información
		ventimp.document.write("<center><b>Rutina:</b> "+rutinaName.value+"<br/> " );
		ventimp.document.write("<b>Descripcion:</b> "+rutinaDescription.value+"</center>" );
		
		ventimp.document.write("<table align='center' border='1'><tr><th>Ejercicio</th>	<th>Series</th><th>Duración</th><th>Medida</th><th>Peso(kg)</th><th>Orden <br/></th></tr>" );
		
			for(var i=0; i<numRecord; i++){
				ventimp.document.write("<tr>");
				var idSelectedExercise=document.getElementById("table["+i+"].idExercise").selectedIndex;
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].idExercise").options[idSelectedExercise].text+"</td>");
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].seriesNumber").value+"</td>");
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].duration").value+"</td>");
				var idSelectedMeasure=document.getElementById("table["+i+"].measures").selectedIndex;
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].measures").options[idSelectedMeasure].text+"</td>");
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].weight").value+"</td>");
				ventimp.document.write("<td>"+document.getElementById("table["+i+"].order").value+"</td>");
				ventimp.document.write("</tr>");
			}
		ventimp.document.write("</table>");
		ventimp.document.close();
		//la ventana con el código html ha sido construida y cerrada

		ventimp.print();//se invoca a la función de impresión

		ventimp.close();

	} 
	</script>
	<h2>
		<c:out value="${errorRutine}" />
	</h2>


	<form:form action="manageRutine" method="post"
		modelAttribute="rutineTable" id="rutineForm">

		<form:hidden path="idRutine" />
		<input type="hidden" value="<c:out value="${idUser}" />" name="idUser"
			id="idUser" />
		<table align="center" >
			<tr>

				<td><form:button name="addRow" disabled="<%=disable%>"
						id="addRow">Añadir Fila</form:button></td>
				<td><form:button name="save" disabled="<%=disable%>" id="save">Salvar</form:button></td>
				<td><input type="button" name="edit" id="edit" value="Editar"
					onclick="activateForm(false);" /></td>
				<td><input type="button" name="cancel" id="cancel"
					value="Cancelar" disabled="<%=disable%>"
					onclick="activateForm(true);" /></td>
					<td><input type="button" name="print" id="print"
					value="Imprimir" 
					onclick="imprSelec();" /></td>
			</tr>
		</table>
		<br />
		<table align="center">
			<tr>
				<td>Rutina: <form:input path="rutineName"
						disabled="<%=disable%>" id="rutineName" /></td>
				<td width="10px"></td>
				<td>Descripción: <form:input path="rutineDescription"
						disabled="<%=disable%>" id="rutineDescription" /></td>
			</tr>
		</table>

		<table align="center" id="rutine">
			<tr>
				<th><br /></th>
				<th>Ejercicio</th>
				<th>Series</th>
				<th>Duración</th>
				<th>Medida</th>
				<th>Peso(kg)</th>
				<th>Orden <br/></th>
			
			</tr>
			
			<c:forEach var="exerciseRecord" items="${rutineTable.table}"
				varStatus="recordStatus">
				<tr>
					<td><a href="#"
						onclick="removeLine(${recordStatus.index } ,this);">Eliminar
							registro</a></td>
					<td><form:select
							path="table[${recordStatus.index }].idExercise"
							disabled="<%=disable %>"
							id="table[${recordStatus.index }].idExercise">

							<form:option value="-1" label="--- Seleccione uno ---" />
							<form:options items="${exerciseList}" />
						</form:select></td>
					<td><form:input type="text" name="seriesNumber"
							path="table[${recordStatus.index }].seriesNumber"
							disabled="<%=disable %>"
							id="table[${recordStatus.index }].seriesNumber" /></td>
					<td><form:input type="text" name="duration"
							path="table[${recordStatus.index }].duration"
							disabled="<%=disable %>"
							id="table[${recordStatus.index }].duration" /></td>


					<td><form:select
							path="table[${recordStatus.index }].exerciseMeasure"
							disabled="<%=disable %>"
							id="table[${recordStatus.index }].measures">
							<form:option value="NONE" label="--- Seleccione uno ---" />
							<form:options items="${measureList}" />
						</form:select></td>
					<td><form:input type="text" name="weight"
							path="table[${recordStatus.index }].weight"
							disabled="<%=disable %>"
							id="table[${recordStatus.index }].weight" /></td>
					<td><form:input type="text" name="order"
							path="table[${recordStatus.index }].order"
							disabled="<%=disable %>" id="table[${recordStatus.index }].order" /></td>


				</tr>
				<form:hidden path="table[${recordStatus.index }].idRecordRutine"
					id="table[${recordStatus.index }].idRecordRutine" />
			</c:forEach>
		</table>
	</form:form>
	<%
		} else {
	%>
	<h2>
		<c:out value="${errorRutine}" />
		<a href="/PersonalTrainer/index.jsp"> aquí</a>
	</h2>
	<%
		}
	%>
</body>
</html>