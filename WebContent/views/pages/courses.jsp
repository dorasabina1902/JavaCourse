<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Courses"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Courses</title>
		</c:if>
		<c:if test="${createParam == 'selected'}">
			<title>Create Course</title>
		</c:if>
			<c:if test="${editParam == 'selected'}">
			<title>Edit Course</title>
		</c:if>
			<c:if test="${displayParam == 'selected'}">
			<title>Course Profile</title>
		</c:if>
			
	</jsp:attribute>

	<jsp:body>
	
	<c:if test="${createParam == null && editParam == null}">
		<div class="container default-block px-0 my-5">
			<div class="container-fluid px-0 d-flex align-items-center"> 
			
				<c:if test="${sessionScope.role_id == 3}">
					<div
							class="container d-flex justify-content-center align-items-center">
						<h1 class="xbootstrap">Courses</h1>
					</div>
				</c:if>
				
				 <c:if
						test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
				  <h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
				  <form method="get" action="ServletCourse">
					<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
						<h6 class="pt-1">Add new Course</h6>
					</button>
				  </form>
				</c:if>		
			</div>
			
			<div class="container px-0">
					<table class="table table-striped table-dark">
			  <thead class="bg-primary">
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Title</th>
			      <th scope="col">Credits</th>
			      <th scope="col">Remarks</th>
			      <th scope="col">Department</th>
			      <%-- <th scope="col">Fee Type</th>
			      <c:if
									test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
			      	<th scope="col">Options</th>
			      </c:if> --%>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${courses}" var="cos">
			  	<tr>
			  	<th scope="row">
			      	<p>${cos.getCosId()}</p>
			      	<form method="get" action="ServletCourse" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${cos.getCosId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${cos.getCosTitle()}</td>
			      <td>${cos.getCosCrd()}</td>
			      <td style="height: 150px;"><div
											class="scrollable custom-scrollbar">${cos.getCosRmk()}</div></td>
			      <td>${serviceCourse.getDepNameByDepId(cos.getCosDept())}</td>
			      <td>${serviceCourse.getFeeTypeByFeeId(cos.getCosFee())}</td>
			      
			    <c:if
										test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
			      <td>
			      	<form method="get" action="ServletCourse" class="pt-3">
			      		<button name="btnEdit" type="submit"
													value="${cos.getCosId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					</form>
			      	
			      	
			      	<form method="post" action="ServletCourse" class="pt-2">
				        <button name="postDelete" class="btn btn-delete"
													value="${cos.getCosId()}" type="submit">
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
	<div class="clearfix"></div>
	
	<c:if test="${createParam == 'selected'}">
		<div class="container create-block px-0 my-5">
			<div class="container-fluid px-0"> 
				<h1 class="xbootstrap">Add Course</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletCourse">		
					  
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crCosTitle">Title</label>
				      <input type="text" class="form-control" id="crCosTitle"
									name="crCosTitle">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crCosCrd">Credits</label>
				      <input type="number" class="form-control" id="crCosCrd"
									step="0.1" name="crCosCrd">
				    </div>
				  </div>
				  
				  <div class="form-row">
				  	<div class="form-group col-md-12">
					    <label for="crCosRmk">Remarks</label>
					    <textarea class="form-control" rows="5" id="crCosRmk"
									name="crCosRmk"></textarea>
					  </div>
				  </div>
				  
				   <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crCosDept">Course</label>
				      <select id="crCosDept" name="crCosDept" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${depNames}" var="depCr">
				      		<option value='<c:out value="${depCr}" />'>
				      			<c:out value="${depCr}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				    
				<%--     <div class="form-group col-md-6">
				      <label for="crCosFee">Fee Type</label>
				      <select id="crCosFee" name="crCosFee" class="form-control">
				      	<option value="Select">Select</option>
			      		<c:forEach items="${feeTypes}" var="feeCr">
				      		<option value='<c:out value="${feeCr}" />'>
				      			<c:out value="${feeCr}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div> --%>
				  </div>
				  
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletCourse">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Courses	
						</label>
					</h1>
					<input id="depView01" name="btnView01" class="d-none allView"
							type="submit" />
				</form>
			</div>
		</div>
	</c:if>
	<div class="clearfix"></div>
	
	<c:if test="${editParam == 'selected'}">
		<div class="container edit-block px-0 my-5">
			<div class="container-fluid px-0"> 
				<h1 class="xbootstrap">Edit Course</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletCourse">
				
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="upCosTitle">Title</label>
				      <input type="text" class="form-control" id="upCosTitle"
									name="upCosTitle"
									value='<c:out value="${course.getCosTitle()}" />'>
				    </div>
				    <div class="form-group col-md-6">
				      <label for="upCosCrd">Credits</label>
				      <input type="number" class="form-control" id="upCosCrd"
									step="0.1" name="upCosCrd"
									value='<c:out value="${course.getCosCrd()}" />'>
				    </div>
				  </div>
				  
				  <div class="form-row">
				  	<div class="form-group col-md-12">
					    <label for="upCosRmk">Remarks</label>
					    <textarea class="form-control" rows="5" id="upCosRmk"
									name="upCosRmk"><c:out value="${course.getCosRmk()}" /></textarea>
					  </div>
				  </div>
			  
				   <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="upCosDept">Course</label>
				      <select id="upCosDept" name="upCosDept" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${depNames}" var="depUp">
				      		<option value='<c:out value="${depUp}" />'
											<c:if test="${serviceCourse.getDepNameByDepId(course.getCosDept()) == depUp}">
												<c:out value="selected" />
											</c:if>>
								<c:out value="${depUp}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				    
				    <div class="form-group col-md-6">
				      <label for="upCosFee">Fee Type</label>
				      <select id="upCosFee" name="upCosFee" class="form-control">
				      	<option value="Select">Select</option>
			      		<c:forEach items="${feeTypes}" var="feeUp">					
							<option value='<c:out value="${feeUp}" />'
											<c:if test="${serviceCourse.getFeeTypeByFeeId(course.getCosFee()) == feeUp}">
												<c:out value="selected" />
											</c:if>>
				      			<c:out value="${feeUp}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				  </div>
			  
				  <button name="postUpdate"
							value='<c:out value= "${course.getCosId()}"/>' type="submit"
							class="btn btn-edit">Update</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletCourse">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Courses	
						</label>
					</h1>
					<input id="depView02" name="btnView02" class="d-none allView"
							type="submit" />
				</form>
			</div>
		</div>
	</c:if>
	<div class="clearfix"></div>
	
		<c:if test="${displayParam == 'selected'}">
		<div class="container display-block px-0 my-5">
			<div
					class="container-fluid px-0 d-flex justify-content-center align-items-center"> 
				<h1 class="xbootstrap pt-2">View Details</h1>
				<form method="get" action="ServletCourse">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Course</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Course View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${course.getCosId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Title</td>
				      <td>${course.getCosTitle()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Credits</td>
				      <td>${course.getCosCrd()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Remarks</td>
				      <td style="height: 150px;"><div
										class="scrollable custom-scrollbar">${course.getCosRmk()}</div></td>
				    </tr>
				    <tr>
				      <td scope="col">Department</td>
				      <td>${serviceCourse.getCosNameByDepId(course.getCosDept())}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Fee Type</td>
				      <td class="pb-4">${serviceCourse.getCosNameByDepId(course.getCosFee())}</td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	
	</jsp:body>


</t:layout>



