<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if test="${sessionScope.role_id == null && registerTeacherRole == null}">
 <c:redirect url="/views/commonPages/ServletHome">
  <c:param name="homepageAccessParam" value="selected"></c:param>
  <c:param name="pageTitle" value="Teachers"></c:param>
 </c:redirect>
</c:if>

<t:layout>
 <jsp:attribute name="title">

  <c:if test="${createParam == null && editParam == null && displayParam == null && registerTeacherRole == null}">
   <title>Teachers</title>
  </c:if>
  <c:if test="${createParam == 'selected' && registerTeacherRole != null}">
   <title>Register Teacher</title>
  </c:if>
  <c:if test="${createParam == 'selected' && registerTeacherRole == null}">
   <title>Create Teacher</title>
  </c:if>
  <c:if test="${editParam == 'selected' && registerTeacherRole != null}">
   <title>Edit Profile</title>
  </c:if>
  <c:if test="${editParam == 'selected' && registerTeacherRole == null}">
   <title>Edit Teacher</title>
  </c:if>
  <c:if test="${displayParam == 'selected'}">
   <title>Teacher Profile</title>
  </c:if>

 </jsp:attribute>

 <jsp:body>

  <c:if test="${createParam == null && editParam == null && displayParam == null && registerTeacherRole == null}">
   <div class="container default-block px-0 my-5">
    <div class="container-fluid px-0 d-flex align-items-center">

     <c:if test="${sessionScope.role_id == 2 || sessionScope.role_id == 3}">
      <div class="container d-flex justify-content-center align-items-center">
       <h1 class="xbootstrap">Teachers</h1>
      </div>
     </c:if>

     <c:if test="${sessionScope.role_id == 1}">
      <h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
      <form method="get" action="ServletTeacher">
       <button name="btnCreate" type="submit" value="create" class="ml-5 btn btn-success float-left">
        <h6 class="pt-1">Add new Teacher</h6>
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
        <th scope="col">Hours</th>
        <th scope="col">Department</th>
        <th scope="col">Role</th>
        <c:if test="${sessionScope.role_id == 1}">
         <th scope="col">Options</th>
        </c:if>
       </tr>
      </thead>
      <tbody>
       <c:forEach items="${teachers}" var="tec">
        <tr>
         <th scope="row">
          <p>${tec.getTecId()}</p>
          <form method="get" action="ServletTeacher" class="">
           <button type="submit" name="btnDisplay" class="btn btn-info" value="${tec.getTecId()}">
            <i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
           </button>
          </form>
         </th>
         <td>${tec.getTecFrst()} ${tec.getTecMid()} ${tec.getTecLast()}</td>
         <td style="height: 150px;">
          <div class="scrollable custom-scrollbar">${tec.getTecAdd()}</div>
         </td>
         <td>${tec.getTecGen()}</td>
         <td>${tec.getTecCont()}</td>
         <td>${tec.getTecHrs()}</td>
         <td>${serviceTeacher.getDepNameByDepId(tec.getTecDept())}</td>
         <td>${serviceTeacher.getRoleNameByRoleId(tec.getTecRole())}</td>

         <c:if test="${sessionScope.role_id == 1}">
          <td>
           <form method="get" action="ServletTeacher" class="pt-3">
            <button name="btnEdit" type="submit" value="${tec.getTecId()}" class="btn btn-edit">
             <i class="fas fa-lg fa-edit"></i>
            </button>
           </form>


           <form method="post" action="ServletTeacher" class="pt-2">
            <button name="postDelete" class="btn btn-delete" value="${tec.getTecId()}" type="submit">
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
   <div class="container create-block px-0 my-5">
    <div class="container-fluid px-0">
     <c:if test="${registerTeacherRole != null || sessionScope.role_id == 1}">
      <h1 class="xbootstrap">Register Teacher</h1>
     </c:if>

     <c:if test="${registerTeacherRole == null && sessionScope.role_id == null}">
      <h1 class="xbootstrap">Add Teacher</h1>
     </c:if>

    </div>

    <div class="container py-5 w-50 form-container float-left">

     <form method="post" action="ServletTeacher" enctype="multipart/form-data">

      <div class="form-row">

       <div class="form-group col-md-4">
        <label for="crTecFrst">First Name</label>
        <input type="text" class="form-control" id="crTecFrst" name="crTecFrst" required>
       </div>

       <div class="form-group col-md-4">
        <label for="crTecMid">Middle Name</label>
        <input type="text" class="form-control" id="crTecMid" name="crTecMid">
       </div>

       <div class="form-group col-md-4">
        <label for="crTecLast">Last Name</label>
        <input type="text" class="form-control" id="crTecLast" name="crTecLast" required>
       </div>

      </div>

      <div class="form-row">

       <div class="form-group col-md-6">
        <label for="crTecMail">Email</label>
        <input type="email" class="form-control" id="crTecMail" name="crTecMail" required>
       </div>

       <div class="form-group col-md-6">
        <label for="crTecPwd">Password</label>
        <input type="password" class="form-control d-flex float-left tecPwdField" id="crTecPwd" name="crTecPwd"
         required>
        <span class="pwdStatClass d-flex float-right mt-2"><i id="pwdIconTeacherCr" class="fas fa-eye"></i></span>
       </div>

      </div>

      <div class="form-row">

       <div class="form-group col-md-12">
        <label for="crTecAdd">Address</label>
        <textarea class="form-control" rows="5" id="crTecAdd" name="crTecAdd"></textarea>
       </div>

      </div>

      <div class="form-row">

       <div class="col-md-6 form-group">

        <label for="crTecGen">Gender</label>

        <select id="crTecGen" name="crTecGen" class="form-control" aria-label="Default select example" required>
         <option value="Select">
          Select
         </option>
         <option value="Male">
          Male
         </option>
         <option value="Female">
          Female
         </option>
         <option value="Transexual">
          Transexual
         </option>
         <option value="Bisexual">
          Bisexual
         </option>
         <option value="Gay">
          Gay
         </option>
         <option value="Lesbian">
          Lesbian
         </option>
        </select>

       </div>

       <div class="form-group col-md-6">
        <label for="crTecDob">Date of Birth</label>
        <input type="date" class="form-control" id="crTecDob" name="crTecDob" required>
       </div>
      </div>


      <div class="form-row">

       <div class="form-group col-md-4">
        <label for="crTecCont">Contact</label>
        <input type="text" class="form-control" id="crTecCont" name="crTecCont" required>
       </div>

       <div class="form-group col-md-4">
        <label for="">Profile Picture</label>
        <label for="crTecImg" class="form-control d-flex justify-content-between pr-1">
         <span>Choose File</span>
         <i class="fas fa-lg fa-camera-retro"></i>
        </label>
        <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="crTecImg" name="crTecImg"
         required />
       </div>

       <div class="form-group col-md-4">
        <label for="crTecHrs">Hours</label>
        <input type="number" class="form-control" id="crTecHrs" name="crTecHrs" required>
       </div>

      </div>

      <div id="crTecImgView" class="form-row" style="display: none;">

       <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
        <div class="col-lg-4">
         <div class="member-card pt-2 pb-2">
          <div class="thumb-lg member-thumb mx-auto">
           <img src="" class="rounded-circle img-thumbnail show-image" alt="teacher-image-on-create-form">
          </div>
         </div>
        </div>
       </div>

       <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;">
       </span>
      </div>

      <div class="form-row">

       <div class="form-group col-md-6">

        <label for="crTecDept">Department</label>
        <select id="crTecDept" name="crTecDept" class="form-control" required>

         <option value="Select">Select</option>
         <c:forEach items="${depNames}" var="depUp">
          <option value='<c:out value="${depUp}" />'>
           <c:out value="${depUp}" />
          </option>
         </c:forEach>
        </select>

       </div>

       <div class="form-group col-md-6">

        <label for="crTecRole">Role</label>
        <select id="crTecRole" name="crTecRole" class="form-control" required>

         <option value="Select">Select</option>
         <c:forEach items="${roleNames}" var="roleN">
          <option value='<c:out value="${roleN}" />' <c:if test="${roleN != 'teacher'}">
           <c:out value="disabled" />
  </c:if>>
  <c:if test="${roleN == 'teacher'}">
   ${roleN} &#xf058;
  </c:if>
  <c:if test="${roleN != 'teacher'}">
   ${roleN} &#xf057;
  </c:if>
  </option>
  </c:forEach>
  </select>

  </div>
  </div>

  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------- Custom URL for post-create
			   	 ------------------------------------------------------------------------------------------>
  <c:url value="../pages/ServletTeacher" var="teacherRegisterServlet">
   <c:param name="postCreate" value="create" />
   <c:param name="comingFromRegisterForm" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------/ Custom URL for post-create
			   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerTeacherRole == null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create">
    Create
   </button>
  </c:if>

  <c:if test="${registerTeacherRole != null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create" formaction="${teacherRegisterServlet}">
    Create
   </button>
  </c:if>
  </form>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">

   <c:if test="${registerTeacherRole != null && sessionScope.role_id == null}">
    <form method="get" action="../commonPages/ServletHome">
     <h1 class="pt-5">
      <label for="homeRegParam">
       Go to Register
      </label>
     </h1>
     <input id="homeRegParam" name="homepageRegisterParam" value="selected" class="d-none allView" type="submit" />
    </form>
   </c:if>

   <c:if test="${registerTeacherRole == null && sessionScope.role_id == null}">
    <form method="get" action="ServletTeacher">

     <h1 class="pt-5">
      <label for="depView01">
       Go to Teachers
      </label>
     </h1>

     <input id="depView01" name="btnView01" class="d-none allView" type="submit" />
    </form>
   </c:if>

   <c:if test="${sessionScope.role_id != null}">
    <form method="get" action="../commonPages/ServletAdmin">
     <h1 class="pt-5">
      <label for="btnView01">
       Go to Welcome Page
      </label>
     </h1>
     <input id="btnView01" name="btnView01" value="selected" class="d-none allView" type="submit" />
    </form>
   </c:if>
  </div>
  </div>
  </c:if>

  <c:if test="${editParam == 'selected'}">
   <div class="container edit-block px-0 my-5">

    <div class="container-fluid px-0">

     <c:if test="${registerTeacherRole != null || sessionScope.role_id == 1}">
      <h1 class="xbootstrap">Edit Profile</h1>
     </c:if>

     <c:if test="${registerTeacherRole == null && sessionScope.role_id == null}">
      <h1 class="xbootstrap">Edit Teacher</h1>
     </c:if>

    </div>

    <div class="container py-5 w-50 form-container float-left">
     <form method="post" action="ServletTeacher" enctype="multipart/form-data">

      <div class="form-row">

       <div class="form-group col-md-4">
        <label for="upTecFrst">First Name</label>
        <input type="text" class="form-control" id="upTecFrst" name="upTecFrst"
         value='<c:out value="${teacher.getTecFrst()}" />' required>
       </div>

       <div class="form-group col-md-4">
        <label for="upTecMid">Middle Name</label>
        <input type="text" class="form-control" id="upTecMid" name="upTecMid"
         value='<c:out value="${teacher.getTecMid()}" />'>
       </div>

       <div class="form-group col-md-4">
        <label for="upTecLast">Last Name</label>
        <input type="text" class="form-control" id="upTecLast" name="upTecLast"
         value='<c:out value="${teacher.getTecLast()}" />' required>
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="upTecMail">Email</label>
        <input type="email" class="form-control" id="upTecMail" name="upTecMail"
         value='<c:out value="${teacher.getTecMail()}" />' required>
       </div>

       <div class="form-group col-md-6">

        <label for="upTecPwd">Password</label>
        <div class="clearfix"></div>

        <input type="password" class="form-control d-flex float-left tecPwdField" id="upTecPwd" name="upTecPwd"
         value='<c:out value="${teacher.getTecPwd()}" />' required>
        <span class="pwdStatClass d-flex float-right mt-2"><i id="pwdIconTeacherUp" class="fas fa-eye"></i></span>

       </div>

      </div>

      <div class="form-row">

       <div class="form-group col-md-12">
        <label for="upTecAdd">Address</label>
        <textarea class="form-control" rows="5" id="upTecAdd" name="upTecAdd" required><c:out
										value="${teacher.getTecAdd()}" /></textarea>
       </div>

      </div>

      <div class="form-row">

       <div class="col-md-6 form-group">
        <label for="upTecGen">Gender</label>

        <select id="upTecGen" name="upTecGen" class="form-control" aria-label="Default select example" required>
         <option value="Select" <c:if test='${teacher.getTecGen() == null}'>
          <c:out value="selected" />
  </c:if>>
  Select
  </option>
  <option value="Male" <c:if test='${teacher.getTecGen() == "Male"}'>
   <c:out value="selected" />
   </c:if>>
   Male
  </option>
  <option value="Female" <c:if test='${teacher.getTecGen() == "Female"}'>
   <c:out value="selected" />
   </c:if>>
   Female
  </option>

  <option value="Transexual" <c:if test='${teacher.getTecGen() == "Transexual"}'>
   <c:out value="selected" />
   </c:if>>
   Transexual
  </option>
  <option value="Bisexual" <c:if test='${teacher.getTecGen() == "Bisexual"}'>
   <c:out value="selected" />
   </c:if>>
   Bisexual
  </option>
  <option value="Gay" <c:if test='${teacher.getTecGen() == "Gay"}'>
   <c:out value="selected" />
   </c:if>>
   Gay
  </option>
  <option value="Lesbian" <c:if test='${teacher.getTecGen() == "Lesbian"}'>
   <c:out value="selected" />
   </c:if>>
   Lesbian
  </option>
  </select>
  </div>

  <div class="form-group col-md-6">
   <label for="upTecDob">Date of Birth</label>
   <input type="date" class="form-control" id="upTecDob" name="upTecDob"
    value='<c:out value="${teacher.getTecDob()}" />' required>
  </div>
  </div>

  <div class="form-row">

   <div class="form-group col-md-4">
    <label for="upTecCont">Contact</label>
    <input type="text" class="form-control" id="upTecCont" name="upTecCont"
     value='<c:out value="${teacher.getTecCont()}" />' required>
   </div>

   <div class="form-group col-md-4">
    <label for="">Profile Picture</label>
    <label for="upTecImg" class="form-control d-flex justify-content-between pr-1">
     <span>Change image</span>
     <i class="fas fa-lg fa-camera-retro"></i>
    </label>
    <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="upTecImg" name="upTecImg"
     required />
   </div>

   <div class="form-group col-md-4">
    <label for="upTecHrs">Hours</label>
    <input type="number" class="form-control" id="upTecHrs" name="upTecHrs"
     value='<c:out value="${teacher.getTecHrs()}" />' required>
   </div>
  </div>

  <div id="upTecImgView" class="form-row" style="display: block;">

   <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
    <div class="col-lg-4">
     <div class="member-card pt-2 pb-2">
      <div class="thumb-lg member-thumb mx-auto">
       <img src="<c:url value='/resources/images/${teacher.getTecImg()}' />"
        class="rounded-circle img-thumbnail show-image" alt="teacher-image-on-edit-form">
      </div>
     </div>
    </div>
   </div>

   <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;">
   </span>
  </div>

  <div class="form-row">
   <div class="form-group col-md-6">
    <label for="upTecDept">Department</label>
    <select id="upTecDept" name="upTecDept" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${depNames}" var="depUp">
      <option value='<c:out value="${depUp}" />' <c:if
       test="${serviceTeacher.getDepNameByDepId(teacher.getTecDept()) == depUp}">
       <c:out value="selected" />
       </c:if>>
       <c:out value="${depUp}" />
      </option>
     </c:forEach>
    </select>
   </div>

   <div class="form-group col-md-6">
    <label for="upTecRole">Role</label>
    <select id="upTecRole" name="upTecRole" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${roleNames}" var="roleUp">
      <option value='<c:out value="${roleUp}" />' <c:if
       test="${serviceTeacher.getRoleNameByRoleId(teacher.getTecRole()) == roleUp}">
       <c:out value="selected" />
       </c:if>
       <c:if test="${roleUp != 'teacher'}">
        <c:out value="disabled" />
       </c:if>>
       <c:if test="${roleUp == 'teacher'}">
        ${roleUp} &#xf058;
       </c:if>
       <c:if test="${roleUp != 'teacher'}">
        ${roleUp} &#xf057;
       </c:if>
      </option>
     </c:forEach>
    </select>
   </div>
  </div>
  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------- Custom URL for post-create
			   	 ------------------------------------------------------------------------------------------>
  <c:url value="../pages/ServletTeacher" var="teacherEditProfileServlet">
   <c:param name="postUpdate" value="${teacher.getTecId()}" />
   <c:param name="comingFromNavbarAccount" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------/ Custom URL for post-create
			   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerTeacherRole == null}">
   <button name="postUpdate" value="${teacher.getTecId()}" type="submit" class="btn btn-edit">
    Update
   </button>
  </c:if>

  <c:if test="${registerTeacherRole != null}">
   <button name="postUpdate" value="${teacher.getTecId()}" type="submit" class="btn btn-edit"
    formaction="${teacherEditProfileServlet}">
    Update
   </button>
  </c:if>
  </form>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">
   <c:if test="${sessionScope.role_id == null}">
    <form method="get" action="ServletTeacher">
     <h1 class="pt-5">
      <label for="depView02">
       Go to Teachers
      </label>
     </h1>
     <input id="depView02" name="btnView02" class="d-none allView" type="submit" />
    </form>
   </c:if>

   <c:if test="${sessionScope.role_id != null}">
    <form method="get" action="../commonPages/ServletAdmin">
     <h1 class="pt-5">
      <label for="btnView02">
       Go to Welcome Page
      </label>
     </h1>
     <input id="btnView02" name="btnView02" value="selected" class="d-none allView" type="submit" />
    </form>
   </c:if>
  </div>
  </div>
  </c:if>

  <c:if test="${displayParam == 'selected'}">
   <div class="container display-block px-0 my-5">
    <div class="container-fluid px-0 d-flex justify-content-center align-items-center">
     <h1 class="xbootstrap pt-2">View Details</h1>

     <c:if test="${sessionScope.role_id == null}">
      <form method="get" action="ServletTeacher">
       <button name="btnDisplay" type="submit" value="create" class="btn btn-success ml-5">
        <h6 class="pt-1">Go to Teacher</h6>
       </button>
      </form>
     </c:if>

     <c:if test="${sessionScope.role_id != null}">
      <form method="get" action="../commonPages/ServletAdmin">
       <button name="btnView02" type="submit" value="create" class="btn btn-success ml-5">
        <h6 class="pt-1">Go to Welcome Page</h6>
       </button>
      </form>
     </c:if>
    </div>

    <div class="container px-0 d-flex justify-content-center">
     <table class="table table-striped table-dark w-50 text-left mt-5">
      <thead>
       <tr class="text-center title-view bg-primary">
        <th scope="row" colspan="2" class="pt-3">
         <h2>Teacher View</h2>
        </th>
       </tr>
      </thead>
      <tbody>
       <tr>
        <td colspan="2" class="text-center">
         <img src="<c:url value='/resources/images/${teacher.getTecImg()}' />"
          class="rounded-circle img-thumbnail show-image" alt="teacher-image-on-view-details" width="100" height="100">
        </td>
       </tr>
       <tr>
        <td scope="col">Id</td>
        <td>${teacher.getTecId()}</td>
       </tr>
       <tr>
        <td scope="col">First Name</td>
        <td>${teacher.getTecFrst()}</td>
       </tr>
       <tr>
        <td scope="col">Middle Name</td>
        <td>${teacher.getTecMid()}</td>
       </tr>
       <tr>
        <td scope="col">Last Name</td>
        <td>${teacher.getTecLast()}</td>
       </tr>

       <c:if test="${sessionScope.role_id == 1 || sessionScope.teacher_id == teacher.getTecId()}">
        <tr>
         <td scope="col">Email</td>
         <td>${teacher.getTecMail()}</td>
        </tr>
        <tr>
         <td scope="col">Password</td>
         <td>${teacher.getTecPwd()}</td>
        </tr>
       </c:if>

       <tr>
        <td scope="col">Address</td>
        <td style="height: 150px;">
         <div class="scrollable custom-scrollbar">${teacher.getTecAdd()}</div>
        </td>
       </tr>
       <tr>
        <td scope="col">Gender</td>
        <td>${teacher.getTecGen()}</td>
       </tr>
       <tr>
        <td scope="col">Date of Birth</td>
        <td>${teacher.getTecDob()}</td>
       </tr>
       <tr>
        <td scope="col">Contact</td>
        <td>${teacher.getTecCont()}</td>
       </tr>
       <tr>
        <td scope="col">Hours</td>
        <td>${teacher.getTecHrs()}</td>
       </tr>
       <tr>
        <td scope="col">Department</td>
        <td>${serviceTeacher.getDepNameByDepId(teacher.getTecDept())}</td>
       </tr>
       <tr>
        <td scope="col" class="pb-4">Role</td>
        <td class="pb-4">${serviceTeacher.getRoleNameByRoleId(teacher.getTecRole())}</td>
       </tr>
      </tbody>
     </table>
    </div>
   </div>
  </c:if>

 </jsp:body>


</t:layout>