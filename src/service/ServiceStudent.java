package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;

import dao.DaoStudent;
import model.Student;
import utility.DbConnection;

public class ServiceStudent implements DaoStudent {

//	public List<Student> getAllStudents() {
//		return Arrays.asList(new Student(1, "Science", 34, "Very good"),
//				new Student(2, "History", 34, "Very bad"));
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
	 ********************************************** Student -> Insert -> Query
	 ***************************************************************************/
	public int addStudent(Student std) {

		query = "INSERT INTO students(first_name, middle_name, last_name, email, password, address, gender, date_of_birth, contact, image_file_name, attendance, department_id, grade_id, role_id, section_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, std.getStdFrst());
			ps.setString(2, std.getStdMid());
			ps.setString(3, std.getStdLast());
			ps.setString(4, std.getStdMail());
			ps.setString(5, std.getStdPwd());
			ps.setString(6, std.getStdAdd());
			ps.setString(7, std.getStdGen());
			ps.setObject(8, std.getStdDob().toDate());
			ps.setString(9, std.getStdCont());
			ps.setString(10, std.getStdImg());
			ps.setString(11, std.getStdAttd());
			ps.setInt(12, std.getStdDept());
			ps.setInt(13, std.getStdGrad());
			ps.setInt(14, std.getStdRole());
			ps.setInt(15, std.getStdSect());
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
	 ****************************************** Student -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Student> getAllStudents() {

		ArrayList<Student> listStd = new ArrayList<>();
		query = "SELECT * FROM students";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Student object
				Student std = new Student();
				std.setStdId(rs.getInt("student_id"));
				std.setStdFrst(rs.getString("first_name"));
				std.setStdMid(rs.getString("middle_name"));
				std.setStdLast(rs.getString("last_name"));
				std.setStdMail(rs.getString("email"));
				std.setStdPwd(rs.getString("password"));
				std.setStdAdd(rs.getString("address"));
				std.setStdGen(rs.getString("gender"));
				std.setStdDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				std.setStdCont(rs.getString("contact"));
				std.setStdImg(rs.getString("image_file_name"));
				std.setStdAttd(rs.getString("attendance"));
				std.setStdDept(rs.getInt("department_id"));
				std.setStdGrad(rs.getInt("grade_id"));
				std.setStdRole(rs.getInt("role_id"));
				std.setStdSect(rs.getInt("section_id"));

				// Adding to the list
				listStd.add(std);
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
		return listStd;
	}

	/****************************************************************************
	 ********************************************** Student -> Update -> Query
	 ***************************************************************************/
	public int updateStudent(Student std) {
		query = "UPDATE students SET first_name = ?, middle_name = ?, last_name = ?, email = ?, password = ?, address = ?, gender = ?, date_of_birth = ?, contact = ?, image_file_name = ?, attendance = ?, department_id = ?, grade_id = ?, role_id = ?, section_id = ?  WHERE student_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, std.getStdFrst());
			ps.setString(2, std.getStdMid());
			ps.setString(3, std.getStdLast());
			ps.setString(4, std.getStdMail());
			ps.setString(5, std.getStdPwd());
			ps.setString(6, std.getStdAdd());
			ps.setString(7, std.getStdGen());
			ps.setObject(8, std.getStdDob().toDate());
			ps.setString(9, std.getStdCont());
			ps.setString(10, std.getStdImg());
			ps.setString(11, std.getStdAttd());
			ps.setInt(12, std.getStdDept());
			ps.setInt(13, std.getStdGrad());
			ps.setInt(14, std.getStdRole());
			ps.setInt(15, std.getStdSect());
			ps.setInt(16, std.getStdId());
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
	 ********************************************** Student -> Delete -> Query
	 ***************************************************************************/
	public int deleteStudent(int id) {
		query = "DELETE FROM students WHERE student_id = ?";
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

	public Student getStudentById(int id) {
		query = "SELECT * FROM students WHERE student_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Student std = new Student();
				std.setStdId(rs.getInt("student_id"));
				std.setStdFrst(rs.getString("first_name"));
				std.setStdMid(rs.getString("middle_name"));
				std.setStdLast(rs.getString("last_name"));
				std.setStdMail(rs.getString("email"));
				std.setStdPwd(rs.getString("password"));
				std.setStdAdd(rs.getString("address"));
				std.setStdGen(rs.getString("gender"));
				std.setStdDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				std.setStdCont(rs.getString("contact"));
				std.setStdImg(rs.getString("image_file_name"));
				std.setStdAttd(rs.getString("attendance"));
				std.setStdDept(rs.getInt("department_id"));
				std.setStdGrad(rs.getInt("grade_id"));
				std.setStdRole(rs.getInt("role_id"));
				std.setStdSect(rs.getInt("section_id"));
				return std;
			} else {
				return null;
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
		}

	}

	public Student getStudentByContact(String contact) {
		query = "SELECT * FROM students WHERE contact = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, contact);
			rs = ps.executeQuery();
			if (rs.next()) {
				Student std = new Student();
				std.setStdId(rs.getInt("student_id"));
				std.setStdFrst(rs.getString("first_name"));
				std.setStdMid(rs.getString("middle_name"));
				std.setStdLast(rs.getString("last_name"));
				std.setStdMail(rs.getString("email"));
				std.setStdPwd(rs.getString("password"));
				std.setStdAdd(rs.getString("address"));
				std.setStdGen(rs.getString("gender"));
				std.setStdDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				std.setStdCont(rs.getString("contact"));
				std.setStdImg(rs.getString("image_file_name"));
				std.setStdAttd(rs.getString("attendance"));
				std.setStdDept(rs.getInt("department_id"));
				std.setStdGrad(rs.getInt("grade_id"));
				std.setStdRole(rs.getInt("role_id"));
				std.setStdSect(rs.getInt("section_id"));
				return std;
			} else {
				return null;
			}

		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
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
		}
		return myList1;
	}

	public ArrayList<String> selectGradeNames() {

		myList1.clear();
		query = "SELECT grade FROM grades";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("grade"));
			}
		} catch (SQLException ex) {
			printSQLException(ex);
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
		}
		return myList1;
	}

	public ArrayList<String> selectSectionNames() {

		myList1.clear();
		query = "SELECT room_number FROM sections";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				myList1.add(rs.getString("room_number"));
			}
		} catch (SQLException ex) {
			printSQLException(ex);
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
		}
	}

	public int getGradeIdByGrade(String grade) {

		query = "SELECT * FROM grades WHERE grade = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, grade);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("grade_id");
			}
		} catch (SQLException ex) {
			printSQLException(ex);
			return result;
		}
	}

	public int getSectionIdByRoomNumber(String roomNumber) {

		query = "SELECT * FROM sections WHERE room_number = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, roomNumber);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return result;
			} else {
				return rs.getInt("section_id");
			}
		} catch (SQLException ex) {
			printSQLException(ex);
			return result;
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
			return null;
		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
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
			}
			return null;
		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
		}
	}

	public String getGradeByGradeId(int gradeId) {
		query = "SELECT grade FROM grades WHERE grade_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, gradeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("grade");
			}
			return null;
		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
		}
	}

	public String getSectionBySecId(int secId) {
		query = "SELECT room_number FROM sections WHERE section_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, secId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("room_number");
			}
			return null;
		} catch (SQLException ex) {
			printSQLException(ex);
			return null;
		}
	}

	public boolean checkDuplicateStudent(String firstName, String contactMe, String email) {

		query = "SELECT * FROM students WHERE (first_name = ? AND contact = ?) OR email = ?";

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
		}
	}

	public boolean checkDuplicateStudentOnEdit(int id, String contactMe, String email) {

		query = "SELECT * FROM students WHERE contact = ? OR email = ?";
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

				query = "SELECT * FROM students WHERE student_id = ?";
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
		}
	}
}