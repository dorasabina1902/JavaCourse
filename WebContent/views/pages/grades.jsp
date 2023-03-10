<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Grades"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Grades</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Grade</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Grade</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Grade Profile</title>
		</c:if>
			
	</jsp:attribute>

	<jsp:body>
	
	<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
		<div class="container default-block px-0 my-5">
			<div class="container-fluid px-0 d-flex align-items-center">
			 
				<c:if test="${sessionScope.role_id == 3}">
					<div
							class="container d-flex justify-content-center align-items-center">
						<h1 class="xbootstrap">Grades</h1>
					</div>
				</c:if>
				
				 <c:if
						test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
					<h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
					<form method="get" action="ServletGrade">
						<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
						<h6 class="pt-1">Add new Grade</h6>
						</button>
					</form>
				</c:if>	
			</div>
			
			<div class="container px-0">
					<table class="table table-striped table-dark">
			  <thead class="bg-primary">
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Grade Grade</th>
			      <th scope="col">Percentage</th>
			      <th scope="col">Remarks</th>
			      <c:if
									test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">
			      	<th scope="col">Options</th>
			      </c:if>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${grades}" var="grd">
			  	<tr>
			      <th scope="row">
			      	<p>${grd.getGrdId()}</p>
			      	<form method="get" action="ServletGrade" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${grd.getGrdId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${grd.getGrdStd()}</td>
			      <td>${grd.getGrdPer()}</td>
			      <td style="height: 150px;"><div
											class="scrollable custom-scrollbar">${grd.getGrdRmk()}</div></td>
				  				
				<c:if
										test="${sessionScope.role_id == 1 || sessionScope.role_id == 2}">			
			      <td>
			      	 <form method="get" action="ServletGrade" class="">
			      		<button name="btnEdit" type="submit"
													value="${grd.getGrdId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					 </form>
					 
			      	<form method="post" action="ServletGrade" class="pt-3">
			   			<button name="postDelete" class="btn btn-delete"
													value="${grd.getGrdId()}" type="submit">
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
	
	<c:if test="${createParam == 'selected' && editParam == null}">
		<div class="container create-block px-0 my-5">
			<div class="container-fluid px-0"> 
				<h1 class="xbootstrap">Add Grade</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletGrade">
				
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crGrdStd">Grade Grade</label>
				      <input type="text" class="form-control" id="crGrdStd"
									name="crGrdStd">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crGrdPer">Percentage</label>
				      <input type="number" class="form-control" step="0.01"
									id="crGrdPer" name="crGrdPer">
				    </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-12">
					    <label for="crGrdRmk">Description</label>
					    <textarea class="form-control" rows="5" id="crGrdRmk"
									name="crGrdRmk"> </textarea>
					  </div>
				  </div>
					  
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletGrade">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Grades	
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
				<h1 class="xbootstrap">Edit Grade</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletGrade">
				
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="upGrdStd">Grade Grade</label>
				      <input type="text" class="form-control" id="upGrdStd"
									name="upGrdStd" value="<c:out value= "${grade.getGrdStd()}"/>">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="upGrdPer">Percentage</label>
				      <input type="number" class="form-control" id="upGrdPer"
									step="0.01" name="upGrdPer"
									value="<c:out value= "${grade.getGrdPer()}"/>">
				    </div>
				  </div>
				  
				  <div class="form-row">
					  <div class="form-group col-md-12">
					    <label for="upGrdRmk">Description</label>
					    <textarea class="form-control" rows="5" id="upGrdRmk"
									name="upGrdRmk"><c:out value="${grade.getGrdRmk()}" /></textarea>
					  </div>
				  </div>
					  
				  <button name="postUpdate"
							value='<c:out value= "${grade.getGrdId()}"/>' type="submit"
							class="btn btn-edit">Update</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletGrade">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Grades	
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
				<form method="get" action="ServletGrade">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Grade</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table
						class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Grade View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${grade.getGrdId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Grade</td>
				      <td>${grade.getGrdStd()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Percentage</td>
				      <td>${grade.getGrdPer()}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Remarks</td>
				      <td style="height: 150px;" class="pb-4"><div
										class="scrollable custom-scrollbar">${grade.getGrdRmk()}</div></td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	</jsp:body>


</t:layout>



