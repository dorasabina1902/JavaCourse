package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoDepartment;
import model.Department;
import utility.DbConnection;

public class ServiceDepartment implements DaoDepartment {

//	public List<Department> getAllDepartments() {
//		return Arrays.asList(new Department(1, "Science", 34, "Very good"),
//				new Department(2, "History", 34, "Very bad"));
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
	 ********************************************** Department -> Insert -> Query
	 ***************************************************************************/
	public int addDepartment(Department dep) {

		query = "INSERT INTO departments(dep_name, no_of_teachers, description) VALUES(?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, dep.getDepName());
			ps.setInt(2, dep.getDepTech());
			ps.setString(3, dep.getDepDesc());
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
	 ****************************************** Department -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Department> getAllDepartments() {

		ArrayList<Department> listDep = new ArrayList<>();
		query = "SELECT * FROM departments";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New department object
				Department dep = new Department();
				dep.setDepId(rs.getInt("department_id"));
				dep.setDepName(rs.getString("dep_name"));
				dep.setDepTech(rs.getInt("no_of_teachers"));
				dep.setDepDesc(rs.getString("description"));

				// Adding to the list
				listDep.add(dep);
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
		return listDep;
	}

	/****************************************************************************
	 ********************************************** Department -> Update -> Query
	 ***************************************************************************/
	public int updateDepartment(Department dep) {
		query = "UPDATE departments SET dep_name = ?, no_of_teachers = ?, description = ? WHERE department_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, dep.getDepName());
			ps.setInt(2, dep.getDepTech());
			ps.setString(3, dep.getDepDesc());
			ps.setInt(4, dep.getDepId());
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
	 ********************************************** Department -> Delete -> Query
	 ***************************************************************************/
	public int deleteDepartment(int id) {
		query = "DELETE FROM departments WHERE department_id = ?";
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

	public boolean checkDuplicateDepName(String depName) {
		query = "SELECT * FROM departments WHERE dep_name = ?";
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, depName);
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

	public boolean checkDuplicateDepNameOnEdit(int id, String depName) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList.clear();
		query01 = "SELECT dep_name from departments WHERE department_id = ?";
		query = "SELECT * FROM departments WHERE dep_name = ?";

		try {
			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				String currentName = rs01.getString("dep_name");
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, depName);
				rs = ps.executeQuery();

				while (rs.next()) {
					myList.add(rs.getString("dep_name"));
				}

				if (myList.isEmpty()) {
					return true;
				} else if (myList.size() == 1 && myList.contains(currentName)) {
					return true;
				} else {
					return false;
				}
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

	public Department getDepById(int id) {
		query = "SELECT * FROM departments WHERE department_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Department dep = new Department();
				dep.setDepId(rs.getInt("department_id"));
				dep.setDepName(rs.getString("dep_name"));
				dep.setDepTech(rs.getInt("no_of_teachers"));
				dep.setDepDesc(rs.getString("description"));
				return dep;
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
}
