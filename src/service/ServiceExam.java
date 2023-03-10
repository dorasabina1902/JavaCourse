package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;

import dao.DaoExam;
import model.Exam;
import utility.DbConnection;

public class ServiceExam implements DaoExam {

//	public List<Exam> getAllExams() {
//		return Arrays.asList(new Exam(1, "Science", 34, "Very good"),
//				new Exam(2, "History", 34, "Very bad"));
//	}

	/****************************************************************************
	 ********************************************************* Required variables
	 ***************************************************************************/
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = null;
	int result = 0;
	ArrayList<String> myList1 = new ArrayList<>();
	ArrayList<String> myList2 = new ArrayList<>();

	/****************************************************************************
	 ************************************************************** SQL Exception
	 ***************************************************************************/
	public void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState : " + ((SQLException) e).getSQLState());
				System.err.println("Error Code : " + ((SQLException) e).getErrorCode());
				System.err.println("Message : " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause : " + t);
					t = t.getCause();
				}
			}
		}
	}

	/****************************************************************************
	 ********************************************** Exam -> Insert -> Query
	 ***************************************************************************/
	public int addExam(Exam exm) {

		query = "INSERT INTO exams(exam_type, subject, exam_date, time, remarks, department_id, fee_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, exm.getExmType());
			ps.setString(2, exm.getExmSub());
			ps.setObject(3, exm.getExmDate().toDate());
			ps.setString(4, exm.getExmTime());
			ps.setString(5, exm.getExmRmk());
			ps.setInt(6, exm.getExmDept());
			ps.setInt(7, exm.getExmFee());
			result = ps.executeUpdate();

		} catch (SQLException ex) {
			printSQLException(ex);
			result = 0;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/****************************************************************************
	 ****************************************** Exam -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Exam> getAllExams() {

		ArrayList<Exam> listExm = new ArrayList<>();
		query = "SELECT * FROM exams";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Exam object
				Exam exm = new Exam();
				exm.setExmId(rs.getInt("exam_id"));
				exm.setExmType(rs.getString("exam_type"));
				exm.setExmSub(rs.getString("subject"));
				exm.setExmDate(LocalDate.fromDateFields(rs.getDate("exam_date")));
				exm.setExmTime(rs.getString("time"));
				exm.setExmRmk(rs.getString("remarks"));
				exm.setExmDept(rs.getInt("department_id"));
				exm.setExmFee(rs.getInt("fee_id"));

				// Adding to the list
				listExm.add(exm);
			}
		} catch (SQLException ex) {
			printSQLException(ex);

		} catch (Exception ex) {
			ex.getMessage();

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return listExm;
	}

	/****************************************************************************
	 ********************************************** Exam -> Update -> Query
	 ***************************************************************************/
	public int updateExam(Exam exm) {
		query = "UPDATE exams SET exam_type = ?, subject = ?, exam_date = ?, time = ?, remarks = ?, department_id = ?, fee_id = ? WHERE exam_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, exm.getExmType());
			ps.setString(2, exm.getExmSub());
			ps.setObject(3, exm.getExmDate().toDate());
			ps.setString(4, exm.getExmTime());
			ps.setString(5, exm.getExmRmk());
			ps.setInt(6, exm.getExmDept());
			ps.setInt(7, exm.getExmFee());
			ps.setInt(8, exm.getExmId());
			result = ps.executeUpdate();

		} catch (SQLException ex) {
			printSQLException(ex);
			result = 0;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/****************************************************************************
	 ********************************************** Exam -> Delete -> Query
	 ***************************************************************************/
	public int deleteExam(int id) {
		query = "DELETE FROM exams WHERE exam_id = ?";
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			result = ps.executeUpdate();

		} catch (SQLException ex) {
			printSQLException(ex);
			result = 0;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	public Exam getExamById(int id) {
		query = "SELECT * FROM exams WHERE exam_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Exam exm = new Exam();
				exm.setExmId(rs.getInt("exam_id"));
				exm.setExmType(rs.getString("exam_type"));
				exm.setExmSub(rs.getString("subject"));
				exm.setExmDate(LocalDate.fromDateFields(rs.getDate("exam_date")));
				exm.setExmTime(rs.getString("time"));
				exm.setExmRmk(rs.getString("remarks"));
				exm.setExmDept(rs.getInt("department_id"));
				exm.setExmFee(rs.getInt("fee_id"));
				return exm;
			} else {
				return null;
			}

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public ArrayList<String> selectFeeTypes() {

		myList1.clear();
		query = "SELECT fee_type FROM fees";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("fee_type"));
			}

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return myList1;
	}

	public ArrayList<String> selectDepNames() {

		myList1.clear();
		query = "SELECT dep_name FROM departments";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("dep_name"));
			}

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return myList1;
	}

	public int getFeeIdByFeeType(String feeType) {

		query = "SELECT * FROM fees WHERE fee_type = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, feeType);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("fee_id");
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return result;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public int getDepIdByDepName(String depName) {

		query = "SELECT * FROM departments WHERE dep_name = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, depName);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("department_id");
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return result;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public String getFeeTypeByFeeId(int feeId) {
		query = "SELECT fee_type FROM fees WHERE fee_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, feeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("fee_type");
			}
			return null;

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public String getDepNameByDepId(int depId) {
		query = "SELECT dep_name FROM departments WHERE department_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, depId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("dep_name");
			}
			return null;
		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public boolean checkDuplicateExam(String examType, String examSub, LocalDate myDate) {
		query = "SELECT * FROM exams WHERE exam_type = ? AND subject = ? AND exam_date = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, examType);
			ps.setString(2, examSub);
			ps.setObject(3, myDate.toDate());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	public boolean checkDuplicateExamOnEdit(int id, String examType, String examSub, LocalDate myDate) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList1.clear();
		myList2.clear();

		try {
			query01 = "SELECT * from exams WHERE exam_id = ?";
			query = "SELECT * FROM exams WHERE exam_type = ? AND subject = ? AND exam_date = ?";

			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				myList1
						.addAll(Arrays.asList(rs01.getString("exam_type"), rs01.getString("subject"), rs01.getString("exam_date")));
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, examType);
				ps.setString(2, examSub);
				ps.setObject(3, myDate.toDate());
				rs = ps.executeQuery();

				while (rs.next()) {
					myList2.addAll(Arrays.asList(rs.getString("exam_type"), rs.getString("subject"), rs.getString("exam_date")));
				}

				if (myList2.isEmpty()) {
					return true;
				} else if (myList1.equals(myList2)) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException ex) {
			printSQLException(ex);

		} finally {
			if (ps != null || ps01 != null)
				try {
					ps.close();
					ps01.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
}