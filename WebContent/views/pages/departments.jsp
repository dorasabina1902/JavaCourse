<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Departments"></c:param>
	</c:redirect>
</c:if>

<c:if
	test='{session.getAttribute("firstName") == null && session.getAttribute("role_id") == null}'>
	<c:redirect url="/views/commonPages/ServletLogin">
		<c:param name="firstLoginParam" value="true"></c:param>
	</c:redirect>
</c:if>


<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Departments</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Department</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Department</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Department Profile</title>
		</c:if>
			
	</jsp:attribute>

	<jsp:body>
	
	<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
		<div class="container default-block px-0 my-5">
			<div class="container-fluid px-0 d-flex align-items-center"> 
			
				<c:if
						test="${sessionScope.role_id == 2 || sessionScope.role_id == 3}">
					<div
							class="container d-flex justify-content-center align-items-center">
						<h1 class="xbootstrap">Departments</h1>
					</div>
				</c:if>
				
				 <c:if test="${sessionScope.role_id == 1}">
				  	<h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
					<form method="get" action="ServletDepartment">
						<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
							<h6 class="pt-1">Add new Department</h6>
						</button>
					</form>
				</c:if>
			</div>
			
			<div class="container px-0">
				<table class="table table-striped table-dark">
				  <thead class="bg-primary">
				    <tr>
				      <th scope="col">Id</th>
				      <th scope="col">Department Name</th>
				      <th scope="col">Number of teachers</th>
				      <th scope="col">Description</th>
				      
			      <c:if test="${sessionScope.role_id == 1}">
			      	<th scope="col">Options</th>
			      </c:if>
				    </tr>
				  </thead>
				  <tbody>
				  <c:forEach items="${departments}" var="dep">
				  	<tr>
				      <th scope="row">
				      	<p>${dep.getDepId()}</p>
				      	<form method="get" action="ServletDepartment" class="">
							<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${dep.getDepId()}">
								<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
							</button>
						</form>
				      </th>
				      <td>${dep.getDepName()}</td>
				      <td>${dep.getDepTech()}</td>
				      <td style="height: 150px;"><div
											class="scrollable custom-scrollbar">${dep.getDepDesc()}</div></td>
				     <c:if test="${sessionScope.role_id == 1}">
				      <td>
				      
				      	<form method="get" action="ServletDepartment" class="pt-3">
				      		<button name="btnEdit" type="submit"
													value="${dep.getDepId()}" class="btn btn-edit">
													<i class="fas fa-lg fa-edit"></i>
												</button>
						</form>
				      	
				      	
				      	<form method="post" action="ServletDepartment" class="pt-2">
							<button type="submit" name="postDelete" class="btn btn-delete"
													value="${dep.getDepId()}">
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
	
	<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
		<div class="container create-block px-0 my-5">
			<div class="container-fluid px-0"> 
				<h1 class="xbootstrap">Add Department</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletDepartment">
				
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crDepName">Name</label>
				      <input type="text" class="form-control" id="crDepName"
									name="crDepName">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crDepTech">Number of teachers</label>
				      <input type="number" class="form-control" id="crDepTech"
									name="crDepTech">
				    </div>
				  </div>
				  
				  <div class="form-row">
				  	<div class="form-group col-md-12">
					    <label for="crDepDesc">Description</label>
					    <textarea class="form-control" rows="5" id="crDepDesc"
									name="crDepDesc"> </textarea>
					  </div>
				  </div>
				  
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletDepartment">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Departments	
						</label>
					</h1>
					<input id="depView01" name="btnView01" class="d-none allView"
							type="submit" />
				</form>
			</div>
		</div>
	</c:if>
	<div class="clearfix"></div>
	
	<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
		<div class="container edit-block px-0 my-5">
			<div class="container-fluid px-0"> 
				<h1 class="xbootstrap">Edit Department</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletDepartment">
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="upDepName">Name</label>
				      <input type="text" class="form-control" id="upDepName"
									name="upDepName"
									value="<c:out value= "${department.getDepName()}"/>">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="upDepTech">Number of teachers</label>
				      <input type="number" class="form-control" id="upDepTech"
									name="upDepTech"
									value="<c:out value= "${department.getDepTech()}"/>">
				    </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-12">
					    <label for="upDepDesc">Description</label>
					    <textarea class="form-control" rows="5" id="upDepDesc"
									name="upDepDesc"><c:out
										value="${department.getDepDesc()}" /></textarea>
					  </div>
				  </div>
				  
				  <button name="postUpdate"
							value="<c:out value= "${department.getDepId()}"/>" type="submit"
							class="btn btn-edit">Update</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletDepartment">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Departments	
						</label>
					</h1>
					<input id="depView02" name="btnView02" class="d-none allView"
							type="submit" />
				</form>
			</div>
		</div>
	</c:if>
	<div class="clearfix"></div>
	
	<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
		<div class="container display-block px-0 my-5">
			<div
					class="container-fluid px-0 d-flex justify-content-center align-items-center"> 
				<h1 class="xbootstrap pt-2">View Details</h1>
				<form method="get" action="ServletDepartment">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Department</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table
						class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Department View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${department.getDepId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Name</td>
				      <td>${department.getDepName()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Number of teachers</td>
				      <td>${department.getDepTech()}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Description</td>
				      <td style="height: 150px;" class="pb-4"><div
										class="scrollable custom-scrollbar">${department.getDepDesc()}</div></td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	
	</jsp:body>


</t:layout>



