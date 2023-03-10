<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" required="false"%>
<%@attribute name="head" fragment="true" required="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------- Head
--------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------->
<head>

<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------- Fragment 1
------------------------------------------------------------------------------------------------------->
<jsp:invoke fragment="title" />
<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------/ Fragment 1
------------------------------------------------------------------------------------------------------->

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="icon"
	href='<c:url value="/resources/images/icon1-megumin.jpg" />'
	type="image/png">

<!------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------- Fonts
------------------------------------------------------------------------------------------------------->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Great+Vibes&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Berkshire+Swash&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Just+Another+Hand&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Big+Shoulders+Display:wght@500&family=Just+Another+Hand&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Big+Shoulders+Display:wght@500&family=Just+Another+Hand&family=Raleway&display=swap"
	rel="stylesheet">
<!------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------/ Fonts
------------------------------------------------------------------------------------------------------->

<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/bootstrap.css" />'>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/brands.min.css" />
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/mycss.css" />'>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/homepage-ribbon.css" />'>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/search_bar.css" />'>

<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------- Fragment 2
------------------------------------------------------------------------------------------------------->
<jsp:invoke fragment="head" />
<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------/ Fragment 2
------------------------------------------------------------------------------------------------------->

</head>
<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------/ Head
--------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------->




<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------- Body
--------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------->
<body>
	<div class="container nav-container">
		<nav
			class="navbar navbar-expand-md navbar-dark bg-dark pl-lg-0 pl-md-0 pl-sm-5">

			<c:url value="/views/commonPages/ServletHome" var="firstHomeServlet">
				<c:param name="btnView01" value="selected" />
			</c:url>
			<a class="navbar-brand nav-link mr-0" href="${firstHomeServlet}">
				<i class="fa-lg fab fa-free-code-camp"></i> Home
			</a>

			<button type="button" class="navbar-toggler" data-toggle="collapse"
				data-target="#navbarCollapse">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="nav navbar-nav mr-auto ml-lg-0 ml-md-0 ml-sm-5">

					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletDepartment" />'>Departments</a></li>
					<%-- <li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletFee" />'>Fees</a></li> --%>
					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletGrade" />'>Grades</a></li>
					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletSection" />'>Sections</a></li>
					<%-- <li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletExam" />'>Exams</a></li> --%>
					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletCourse" />'>Courses</a></li>
					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletTeacher" />'>Teachers</a></li>
					<%-- <li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletAssignment" />'>Assignments</a></li> --%>
					<li class="nav-item"><a class="nav-link"
						href='<c:url value = "/views/pages/ServletStudent" />'>Students</a></li>


					<c:if test="${sessionScope.role_id == null}">

						<!----------------------------------------------------------------------------------
 			             ----------------------------------------- login and register navbar items/links
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletHome"
							var="firstLoginServlet">
							<c:param name="homepageLoginParam" value="selected" />
						</c:url>

						<c:url value="/views/commonPages/ServletHome"
							var="firstRegisterServlet">
							<c:param name="homepageRegisterParam" value="selected" />
						</c:url>
						<!------------------------------------------------------------------------------
 			             ----------------------------------------/ login and register navbar items/links
			             ------------------------------------------------------------------------------>

						<li class="nav-item ml-3">
							<div class="p-0 m-0 ml-3">
								<a class="nav-link" href="${firstLoginServlet}"> <i
									class="fas fa-user-secret"></i> Login
								</a>
							</div>
						</li>


						<li class="nav-item"><a class="nav-link"
							href="${firstRegisterServlet}"> <i class="fas fa-user-plus"></i>
								Register
						</a></li>
					</c:if>

					<c:if test="${sessionScope.role_id != null}">
						<!------------------------------------------------------------------------------
 			             ----------------------------------------- View user tables from navbar settings
			             ------------------------------------------------------------------------------>

						<!------------------------------------------------------------------------------
 			             ---------------------------------------- /View user tables from navbar settings
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletAdmin" var="adminsTable">
							<c:param name="viewParam" value="selected" />
						</c:url>

						<c:url value="../pages/ServletTeacher" var="teachersTable">
							<c:param name="btnView01" value="selected" />
						</c:url>

						<c:url value="../pages/ServletStudent" var="studentsTable">
							<c:param name="btnView01" value="selected" />
						</c:url>

						<!------------------------------------------------------------------------------
 			             ------------------------------------------------------- Register user in navbar
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletAdmin"
							var="accountRegisterAdminNavbar">
							<c:param name="btnRegister" value="create" />
							<c:param name="redirectToThisPage" value="selected" />
						</c:url>

						<c:url value="../pages/ServletTeacher"
							var="accountRegisterTeacherNavbar">
							<c:param name="btnRegister" value="create" />
							<c:param name="redirectToThisPage" value="selected" />
						</c:url>

						<c:url value="../pages/ServletStudent"
							var="accountRegisterStudentNavbar">
							<c:param name="btnRegister" value="create" />
							<c:param name="redirectToThisPage" value="selected" />
						</c:url>
						<!------------------------------------------------------------------------------
 			             ------------------------------------------------------/ Register user in navbar
			             ------------------------------------------------------------------------------>

						<!------------------------------------------------------------------------------
 			             ----------------------------------------------------------- Edit user in navbar
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletAdmin"
							var="editSessionAdmin">
							<c:param name="editAdminProfile" value="${sessionScope.contact}" />
							<c:param name="comingFromNavbarAccount" value="selected" />
						</c:url>

						<c:url value="../pages/ServletTeacher" var="editSessionTeacher">
							<c:param name="editTeacherProfile"
								value="${sessionScope.contact}" />
							<c:param name="comingFromNavbarAccount" value="selected" />
						</c:url>

						<c:url value="../pages/ServletStudent" var="editSessionStudent">
							<c:param name="editStudentProfile"
								value="${sessionScope.contact}" />
							<c:param name="comingFromNavbarAccount" value="selected" />
						</c:url>
						<!------------------------------------------------------------------------------
 			              ---------------------------------------------------------/ Edit user in navbar
			              ----------------------------------------------------------------------------->

						<!------------------------------------------------------------------------------
 			             --------------------------------------------------- View/Display user in navbar
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletLogout"
							var="logoutSession">
							<c:param name="logoutParam" value="selected" />
						</c:url>

						<c:url value="/views/commonPages/ServletAdmin"
							var="viewProfileAdmin">
							<c:param name="displayProfile" value="${sessionScope.contact}" />
						</c:url>

						<c:url value="/views/pages/ServletTeacher"
							var="viewProfileTeacher">
							<c:param name="displayProfile" value="${sessionScope.contact}" />
						</c:url>

						<c:url value="/views/pages/ServletStudent"
							var="viewProfileStudent">
							<c:param name="displayProfile" value="${sessionScope.contact}" />
						</c:url>
						<!------------------------------------------------------------------------------
 			             --------------------------------------------------- View/Display user in navbar
			             ------------------------------------------------------------------------------>

						<!------------------------------------------------------------------------------
 			             --------------------------------------------------------- Other links in navbar
			             ------------------------------------------------------------------------------>
						<c:url value="/views/commonPages/ServletAdmin"
							var="sessionWelcomePage">
							<c:param name="btnView01" value="selected" />
						</c:url>
						<!------------------------------------------------------------------------------
 			             -------------------------------------------------------- /Other links in navbar
			             ------------------------------------------------------------------------------>

						<li class="nav-item ml-3">
							<div class="p-0 m-0 ml-3">
								<img
									src="<c:url value='/resources/images/${sessionScope.image_file_name}' />"
									class="rounded-circle display-nav-avatar"
									alt="user-image-on-session-active" width="40" height="40" />
							</div>
						</li>

						<li class="nav-item dropdown first-one"><a
							class="nav-link dropdown-toggle" href="#"
							id="username-dropdown-link" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"> <span>${sessionScope.first_name}</span></a>

							<div class="dropdown-menu"
								aria-labelledby="username-dropdown-link">
								<a class="dropdown-item add-me"
									href='
									<c:if test="${sessionScope.role_id == 1}">
										${viewProfileAdmin} </c:if>
										<c:if test="${sessionScope.role_id == 2}">
										${viewProfileTeacher} </c:if> 
									<c:if test="${sessionScope.role_id == 3}">
										${viewProfileStudent}
									</c:if>
								'>View
									profile</a> <a class="dropdown-item add-me"
									href="${sessionWelcomePage}">Go to welcome page</a>
								<div class="clearfix"></div>
								<a class="dropdown-item" href="javascript:void(0)">${sessionScope.email}</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item font-weight-bold text-success"
									href="javascript:void(0)"> <c:if
										test="${sessionScope.role_id == 1 && sessionScope.admin_id == 1}">
											Role : Super Admin
										</c:if> <c:if
										test="${sessionScope.role_id == 1 && sessionScope.admin_id != 1}">
											Role : Admin
										</c:if> <c:if test="${sessionScope.role_id == 2}">
											Role : Teacher
										</c:if> <c:if test="${sessionScope.role_id == 3}">
											Role : Student
										</c:if>
								</a>

							</div></li>


						<li class="nav-item dropdown second-one">
							<div class="clearfix"></div> <a class="nav-link dropdown-toggle"
							href="#" id="username-dropdown-link" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"><i
								class="fa fa-cog" aria-hidden="true"></i><span class="ml-1">Account</span></a>

							<div class="dropdown-menu"
								aria-labelledby="username-dropdown-link">

								<c:if test="${sessionScope.role_id == 1}">
									<a class="dropdown-item add-me" href="${adminsTable}">
										Manage admins </a>
								</c:if>

								<c:if test="${sessionScope.role_id == 2}">
									<a class="dropdown-item add-me" href="${teachersTable}">
										Manage teachers </a>
								</c:if>

								<c:if test="${sessionScope.role_id == 3}">
									<a class="dropdown-item add-me" href="${studentsTable}">
										Manage students </a>
								</c:if>

								<a class="dropdown-item add-me"
									href="
									<c:if test="${sessionScope.role_id == 1}">
										${editSessionAdmin}
									</c:if>
									
									<c:if test="${sessionScope.role_id == 2}">
										${editSessionTeacher}
									</c:if>
								
									<c:if test="${sessionScope.role_id == 3}">
										${editSessionStudent}
									</c:if>
								">Edit
									user</a>

								<c:if
									test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
									<a class="dropdown-item add-me"
										href="${accountRegisterStudentNavbar}">Register student</a>
									<div class="clearfix"></div>
								</c:if>

								<c:if test="${sessionScope.role_id == 1}">
									<a class="dropdown-item add-me"
										href="${accountRegisterTeacherNavbar}">Register teacher</a>
								</c:if>
								<c:if
									test="${sessionScope.role_id == 1 && sessionScope.admin_id == 1}">
									<a class="dropdown-item add-me"
										href="${accountRegisterAdminNavbar}">Register admin</a>
								</c:if>

								<div class="dropdown-divider"></div>
								<a class="dropdown-item add-me font-weight-bold text-danger"
									href="${logoutSession}">Log out</a>
							</div>
						</li>
					</c:if>
				</ul>

				<ul class="nav navbar-right mr-0" style="width: 18%;">
					<li class="nav-item">
						<div
							class="container mySearchContainer pl-0 ml-0 col-md-12 col-sm-1 col-lg-12">
							<input type="text"
								class="form-control mr-sm-2 search-input-navbar float-left"
								style="width: 40%;" id="mySearchInputNavbar"
								placeholder="Search">
							<button type="button" id="btnSearchInputNavbar"
								class="btn btn-outline-light p-2 ml-lg-0 ml-md-0 ml-sm-1 float-left border-radius-20per">
								<i class="fa fa-search" aria-hidden="true"></i>
							</button>
							<p class="search-count-text float-left ml-2 pt-2">
								Found&nbsp;:&nbsp;<span class="search-count-navbar"></span>
							</p>
						</div>
						<div class="clear-fix"></div>
					</li>
				</ul>

				<!-- 				<ul class="nav navbar-right mr-0">
					<li class="nav-item pt-2">
						<div class="search search-bar">
							<input type="text" class="form-control mr-sm-2 w-50 ml-4"
								id="searchInputNavbar" placeholder="Search">
							<input type="text" class="search-box search-input" />
							<span class="search-button"> <span class="search-icon"></span>
							</span>
						</div>
					<li>
					<li class="nav-item pt-2 ml-2">
						<p class="search-count-text">
							Found: <span class="search-count"></span>
						</p>
					</li> -->


				<!-- 					<form class="form-inline my-2 my-lg-0" style="width: 15%;">
						<input class="form-control mr-sm-2 w-65" type="search"
							placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success my-2 my-sm-0 w-25"
							type="submit">
							<i class="fas fa-search"></i>
						</button>
					</form> -->
			</div>
		</nav>
	</div>

	<div id="body" class="container main-container p-5 mb-5">

		<!-------------------------------------------------------------------------------------------
   		-------------------------------------------------------------------------- Fragment 3 as Body
   		-------------------------------------------------------------------------------------------->
		<jsp:doBody />
		<!-------------------------------------------------------------------------------------------
   		-------------------------------------------------------------------------/ Fragment 3 as Body
  		-------------------------------------------------------------------------------------------->

	</div>
	<div class="clearfix"></div>


	<!-----------------------------------------------------------------------------------------------
	-------------------------------------------------------------------------------------------------
   	------------------------------------------------------------------------------------------ Footer
   	-------------------------------------------------------------------------------------------------
  	------------------------------------------------------------------------------------------------>
	<footer id="footer" class="footer-1 container px-0 mt-5">
		<div class="main-footer widgets-dark typo-light">
			<div class="container">
				<div class="row">



				</div>
			</div>
		</div>

		<div class="footer-copyright">
			<div class="container">
				<div class="row">
					<div class="col-md-12 text-center">
						<p>Gheorghe Teodora Sabina - Simplilearn Java Course</p>
					</div>
				</div>
			</div>
		</div>
	</footer>
	<!---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
   	---------------------------------------------------------------------------------------------/ Footer
   	-----------------------------------------------------------------------------------------------------
  	---------------------------------------------------------------------------------------------------->


	<script type="text/javascript"
		src='<c:url value="/resources/js/jquery.js" />'></script>
	<script type="text/javascript"
		src='<c:url value="/resources/js/bootstrap.js" />'></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/js/all.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/js/brands.min.js"></script>

	<!-- mark js online -->
	<script src="https://cdn.jsdelivr.net/mark.js/7.0.0/jquery.mark.min.js"></script>

	<!-- mark js offline -->
	<%-- 	<script type="text/javascript"
		src='<c:url value="/resources/js/mark.js" />'></script> --%>

	<!-- dom js offline -->
	<script type="text/javascript"
		src='<c:url value="/resources/js/findAndReplaceDomText.js" />'></script>

	<script type="text/javascript"
		src='<c:url value="/resources/js/myscript.js" />'></script>
</body>
<!------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------/ Body
--------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------->

</html>
