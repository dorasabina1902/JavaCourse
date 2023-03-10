<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Sections"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Sections</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Section</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Section</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Section Profile</title>
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
						<h1 class="xbootstrap">Sections</h1>
					</div>
				</c:if>
				
				 <c:if test="${sessionScope.role_id == 1}">
		
					<h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
					<form method="get" action="ServletSection">
						<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
							<h6 class="pt-1">Add new Section</h6>
						</button>
					</form>
				</c:if>	
			</div>
			
			<div class="container px-0">
					<table class="table table-striped table-dark">
			  <thead class="bg-primary">
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Room Number</th>
			      <th scope="col">Number of students</th>
			      <th scope="col">Number of seats</th>
			      <th scope="col">Rating</th>
			      <th scope="col">Active Status</th>
			      <c:if test="${sessionScope.role_id == 1}">
			      	<th scope="col">Options</th>
			      </c:if>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${sections}" var="sec">
			  	<tr>
			      <th scope="row">
			      	<p>${sec.getSecId()}</p>
			      	<form method="get" action="ServletSection" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${sec.getSecId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${sec.getSecRoom()}</td>
			      <td>${sec.getSecStud()}</td>
			      <td>${sec.getSecSeat()}</td>
			      <td>${sec.getSecRate()}</td>
			      <td>${sec.getSecStat()}</td>
			      
			      <c:if test="${sessionScope.role_id == 1}">
			        <td>
			      
			      	 <form method="get" action="ServletSection" class="">
			      		<button name="btnEdit" type="submit"
													value="${sec.getSecId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					 </form>
			      	
			      	
			      	 <form method="post" action="ServletSection" class="pt-2">
			      		<button name="postDelete" class="btn btn-delete"
													value="${sec.getSecId()}" type="submit">
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
				<h1 class="xbootstrap">Add Section</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletSection">
					<div class="form-row">
						<div class="form-group col-md-12">
							<label for="crSecRoom">Room number</label>
				      		<input type="text" class="form-control" id="crSecRoom"
									name="crSecRoom">
						</div>
					</div>
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crSecStud">Number of students</label>
				      <input type="text" class="form-control" id="crSecStud"
									name="crSecStud">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crSecSeat">Number of seats</label>
				      <input type="number" class="form-control" id="crSecSeat"
									name="crSecSeat">
				    </div>
				  </div>
				  <div class="form-row">
				  	  <div class="col-md-6 form-group">
				  	  	<label for="crSecRate">Rating</label>
				  	  	<select id="crSecRate" name="crSecRate" class="form-control"
									aria-label="Default select example">
						  <option value="Select" selected>Select</option>
						  <option value="A">A</option>
						  <option value="B">B</option>
						  <option value="C">C</option>
						  <option value="D">D</option>
						  <option value="E">E</option>
						</select>
				  	  </div>
					  <div class="col-md-6 form-group">
				  	  	<label for="crSecStat">Status</label>
				  	  	<select id="crSecStat" name="crSecStat" class="form-control"
									aria-label="Default select example">
						  <option value="Select" selected>Select</option>
						  <option value="Active">Active</option>
						  <option value="Not Active">Not Active</option>
						</select>
				  	  </div>
				  </div>
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletSection">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Sections	
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
				<h1 class="xbootstrap">Edit Section</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletSection">
				  <div class="form-row">
					<div class="form-group col-md-12">
						<label for="upSecRoom">Room number</label>
			      		<input type="text" class="form-control" id="upSecRoom"
									name="upSecRoom"
									value="<c:out value= "${section.getSecRoom()}"/>">
					</div>
				</div>
			  <div class="form-row">
			    <div class="form-group col-md-6">
			      <label for="upSecStud">Number of students</label>
			      <input type="text" class="form-control" id="upSecStud"
									name="upSecStud"
									value="<c:out value= "${section.getSecStud()}"/>">
			    </div>
			    <div class="form-group col-md-6">
			      <label for="upSecSeat">Number of seats</label>
			      <input type="number" class="form-control" id="upSecSeat"
									name="upSecSeat"
									value="<c:out value= "${section.getSecSeat()}"/>">
			    </div>
			  </div>
			  <div class="form-row">
			  	  <div class="col-md-6 form-group">
			  	  	<label for="upSecRate">Rating</label>
			  	  	<select id="upSecRate" name="upSecRate" class="form-control"
									aria-label="Default select example">
						  <option
										<c:if test='${section.getSecRate() == null}'><c:out value="selected" /></c:if>>
						  	Select
					  	  </option>
						  <option value="A"
										<c:if test='${section.getSecRate() == "A"}'><c:out value="selected" /></c:if>>
							A
						 </option>
						 <option value="B"
										<c:if test='${section.getSecRate() == "B"}'><c:out value="selected" /></c:if>>
							B
						 </option>
						  
							<option value="C"
										<c:if test='${section.getSecRate() == "C"}'><c:out value="selected" /></c:if>>
							C
						 </option>
						 <option value="D"
										<c:if test='${section.getSecRate() == "D"}'><c:out value="selected" /></c:if>>
							D
						 </option>
						 <option value="E"
										<c:if test='${section.getSecRate() == "E"}'><c:out value="selected" /></c:if>>
							E
						 </option>
					</select>
			  	  </div>
				  <div class="col-md-6 form-group">
			  	  	<label for="upSecStat">Status</label>
			  	  	<select id="upSecStat" name="upSecStat" class="form-control"
									aria-label="Default select example">
					  <option
										<c:if test='${section.getSecStat() == null}'><c:out value="selected" /></c:if>>
						Select
					  </option>
					  <option value="Active"
										<c:if test='${section.getSecStat() == "Active"}'><c:out value="selected" /></c:if>>
						Active
					  </option>
					  <option value="Not Active"
										<c:if test='${section.getSecStat() == "Not Active"}'><c:out value="selected" /></c:if>>
						Not Active
					  </option>
					</select>
			  	  </div>
			  	</div>
				<button name="postUpdate"
							value='<c:out value= "${section.getSecId()}"/>' type="submit"
							class="btn btn-edit">Update</button>
			  </form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletSection">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Sections	
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
				<form method="get" action="ServletSection">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Section</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table
						class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Section View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${section.getSecId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Room Number</td>
				      <td>${section.getSecRoom()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Number of students</td>
				      <td>${section.getSecStud()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Number of seats</td>
				      <td>${section.getSecSeat()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Rating</td>
				      <td>${section.getSecRate()}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Status</td>
				      <td class="pb-4">${section.getSecStat()}</td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	
	</jsp:body>


</t:layout>



