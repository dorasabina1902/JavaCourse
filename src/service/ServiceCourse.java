package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoCourse;
import model.Course;
import utility.DbConnection;

public class ServiceCourse implements DaoCourse {

//	public List<Course> getAllCourses() {
//		return Arrays.asList(new Course(1, "Science", 34, "Very good"),
//				new Course(2, "History", 34, "Very bad"));
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
	 ********************************************** Course -> Insert -> Query
	 ***************************************************************************/
	public int addCourse(Course crs) {

		query = "INSERT INTO courses(title, credits, remarks, department_id, fee_id) VALUES(?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, crs.getCosTitle());
			ps.setDouble(2, crs.getCosCrd());
			ps.setString(3, crs.getCosRmk());
			ps.setInt(4, crs.getCosDept());
			ps.setInt(5, crs.getCosFee());
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
	 ****************************************** Course -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Course> getAllCourses() {

		ArrayList<Course> listTec = new ArrayList<>();
		query = "SELECT * FROM courses";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Course object
				Course crs = new Course();
				crs.setCosId(rs.getInt("course_id"));
				crs.setCosTitle(rs.getString("title"));
				crs.setCosCrd(rs.getDouble("credits"));
				crs.setCosRmk(rs.getString("remarks"));
				crs.setCosDept(rs.getInt("department_id"));
				crs.setCosFee(rs.getInt("fee_id"));

				// Adding to the list
				listTec.add(crs);
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
		return listTec;
	}

	/****************************************************************************
	 ********************************************** Course -> Update -> Query
	 ***************************************************************************/
	public int updateCourse(Course crs) {
		query = "UPDATE courses SET title = ?, credits = ?, remarks = ?, department_id = ?, fee_id = ? WHERE course_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, crs.getCosTitle());
			ps.setDouble(2, crs.getCosCrd());
			ps.setString(3, crs.getCosRmk());
			ps.setInt(4, crs.getCosDept());
			ps.setInt(5, crs.getCosFee());
			ps.setInt(6, crs.getCosId());
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
	 ********************************************** Course -> Delete -> Query
	 ***************************************************************************/
	public int deleteCourse(int id) {
		query = "DELETE FROM courses WHERE course_id = ?";
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

	public Course getCourseById(int id) {
		query = "SELECT * FROM courses WHERE course_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Course crs = new Course();
				crs.setCosId(rs.getInt("course_id"));
				crs.setCosTitle(rs.getString("title"));
				crs.setCosCrd(rs.getDouble("credits"));
				crs.setCosRmk(rs.getString("remarks"));
				crs.setCosDept(rs.getInt("department_id"));
				crs.setCosFee(rs.getInt("fee_id"));
				return crs;
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

	public boolean checkDuplicateCourse(String courseTitle) {
		query = "SELECT * FROM courses WHERE title = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, courseTitle);
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

	public boolean checkDuplicateCourseOnEdit(int id, String courseTitle) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList2.clear();

		try {
			query01 = "SELECT * from courses WHERE course_id = ?";
			query = "SELECT * FROM courses WHERE title = ?";

			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				String currrentTitle = rs01.getString("title");
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, courseTitle);
				rs = ps.executeQuery();

				while (rs.next()) {
					myList2.add(rs.getString("title"));
				}

				if (myList2.isEmpty()) {
					return true;
				} else if (myList2.size() == 1 && myList2.contains(currrentTitle)) {
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