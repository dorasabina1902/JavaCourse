<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Assignments"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Assignments</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Assignment</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Assignment</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Assignment Profile</title>
		</c:if>
			
	</jsp:attribute>

	<jsp:body>
	
	<c:if test="${createParam == null && editParam == null}">
		<div class="container default-block px-0 my-5">
			<div class="container-fluid px-0 d-flex align-items-center"> 
			
				<c:if test="${sessionScope.role_id == 3}">
					<div
							class="container d-flex justify-content-center align-items-center">
						<h1 class="xbootstrap">Assignments</h1>
					</div>
				</c:if>
				
				 <c:if
						test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
				  <h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
				  <form method="get" action="ServletAssignment">
					<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
						<h6 class="pt-1">Add new Assignment</h6>
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
			      <th scope="col">Release Date</th>
			      <th scope="col">Deadline</th>
			      <th scope="col">Skill Level</th>
			      <th scope="col">Assignment</th>
			      <th scope="col">Teacher</th>
			      <c:if
									test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
			      	<th scope="col">Options</th>
			      </c:if>
			    
							</tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${assignments}" var="asg">
			  	<tr>
		          <th scope="row">
			      	<p>${asg.getAsgId()}</p>
			      	<form method="get" action="ServletAssignment" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${asg.getAsgId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${asg.getAsgTitle()}</td>
			      <td>${asg.getAsgRels()}</td>
			      <td>${asg.getAsgDead()}</td>
			      <td>${asg.getAsgLvl()}</td>
			      <td>${serviceAssignment.getDepNameByDepId(asg.getAsgDept())}</td>
			      <td>${serviceAssignment.getTechNameByTechId(asg.getAsgTech())}</td>
			      
			    <c:if
										test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
			      <td>
			      	<form method="get" action="ServletAssignment" class="pt-1">
			      		<button name="btnEdit" type="submit"
													value="${asg.getAsgId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					</form>
			      	
			      	
			      	<form method="post" action="ServletAssignment" class="pt-2">
				      	<button name="postDelete" class="btn btn-delete"
													value="${asg.getAsgId()}" type="submit">
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
				<h1 class="xbootstrap">Add Assignment</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletAssignment">		
					  
				  <div class="form-row">
				    <div class="form-group col-md-12">
				      <label for="crAsgTitle">Title</label>
				      <input type="text" class="form-control" id="crAsgTitle"
									name="crAsgTitle">
				    </div>
				    
				  </div>
				  
				  <div class="form-row">
					 <div class="form-group col-md-6">
				      <label for="crAsgRls">Release Date</label>
				      <input type="date" class="form-control" id="crAsgRls"
									name="crAsgRls">
				    </div>
				    <div class="form-group col-md-6">
					    <label for="crAsgDead">Deadline</label>
					    <input type="date" class="form-control" id="crAsgDead"
									name="crAsgDead">
					  </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-12">
					    <label for="crAsgLvl">Skill Level</label>
					    <select id="crAsgLvl" name="crAsgLvl" class="form-control"
									aria-label="Default select example">
						  <option selected>Select</option>
						  <option value="A">A</option>
						  <option value="B">B</option>
						  <option value="C">C</option>
						  <option value="D">D</option>
						  <option value="E">E</option>
						</select>
					  </div>
				  </div>
				  
				  <div class="form-row">
				   <div class="form-group col-md-6">
				      <label for="crAsgDept">Assignment</label>
				      <select id="crAsgDept" name="crAsgDept" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${depNames}" var="depCr">
				      		<option value='<c:out value="${depCr}" />'>
				      			<c:out value="${depCr}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				    
				    <div class="form-group col-md-6">
				      <label for="crAsgTech">Teacher</label>
				      <select id="crAsgTech" name="crAsgTech" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${techNames}" var="techUp">
				      		<option value='<c:out value="${techUp}" />'>
				      			<c:out value="${techUp}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				  </div>
				  
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletAssignment">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Assignments	
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
				<h1 class="xbootstrap">Edit Assignment</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletAssignment">
				
				   <div class="form-row">
				    <div class="form-group col-md-12">
				      <label for="upAsgTitle">Title</label>
				      <input type="text" class="form-control" id="upAsgTitle"
									name="upAsgTitle"
									value='<c:out value="${assignment.getAsgTitle()}" />'>
				    </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-6">
					      <label for="upAsgRels">Release Date</label>
					      <input type="date" class="form-control" id="upAsgRels"
									name="upAsgRels"
									value='<c:out value="${assignment.getAsgRels()}" />'>
				      </div>
					  <div class="form-group col-md-6">
					    <label for="upAsgDead">Deadline</label>
					    <input type="date" class="form-control" id="upAsgDead"
									name="upAsgDead"
									value='<c:out value="${assignment.getAsgDead()}" />'>
					  </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-12">
					    <label for="upAsgLvl">Skill Level</label>
					    <select id="upAsgLvl" name="upAsgLvl" class="form-control"
									aria-label="Default select example">
						  <option value="Select"
										<c:if test='${assignment.getAsgLvl() == null}'><c:out value="selected" /></c:if>>
						  	Select
					  	  </option>
						  <option value="A"
										<c:if test='${assignment.getAsgLvl() == "A"}'><c:out value="selected" /></c:if>>
							A
						 </option>
						 <option value="B"
										<c:if test='${assignment.getAsgLvl() == "B"}'><c:out value="selected" /></c:if>>
							B
						 </option>
						  
						<option value="V"
										<c:if test='${assignment.getAsgLvl() == "C"}'><c:out value="selected" /></c:if>>
							C
						 </option>
						 <option value="D"
										<c:if test='${assignment.getAsgLvl() == "D"}'><c:out value="selected" /></c:if>>
							D
						 </option>
						 <option value="E"
										<c:if test='${assignment.getAsgLvl() == "E"}'><c:out value="selected" /></c:if>>
							E
						 </option>
						</select>
					  </div>
				  </div>
				  
				  <div class="form-row">
				   <div class="form-group col-md-6">
				      <label for="upAsgDept">Assignment</label>
				      <select id="upAsgDept" name="upAsgDept" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${depNames}" var="depUp">
				      		<option value='<c:out value="${depUp}" />'
											<c:if test="${serviceAssignment.getDepNameByDepId(assignment.getAsgDept()) == depUp}">
												<c:out value="selected" />
											</c:if>>
				      			<c:out value="${depUp}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				    
				    <div class="form-group col-md-6">
				      <label for="upAsgTech">Teacher</label>
				      <select id="upAsgTech" name="upAsgTech" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${techNames}" var="techUp">
				      		<option value='<c:out value="${techUp}" />'
											<c:if test="${serviceAssignment.getTechNameByTechId(assignment.getAsgTech()) == techUp}">
												<c:out value="selected" />
											</c:if>>
				      			<c:out value="${techUp}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>			    	    
				  </div>
			  
				  <button name="postUpdate"
							value='<c:out value= "${assignment.getAsgId()}"/>' type="submit"
							class="btn btn-edit">Update</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletAssignment">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Assignments	
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
				<form method="get" action="ServletAssignment">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Assignment</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table
						class="table table-striped table-dark w-50 text-left border-radius-7per mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Assignment View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${assignment.getAsgId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Title</td>
				      <td>${assignment.getAsgTitle()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Release Date</td>
				      <td>${assignment.getAsgRels()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Deadline</td>
				      <td>${assignment.getAsgDead()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Skill Level</td>
				      <td>${assignment.getAsgLvl()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Department</td>
				      <td>${serviceAssignment.getDepNameByDepId(assignment.getAsgDept())}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Teacher</td>
				      <td class="pb-4">${serviceAssignment.getDepNameByDepId(assignment.getAsgTech())}</td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>

	</jsp:body>


</t:layout>



