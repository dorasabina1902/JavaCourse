<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if test="${sessionScope.role_id == null && registerStudentRole == null}">
 <c:redirect url="/views/commonPages/ServletHome">
  <c:param name="homepageAccessParam" value="selected"></c:param>
  <c:param name="pageTitle" value="Students"></c:param>
 </c:redirect>
</c:if>

<t:layout>
 <jsp:attribute name="title">

  <c:if test="${createParam == null && editParam == null && displayParam == null && registerStudentRole == null}">
   <title>Students</title>
  </c:if>
  <c:if test="${createParam == 'selected' && registerStudentRole != null}">
   <title>Register Student</title>
  </c:if>
  <c:if test="${createParam == 'selected' && registerStudentRole == null}">
   <title>Create Student</title>
  </c:if>
  <c:if test="${editParam == 'selected' && registerStudentRole != null}">
   <title>Edit Profile</title>
  </c:if>
  <c:if test="${editParam == 'selected' && registerStudentRole == null}">
   <title>Edit Student</title>
  </c:if>
  <c:if test="${createParam == null && editParam == null && displayParam == 'selected'}">
   <title>Student Profile</title>
  </c:if>

 </jsp:attribute>

 <jsp:body>

  <c:if test="${createParam == null && editParam == null && displayParam == null && registerStudentRole == null}">
   <div class="container default-block px-0 my-5">
    <div class="container-fluid px-0 d-flex align-items-center">

     <c:if test="${sessionScope.role_id == 3}">
      <div class="container d-flex justify-content-center align-items-center">
       <h1 class="xbootstrap">Students</h1>
      </div>
     </c:if>

     <c:if test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
      <h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
      <form method="get" action="ServletStudent">
       <button name="btnCreate" type="submit" value="create" class="ml-5 btn btn-success float-left">
        <h6 class="pt-1">Add new Student</h6>
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
        <th scope="col">Student</th>
        <th scope="col">Grade</th>
        <th scope="col">Role</th>
        <th scope="col">Section</th>
        <c:if test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
         <th scope="col">Options</th>
        </c:if>
       </tr>
      </thead>
      <tbody>
       <c:forEach items="${students}" var="std">
        <tr>
         <th scope="row">
          <p>${std.getStdId()}</p>
          <form method="get" action="ServletStudent" class="">
           <button type="submit" name="btnDisplay" class="btn btn-info" value="${std.getStdId()}">
            <i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
           </button>
          </form>
         </th>
         <td>${std.getStdFrst()} ${std.getStdMid()} ${std.getStdLast()}</td>
         <td style="height: 150px;">
          <div class="scrollable custom-scrollbar">${std.getStdAdd()}</div>
         </td>
         <td>${serviceStudent.getDepNameByDepId(std.getStdDept())}</td>
         <td>${serviceStudent.getGradeByGradeId(std.getStdGrad())}</td>
         <td>${serviceStudent.getRoleNameByRoleId(std.getStdRole())}</td>
         <td>${serviceStudent.getSectionBySecId(std.getStdSect())}</td>


         <c:if test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
          <td>
           <form method="get" action="ServletStudent" class="pt-1">
            <button name="btnEdit" type="submit" value="${std.getStdId()}" class="btn btn-edit">
             <i class="fas fa-lg fa-edit"></i>
            </button>
           </form>


           <form method="post" action="ServletStudent" class="pt-2">
            <button name="postDelete" class="btn btn-delete" value="${std.getStdId()}" type="submit">
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
   <div class="clearfix"></div>
  </c:if>


  <c:if test="${createParam == 'selected'}">
   <div class="container create-block px-0 my-5">
    <div class="container-fluid px-0">

     <c:if test="${registerStudentRole != null || sessionScope.role_id == 1 || sessionScope.role_id == 2}">
      <h1 class="xbootstrap">Register Student</h1>
     </c:if>

     <c:if test="${registerStudentRole == null && sessionScope.role_id == null}">
      <h1 class="xbootstrap">Add Student</h1>
     </c:if>

    </div>

    <div class="container py-5 w-50 form-container float-left">

     <form method="post" action="ServletStudent" enctype="multipart/form-data">
      <div class="form-row">
       <div class="form-group col-md-4">
        <label for="crStdFrst">First Name</label>
        <input type="text" class="form-control" id="crStdFrst" name="crStdFrst" required>
       </div>
       <div class="form-group col-md-4">
        <label for="crStdMid">Middle Name</label>
        <input type="text" class="form-control" id="crStdMid" name="crStdMid">
       </div>
       <div class="form-group col-md-4">
        <label for="crStdLast">Last Name</label>
        <input type="text" class="form-control" id="crStdLast" name="crStdLast" required>
       </div>
      </div>
      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="crStdMail">Email</label>
        <input type="email" class="form-control" id="crStdMail" name="crStdMail">
       </div>
       <div class="form-group col-md-6">
        <label for="crStdPwd">Password</label>
        <input type="password" class="form-control d-flex float-left stdPwdField" id="crStdPwd" name="crStdPwd"
         required>
        <span class="pwdStatClass d-flex float-right mt-2"><i id="pwdIconStudentCr" class="fas fa-eye"></i></span>
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-12">
        <label for="crStdAdd">Address</label>
        <textarea class="form-control" rows="5" id="crStdAdd" name="crStdAdd"></textarea>
       </div>
      </div>

      <div class="form-row">

       <div class="col-md-6 form-group">
        <label for="crStdGen">Gender</label>

        <select id="crStdGen" name="crStdGen" class="form-control" aria-label="Default select example" required>
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
        <label for="crStdDob">Date of Birth</label>
        <input type="date" class="form-control" id="crStdDob" name="crStdDob" required>
       </div>

      </div>

      <div class="form-row">

       <div class="form-group col-md-4">
        <label for="crStdCont">Contact</label>
        <input type="text" class="form-control" id="crStdCont" name="crStdCont" required>
       </div>

       <div class="form-group col-md-4">
        <label for="">Profile Picture</label>
        <label for="crStdImg" class="form-control d-flex justify-content-between pr-1">
         <span>Choose File</span>
         <i class="fas fa-lg fa-camera-retro"></i>
        </label>
        <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="crStdImg" name="crStdImg"
         required />
       </div>

       <div class="form-group col-md-4">
        <label for="crStdAttd">Attendance</label>
        <select id="crStdAttd" name="crStdAttd" class="form-control" aria-label="Default select example">
         <option value="Select">
          Select
         </option>
         <option value="Less than 20%">
          Less than 20%
         </option>
         <option value="Less than 50%">
          Less than 50%
         </option>
         <option value="(50 - 70)%">
          (50 - 70)%
         </option>
         <option value="(70 - 80)%">
          (70 - 80)%
         </option>
         <option value="(80 - 90)%">
          (80 - 90)%
         </option>
         <option value="More than 90%">
          More than 90%
         </option>
        </select>
       </div>

      </div>

      <div id="crStdImgView" class="form-row" style="display: none;">

       <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
        <div class="col-lg-4">
         <div class="member-card pt-2 pb-2">
          <div class="thumb-lg member-thumb mx-auto">
           <img src="" class="rounded-circle img-thumbnail show-image" alt="student-image-on-create-form">
          </div>
         </div>
        </div>
       </div>

       <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;">
       </span>
      </div>

      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="crStdDept">Department</label>
        <select id="crStdDept" name="crStdDept" class="form-control" required>
         <option value="Select">Select</option>
         <c:forEach items="${depNames}" var="depCr">
          <option value='<c:out value="${depCr}" />'>
           <c:out value="${depCr}" />
          </option>
         </c:forEach>
        </select>
       </div>

       <div class="form-group col-md-6">
        <label for="crStdGrad">Grade</label>
        <select id="crStdGrad" name="crStdGrad" class="form-control" required>
         <option value="Select">Select</option>
         <c:forEach items="${gradeScores}" var="gradeCr">
          <option value='<c:out value="${gradeCr}" />'>
           <c:out value="${gradeCr}" />
          </option>
         </c:forEach>
        </select>
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-6">
        <label for="crStdRole">Role</label>
        <select id="crStdRole" name="crStdRole" class="form-control" required>
         <option value="Select">Select</option>
         <c:forEach items="${roleNames}" var="roleCr">
          <option value='${roleCr}' <c:if test="${roleCr != 'student'}">
           <c:out value="disabled" />
  </c:if>>
  <c:if test="${roleCr == 'student'}">
   ${roleCr} &#xf058;
  </c:if>
  <c:if test="${roleCr != 'student'}">
   ${roleCr} &#xf057;
  </c:if>
  </option>
  </c:forEach>
  </select>
  </div>

  <div class="form-group col-md-6">
   <label for="crStdSect">Section</label>
   <select id="crStdSect" name="crStdSect" class="form-control" required>
    <option value="Select">Select</option>
    <c:forEach items="${sectionRooms}" var="secCr">
     <option value='<c:out value="${secCr}" />'>
      <c:out value="${secCr}" />
     </option>
    </c:forEach>
   </select>
  </div>
  </div>

  <!------------------------------------------------------------------------------------------
		   	 ---------------------------------------------------------------- Custom URL for post-create
		   	 ------------------------------------------------------------------------------------------>
  <c:url value="ServletStudent" var="studentRegisterServlet">
   <c:param name="postCreate" value="create" />
   <c:param name="registerStudentRole" value="selected" />
   <c:param name="comingFromRegisterForm" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
		   	 ---------------------------------------------------------------/ Custom URL for post-create
		   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerStudentRole == null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create">
    Create
   </button>
  </c:if>

  <c:if test="${registerStudentRole != null}">
   <button name="postCreate" value="create" type="submit" class="btn btn-create" formaction="${studentRegisterServlet}">
    Create
   </button>
  </c:if>

  </form>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">

   <c:if test="${registerStudentRole != null && sessionScope.role_id == null}">
    <form method="get" action="../commonPages/ServletHome">
     <h1 class="pt-5">
      <label for="homeRegParam">
       Go to Register
      </label>
     </h1>
     <input id="homeRegParam" name="homepageRegisterParam" value="selected" class="d-none allView" type="submit" />
    </form>
   </c:if>

   <c:if test="${registerStudentRole == null && sessionScope.role_id == null}">
    <form method="get" action="ServletStudent">
     <h1 class="pt-5">
      <label for="depView01">
       Go to Students
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
  <div class="clearfix"></div>
  </c:if>


  <c:if test="${editParam == 'selected'}">
   <div class="container edit-block px-0 my-5">
    <div class="container-fluid px-0">

     <c:if test="${registerStudentRole != null}">
      <h1 class="xbootstrap">Edit Profile</h1>
     </c:if>

     <c:if test="${registerStudentRole == null}">
      <h1 class="xbootstrap">Edit Student</h1>
     </c:if>
    </div>

    <div class="container py-5 w-50 form-container float-left">
     <form method="post" action="ServletStudent" enctype="multipart/form-data">
      <div class="form-row">
       <div class="form-group col-md-4">
        <label for="upStdFrst">First Name</label>
        <input type="text" class="form-control" id="upStdFrst" name="upStdFrst"
         value='<c:out value="${student.getStdFrst()}" />'>
       </div>
       <div class="form-group col-md-4">
        <label for="upStdMid">Middle Name</label>
        <input type="text" class="form-control" id="upStdMid" name="upStdMid"
         value='<c:out value="${student.getStdMid()}" />'>
       </div>
       <div class="form-group col-md-4">
        <label for="upStdLast">Middle Name</label>
        <input type="text" class="form-control" id="upStdLast" name="upStdLast"
         value='<c:out value="${student.getStdLast()}" />'>
       </div>
      </div>
      <div class="form-row">

       <div class="form-group col-md-6">
        <label for="upStdMail">Email</label>
        <input type="email" class="form-control" id="upStdMail" name="upStdMail"
         value='<c:out value="${student.getStdMail()}" />'>
       </div>

       <div class="form-group col-md-6">
        <label for="upStdPwd">Password</label>

        <input type="password" class="form-control d-flex float-left stdPwdField" id="upStdPwd" name="upStdPwd"
         value='<c:out value="${student.getStdPwd()}" />'>
        <span class="pwdStatClass d-flex float-right mt-2"><i id="pwdIconStudentUp" class="fas fa-eye"></i></span>
       </div>
      </div>

      <div class="form-row">
       <div class="form-group col-md-12">
        <label for="upStdAdd">Address</label>
        <textarea class="form-control" rows="5" id="upStdAdd" name="upStdAdd"><c:out
										value="${student.getStdAdd()}" /></textarea>
       </div>
      </div>

      <div class="form-row">

       <div class="col-md-6 form-group">
        <label for="upStdGen">Gender</label>

        <select id="upStdGen" name="upStdGen" class="form-control" aria-label="Default select example">
         <option value="Select" <c:if test='${student.getStdGen() == null}'>
          <c:out value="selected" />
  </c:if>>
  Select
  </option>
  <option value="Male" <c:if test='${student.getStdGen() == "Male"}'>
   <c:out value="selected" />
   </c:if>>
   Male
  </option>
  <option value="Female" <c:if test='${student.getStdGen() == "Female"}'>
   <c:out value="selected" />
   </c:if>>
   Female
  </option>
  <option value="Transexual" <c:if test='${student.getStdGen() == "Transexual"}'>
   <c:out value="selected" />
   </c:if>>
   Transexual
  </option>
  <option value="Bisexual" <c:if test='${student.getStdGen() == "Bisexual"}'>
   <c:out value="selected" />
   </c:if>>
   Bisexual
  </option>
  <option value="Gay" <c:if test='${student.getStdGen() == "Gay"}'>
   <c:out value="selected" />
   </c:if>>
   Gay
  </option>
  <option value="Lesbian" <c:if test='${student.getStdGen() == "Lesbian"}'>
   <c:out value="selected" />
   </c:if>>
   LESBIAN
  </option>

  </select>
  </div>
  <div class="form-group col-md-6">
   <label for="upStdDob">Date of Birth</label>
   <input type="date" class="form-control" id="upStdDob" name="upStdDob"
    value='<c:out value="${student.getStdDob()}" />' required>
  </div>
  </div>

  <div class="form-row">
   <div class="form-group col-md-4">
    <label for="upStdCont">Contact</label>
    <input type="text" class="form-control" id="upStdCont" name="upStdCont"
     value='<c:out value="${student.getStdCont()}" />' required>
   </div>

   <div class="form-group col-md-4">
    <label for="">Profile Picture</label>
    <label for="upStdImg" class="form-control d-flex justify-content-between pr-1">
     <span>Change image</span>
     <i class="fas fa-lg fa-camera-retro"></i>
    </label>
    <input type="file" class="form-control d-none" onchange="showImageChoice(this)" id="upStdImg" name="upStdImg"
     required />
   </div>

   <div class="form-group col-md-4">
    <label for="upStdAttd">Attendance</label>
    <select id="upStdAttd" name="upStdAttd" class="form-control" aria-label="Default select example" required>
     <option value="Select" <c:if test='${student.getStdAttd() == null}'>
      <c:out value="selected" />
      </c:if>>
      Select
     </option>
     <option value="Less than 20%" <c:if test='${student.getStdAttd() == "Less than 20%"}'>
      <c:out value="selected" />
      </c:if>>
      Less than 20%
     </option>
     <option value="Less than 50%" <c:if test='${student.getStdAttd() == "Less than 50%"}'>
      <c:out value="selected" />
      </c:if>>
      Less than 50%
     </option>
     <option value="(50 - 70)%" <c:if test='${student.getStdAttd() == "(50 - 70)%"}'>
      <c:out value="selected" />
      </c:if>>
      (50 - 70)%
     </option>
     <option value="(70 - 80)%" <c:if test='${student.getStdAttd() == "(70 - 80)%"}'>
      <c:out value="selected" />
      </c:if>>
      (70 - 80)%
     </option>
     <option value="(80 - 90)%" <c:if test='${student.getStdAttd() == "(80 - 90)%"}'>
      <c:out value="selected" />
      </c:if>>
      (80 - 90)%
     </option>
     <option value="More than 90%" <c:if test='${student.getStdAttd() == "More than 90%"}'>
      <c:out value="selected" />
      </c:if>>
      More than 90%
     </option>
    </select>
   </div>
  </div>

  <div id="upStdImgView" class="form-row" style="display: block;">

   <div class="form-group col-md-12 d-flex justify-content-center align-items-center">
    <div class="col-lg-4">
     <div class="member-card pt-2 pb-2">
      <div class="thumb-lg member-thumb mx-auto">
       <img src="<c:url value='/resources/images/${student.getStdImg()}' />"
        class="rounded-circle img-thumbnail show-image" alt="student-image-on-edit-form">
      </div>
     </div>
    </div>
   </div>

   <span class="text-danger bg-white error-message px-3 py-2 form-control mb-3" style="display: none;">
   </span>
  </div>

  <div class="form-row">
   <div class="form-group col-md-6">
    <label for="upStdDept">Department</label>
    <select id="upStdDept" name="upStdDept" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${depNames}" var="depUp">
      <option value='<c:out value="${depUp}" />' <c:if
       test="${serviceStudent.getDepNameByDepId(student.getStdDept()) == depUp}">
       <c:out value="selected" />
       </c:if>>
       <c:out value="${depUp}" />
      </option>
     </c:forEach>
    </select>
   </div>

   <div class="form-group col-md-6">
    <label for="upStdGrad">Grade</label>
    <select id="upStdGrad" name="upStdGrad" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${gradeScores}" var="gradeUp">
      <option value='<c:out value="${gradeUp}" />' <c:if
       test="${serviceStudent.getGradeByGradeId(student.getStdGrad()) == gradeUp}">
       <c:out value="selected" />
       </c:if>>
       <c:out value="${gradeUp}" />
      </option>
     </c:forEach>
    </select>
   </div>
  </div>

  <div class="form-row">
   <div class="form-group col-md-6">
    <label for="upStdRole">Role</label>
    <select id="upStdRole" name="upStdRole" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${roleNames}" var="roleUp">
      <option value='<c:out value="${roleUp}" />' <c:if
       test="${serviceStudent.getRoleNameByRoleId(student.getStdRole()) == roleUp}">
       <c:out value="selected" />
       </c:if>
       <c:if test="${roleUp != 'student'}">
        <c:out value="disabled" />
       </c:if>>
       <c:if test="${roleUp == 'student'}">
        ${roleUp} &#xf058;
       </c:if>
       <c:if test="${roleUp != 'student'}">
        ${roleUp} &#xf057;
       </c:if>
      </option>
     </c:forEach>
    </select>
   </div>

   <div class="form-group col-md-6">
    <label for="upStdSect">Section</label>
    <select id="upStdSect" name="upStdSect" class="form-control" required>
     <option value="Select">Select</option>
     <c:forEach items="${sectionRooms}" var="secUp">
      <option value='<c:out value="${secUp}" />' <c:if
       test="${serviceStudent.getSectionBySecId(student.getStdSect()) == secUp}">
       <c:out value="selected" />
       </c:if>>
       <c:out value="${secUp}" />
      </option>
     </c:forEach>
    </select>
   </div>
  </div>

  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------- Custom URL for post-update
			   	 ------------------------------------------------------------------------------------------>
  <c:url value="../pages/ServletStudent" var="studentEditProfileServlet">
   <c:param name="postUpdate" value="${student.getStdId()}" />
   <c:param name="comingFromNavbarAccount" value="selected" />
  </c:url>
  <!------------------------------------------------------------------------------------------
			   	 ---------------------------------------------------------------/ Custom URL for post-update
			   	 ------------------------------------------------------------------------------------------>

  <c:if test="${registerStudentRole == null}">
   <button name="postUpdate" value="${student.getStdId()}" type="submit" class="btn btn-edit">
    Update
   </button>
  </c:if>

  <c:if test="${registerStudentRole != null}">
   <button name="postUpdate" value="${student.getStdId()}" type="submit" class="btn btn-edit"
    formaction="${studentEditProfileServlet}">
    Update
   </button>
  </c:if>
  </form>
  </div>

  <div class="container-fluid py-5 w-50 h-100 float-right mt-5">

   <c:if test="${sessionScope.role_id == null}">
    <form method="get" action="ServletStudent">
     <h1 class="pt-5">
      <label for="depView02">
       Go to Students
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
      <form method="get" action="ServletStudent">
       <button name="depView01" type="submit" value="create" class="btn btn-success ml-5">
        <h6 class="pt-1">Go to Student</h6>
       </button>
      </form>
     </c:if>

     <c:if test="${sessionScope.role_id != null}">
      <form method="get" action="../commonPages/ServletLogin">
       <button name="btnView01" type="submit" value="create" class="btn btn-success ml-5">
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
         <h2>Student View</h2>
        </th>
       </tr>
      </thead>
      <tbody>
       <tr>
        <td colspan="2" class="text-center">
         <img src="<c:url value='/resources/images/${student.getStdImg()}' />"
          class="rounded-circle img-thumbnail show-image" alt="student-image-on-view-details" width="100" height="100">
        </td>
       </tr>
       <tr>
        <td scope="col">Id</td>
        <td>${student.getStdId()}</td>
       </tr>
       <tr>
        <td scope="col">First Name</td>
        <td>${student.getStdFrst()}</td>
       </tr>
       <tr>
        <td scope="col">Middle Name</td>
        <td>${student.getStdMid()}</td>
       </tr>
       <tr>
        <td scope="col">Last Name</td>
        <td>${student.getStdLast()}</td>
       </tr>

       <c:if test="${sessionScope.role_id == 1 || sessionScope.student_id == student.getStdId()}">
        <tr>
         <td scope="col">Email</td>
         <td>${student.getStdMail()}</td>
        </tr>
        <tr>
         <td scope="col">Password</td>
         <td>${student.getStdPwd()}</td>
        </tr>
       </c:if>

       <tr>
        <td scope="col">Address</td>
        <td style="height: 150px;">
         <div class="scrollable custom-scrollbar">${student.getStdAdd()}</div>
        </td>
       </tr>
       <tr>
        <td scope="col">Gender</td>
        <td>${student.getStdGen()}</td>
       </tr>
       <tr>
        <td scope="col">Date of Birth</td>
        <td>${student.getStdDob()}</td>
       </tr>
       <tr>
        <td scope="col">Contact</td>
        <td>${student.getStdCont()}</td>
       </tr>
       <tr>
        <td scope="col">Attendance</td>
        <td>${student.getStdAttd()}</td>
       </tr>
       <tr>
        <td scope="col">Department</td>
        <td>${serviceStudent.getDepNameByDepId(student.getStdDept())}</td>
       </tr>
       <tr>
        <td scope="col">Grade</td>
        <td>${serviceStudent.getGradeByGradeId(student.getStdGrad())}</td>
       </tr>
       <tr>
        <td scope="col">Role</td>
        <td>${serviceStudent.getRoleNameByRoleId(student.getStdRole())}</td>
       </tr>
       <tr>
        <td scope="col" class="pb-4">Section</td>
        <td class="pb-4">${serviceStudent.getSectionBySecId(student.getStdSect())}</td>
       </tr>
      </tbody>
     </table>
    </div>
   </div>
   <div class="clearfix"></div>

  </c:if>


 </jsp:body>


</t:layout>