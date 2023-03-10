package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoGrade;
import model.Grade;
import utility.DbConnection;

public class ServiceGrade implements DaoGrade {

//	public List<Grade> getAllGrades() {
//		return Arrays.asList(new Grade(1, "Science", 34, "Very good"),
//				new Grade(2, "History", 34, "Very bad"));
//	}

	/****************************************************************************
	 ********************************************************* Required variables
	 ***************************************************************************/
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = null;
	int result = 0;
	ArrayList<String> myList = new ArrayList<>();

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
	 ********************************************** Grade -> Insert -> Query
	 ***************************************************************************/
	public int addGrade(Grade grade) {

		query = "INSERT INTO grades(grade, percentage, remarks) VALUES(?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, grade.getGrdStd());
			ps.setDouble(2, grade.getGrdPer());
			ps.setString(3, grade.getGrdRmk());
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
	 ****************************************** Grade -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Grade> getAllGrades() {

		ArrayList<Grade> listGrade = new ArrayList<>();
		query = "SELECT * FROM grades";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Grade object
				Grade grade = new Grade();
				grade.setGrdId(rs.getInt("grade_id"));
				grade.setGrdStd(rs.getString("grade"));
				grade.setGrdPer(rs.getDouble("percentage"));
				grade.setGrdRmk(rs.getString("remarks"));

				// Adding to the list
				listGrade.add(grade);
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
		return listGrade;
	}

	/****************************************************************************
	 ********************************************** Grade -> Update -> Query
	 ***************************************************************************/
	public int updateGrade(Grade grade) {
		query = "UPDATE grades SET grade = ?, percentage = ?, remarks = ? WHERE grade_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, grade.getGrdStd());
			ps.setDouble(2, grade.getGrdPer());
			ps.setString(3, grade.getGrdRmk());
			ps.setInt(4, grade.getGrdId());
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
	 ********************************************** Grade -> Delete -> Query
	 ***************************************************************************/
	public int deleteGrade(int id) {
		query = "DELETE FROM grades WHERE grade_id = ?";
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

	public boolean checkDuplicateStdGrade(String gradeName) {
		query = "SELECT * FROM grades WHERE grade = ?";
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, gradeName);
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

	public boolean checkDuplicateStdGradeOnEdit(int id, String gradeName) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList.clear();
		query01 = "SELECT fee_type from grades WHERE grade = ?";
		query = "SELECT * FROM grades WHERE grade = ?";

		try {
			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				String currentGrade = rs01.getString("grade");
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, gradeName);
				rs = ps.executeQuery();

				while (rs.next()) {
					myList.add(rs.getString("grade"));
				}

				if (myList.isEmpty()) {
					return true;
				} else if (myList.size() == 1 && myList.contains(currentGrade)) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return false;
		}
	}

	public Grade getGradeById(int id) {
		query = "SELECT * FROM grades WHERE grade_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Grade grade = new Grade();
				grade.setGrdId(rs.getInt("grade_id"));
				grade.setGrdStd(rs.getString("grade"));
				grade.setGrdPer(rs.getInt("percentage"));
				grade.setGrdRmk(rs.getString("remarks"));
				return grade;
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
}
