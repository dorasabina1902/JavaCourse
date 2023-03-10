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
		<!------------------------------------------------------------------------------------------
		 -------------------------------------------------------------------------------------------
		 ----------------------------------------------------------------- Dynamic Title of the page
		 -------------------------------------------------------------------------------------------
		 ------------------------------------------------------------------------------------------>

		<c:if
			test='${homepageLoginForm == null && homepageRegisterForm == null && homepageAccessChecker == null && logoutParam == null && loginAsAdmin == null}'>
			<title>Homepage</title>
		</c:if>
		<c:if test='${logoutParam == "selected"}'>
			<title>Homepage</title>
		</c:if>
		<c:if
			test='${homepageAccessChecker == "selected" && loginAsAdmin != null}'>
			<title>Requires admin login</title>
		</c:if>
		<c:if
			test='${homepageAccessChecker == "selected" && loginAsAdmin == null}'>
			<title>Requires you to login</title>
		</c:if>
		<c:if test='${homepageRegisterForm == "selected"}'>
			<title>Register now</title>
		</c:if>
		<c:if test='${homepageLoginForm == "selected"}'>
			<title>Login</title>
		</c:if>
	</jsp:attribute>

	<jsp:body>
	<!-----------------------------------------------------------------------------------------------
    -------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------- Start of Body Tag
    -------------------------------------------------------------------------------------------------
    ------------------------------------------------------------------------------------------------>
	
     <!-------------------------------------------------------------------------------------------------
     ---------------------------------------------------------------------------------------------------
     --------------------------------------------------------------- Message will appear when you logout
     ---------------------------------------------------------------------------------------------------
     -------------------------------------------------------------------------------------------------->
	 <c:if test='${logoutParam == "selected"}'>
		 <!---------------------------------------------------------------------------------------------
    	  -------------------------------------------------- Custom URLS for the login and register form
    	  --------------------------------------------------------------------------------------------->
		 <c:url value="/views/commonPages/ServletHome" var="myHomepageServlet">
	            <c:param name="homepageAccessChecker" value="selected" />
	     </c:url>            	
	     <!---------------------------------------------------------------------------------------------
    	  -------------------------------------------------/ Custom URLS for the login and register form
    	  --------------------------------------------------------------------------------------------->
    	  
    	  <div
				class="container default-block homepage-block bg-cool-black py-4 mt-2 px-0">
			
			<h1 class="xbootstrap text-white bg-danger py-2 mt-2"
					style="text-shadow: none;">
				You have been logged out successfully!
			</h1>
			
	    	<div class="mt-5 pr-5">
	          <h3 class="text-right">
	          	<i class="fa fa-2x fa-home text-white" aria-hidden="true"></i><span
							class="ml-2 text-white">Go to homepage</span>
			    <a class="homepage-access-anchor ml-3"
							href="${myHomepageServlet}">Click here</a>
		      </h3>
        	</div>
		 </div>
	  </c:if>
	
      <!-------------------------------------------------------------------------------------------------
       --------------------------------------------------------------------------------------------------
       ------------------------------------------------------------------------------- Show homepage only
       --------------------------------------------------------------------------------------------------
       ------------------------------------------------------------------------------------------------->	
	  <c:if
			test='${homepageLoginForm == null && homepageRegisterForm == null && homepageAccessChecker == null && logoutParam == null}'>

		<div class="container homepage-block px-0 my-2">
		
		<div class="ribbon">
			  <h2 class="x-bootstrap pt-3">Homepage</h2>
		  <i></i>
		  <i></i>
		  <i></i>
		  <i></i>
	    </div>
	    
		  <div class="splitview skewed pt-2">
	        <div class="panel bottom">
		        <div class="content">
					<!--  <h1 class="xbootstrap header-title">Home Page</h1> -->
	                <div class="description">
	                    <h1>School Management System</h1>
	                </div>
	
	                <img
								src="https://i.pinimg.com/originals/a8/62/dd/a862dd45d9183ca1da7431b55add711a.jpg"
								alt="Original">
	            </div>
	        </div>
			
	        <div class="panel top">
	            <div class="content">
	                <div class="description">
	                    <h1>Welcome to Learner's Academy</h1>
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

	  
	  <!------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------
	    ------------------------------------------------------ When you need to show login/register info
	    ------------------------------------------------------------------------------------------------
	    ----------------------------------------------------------------------------------------------->
	  <c:if test='${homepageAccessChecker == "selected"}'>
    
    	<!----------------------------------------------------------------------------------------------
    	 --------------------------------------------------- Custom URLS for the login and register form
    	 ---------------------------------------------------------------------------------------------->
		<c:url value="/views/commonPages/ServletHome"
				var="secondRegisterServlet">
	            <c:param name="homepageRegisterParam" value="selected" />
	    </c:url>
	      		            	
	    <c:url value="/views/commonPages/ServletHome"
				var="secondLoginServlet">
					<c:param name="homepageLoginParam" value="selected" />
	    </c:url>
	    
	    <c:url value="/views/commonPages/ServletLogin"
				var="loginAsAdminServlet">
			<c:param name="postLoginUser" value="admin" />
	    </c:url>
	    
	    <c:url value="/views/commonPages/ServletHome"
				var="goBackToRegister">
			<c:param name="homepageRegisterParam" value="selected" />
	    </c:url>
	    <!----------------------------------------------------------------------------------------------
    	 --------------------------------------------------/ Custom URLS for the login and register form
    	 ---------------------------------------------------------------------------------------------->

		<div
				class="container default-block homepage-block bg-cool-black py-4 mt-2 px-0">

			<c:if test='${loginAsAdmin != null}'>
				<h1 class="xbootstrap text-dark bg-warning py-2 mt-2"
						style="text-shadow: none;">
					<c:out value="Requires you to login as admin" />
				</h1>
				<h2 class="xbootstrap mt-5">You must login as an admin to continue.</h2>
				
					<table
						class="table homepage-access-table d-flex container justify-content-center align-items-center">
						<tr>
							<td colspan="2"><h3 class="text-muted">
								<i class="fas fa-arrow-right text-white mr-2 fa-lg mt-3"></i><span
										class="border-bottom">Here are some options for you</span> 
							</h3></td>
						</tr>
						<tr>
							<td><h3>
				            	<i class="fas fa-user-cog text-white mr-2 fa-lg"></i><span
										class="text-white">Are you an admin?</span>
					    	</h3></td>
					    	<td><h3 class="text-left">
								<a href="${loginAsAdminServlet}" class="ml-2">Login as admin</a>
					    	</h3></td>
						</tr>
						
						<tr>
							<td colspan="2">
								<h1 class="text-white">OR</h1>	
							</td>
						</tr>
						
						<tr>
							<td><h3 class="text-left">
					            	<i
										class="fas fa-arrow-alt-circle-left text-white mr-2 fa-lg"></i><span
										class="text-white">Go back to Register</span>
						    	</h3></td>
						    	<td><h3 class="text-left">
									<a href="${goBackToRegister}" class="ml-2">Click here</a>
					    	</h3></td>
						</tr>
					</table>
				
			
				</c:if>
			
			
				<c:if test="${loginAsAdmin == null}">
					<h1 class="xbootstrap text-white bg-primary py-2 mt-2"
						style="text-shadow: none;">
							<c:out value="${pageTitle}" />
					</h1>
					<h2 class="xbootstrap mt-5">Please login before viewing this content! Your role will give you access to this page.</h2>
					
					<table
						class="table homepage-access-table d-flex container justify-content-center align-items-center">
						<tr>
					
						</tr>
						<tr>
							<td><h3>
				            	<i class="fas fa-user-secret text-white mr-2 fa-lg"></i><span
										class="text-white">Already have an account?</span>
					    	</h3></td>
					    	<td><h3 class="text-left">
								<a href="${secondLoginServlet}" class="ml-2">Sign in here</a>
					    	</h3></td>
						</tr>
						
						<tr>
							<td colspan="2">
								<h1 class="text-white">OR</h1>	
							</td>
						</tr>
						
						<tr>
							<td><h3 class="text-left">
					            	<i class="fas fa-user-plus text-white mr-2 fa-lg"></i><span
										class="text-white">Don't have an account?</span>
						    	</h3></td>
						    	<td><h3 class="text-left">
									<a href="${secondRegisterServlet}" class="ml-2">Sign up here</a>
					    	</h3></td>
						</tr>
				  </table>
			  </c:if>
		 </div>	
	  </c:if>	

	  <!------------------------------------------------------------------------------------------------
      --------------------------------------------------------------------------------------------------
      --------------------------------------------------------- When you need to show register directory
      --------------------------------------------------------------------------------------------------
      ------------------------------------------------------------------------------------------------->			
	  <c:if test='${homepageRegisterForm == "selected"}'>
		<div
				class="container default-block homepage-block bg-cool-black px-0 mt-2 pt-4">

				
			 <h3 class="xbootstrap text-white bg-success py-2 mt-2 mb-4"
					style="text-shadow: none;">
				<c:out value="Register Now" />
			</h3>	
			

			<div class="container px-5 pb-4">
				
				<div class="row">
				
				  <c:if test='${sessionScope.role_id == null}'>
				
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2 pb-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
												src="https://cdn-icons-png.flaticon.com/512/201/201818.png"
												class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get"
											action="../pages/ServletStudent">
			                        <button type="submit" name="btnRegister"
												value="create"
												class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Register as Student</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            
		            
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2 pb-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
												src="https://cdn-icons-png.flaticon.com/512/206/206897.png"
												class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get"
											action="../pages/ServletTeacher">
			                        <button type="submit" name="btnRegister"
												value="create"
												class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Register as Teacher</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center"
															class="text-center">PARTIAL</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</c:if>
				
		        <c:if
							test='${sessionScope.role_id == 1 || sessionScope.role_id == null}'> 
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2 pb-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
												src="https://cdn-icons-png.flaticon.com/512/2206/2206368.png"
												class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get" action="ServletAdmin">
			                        <button type="submit" name="btnRegister"
												value="create"
												class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Register as Admin</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		          </c:if>
		          
				</div>	
				
				<c:if test="${sessionScope.role_id == null}">
					<div class="clear-fix"></div>
					
					<!----------------------------------------------------------------------------------------------
			    	 --------------------------------------------------- Custom URLS for the login and register form
			    	 ---------------------------------------------------------------------------------------------->
				    <c:url value="/views/commonPages/ServletHome"
							var="secondLoginServlet">
								<c:param name="homepageLoginParam" value="selected" />
				    </c:url>
				    <!----------------------------------------------------------------------------------------------
			    	 --------------------------------------------------/ Custom URLS for the login and register form
			    	 ------------------ ---------------------------------------------------------------------------->
					
		            <div class="">
			            <h3>
			            	<span class="text-white">Already have an account?</span>
	
							<a class="homepage-access-anchor" href="${secondLoginServlet}">Sign in here</a>
						</h3>
		            </div>
		         </c:if>
			</div>				
		</div>
		<div class="clearfix"></div>
	  </c:if>

      <!------------------------------------------------------------------------------------------------
       -------------------------------------------------------------------------------------------------
       ----------------------------------------------------------- When you need to show login directory
       -------------------------------------------------------------------------------------------------
       ------------------------------------------------------------------------------------------------>	
	   <c:if test='${homepageLoginForm == "selected"}'>
		<div
				class="container default-block homepage-block bg-cool-black px-0 mt-2 pt-4">

				
			 <h3 class="xbootstrap text-white bg-success py-2 mt-2 mb-4"
					style="text-shadow: none;">
				<c:out value="Login" />
			</h3>		
			
			<div class="container px-5 pb-4">
				<div class="row">
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
											src="https://cdn-icons-png.flaticon.com/512/201/201818.png"
											class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get" action="ServletLogin">
			                        <button type="submit" name="btnStudentLogin"
											value="student"
											class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Login as Student</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            
		            
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2 pb-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
											src="https://cdn-icons-png.flaticon.com/512/206/206897.png"
											class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get" action="ServletLogin">
			                        <button type="submit" name="btnTeacherLogin"
											value="teacher"
											class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Login as Teacher</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center"
														class="text-center">PARTIAL</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">NONE</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            
		            <div class="col-lg-4">
		                <div class="text-center card-box border-radius-10per">
		                    <div class="member-card pt-2 pb-2">
		                        <div class="thumb-lg member-thumb mx-auto">
											<img
											src="https://cdn-icons-png.flaticon.com/512/2206/2206368.png"
											class="rounded-circle img-thumbnail" alt="profile-image">
								</div>
		                        
		                        <form method="get" action="ServletLogin">
			                        <button type="submit" name="btnAdminLogin"
											value="admin"
											class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">Login as Admin</button>
		                        </form>
		                        <div class="mt-4">
		                            <div class="row">
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">View/Read access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">Modify access</p>
		                                    </div>
		                                </div>
		                                <div class="col-4">
		                                    <div class="mt-3">
		                                        <h5 class="text-center">FULL</h5>
		                                        <p class="mb-0 text-muted">Delete access</p>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</div>	
				
				<c:if test='${sessionScope.role_id == null}'>

					<!----------------------------------------------------------------------------------------------
			    	 --------------------------------------------------- Custom URLS for the login and register form
			    	 ---------------------------------------------------------------------------------------------->
					<c:url value="/views/commonPages/ServletHome"
							var="secondRegisterServlet">
				            <c:param name="homepageRegisterParam" value="selected" />
				    </c:url>
				    <!----------------------------------------------------------------------------------------------
			    	 --------------------------------------------------/ Custom URLS for the login and register form
			    	 ------------------ ---------------------------------------------------------------------------->
		            <div class="">
			            <h3>
			            	<span class="text-white">Don't have an account?</span>
							<a class="homepage-access-anchor" href="${secondRegisterServlet}">Sign up here</a>
						</h3>
		            </div>
		          </c:if>		            
			</div>				
		</div>							
		<div class="clearfix"></div>
	</c:if>


      <!------------------------------------------------------------------------------------------------
      --------------------------------------------------------------------------------------------------
      ---------------------------------------------------------------- Use this script for homepage only
      --------------------------------------------------------------------------------------------------
      ------------------------------------------------------------------------------------------------->	
	  <c:if
			test='${homepageLoginForm == null && homepageRegisterForm == null && homepageAccessChecker == null && logoutParam == null && loginAsAdmin == null}'>
			
		
	</c:if>
	<!--------------------------------------------------------------------------------------------------
     ---------------------------------------------------------------------------------------------------
     ----------------------------------------------------------------------------------- End of Body Tag
     ---------------------------------------------------------------------------------------------------
     -------------------------------------------------------------------------------------------------->
	</jsp:body>

</t:layout>

