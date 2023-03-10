package service.commonService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import model.Admin;
import model.Student;
import model.Teacher;
import utility.DbConnection;

public class ServiceLogin {

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

	public Student getStudentDetails(String email, String password) throws SQLException {

		query = "SELECT * FROM students WHERE email = ? AND password = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {

				// New Student
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
				std.setStdAttd(rs.getString("attendance"));
				std.setStdImg(rs.getString("image_file_name"));
				std.setStdDept(rs.getInt("department_id"));
				std.setStdGrad(rs.getInt("grade_id"));
				std.setStdRole(rs.getInt("role_id"));
				std.setStdSect(rs.getInt("section_id"));

				// Adding to the list
				return std;
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

	public Teacher getTeacherDetails(String email, String password) throws SQLException {

		query = "SELECT * FROM teachers WHERE email = ? AND password = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {

				// New Student
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
				tec.setTecHrs(rs.getInt("hours"));
				tec.setTecImg(rs.getString("image_file_name"));
				tec.setTecDept(rs.getInt("department_id"));
				tec.setTecRole(rs.getInt("role_id"));

				// Adding to the list
				return tec;
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

	public Admin getAdminDetails(String email, String password) throws SQLException {

		query = "SELECT * FROM admins WHERE email = ? AND password = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {

				// New Student
				Admin adm = new Admin();

				adm.setAdmId(rs.getInt("admin_id"));
				adm.setAdmFrst(rs.getString("first_name"));
				adm.setAdmMid(rs.getString("middle_name"));
				adm.setAdmLast(rs.getString("last_name"));
				adm.setAdmMail(rs.getString("email"));
				adm.setAdmPwd(rs.getString("password"));
				adm.setAdmAdd(rs.getString("address"));
				adm.setAdmGen(rs.getString("gender"));
				adm.setAdmDob(LocalDate.fromDateFields(rs.getDate("date_of_birth")));
				adm.setAdmCont(rs.getString("contact"));
				adm.setAdmImg(rs.getString("image_file_name"));
				adm.setAdmRole(rs.getInt("role_id"));

				// Adding to the list
				return adm;
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
