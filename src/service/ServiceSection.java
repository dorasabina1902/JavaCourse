package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoSection;
import model.Section;
import utility.DbConnection;

public class ServiceSection implements DaoSection {

//	public List<Section> getAllSections() {
//		return Arrays.asList(new Section(1, "Science", 34, "Very good"),
//				new Section(2, "History", 34, "Very bad"));
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
	 ********************************************** Section -> Insert -> Query
	 ***************************************************************************/
	public int addSection(Section sec) {

		query = "INSERT INTO sections(room_number, no_of_students, no_of_seats, rating, active_status) VALUES(?, ?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, sec.getSecRoom());
			ps.setInt(2, sec.getSecStud());
			ps.setInt(3, sec.getSecSeat());
			ps.setString(4, sec.getSecRate());
			ps.setString(5, sec.getSecStat());
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
	 ****************************************** Section -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Section> getAllSections() {

		ArrayList<Section> listSec = new ArrayList<>();
		query = "SELECT * FROM sections";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Section object
				Section sec = new Section();
				sec.setSecId(rs.getInt("section_id"));
				sec.setSecRoom(rs.getString("room_number"));
				sec.setSecStud(rs.getInt("no_of_students"));
				sec.setSecSeat(rs.getInt("no_of_seats"));
				sec.setSecRate(rs.getString("rating"));
				sec.setSecStat(rs.getString("active_status"));

				// Adding to the list
				listSec.add(sec);
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
		return listSec;
	}

	/****************************************************************************
	 ********************************************** Section -> Update -> Query
	 ***************************************************************************/
	public int updateSection(Section sec) {
		query = "UPDATE sections SET room_number = ?, no_of_students = ?, no_of_seats = ?, rating = ?, active_status = ? WHERE section_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, sec.getSecRoom());
			ps.setInt(2, sec.getSecStud());
			ps.setInt(3, sec.getSecSeat());
			ps.setString(4, sec.getSecRate());
			ps.setString(5, sec.getSecStat());
			ps.setInt(6, sec.getSecId());
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
	 ********************************************** Section -> Delete -> Query
	 ***************************************************************************/
	public int deleteSection(int id) {
		query = "DELETE FROM sections WHERE section_id = ?";
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

	public boolean checkDuplicateRoom(String roomNumber) {
		query = "SELECT * FROM sections WHERE room_number = ?";
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, roomNumber);
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

	public boolean checkDuplicateRoomOnEdit(int id, String roomNumber) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList.clear();
		query01 = "SELECT room_number from sections WHERE section_id = ?";
		query = "SELECT * FROM sections WHERE room_number = ?";

		try {
			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				String currentRoom = rs01.getString("room_number");
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, roomNumber);
				rs = ps.executeQuery();

				while (rs.next()) {
					myList.add(rs.getString("room_number"));
				}

				if (myList.isEmpty()) {
					return true;
				} else if (myList.size() == 1 && myList.contains(currentRoom)) {
					return true;
				} else {
					return false;
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

	public Section getSecById(int id) {
		query = "SELECT * FROM sections WHERE section_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Section sec = new Section();
				sec.setSecId(rs.getInt("section_id"));
				sec.setSecRoom(rs.getString("room_number"));
				sec.setSecStud(rs.getInt("no_of_students"));
				sec.setSecSeat(rs.getInt("no_of_seats"));
				sec.setSecRate(rs.getString("rating"));
				sec.setSecStat(rs.getString("active_status"));
				return sec;
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

//	public String getSectionStat(int id) {
//		try {
//			query = "SELECT * FROM sections WHERE section_id = ?";
//			ps = DbConnection.DbConnect().prepareStatement(query);
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if (!rs.next()) {
//				return null;
//			} else {
//				return rs.getString("active_status");
//			}
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//			return ex.getMessage();
//		}
//
//	}

//	public static void main(String[] args) {
//
//		ServiceExam exam = new ServiceExam();
//		System.out.println(exam.getExamFeeTypes());
//		System.out.println(exam.getExamDepNames());
//
//	}
}
