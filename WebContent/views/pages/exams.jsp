<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Exams"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Examss</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Exams</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Exams</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Exams Profile</title>
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
						<h1 class="xbootstrap">Exams</h1>
					</div>
				</c:if>
				
				 <c:if test="${sessionScope.role_id == 1}">
					<h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
					<form method="get" action="ServletExam">
						<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
							<h6 class="pt-1">Add new Exam</h6>
						</button>
					</form>
				</c:if>	
						
			</div>
			
			<div class="container px-0">
					<table class="table table-striped table-dark">
			  <thead class="bg-primary">
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Exam Type</th>
			      <th scope="col">Subject</th>
			      <th scope="col">Exam Date</th>
			      <th scope="col">Time</th>
			      <th scope="col">Remarks</th>
			      <th scope="col">Fee Type</th>
			      <th scope="col">Department</th>
			      
			      <c:if test="${sessionScope.role_id == 1}">
			      	<th scope="col">Options</th>
			      </c:if>
			      
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${exams}" var="exm">
			  	<tr>
			     <th scope="row">
			      	<p>${exm.getExmId()}</p>
			      	<form method="get" action="ServletExam" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${exm.getExmId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${exm.getExmType()}</td>
			      <td>${exm.getExmSub()}</td>
			      <td>${exm.getExmDate()}</td>
			      <td>${exm.getExmTime()}</td>
			      <td style="height: 150px;"><div
											class="scrollable custom-scrollbar">${exm.getExmRmk()}</div></td>
			      <td>${serviceExam.getFeeTypeByFeeId(exm.getExmFee())}</td>
			      <td>${serviceExam.getDepNameByDepId(exm.getExmDept())}</td>
			      
			    <c:if test="${sessionScope.role_id == 1}">
			      
			      <td>
			      	<form method="get" action="ServletExam" class="pt-3">
			      		<button name="btnEdit" type="submit"
													value="${exm.getExmId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					</form>
			      	
			      	
			      	<form method="post" action="ServletExam" class="pt-2">
						<button name="postDelete" class="btn btn-delete"
													value="${exm.getExmId()}" type="submit">
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
				<h1 class="xbootstrap">Add Exam</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletExam">
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crExmType">Exam Type</label>
				      <input type="text" class="form-control" id="crExmType"
									name="crExmType">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crExmSub">Subject</label>
				      <input type="text" class="form-control" id="crExmSub"
									name="crExmSub">
				    </div>
				  </div>
				  
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crExmDate">Exam Date</label>
				      <input type="date" class="form-control" id="crExmDate"
									name="crExmDate">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crExmTime">Time</label>
				      <input type="text" class="form-control" id="crExmTime"
									name="crExmTime">
				    </div>
				  </div>
				  
				  <div class="form-row">
					<div class="form-group col-md-12">
					  <label for="crExmRmk">Remarks</label>
					  <textarea class="form-control" rows="5" id="crExmRmk"
									name="crExmRmk"> </textarea>
					</div>
				  </div>
				  
				   <div class="form-row">
				     <div class="form-group col-md-6">
				      <label for="crExmFee">Fee Type</label>
				      <select id="crExmFee" name="crExmFee" class="form-control">
				      	<option value="Select">Select</option>
			      		<c:forEach items="${feeTypes}" var="feeCr">
				      		<option value='<c:out value="${feeCr}" />'>
				      			<c:out value="${feeCr}" />
							</option>
				      	</c:forEach>
				      </select>
				    </div>
				    
				    <div class="form-group col-md-6">
				      <label for="crExmDept">Exam</label>
				      <select id="crExmDept" name="crExmDept" class="form-control">
				      	<option value="Select">Select</option>
				      	<c:forEach items="${depNames}" var="depCr">
				      		<option value='<c:out value="${depCr}" />'>
				      			<c:out value="${depCr}" />
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
				<form method="get" action="ServletExam">
					<h1 class="pt-5">
						<label for="depView01">
								Go to Exams	
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
				<h1 class="xbootstrap">Edit Exam</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletExam">
				
				  <div class="form-row">
			   	  `<div class="form-group col-md-6">
			      	<label for="upExmType">Exam Type</label>
			      	<input type="text" class="form-control" id="upExmType"
									name="upExmType" value='<c:out value="${exam.getExmType()}" />'>
				    </div>
				    <div class="form-group col-md-6">
				      <label for="upExmSub">Subject</label>
				      <input type="text" class="form-control" id="upExmSub"
									name="upExmSub" value='<c:out value="${exam.getExmSub()}" />'>
					</div>
				  </div>
				  
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="upExmDate">Exam Date</label>
				      <input type="date" class="form-control" id="upExmDate"
									name="upExmDate" value='<c:out value="${exam.getExmDate()}" />'>
				    </div>
				    <div class="form-group col-md-6">
				      <label for="upExmTime">Time</label>
				      <input type="text" class="form-control" id="upExmTime"
									name="upExmTime" value='<c:out value="${exam.getExmTime()}" />'>
				    </div>
				  </div>
				  
				  <div class="form-row">
				  	<div class="form-group col-md-12">
					  <label for="upExmRmk">Remarks</label>
					  <textarea class="form-control" rows="5" id="upExmRmk"
									name="upExmRmk"><c:out value="${exam.getExmRmk()}" /></textarea>
					  </div>
				  </div>
				  
				   <div class="form-row">
				    <div class="form-group col-md-6">
					      <label for="upExmFee">Fee Type</label>
					      <select id="upExmFee" name="upExmFee" class="form-control">
					      	<option value="Select">Select</option>
				      		<c:forEach items="${feeTypes}" var="feeUp">					
								<option value='<c:out value="${feeUp}" />'
											<c:if test="${serviceExam.getFeeTypeByFeeId(exam.getExmFee()) == feeUp}">
												<c:out value="selected" />
											</c:if>>
					      			<c:out value="${feeUp}" />
								</option>
					      	</c:forEach>
					      </select>
					    </div>
					    
					    <div class="form-group col-md-6">
					      <label for="upExmDept">Exam</label>
					      <select id="upExmDept" name="upExmDept" class="form-control">
					      	<option value="Select">Select</option>
					      	<c:forEach items="${depNames}" var="depUp">
					      		<option value='<c:out value="${depUp}" />'
											<c:if test="${serviceExam.getDepNameByDepId(exam.getExmDept()) == depUp}">
												<c:out value="selected" />
											</c:if>>
									<c:out value="${depUp}" />
								</option>
					      	</c:forEach>
					      </select>
					    </div>
				  </div>
				  
				  <button name="postUpdate"
							value='<c:out value= "${exam.getExmId()}"/>' type="submit"
							class="btn btn-edit">Update</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletExam">
					<h1 class="pt-5">
						<label for="depView02">
								Go to Exams	
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
				<form method="get" action="ServletExam">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Exam</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Exam View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${exam.getExmId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Exam Type</td>
				      <td>${exam.getExmType()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Subject</td>
				      <td>${exam.getExmSub()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Exam Date</td>
				      <td>${exam.getExmDate()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Time</td>
				      <td>${exam.getExmTime()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Remarks</td>
				      <td style="height: 150px;"><div
										class="scrollable custom-scrollbar">${exam.getExmRmk()}</div></td>
				    </tr>
				     <tr>
				      <td scope="col">Fee Type</td>
				      <td>${serviceExam.getFeeTypeByFeeId(exam.getExmDept())}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Department</td>
				      <td class="pb-4">${serviceExam.getDepNameByDepId(exam.getExmDept())}</td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	
	</jsp:body>


</t:layout>



