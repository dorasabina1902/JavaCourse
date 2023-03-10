<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts"%>

<c:if
	test="${sessionScope.first_name == null || sessionScope.role_id == null}">
	<c:redirect url="/views/commonPages/ServletHome">
		<c:param name="homepageAccessParam" value="selected"></c:param>
		<c:param name="pageTitle" value="Fees"></c:param>
	</c:redirect>
</c:if>

<t:layout>
	<jsp:attribute name="title">
			
		<c:if
			test="${createParam == null && editParam == null && displayParam == null}">
			<title>Fees</title>
		</c:if>
		<c:if
			test="${createParam == 'selected' && editParam == null && displayParam == null}">
			<title>Create Fee</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == 'selected' && displayParam == null}">
			<title>Edit Fee</title>
		</c:if>
			<c:if
			test="${createParam == null && editParam == null && displayParam == 'selected'}">
			<title>Fee Profile</title>
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
						<h1 class="xbootstrap">Fees</h1>
					</div>
				</c:if>
				
				 <c:if test="${sessionScope.role_id == 1}">
					<h1 class="w-50 ml-3 xbootstrap float-left text-right">View and Add</h1>
					<form method="get" action="ServletFee">
						<button name="btnCreate" type="submit" value="create"
								class="ml-5 btn btn-success float-left">
							<h6 class="pt-1">Add new Fee</h6>
						</button>
					</form>
				</c:if>	
			</div>
			
			<div class="container px-0">
					<table class="table table-striped table-dark">
			  <thead class="bg-primary">
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">CRS Id</th>
			      <th scope="col">Amount</th>
			      <th scope="col">Type</th>
			      <th scope="col">Description</th>
			      
			      <c:if test="${sessionScope.role_id == 1}">
			      	<th scope="col">Options</th>
			      </c:if>
			      
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${fees}" var="myFee">
			  	<tr>
			      <th scope="row">
			      	<p>${myFee.getFeeId()}</p>
			      	<form method="get" action="ServletFee" class="">
						<button type="submit" name="btnDisplay" class="btn btn-info"
												value="${myFee.getFeeId()}">
							<i class="fa fa-lg fa-id-badge" aria-hidden="true"></i>
						</button>
					</form>
			      </th>
			      <td>${myFee.getFeeCrs()}</td>
			      <td>${myFee.getFeeAmt()}</td>
			      <td>${myFee.getFeeType()}</td>
			      <td style="height: 150px;"><div
											class="scrollable custom-scrollbar">${myFee.getFeeDesc()}</div></td>
											
			     <c:if test="${sessionScope.role_id == 1}">
				   <td>
			      
			      	<form method="get" action="ServletFee" class="pt-3">
			      		<button name="btnEdit" type="submit"
													value="${myFee.getFeeId()}" class="btn btn-edit">
												<i class="fas fa-lg fa-edit"></i>
											</button>
					</form>
			      	
			      	
			      	<form method="post" action="ServletFee" class="pt-2">
			      		<button name="postDelete" class="btn btn-delete"
													value="${myFee.getFeeId()}" type="submit">
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
				<h1 class="xbootstrap">Add Fee</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletFee">
				  <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="crFeeCrs">CRS Id</label>
				      <input type="text" class="form-control" id="crFeeCrs"
									name="crFeeCrs">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="crFeeAmt">Amount</label>
				      <input type="number" class="form-control" id="crFeeAmt"
									name="crFeeAmt">
				    </div>
				    <div class="form-group col-md-12">
				      <label for="crFeeType">Type</label>
				      <input type="text" class="form-control" id="crFeeType"
									name="crFeeType">
				    </div>
				  </div>
				  
				  <div class="form-row">
				    <div class="form-group col-md-12">
				      <label for="crFeeDesc">Description</label>
				      <textarea class="form-control" rows="5" id="crFeeDesc"
									name="crFeeDesc"></textarea>
				    </div>
				  </div>
				  
				  <button name="postCreate" value="create" type="submit"
							class="btn btn-create">Create</button>
				</form>
			</div>
			
			<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
				<form method="get" action="ServletFee">
					<h1 class="pt-5">
						<label for="feeView01">
								Go to Fees	
						</label>
					</h1>
					<input id="feeView01" name="btnView01" class="d-none allView"
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
				<h1 class="xbootstrap">Edit Fee</h1>
			</div>
			
			<div class="container py-5 w-50 form-container float-left">
				<form method="post" action="ServletFee">
				
				  <div class="form-row">
					  <div class="form-group">
					      <label for="upFeeCrs">CRS Id</label>
					      <input type="text" class="form-control" id="upFeeCrs"
									value='<c:out value="${fee.getFeeCrs()}" />' name="upFeeCrs">
				      </div>
				  </div>
				  
				  <div class="form-row">
				      <div class="form-group col-md-6">
					      <label for="upFeeAmt">Amount</label>
					      <input type="number" class="form-control" id="upFeeAmt"
									value='<c:out value="${fee.getFeeAmt()}"/>' name="upFeeAmt">
				      </div>
				      
				      <div class="form-group col-md-12">
					      <label for="upFeeType">Type</label>
					      <input type="text" class="form-control" id="upFeeType"
									value='<c:out value="${fee.getFeeType()}" />' name="upFeeType">
				      </div>
				  </div>
				  
				  <div class="form-row">
				  	<div class="form-group col-md-12">
					    <label for="upFeeDesc">Description</label>
					    <textarea class="form-control" rows="5" id="upFeeDesc"
									name="upFeeDesc"><c:out value="${fee.getFeeDesc()}" /></textarea>
				  	</div>
				  </div>
				  
				  <button name="postUpdate"
							value="<c:out value= "${fee.getFeeId()}"/>" type="submit"
							class="btn btn-edit">Update</button>
					</form>
				</div>
				
				<div class="container-fluid py-5 w-50 h-100 float-right mt-5">
					<form method="get" action="ServletFee">
						<h1 class="pt-5">
							<label for="feeView02">
									Go to Fees	
							</label>
						</h1>
						<input id="feeView02" name="btnView02" class="d-none allView"
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
				<form method="get" action="ServletFee">
					<button name="depView01" type="submit" value="create"
							class="btn btn-success ml-5">
						<h6 class="pt-1">Go to Fee</h6>
					</button>
				</form>
			</div>
			
			<div class="container px-0 d-flex justify-content-center">
				<table
						class="table table-striped table-dark w-50 text-left mt-5">
				  <thead>
				    <tr class="text-center title-view bg-primary">
					  <th scope="row" colspan="2" class="pt-3"><h2>Fee View</h2></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<tr>
				      <td scope="col">Id</td>
				      <td>${fee.getFeeId()}</td>
				    </tr>
				    <tr>
				      <td scope="col">CRS</td>
				      <td>${fee.getFeeCrs()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Amount</td>
				      <td>${fee.getFeeAmt()}</td>
				    </tr>
				    <tr>
				      <td scope="col">Fee Type</td>
				      <td>${fee.getFeeType()}</td>
				    </tr>
				    <tr>
				      <td scope="col" class="pb-4">Description</td>
				      <td style="height: 150px;" class="pb-4"><div
										class="scrollable custom-scrollbar">${fee.getFeeDesc()}</div></td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>				
	</c:if>
	<div class="clearfix"></div>
	
	</jsp:body>


</t:layout>



