<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>


<%-- <c:if test="${sessionScope.role_id != 1 && registerAdminRole == null}"> --%>
<c:if test="${sessionScope.role_id != 1}">
 <c:redirect url="/views/commonPages/ServletHome">
  <c:param name="homepageAccessParam" value="selected"></c:param>
  <c:param name="loginAsAdmin" value="${loginAsAdmin}"></c:param>
 </c:redirect>
</c:if>

<t:layout>
 <jsp:attribute name="title">
  <c:if test="${viewParam == 'selected'}">
   <title>Admins</title>
  </c:if>
  <c:if test="${createParam == 'selected' && registerAdminRole != null}">
   <title>Register Admin</title>
  </c:if>
  <c:if test="${createParam == 'selected'  && registerAdminRole == null}">
   <title>Create Admin</title>
  </c:if>
  <c:if test="${editParam == 'selected'}">
   <title>Edit Admin</title>
  </c:if>
  <c:if test="${displayParam == 'selected'}">
   <title>Admin Profile</title>
  </c:if>

 </jsp:attribute>

 <jsp:body>

  <c:if test="${viewParam == 'selected'}">
   <div class="container default-block px-0 my-5">
    <div class="container-fluid px-0 d-flex align-items-center">

     <c:if test="${sessionScope.role_id == 1 && sessionScope.admin_id != 1}">
      <div class="container d-flex justify-content-center align-items-center">
       <h1 class="xbootstrap">Admins</h1>
      </div>
     </c:if>

     <c:if test="${sessionScope.admin_id == 1}">
      <h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
      <form method="get" action="ServletAdmin">
       <button name="btnCreate" type="submit" value="create" class="ml-5 btn btn-success float-left">
        <h6 class="pt-1">Add new Admin</h6>
       </button>
      </form>
     </c:if>
    </div>

    <div class="container px-0">
     <table class="table table-striped table-dark">
      <thead class="bg-primary">
       <tr>
        <th scope="col">Id</th>
        <th scope="col">Name</th>
        <th scope="col">Address</th>
        <th scope="col">Gender</th>
        <th scope="col">Contact</th>
        <th scope="col">Role</th>

        <c:if test="${sessionScope.admin_id == 1}">
         <th scope="col">Options</th>
        </c:if>
       </tr>
      </thead>
      <tbody>
       <c:forEach items="${admins}" var="adm">
        <tr>
         <th scope="row">
          <p>${adm.getAdmId()}</p>
          <form method="get" action="ServletAdmin" class="">
           <button type="submit" name="btnDisplay" class="btn btn-info" value="${adm.getAdmId()}">
            <i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
           </button>
          </form>
         </th>
         <td>${adm.getAdmFrst()} ${adm.getAdmMid()} ${adm.getAdmLast()}</td>
         <td style="height: 150px;">
          <div class="scrollable custom-scrollbar">${adm.getAdmAdd()}</div>
         </td>
         <td>${adm.getAdmGen()}</td>
         <td>${adm.getAdmCont()}</td>


         <td class="font-weight-bold">
          <c:if test="${adm.getAdmId() == 1}">
           super
          </c:if>
          ${serviceAdmin.getRoleNameByRoleId(adm.getAdmRole())}
         </td>

         <c:if test="${sessionScope.admin_id == 1}">
          <td>
           <form method="get" action="ServletAdmin" class="pt-3">
            <button name="btnEdit" type="submit" value="${adm.getAdmId()}" class="btn btn-edit">
             <i class="fas fa-lg fa-edit"></i>
            </button>
           </form>


           <form method="post" action="ServletAdmin" class="pt-2">
            <button name="postDelete" class="btn btn-delete" value="${adm.getAdmId()}" type="submit">
             <i class="fas fa-lg fa-trash-alt"></i>
            </button>
           </form>
          </td>
         </c:if>
        </tr>
       </c:forEach>
      </tbody>
     </table>
    </div>
   </div>
  </c:if>

  <c:if test="${createParam == 'selected'}">

   <!----------------------------------------------------------------------------------------------
    	 --------------------------------------------------- Custom URLS for the login and register form
    	 ---------------------------------------------------------------------------------------------->
   <c:url value="/views/commonPages/ServletLogin" var="loginAsAdmin1">
    <c:param name="showAdminLoginFormParam" value="Admin" />
   </c:url>
   <!----------------------------------------------------------------------------------------------
    	 --------------------------------------------------/ Custom URLS for the login and register form
    	 ------------------ ---------------------------------------------------------------------------->

   <div class="container create-block px-0 my-5">
    <div class="container-fluid px-0">
     <h1 class="xbootstrap">Register Admin</h1>
    </div>

    <div class="container py-5 w-50 form-container float-left">
     <form method="post" action="ServletAdmin" enctype="multipart/form-data">
      <div class="form-row">
       <div class="form-group col-md-4">
        <label for="rgAdmFrst">First Name</label> <input type="text" class="form-control" id="rgAdmFrst"
         name="rgAdmFrst" required>
       </div>
       <div class="form-group col-md-4">
        <label for="rgAdmMid">Middle Name</label> <input type="text" class="form-control" id="rgAdmMid" name="rgAdmMid">
       </div>
       <div class="form-group col-md-4">
        <label for="rgAdmLast">Last Name</label> <input type="text" class="form-control" id="rgAdmLast" name="rgAdmLast"
         required>
       </div>
      </div>
      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="rgAdmMail">Email</label> <input type="email" class="form-control" id="rgAdmMail" name="rgAdmMail"
         required>
       </div>
       <div class="form-group col-md-6">
        <label for="rgAdmPwd">Password</label> <input type="password" class="form-control d-flex float-left admPwdField"
         id="rgAdmPwd" name="rgAdmPwd" required> <span class="pwdStatClass d-flex float-right mt-2"><i
          id="pwdIconAdminCr" class="fas fa-eye"></i></span>
       </div>
      </div>
      <div class="form-row">
       <div class="form-group col-md-12">
        <label for="rgAdmAdd">Address</label>
        <textarea class="form-control" rows="5" id="rgAdmAdd" name="rgAdmAdd"></textarea>
       </div>
      </div>

      <div class="form-row">
       <div class="col-md-6 form-group">
        <label for="rgAdmGen">Gender</label> <select id="rgAdmGen" name="rgAdmGen" class="form-control"
         aria-label="Default select example" required>
         <option value="Select">Select</option>
         <option value="Male">Male</option>
         <option value="Female">Female</option>
         <option value="Transexual">Transexual</option>
         <option value="Bisexual">Bisexual</option>
         <option value="Gay">Gay</option>
         <option value="Lesbian">Lesbian</option>
        </select>
       </div>

       <div class="form-group col-md-6">
        <label for="rgAdmDob">Date of Birth</label> <input type="date" class="form-control" id="rgAdmDob"
         name="rgAdmDob">
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-4">
        <label for="rgAdmCont">Contact</label> <input type="text" class="form-control" id="rgAdmCont" name="rgAdmCont"
         required>
       </div>
       <div class="form-group col-md-4">
        <label for="">Profile Picture</label> <label for="rgAdmImg"
         class="form-control d-flex justify-content-between pr-1">
         <span>Choose File</span> <i class="fas fa-lg fa-camera-retro"></i>
        </label> <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="rgAdmImg"
         name="rgAdmImg" required />
       </div>

       <div class="form-group col-md-4">
        <label for="rgAdmRole">Role</label> <select id="rgAdmRole" name="rgAdmRole" class="form-control" required>
         <option value="Select">Select</option>
         <c:forEach items="${roleNames}" var="roleCr">
          <option value='<c:out value="${roleCr}" />' <c:if test="${roleCr != 'admin'}">
           <c:out value="disabled" />
  </c:if>>
  <c:if test="${roleCr == 'admin'}">
   ${roleCr} &#xf058;
  </c:if>
  <c:if test="${roleCr != 'admin'}">
   ${roleCr} &#xf057;
  </c:if>
  </option>
  </c:forEach>
  </select>
  </div>
  </div>

  <div id="rgAdmImgView" class="form-row" style="display: none;">
   <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
    <div class="col-lg-4">
     <div class="member-card pt-2 pb-2">
      <div class="thumb-lg member-thumb mx-auto">
       <img src="" class="rounded-circle img-thumbnail show-image" alt="admin-image-on-register-form">
      </div>
     </div>
    </div>
   </div>

   <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;"> </span>
  </div>

  <!------------------------------------------------------------------------------------------
				   	 ---------------------------------------------------------------- Custom URL for post-create
				   	 ------------------------------------------------------------------------------------------>
  <c:url value="ServletAdmin" var="adminRegisterServlet">
   <c:param name="postCreate" value="create" />
   <c:param name="registerAdminRole" value="selected" />
   <c:param name="comingFromRegisterForm" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
				   	 ---------------------------------------------------------------/ Custom URL for post-create
				   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerAdminRole == null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create">
    Create
   </button>
  </c:if>

  <c:if test="${registerAdminRole != null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create" formaction="${adminRegisterServlet}">
    Create
   </button>
  </c:if>

  </form>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">
   <c:if test="${registerAdminRole != null && sessionScope.role_id == null}">
    <form method="get" action="../commonPages/ServletHome">
     <h1 class="pt-5">
      <label for="homeRegParam">
       Go to Register
      </label>
     </h1>
     <input id="homeRegParam" name="homepageRegisterParam" value="selected" class="d-none allView" type="submit" />
    </form>
   </c:if>

   <c:if test="${registerTeacherRole == null && sessionScope.role_id == 1}">
    <form method="get" action="ServletAdmin">
     <h1 class="pt-5">
      <label for="viewParam">
       Go to Admins
      </label>
     </h1>
     <input id="viewParam" name="viewParam" value="view" class="d-none allView" type="submit" />
    </form>
   </c:if>



  </div>
  </div>
  </c:if>

  <c:if test="${editParam == 'selected'}">

   <div class="container edit-block px-0 my-5">

    <div class="container-fluid px-0">
     <h1 class="xbootstrap">Edit Admin</h1>
    </div>

    <div class="container py-5 w-50 form-container float-left">
     <form method="post" action="ServletAdmin" enctype="multipart/form-data">

      <div class="form-row">

       <div class="form-group col-md-4">
        <label for="rgAdmFrst">First Name</label>
        <input type="text" class="form-control" id="rgAdmFrst" name="rgAdmFrst"
         value='<c:out value="${admin.getAdmFrst()}" />' required>
       </div>

       <div class="form-group col-md-4">
        <label for="rgAdmMid">Middle Name</label>
        <input type="text" class="form-control" id="rgAdmMid" name="rgAdmMid"
         value='<c:out value="${admin.getAdmMid()}" />'>
       </div>

       <div class="form-group col-md-4">
        <label for="rgAdmLast">Last Name</label>
        <input type="text" class="form-control" id="rgAdmLast" name="rgAdmLast"
         value='<c:out value="${admin.getAdmLast()}" />' required>
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="rgAdmMail">Email</label>
        <input type="email" class="form-control" id="rgAdmMail" name="rgAdmMail"
         value='<c:out value="${admin.getAdmMail()}" />' required>
       </div>

       <div class="form-group col-md-6">

        <label for="rgAdmPwd">Password</label>
        <div class="clearfix"></div>

        <input type="password" class="form-control d-flex float-left admPwdField" id="rgAdmPwd" name="rgAdmPwd"
         value='<c:out value="${admin.getAdmPwd()}" />' required>
        <span class="pwdStatClass d-flex float-right mt-2"><i id="pwdIconAdminUp" class="fas fa-eye"></i></span>

       </div>

      </div>

      <div class="form-row">

       <div class="form-group col-md-12">
        <label for="rgAdmAdd">Address</label>
        <textarea class="form-control" rows="5" id="rgAdmAdd" name="rgAdmAdd"
         required><c:out value="${admin.getAdmAdd()}"/></textarea>
       </div>

      </div>

      <div class="form-row">

       <div class="col-md-6 form-group">
        <label for="rgAdmGen">Gender</label>

        <select id="rgAdmGen" name="rgAdmGen" class="form-control" aria-label="Default select example" required>
         <option value="Select" <c:if test='${admin.getAdmGen() == null}'>
          <c:out value="selected" />
  </c:if>>
  Select
  </option>
  <option value="Male" <c:if test='${admin.getAdmGen() == "Male"}'>
   <c:out value="selected" />
   </c:if>>
   Male
  </option>
  <option value="Female" <c:if test='${admin.getAdmGen() == "Female"}'>
   <c:out value="selected" />
   </c:if>>
   Female
  </option>

  <option value="Transexual" <c:if test='${admin.getAdmGen() == "Transexual"}'>
   <c:out value="selected" />
   </c:if>>
   Transexual
  </option>
  <option value="Bisexual" <c:if test='${admin.getAdmGen() == "Bisexual"}'>
   <c:out value="selected" />
   </c:if>>
   Bisexual
  </option>
  <option value="Gay" <c:if test='${admin.getAdmGen() == "Gay"}'>
   <c:out value="selected" />
   </c:if>>
   Gay
  </option>
  <option value="Lesbian" <c:if test='${admin.getAdmGen() == "Lesbian"}'>
   <c:out value="selected" />
   </c:if>>
   Lesbian
  </option>
  </select>
  </div>

  <div class="form-group col-md-6">
   <label for="rgAdmDob">Date of Birth</label>
   <input type="date" class="form-control" id="rgAdmDob" name="rgAdmDob" value='<c:out value="${admin.getAdmDob()}" />'
    required>
  </div>
  </div>

  <div class="form-row">

   <div class="form-group col-md-4">
    <label for="rgAdmCont">Contact</label>
    <input type="text" class="form-control" id="rgAdmCont" name="rgAdmCont"
     value='<c:out value="${admin.getAdmCont()}" />' required>
   </div>

   <div class="form-group col-md-4">
    <label for="">Profile Picture</label>
    <label for="rgAdmImg" class="form-control d-flex justify-content-between pr-1">
     <span>Change image</span>
     <i class="fas fa-lg fa-camera-retro"></i>
    </label>
    <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="rgAdmImg" name="rgAdmImg"
     required />
   </div>

   <div class="form-group col-md-4">
    <label for="rgAdmRole">Role</label>
    <select id="rgAdmRole" name="rgAdmRole" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${roleNames}" var="roleUp">
      <option value='<c:out value="${roleUp}" />' <c:if
       test="${serviceAdmin.getRoleNameByRoleId(admin.getAdmRole()) == roleUp}">
       <c:out value="selected" />
       </c:if>
       <c:if test="${roleUp != 'admin'}">
        <c:out value="disabled" />
       </c:if>>
       <c:if test="${roleUp == 'admin'}">
        ${roleUp} &#xf058;
       </c:if>
       <c:if test="${roleUp != 'admin'}">
        ${roleUp} &#xf057;
       </c:if>
      </option>
     </c:forEach>
    </select>
   </div>
  </div>

  <div id="rgAdmImgView" class="form-row" style="display: block;">

   <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
    <div class="col-lg-4">
     <div class="member-card pt-2 pb-2">
      <div class="thumb-lg member-thumb mx-auto">
       <img src="<c:url value='/resources/images/${admin.getAdmImg()}' />"
        class="rounded-circle img-thumbnail show-image" alt="admin-image-on-edit-form">
      </div>
     </div>
    </div>
   </div>

   <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;">
   </span>
  </div>

  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------- Custom URL for post-update
			   	 ------------------------------------------------------------------------------------------>
  <c:url value="ServletAdmin" var="adminEditProfileServlet">
   <c:param name="postUpdate" value="${admin.getAdmId()}" />
   <c:param name="comingFromNavbarAccount" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------/ Custom URL for post-update
			   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerAdminRole == null}">
   <button name="postUpdate" value="${admin.getAdmId()}" type="submit" class="btn btn-edit">
    Update
   </button>
  </c:if>

  <c:if test="${registerAdminRole != null}">
   <button name="postUpdate" value="${admin.getAdmId()}" type="submit" class="btn btn-edit"
    formaction="${adminEditProfileServlet}">
    Update
   </button>
  </c:if>

  </form>
  </div>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">
   <form method="get" action="ServletAdmin">
    <h1 class="pt-5">
     <label for="viewParam">
      Go to Admins
     </label>
    </h1>
    <input id="viewParam" name="viewParam" class="d-none allView" type="submit" />
   </form>
  </div>
  </c:if>

  <c:if test="${displayParam == 'selected'}">
   <div class="container display-block px-0 my-5">
    <div class="container-fluid px-0 d-flex justify-content-center align-items-center">
     <h1 class="xbootstrap pt-2">View Admin Details</h1>
     <form method="get" action="ServletLogin">
      <button name="depView01" type="submit" value="create" class="btn btn-success ml-5">
       <h6 class="pt-1">Go to Welcome page</h6>
      </button>
     </form>
    </div>

    <div class="container px-0 d-flex justify-content-center">

     <table class="table table-striped table-dark w-50 text-left mt-5">
      <thead>
       <tr class="text-center title-view bg-primary">
        <th scope="row" colspan="2" class="pt-3">
         <h2>Admin View</h2>
        </th>
       </tr>
      </thead>
      <tbody>
       <tr>
        <td colspan="2" class="text-center">
         <img src="<c:url value='/resources/images/${admin.getAdmImg()}' />"
          class="rounded-circle img-thumbnail show-image" alt="student-image-on-view-details" width="100" height="100">
        </td>
       </tr>
       <tr>
        <td scope="col">Id</td>
        <td>${admin.getAdmId()}</td>
       </tr>
       <tr>
        <td scope="col">First Name</td>
        <td>${admin.getAdmFrst()}</td>
       </tr>
       <tr>
        <td scope="col">Middle Name</td>
        <td>${admin.getAdmMid()}</td>
       </tr>
       <tr>
        <td scope="col">Last Name</td>
        <td>${admin.getAdmLast()}</td>
       </tr>

       <c:if test="${sessionScope.admin_id == 1 || sessionScope.admin_id == admin.getAdmId()}">
        <tr>
         <td scope="col">Email</td>
         <td>${admin.getAdmMail()}</td>
        </tr>
        <tr>
         <td scope="col">Password</td>
         <td>${admin.getAdmPwd()}</td>
        </tr>
       </c:if>

       <tr>
        <td scope="col">Address</td>
        <td style="height: 150px;">
         <div class="scrollable custom-scrollbar">${admin.getAdmAdd()}</div>
        </td>
       </tr>
       <tr>
        <td scope="col">Gender</td>
        <td>${admin.getAdmGen()}</td>
       </tr>
       <tr>
        <td scope="col">Date of Birth</td>
        <td>${admin.getAdmDob()}</td>
       </tr>
       <tr>
        <td scope="col">Contact</td>
        <td>${admin.getAdmCont()}</td>
       </tr>
       <tr>
        <td scope="col">Role</td>
        <td class="font-weight-bold">
         <c:if test="${admin.getAdmRole() == 1}">
          super
         </c:if>
         ${serviceAdmin.getRoleNameByRoleId(admin.getAdmRole())}
        </td>
       </tr>
      </tbody>
     </table>
    </div>
   </div>
  </c:if>

 </jsp:body>
</t:layout>