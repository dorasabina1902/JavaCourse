package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;
import dao.DaoTeacher;
import model.Teacher;
import utility.DbConnection;

public class ServiceTeacher implements DaoTeacher {

//	public List<Teacher> getAllTeachers() {
//		return Arrays.asList(new Teacher(1, "Science", 34, "Very good"),
//				new Teacher(2, "History", 34, "Very bad"));
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
	 ********************************************** Teacher -> Insert -> Query
	 ***************************************************************************/
	public int addTeacher(Teacher tec) {

		query = "INSERT INTO teachers(first_name, middle_name, last_name, email, password, address, gender, date_of_birth, contact, image_file_name, hours, department_id, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, tec.getTecFrst());
			ps.setString(2, tec.getTecMid());
			ps.setString(3, tec.getTecLast());
			ps.setString(4, tec.getTecMail());
			ps.setString(5, tec.getTecPwd());
			ps.setString(6, tec.getTecAdd());
			ps.setString(7, tec.getTecGen());
			ps.setObject(8, tec.getTecDob().toDate());
			ps.setString(9, tec.getTecCont());
			ps.setString(10, tec.getTecImg());
			ps.setInt(11, tec.getTecHrs());
			ps.setInt(12, tec.getTecDept());
			ps.setInt(13, tec.getTecRole());
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
	 ****************************************** Teacher -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Teacher> getAllTeachers() {

		ArrayList<Teacher> listTec = new ArrayList<>();
		query = "SELECT * FROM teachers";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Teacher object
				Teacher tec = new Teacher();
				tec.setTecId(rs.getInt("teacher_id"));
				tec.setTecFrst(rs.getString("first_name"));
				tec.setTecMid(rs.getString("middle_name"));
				tec.setTecLast(rs.getString("last_name"));
				tec.setTecMail(rs.getString("email"));
				tec.setTecPwd(rs.getString("password"));
				tec.setTecAdd(rs.getString("address"));
				tec.setTecGen(rs.getString("gender"));
				tec.setTecDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				tec.setTecCont(rs.getString("contact"));
				tec.setTecImg(rs.getString("image_file_name"));
				tec.setTecHrs(rs.getInt("hours"));
				tec.setTecDept(rs.getInt("department_id"));
				tec.setTecRole(rs.getInt("role_id"));

				// Adding to the list
				listTec.add(tec);
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
	 ********************************************** Teacher -> Update -> Query
	 ***************************************************************************/
	public int updateTeacher(Teacher tec) {
		query = "UPDATE teachers SET first_name = ?, middle_name = ?, last_name = ?, email = ?, password = ?, address = ?, gender = ?, date_of_birth = ?, contact = ?, image_file_name = ?, hours = ?, department_id = ?, role_id = ?  WHERE teacher_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, tec.getTecFrst());
			ps.setString(2, tec.getTecMid());
			ps.setString(3, tec.getTecLast());
			ps.setString(4, tec.getTecMail());
			ps.setString(5, tec.getTecPwd());
			ps.setString(6, tec.getTecAdd());
			ps.setString(7, tec.getTecGen());
			ps.setObject(8, tec.getTecDob().toDate());
			ps.setString(9, tec.getTecCont());
			ps.setString(10, tec.getTecImg());
			ps.setInt(11, tec.getTecHrs());
			ps.setInt(12, tec.getTecDept());
			ps.setInt(13, tec.getTecRole());
			ps.setInt(14, tec.getTecId());
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
	 ********************************************** Teacher -> Delete -> Query
	 ***************************************************************************/
	public int deleteTeacher(int id) {
		query = "DELETE FROM teachers WHERE teacher_id = ?";
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

	public Teacher getTeacherById(int id) {
		query = "SELECT * FROM teachers WHERE teacher_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Teacher tec = new Teacher();
				tec.setTecId(rs.getInt("teacher_id"));
				tec.setTecFrst(rs.getString("first_name"));
				tec.setTecMid(rs.getString("middle_name"));
				tec.setTecLast(rs.getString("last_name"));
				tec.setTecMail(rs.getString("email"));
				tec.setTecPwd(rs.getString("password"));
				tec.setTecAdd(rs.getString("address"));
				tec.setTecGen(rs.getString("gender"));
				tec.setTecDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				tec.setTecCont(rs.getString("contact"));
				tec.setTecImg(rs.getString("image_file_name"));
				tec.setTecHrs(rs.getInt("hours"));
				tec.setTecDept(rs.getInt("department_id"));
				tec.setTecRole(rs.getInt("role_id"));
				return tec;
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

	public Teacher getTeacherByContact(String contact) {
		query = "SELECT * FROM teachers WHERE contact = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, contact);
			rs = ps.executeQuery();
			if (rs.next()) {
				Teacher tec = new Teacher();
				tec.setTecId(rs.getInt("teacher_id"));
				tec.setTecFrst(rs.getString("first_name"));
				tec.setTecMid(rs.getString("middle_name"));
				tec.setTecLast(rs.getString("last_name"));
				tec.setTecMail(rs.getString("email"));
				tec.setTecPwd(rs.getString("password"));
				tec.setTecAdd(rs.getString("address"));
				tec.setTecGen(rs.getString("gender"));
				tec.setTecDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				tec.setTecCont(rs.getString("contact"));
				tec.setTecImg(rs.getString("image_file_name"));
				tec.setTecHrs(rs.getInt("hours"));
				tec.setTecDept(rs.getInt("department_id"));
				tec.setTecRole(rs.getInt("role_id"));
				return tec;
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

	public ArrayList<String> selectRoleNames() {

		myList1.clear();
		query = "SELECT role_name FROM roles";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("role_name"));
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

	public int getRoleIdByRoleName(String roleName) {

		query = "SELECT * FROM roles WHERE role_name = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, roleName);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("role_id");
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

	public String getRoleNameByRoleId(int roleId) {
		query = "SELECT role_name FROM roles WHERE role_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("role_name");
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

	public String getDepNameByDepId(int depId) {
		query = "SELECT dep_name FROM departments WHERE department_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, depId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("dep_name");
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

	public boolean checkDuplicateTeacher(String firstName, String contactMe, String email) {
		query = "SELECT * FROM teachers WHERE (first_name = ? AND contact = ?) OR email = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, contactMe);
			ps.setString(3, email);
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

	public boolean checkDuplicateTeacherOnEdit(int id, String contactMe, String email) {

		query = "SELECT * FROM teachers WHERE contact = ? OR email = ?";
		myList1.clear();
		myList2.clear();
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, contactMe);
			ps.setString(2, email);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return true;

			} else {
				myList1.addAll(Arrays.asList(rs.getString("contact"), rs.getString("email")));
				ps.close();
				rs = null;

				query = "SELECT * FROM teachers WHERE teacher_id = ?";
				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setInt(1, id);
				rs = ps.executeQuery();

				if (!rs.next()) {
					return false;

				} else {

					myList2.addAll(Arrays.asList(rs.getString("contact"), rs.getString("email")));

					if (myList1.contains(myList2.get(0)) || myList1.contains(myList2.get(1))) {
						return true;
					} else {
						return false;
					}
				}
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
}