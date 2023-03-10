<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Welcome Page"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
	    <c:if test="${displayParam == null}">
	    	<title>Welcome Page</title>
		</c:if>
		 
	    <c:if test="${displayParam == 'selected'}">
	    	<title>Admin Profile</title>
		</c:if>
	</jsp:attribute>

	<jsp:body>

	  <c:if test="${displayParam == null}">
		<div class="container homepage-block px-0 my-2">
		
		<div class="ribbon">
		
			  <h2 class="x-bootstrap">
				  <i class="fa fa-quote-left fa-xs mb-3" aria-hidden="true"></i>
				  <span>Hi, ${sessionScope.first_name}</span>
<!-- 				  <i class="fa fa-quote-right fa-xs mb-3" aria-hidden="true"></i> -->
			  </h2>
					
			  
		  <i></i>
		  <i></i>
		  <i></i>
		  <i></i>
	    </div>
	    
		  <div class="splitview skewed pt-2">
	        <div class="panel bottom">
	            <div class="content">
					
	                <c:if test='${role_id == 1}'>
		                <img
									src="https://i.pinimg.com/originals/a8/62/dd/a862dd45d9183ca1da7431b55add711a.jpg"
									alt="Original" />
	                </c:if>
	                <c:if test='${role_id == 2}'>
		                <img
									src="https://i.pinimg.com/originals/a8/62/dd/a862dd45d9183ca1da7431b55add711a.jpg"
									alt="Original" />
	                </c:if>
	                <c:if test='${role_id == 3}'>
		                <img
									src="https://i.pinimg.com/originals/a8/62/dd/a862dd45d9183ca1da7431b55add711a.jpg"
									alt="Original" />
	                </c:if>
	                
	            </div>
	        </div>
	
	        <div class="panel top">
	            <div class="content">
					<!-- <h1 class="xbootstrap header-title">Home Page</h1> -->
	                <div class="description">
	                    <h2>Welcome to Learner's Academy</h2>
	                </div>
	
	                <img
								src="https://i.pinimg.com/originals/a8/62/dd/a862dd45d9183ca1da7431b55add711a.jpg"
								alt="Duotone">
	            </div>
	        </div>
	
	        <div class="handle"></div>
	      </div>
	    </div>
	    <div class="clearfix"></div>
      </c:if>

	</jsp:body>
</t:layout>
