<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1" import="java.util.*" session="true"%>
<%@ taglib prefix="c" 
uri="http://java.sun.com/jsp/jstl/core"%> <!-- Con esto digo que puedo invocar a la funcionalidad de
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
	<tr><td><img src="images/HeaderpersonalTrainer.jpg" /></td></tr>
	</table>
</center>
<%
		if (session.getAttribute("user") == null) {
			pageContext.setAttribute("errorUserData", "Sesión cerrada, autentifiquese");
		}
	%>
<table align="center"  bgcolor="#C0C0C0" style="width: 1220px; "> 
		<tr><td style="width: 200px; "><a href="/PersonalTrainer/WelcomeUser.jsp"><c:out value="${user}"/></a></td><td style="width: 200px; "><a href="/PersonalTrainer/UserPage">Datos Personales</a></td><td style="width: 200px; "><a href="/PersonalTrainer/UserRutinesList">Tablas</a></td><td style="width: 200px; "><a href="/PersonalTrainer/HistoricPage.jsp">Evolucion</a></td><td style="width: 100px; "></td><td><a href="/PersonalTrainer/LogOut.jsp">Cerrar Sesión</a></td></tr>
		</table>

<% if(null==pageContext.getAttribute("errorUserData")){ %>
<script type="text/javascript">
	function validateForm(userDataForm){
	 	var camposNoValidos="";
	 	var camposVacios="";
	 	var continuar;
	 	var numCamposNoValidos=0;
	 	var numCamposVacios=0;
	 	//primero se validan que los campos no estén vacíos
	 	
	 	if(userDataForm.age.value==0){
	 		camposVacios+=' Edad';
	 		numCamposVacios++;
	 	}
	 	if(userDataForm.weight.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Peso';
	 		}else{
	 			camposVacios+=', Peso';
	 		}
	 		numCamposVacios++;
	 	}
	 	if(userDataForm.height.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Altura';
	 		}else{
	 			camposVacios+=', Altura';
	 		}
	 		numCamposVacios++;
	 	}
	 	if(userDataForm.hip.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Cadera';
	 		}else{
	 			camposVacios+=', Cadera';
	 		}
	 		
	 	}
	 	if(userDataForm.belly.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Cintura';
	 		}else{
	 			camposVacios+=', Cintura';
	 		}
	 		numCamposVacios++;
	 	}
	 	if(userDataForm.chest.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Pecho';
	 		}else{
	 			camposVacios+=', Pecho';
	 		}
	 		numCamposVacios++;
	 	} 
	 	if(userDataForm.heartrate.value==0){
	 		if(numCamposVacios==0){
	 			camposVacios+=' Frecuencia Cardiaca';
	 		}else{
	 			camposVacios+=', Frecuencia Cardiaca';
	 		}
	 		numCamposVacios++;
	 	} 
	 	//ahora se valida que los datos sean numericos
	 	
	 	if(isNaN(userDataForm.age.value)){
	 		camposNoValidos+=' Edad';
	 		numCamposNoValidos++;
	 	}
	 	if(isNaN(userDataForm.weight.value)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Peso';
	 		}else{
	 			camposNoValidos+=', Peso';
	 		}
	 		numCamposNoValidos++;
	 	}
	 	if(isNaN(userDataForm.height.value)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Altura';
	 		}else{
	 			camposNoValidos+=', Altura';
	 		}
	 		numCamposNoValidos++;
	 	}
	 	if(isNaN(userDataForm.hip.value==0)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Cadera';
	 		}else{
	 			camposNoValidos+=', Cadera';
	 		}
	 		
	 	}
	 	if(isNaN(userDataForm.belly.value)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Cintura';
	 		}else{
	 			camposNoValidos+=', Cintura';
	 		}
	 		numCamposNoValidos++;
	 	}
	 	if(isNaN(userDataForm.chest.value)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Pecho';
	 		}else{
	 			camposNoValidos+=', Pecho';
	 		}
	 		numCamposNoValidos++;
	 	} 
	 	if(isNaN(userDataForm.heartrate.value)){
	 		if(numCamposNoValidos==0){
	 			camposNoValidos+=' Frecuencia Cardiaca';
	 		}else{
	 			camposNoValidos+=', Frecuencia Cardiaca';
	 		}
	 		numCamposNoValidos++;
	 	} 
	 	if(numCamposNoValidos==0){//todos los campos tienen valores nulos o correctos
	 		continuar=window.confirm("Se van a guardar sus datos, ¿Desea Continuar?");
	 		if(numCamposVacios==1){
	 			continuar=window.confirm("El campo"+camposVacios+" está vacío. Algunas medidas no se calcularán, ¿Desea Continuar?");
	 		}else if(numCamposVacios>1){
	 			continuar=window.confirm("Los siguientes campos está vacíos: "+camposVacios+". Algunas medidas no se calcularán, ¿Desea Continuar?");
	 		}
	 	}else if(numCamposNoValidos==1){
	 		alert("El campo"+camposNoValidos+" tiene un valor incorrecto. No se puede continuar.");
	 		continuar=false;
	 	}else{
	 		alert("Los siguientes campos tienen valores incorrectos: "+camposNoValidos+". No se puede continuar.");	 	
	 		continuar=false;
	 	}

	 return continuar;
	}
</script>
	<form action="addUserData" method = "post" id="userDataForm" onsubmit="return validateForm(this);" >
		<input type="hidden" name="login" value="<c:out value="${user}"/>" />
		<input type="hidden" name="profile" value="<c:out value="${profile}"/>"  />
		<input type="hidden" name="idUserData" value="<c:out value="${idUserData}" />"/>
		<table align="center">
		<tr><td>Nombre: <input type="text" name="name" id="name" value="<c:out value="${name}" />" /></td><td>Edad: <input type="text" name="age" id="age" value="<c:out value="${age}"/>"/></td><td>Sexo: <SELECT SIZE=1 NAME="sex" >
			<option value="male" ${sex == 'male' ? 'selected' : ''}> Hombre</option>
			<option value="female" ${sex == 'female' ? 'selected' : ''}> Mujer</option>
		</SELECT> </td></tr><!-- Revisar el tipo SELECT -->
		<tr><td>Email: <input type="text" name="email" id="email" value="<c:out value="${email}"/>" style="width: 170px; "/></td><td>Peso: <input type="text" name="weight" id="weight" value="<c:out value="${weight}"/>"/></td><td>Altura(en cm): <input type="text" name="height" id="height" value="<c:out value="${height}"/>"/></td></tr>
		<tr><td>Cintura(en cm): <input type="text" name="belly" id="belly" value="<c:out value="${belly}"/>"/></td><td>Cadera(en cm): <input type="text" name="hip" id="hip" value="<c:out value="${hip}"/>"/></td><td>Pecho: <input type="text" name="chest" id="chest" value="<c:out value="${chest}"/>"/></td></tr>
		<tr><td>IMC: <input disabled="disabled" type="text" name="imc" id="imc" value="<c:out value="${imc}"/>"/></td><td>IGC: <input disabled="disabled" type="text" name="IGC" id="IGC" value="<c:out value="${IGC}"/>"/></td><td>Cintura/Altura: <input disabled="disabled" type="text" name="ICA" id="ICA" value="<c:out value="${ICA}"/>"/></td></tr>
		<tr><td>Met. Basal: <input disabled="disabled" type="text" name="mbasal" id="mbasal" value="<c:out value="${mbasal}"/>"/></td><td>Frec. Card: <input type="text" name="heartrate" id="heartrate" value="<c:out value="${heartrate}"/>"/></td><td>Magra Corporal: <input disabled="disabled" type="text" name="mcm" id="mcm" value="<c:out value="${mcm}"/>"/></td></tr>
		</table>
		<center><h3>Frecuencias cardiacas según esfuerzo</h3></center>
		<table align="center">
		<tr><td></td><td>Frec. Max: <input disabled="disabled" type="text" name="maxheartrate" id="maxheartrate" value="<c:out value="${maxheartrate}"/>"/></td><td></td></tr>
		<tr><td></td><td>Grupo A: <input disabled="disabled" type="text" name="heartrateA" id="heartrateA" value="<c:out value="${heartrateA}"/>"/></td><td></td></tr>
		<tr><td></td><td>Grupo B: <input disabled="disabled" type="text" name="heartrateB" id="heartrateB" value="<c:out value="${heartrateB}"/>"/></td><td></td></tr>
		<tr><td></td><td>Grupo C: <input disabled="disabled" type="text" name="heartrateC" id="heartrateC" value="<c:out value="${heartrateC}"/>"/></td><td></td></tr>
		<tr><td></td><td>Grupo D: <input disabled="disabled" type="text" name="heartrateD" id="heartrateD" value="<c:out value="${heartrateD}"/>"/></td><td></td></tr>
		<tr><td></td><td>Grupo E: <input disabled="disabled" type="text" name="heartrateE" id="heartrateE" value="<c:out value="${heartrateE}"/>"/></td><td></td></tr>
		<tr><td></td><td><input type="submit" alt="Guardar" value="Guardar" /></td><td></td></tr>
		</table>
	</form>
<%}else{%>
<h2 ><c:out value="${errorUserData}"/><a href="/PersonalTrainer/index.jsp"> aquí</a></h2>
<%} %>
</body>
</html>