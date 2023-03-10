<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>


<c:if test="${sessionScope.role_id != null}">
	<c:redirect url="/views/commonPages/welcome_page.jsp">
	</c:redirect>
</c:if>


<t:layout>
	<jsp:attribute name="title">
		
		<c:if test="${roleLoginParam == 'student'}">
			<title>Login as student</title>
		</c:if>
		
		<c:if test="${roleLoginParam == 'teacher'}">
			<title>Login as teacher</title>
		</c:if>
		
		<c:if test="${roleLoginParam == 'admin'}">
			<title>Login as admin</title>
		</c:if>
		
		<c:if test="${showAdminLoginForm == 'admin'}">
			<title>Login as admin</title>
		</c:if>
		
	</jsp:attribute>

	<jsp:body>


	<c:if test="${roleLoginParam != null || showAdminLoginForm != null}">
	<div
				class="container default-block bg-cool-black px-0 mt-2 pt-4 pb-5 mb-5">
				
	  <h1 class="xbootstrap text-white bg-success py-2 mt-2"
					style="text-shadow: none;">
				<c:out value="Login Form" />
	  </h1>
	  
	  <div
					class="container px-0 pt-5 d-flex justify-content-center align-items-center mb-2">
		 <div class="card w-50 login-form-card">
			<div class="card-body">
				<h2 class="card-title text-center mb-4 text-white">Sign in</h2>
				<hr class="bg-white">
				<h3 class="text-success text-center pb-2">
						<c:if test="${showAdminLoginForm != null}">
							<c:out value="${showAdminLoginForm}" /> login
						</c:if>
						
						<c:if test="${roleLoginParam != null}">
							<c:out value="${roleLoginParam}" /> login
						</c:if>
				</h3>
				<form method="post" action="ServletLogin">
				
					<div class="form-row justify-content-center align-items-center">
						<div class="form-group w-75">
							<div class="input-group">
								<div class="input-group-prepend">
								    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
								 </div>
								<input id="userEmailLogin" name="userEmailLogin"
												class="form-control" placeholder="Email Address"
												type="email">
							</div>
						</div>
					</div>
					
					<div class="form-row justify-content-center align-items-center">
						<div class="form-group w-75">
							<div class="input-group">
								<div class="input-group-prepend">
								    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
								 </div>
							    <input id="userPwdLogin" name="userPwdLogin"
												class="form-control" placeholder="********" type="password">
							</div>
						</div>
					</div>
					
					<div class="form-row">
						<div
										class="form-group w-100 d-flex justify-content-center align-items-center">
							<c:if test="${showAdminLoginForm != null}">
								<button type="submit" name="postLoginUser"
												value="${showAdminLoginForm}" class="btn btn-primary">Login</button>
							</c:if>
							
							<c:if test="${roleLoginParam != null}">
								<button type="submit" name="postLoginUser"
												value="${roleLoginParam}" class="btn btn-primary">Login</button>
							</c:if>
							<button type="button" class="btn btn-primary ml-3 pt-2">Forgot Password?</button>
						</div>
					</div>
				</form>
			</div>
		  </div>
		</div>	
	  </div>		
	</c:if>								

	<div class="clearfix"></div>
	<script type="text/javascript">
		document
				.addEventListener(
						'DOMContentLoaded',
						function() {
							var parent = document.querySelector('.splitview'), topPanel = parent
									.querySelector('.top'), handle = parent
									.querySelector('.handle'), skewHack = 0, delta = 0;

							// If the parent has .skewed class, set the skewHack var.
							if (parent.className.indexOf('skewed') != -1) {
								skewHack = 1000;
							}

							parent
									.addEventListener(
											'mousemove',
											function(event) {
												// Get the delta between the mouse position and center point.
												delta = (event.clientX - window.innerWidth / 2) * 0.5;

												// Move the handle.
												handle.style.left = event.clientX
														+ delta + 'px';

												// Adjust the top panel width.
												topPanel.style.width = event.clientX
														+ skewHack
														+ delta
														+ 'px';
											});
						});
	</script>
	
	</jsp:body>
</t:layout>
