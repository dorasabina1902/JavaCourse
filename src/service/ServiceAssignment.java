package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import dao.DaoAssignment;
import model.Assignment;
import utility.DbConnection;

public class ServiceAssignment implements DaoAssignment {

//	public List<Assignment> getAllAssignments() {
//		return Arrays.asList(new Assignment(1, "Science", 34, "Very good"),
//				new Assignment(2, "History", 34, "Very bad"));
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
	 ********************************************** Assignment -> Insert -> Query
	 ***************************************************************************/
	public int addAssignment(Assignment asg) {

		query = "INSERT INTO assignments(title, release_date, deadline, skill_level, department_id, teacher_id) VALUES(?, ?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, asg.getAsgTitle());
			ps.setObject(2, asg.getAsgRels().toDate());
			ps.setObject(3, asg.getAsgDead().toDate());
			ps.setObject(4, asg.getAsgLvl());
			ps.setInt(5, asg.getAsgDept());
			ps.setInt(6, asg.getAsgTech());
			result = ps.executeUpdate();

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
		return result;
	}

	/****************************************************************************
	 ****************************************** Assignment -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Assignment> getAllAssignments() {

		ArrayList<Assignment> listAsg = new ArrayList<>();
		query = "SELECT * FROM assignments";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Assignment object
				Assignment asg = new Assignment();
				asg.setAsgId(rs.getInt("assignment_id"));
				asg.setAsgTitle(rs.getString("title"));
				asg.setAsgRels(LocalDate.fromDateFields(rs.getDate("release_date")));
				asg.setAsgDead(LocalDate.fromDateFields(rs.getDate("deadline")));
				asg.setAsgLvl(rs.getString("skill_level"));
				asg.setAsgDept(rs.getInt("department_id"));
				asg.setAsgTech(rs.getInt("teacher_id"));

				// Adding to the list
				listAsg.add(asg);
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
		return listAsg;
	}

	/****************************************************************************
	 ********************************************** Assignment -> Update -> Query
	 ***************************************************************************/
	public int updateAssignment(Assignment asg) {
		query = "UPDATE assignments SET title = ?, release_date = ?, deadline = ?, skill_level = ?, department_id = ?, teacher_id = ? WHERE assignment_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, asg.getAsgTitle());
			ps.setObject(2, asg.getAsgRels().toDate());
			ps.setObject(3, asg.getAsgDead().toDate());
			ps.setObject(4, asg.getAsgLvl());
			ps.setInt(5, asg.getAsgDept());
			ps.setInt(6, asg.getAsgTech());
			ps.setInt(7, asg.getAsgId());
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
	 ********************************************** Assignment -> Delete -> Query
	 ***************************************************************************/
	public int deleteAssignment(int id) {
		query = "DELETE FROM assignments WHERE assignment_id = ?";
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

	public Assignment getAssignmentById(int id) {
		query = "SELECT * FROM assignments WHERE assignment_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Assignment asg = new Assignment();
				asg.setAsgId(rs.getInt("assignment_id"));
				asg.setAsgTitle(rs.getString("title"));
				asg.setAsgRels(LocalDate.fromDateFields(rs.getDate("release_date")));
				asg.setAsgDead(LocalDate.fromDateFields(rs.getDate("deadline")));
				asg.setAsgLvl(rs.getString("skill_level"));
				asg.setAsgDept(rs.getInt("department_id"));
				asg.setAsgTech(rs.getInt("teacher_id"));
				return asg;
			} else {
				return null;
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return null;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

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

	public ArrayList<String> selectTechNames() {

		myList1.clear();
		query = "SELECT first_name, middle_name, last_name FROM teachers";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("first_name") + " " + rs.getString("middle_name") + " " + rs.getString("last_name"));
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

	public int getTecIdByTechName(String techName) {

		query = "SELECT * FROM teachers WHERE first_name = ? AND middle_name = ? AND last_name = ?";

		String[] myListStr = techName.split(" ");
		String firstName = myListStr[0];
		String middleName = myListStr[1];
		String lastName = myListStr[2];

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, middleName);
			ps.setString(3, lastName);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("teacher_id");
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

	public String getDepNameByDepId(int depId) {
		query = "SELECT dep_name FROM departments WHERE department_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, depId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("dep_name");
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

	public String getTechNameByTechId(int tecId) {
		query = "SELECT * FROM teachers WHERE teacher_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, tecId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("first_name") + " " + rs.getString("middle_name") + " " + rs.getString("last_name");
			}
			return null;

		} catch (SQLException ex) {
			printSQLException(ex);
			return null;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public boolean checkDuplicateAssignment(String asgTitle, LocalDate rlsDate) {
		query = "SELECT * FROM assignments WHERE title = ? AND release_date = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, asgTitle);
			ps.setObject(2, rlsDate.toDate());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			printSQLException(ex);
			return false;

		} finally {
			if (ps != null)
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public boolean checkDuplicateAssignmentOnEdit(int id, String asgTitle, LocalDate rlsDate) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList1.clear();
		myList2.clear();

		try {
			query01 = "SELECT * from assignments WHERE assignment_id = ?";
			query = "SELECT * FROM assignments WHERE title = ? AND release_date = ?";

			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				myList1.addAll(Arrays.asList(rs01.getString("title"), rs01.getString("release_date")));
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, asgTitle);
				ps.setObject(2, rlsDate.toDate());
				rs = ps.executeQuery();

				while (rs.next()) {
					myList2.addAll(Arrays.asList(rs.getString("title"), rs.getString("release_date")));
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
			return false;

		} finally {

			if (ps != null || ps01 != null)
				try {
					ps.close();
					ps01.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public LocalDate DateTimeToLocalDate(Date date) {

		DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDate = inputFormat.parseLocalDate(date.toString());
		return localDate;
	}
}