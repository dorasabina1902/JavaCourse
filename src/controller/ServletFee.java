package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Fee;
import service.ServiceFee;

@WebServlet(name = "ServletFee", urlPatterns = "/views/pages/ServletFee")
public class ServletFee extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServiceFee serviceFee;

	public void init() {
		serviceFee = new ServiceFee();
	}

	public ServletFee() {
		super();
	}

	private boolean isOutOfRange(String str) {
		int len = str.length();
		if (len < 8 || len > 255) {
			return true;
		} else {
			return false;
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("fees", serviceFee.getAllFees().toArray());
		RequestDispatcher dispatcher = request.getRequestDispatcher("fees.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String createParamId = request.getParameter("btnCreate");
			String editParamId = request.getParameter("btnEdit");
			String displayParamId = request.getParameter("btnDisplay");
			String view01ParamId = request.getParameter("btnView01");
			String view02ParamId = request.getParameter("btnView02");

			if (view01ParamId != null || view02ParamId != null) {
				createParamId = editParamId = displayParamId = null;
				processRequest(request, response);
			} else if (createParamId != null) {
				request.setAttribute("createParam", "selected");
				processRequest(request, response);
			} else if (editParamId != null) {
				int id = Integer.parseInt(editParamId);
				if (id > 0) {
					request.setAttribute("editParam", "selected");
					request.setAttribute("fee", serviceFee.getFeeById(id));
					processRequest(request, response);
				}

			} else if (displayParamId != null) {
				int id = Integer.parseInt(displayParamId);
				request.setAttribute("displayParam", "selected");
				request.setAttribute("fee", serviceFee.getFeeById(id));
				processRequest(request, response);
			} else {
				processRequest(request, response);
			}
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// Required Variables

			int id, result;
			String feeCrs, feeAmt, feeType, feeDesc, error, emptyFields, outOfRange, duplicateVal, idNotfound, sqlIssue;

			id = result = 0;
			feeCrs = feeAmt = feeType = feeDesc = null;

			error = "error";
			emptyFields = "Empty Input Fields! Cannot access data.";
			outOfRange = "Value out of bounds! The character is either less than 8 or more than 255 or more than the limit.";
			duplicateVal = "Duplicate value! It already exists or it is already used in the database.";
			idNotfound = "Id not Found! Cannot find the data you are looking for.";
			sqlIssue = "Undefined error! There was a problem while inserting data to the database.";

			/*************************************************
			 ************************************* Create Form
			 *************************************************/
			if (request.getParameter("postCreate") != null) {
				try {
					// User Input Variables
					feeCrs = request.getParameter("crFeeCrs");
					feeAmt = request.getParameter("crFeeAmt");
					feeType = request.getParameter("crFeeType");
					feeDesc = request.getParameter("crFeeDesc");

					if (feeCrs.isEmpty() || feeAmt.isEmpty() || feeType.isEmpty()) {
						request.setAttribute(error, emptyFields);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						int feeAmtNum = Integer.parseInt(feeAmt);
						if (isOutOfRange(feeType) || feeCrs.length() > 60 || feeAmt.length() > 11 || feeType.length() > 60) {
							request.setAttribute(error, outOfRange);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							if (serviceFee.checkDuplicateFeeName(feeType)) {
								request.setAttribute(error, duplicateVal);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							} else {
								Fee fee = new Fee();
								fee.setFeeCrs(feeCrs);
								fee.setFeeAmt(feeAmtNum);
								fee.setFeeType(feeType);
								fee.setFeeDesc(feeDesc);
								result = serviceFee.addFee(fee);
								if (result == 1) {
									processRequest(request, response);
								} else {
									request.setAttribute(error, sqlIssue);
									request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
								}
							}
						}
					}
				} catch (Exception ex) {
					request.setAttribute(error, ex.getMessage());
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				}
			}
			/*************************************************
			 *************************************** Edit Form
			 *************************************************/
			else if (request.getParameter("postUpdate") != null) {

				// User Input Variables
				feeCrs = request.getParameter("upFeeCrs");
				feeAmt = request.getParameter("upFeeAmt");
				feeType = request.getParameter("upFeeType");
				feeDesc = request.getParameter("upFeeDesc");
				id = Integer.parseInt(request.getParameter("postUpdate"));

				if (feeCrs.isEmpty() || feeAmt.isEmpty() || feeType.isEmpty()) {
					request.setAttribute(error, emptyFields);
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				} else {
					if (isOutOfRange(feeType) || feeCrs.length() > 60 || feeAmt.length() > 11 || feeType.length() > 60) {
						request.setAttribute(error, outOfRange);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					} else {
						if (serviceFee.checkDuplicateFeeNameOnEdit(id, feeType) == false) {
							request.setAttribute(error, duplicateVal);
							request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
						} else {
							int feeAmtNum = Integer.parseInt(feeAmt);

							Fee fee = new Fee();
							fee.setFeeId(id);
							fee.setFeeCrs(feeCrs);
							fee.setFeeAmt(feeAmtNum);
							fee.setFeeType(feeType);
							fee.setFeeDesc(feeDesc);
							result = serviceFee.updateFee(fee);
							if (result == 1) {
								processRequest(request, response);
							} else {
								request.setAttribute(error, sqlIssue);
								request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
							}
						}
					}
				}

			}
			/*************************************************
			 ************************************* Delete Form
			 *************************************************/
			else if (request.getParameter("postDelete") != null) {
				id = Integer.parseInt(request.getParameter("postDelete"));
				if (id > 0) {
					result = serviceFee.deleteFee(id);
					if (result == 1) {
						processRequest(request, response);
					} else {
						request.setAttribute(error, sqlIssue);
						request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
					}
				} else {
					request.setAttribute(error, idNotfound);
					request.getRequestDispatcher("../errors/default_error.jsp").forward(request, response);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
