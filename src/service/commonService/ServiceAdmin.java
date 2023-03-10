package service.commonService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;

import model.Admin;
import utility.DbConnection;

public class ServiceAdmin {

	/****************************************************************************
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
	 ********************************************** Admin -> Insert -> Query
	 ***************************************************************************/
	public int addAdmin(Admin adm) {

		query = "INSERT INTO admins(first_name, middle_name, last_name, email, password, address, gender, date_of_birth, contact, image_file_name, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, adm.getAdmFrst());
			ps.setString(2, adm.getAdmMid());
			ps.setString(3, adm.getAdmLast());
			ps.setString(4, adm.getAdmMail());
			ps.setString(5, adm.getAdmPwd());
			ps.setString(6, adm.getAdmAdd());
			ps.setString(7, adm.getAdmGen());
			ps.setObject(8, adm.getAdmDob().toDate());
			ps.setString(9, adm.getAdmCont());
			ps.setString(10, adm.getAdmImg());
			ps.setInt(11, adm.getAdmRole());
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
	 ****************************************** Admin -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Admin> getAllAdmins() {

		ArrayList<Admin> listAdm = new ArrayList<>();
		query = "SELECT * FROM admins";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Admin object
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
				listAdm.add(adm);
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
		return listAdm;
	}

	/****************************************************************************
	 ********************************************** Admin -> Update -> Query
	 ***************************************************************************/
	public int updateAdmin(Admin adm) {
		query = "UPDATE admins SET first_name = ?, middle_name = ?, last_name = ?, email = ?, password = ?, address = ?, gender = ?, date_of_birth = ?, contact = ?, image_file_name = ?, role_id = ? WHERE admin_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, adm.getAdmFrst());
			ps.setString(2, adm.getAdmMid());
			ps.setString(3, adm.getAdmLast());
			ps.setString(4, adm.getAdmMail());
			ps.setString(5, adm.getAdmPwd());
			ps.setString(6, adm.getAdmAdd());
			ps.setString(7, adm.getAdmGen());
			ps.setObject(8, adm.getAdmDob().toDate());
			ps.setString(9, adm.getAdmCont());
			ps.setString(10, adm.getAdmImg());
			ps.setInt(11, adm.getAdmRole());
			ps.setInt(12, adm.getAdmId());
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
	 ********************************************** Admin -> Delete -> Query
	 ***************************************************************************/
	public int deleteAdmin(int id) {
		query = "DELETE FROM admins WHERE admin_id = ?";
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

	public Admin getAdminById(int id) {
		query = "SELECT * FROM admins WHERE admin_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
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
				return adm;
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

	public Admin getAdminByContact(String contact) {
		query = "SELECT * FROM admins WHERE contact = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, contact);
			rs = ps.executeQuery();
			if (rs.next()) {
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
				return adm;
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
		}
	}

	public boolean checkDuplicateAdmin(String firstName, String contactMe, String email) {

		query = "SELECT * FROM admins WHERE (first_name = ? AND contact = ?) OR email = ?";

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

	public boolean checkDuplicateAdminOnEdit(int id, String contactMe, String email) {

		query = "SELECT * FROM admins WHERE contact = ? OR email = ?";
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

				query = "SELECT * FROM admins WHERE admin_id = ?";
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
