<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="org.jfree.chart.ChartFactory"%>
<%@page import="org.jfree.chart.ChartUtilities"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="java.io.File"%>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%
/*<!-- page trimDirectiveWhitespaces="true" hace que no falle al escribir el gráfico. Hay problemas con las lineas en blanco. En cualquier caso se recomienda usar un servlet antes que dejar la lógica en el jsp. Esoty deacuerdo -->*/

DefaultCategoryDataset dataset = (DefaultCategoryDataset)request.getAttribute("dataSet");
String xAxxis=(String)request.getAttribute("xAxxis");
String yAxxis=(String)request.getAttribute("yAxxis");
String topReport=(String)request.getAttribute("topReport");
	JFreeChart chart = ChartFactory.createBarChart3D(
			topReport, xAxxis, yAxxis,
			dataset, PlotOrientation.VERTICAL, false, true, false);
	try {
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ChartUtilities.writeChartAsPNG(os, chart, 625, 500);
	} catch (IOException e) {
		System.err.println("Error creando grafico.");
	}
%>