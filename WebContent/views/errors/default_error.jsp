<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Department"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<%
String error = request.getParameter("error");
String notFound = "No any error found at the moment.";
%>

<t:layout>
	<jsp:attribute name="title">
		<title>Error Page</title>
	</jsp:attribute>

	<jsp:body>
	<div class="container error-block px-0 my-5">
		<div
				class="container-fluid px-0 d-flex justify-content-center align-items-center"> 
			<h1 class="xbootstrap">Error Details</h1>
		</div>
		<div class="container-fluid bg-white text-danger">
			<div class="row form-group">
				<c:if test="${error == null}">
					<ol class="list-group">
						<li class="list-group-item border-0"><c:out
									value="${notFound}" /></li>	
					</ol>
				</c:if>
				<c:if test="${error != null}">
					<ol class="list-group">
					  	<li class="list-group-item border-0"><c:out
									value="${error}" /></li>
					</ol>
				</c:if>
				<c:if test="${error01 != null}">
					<ol class="list-group">
					  	<li class="list-group-item border-0"><c:out
									value="${error01}" /></li>
					</ol>
				</c:if>
			</div>
		</div>
	</div>	
	<div class="clearfix"></div>
	</jsp:body>


</t:layout>



