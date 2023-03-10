package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoFee;
import model.Fee;
import utility.DbConnection;

public class ServiceFee implements DaoFee {

//	public List<Fee> getAllFees() {
//		return Arrays.asList(new Fee(1, "Science", 34, "Very good"),
//				new Fee(2, "History", 34, "Very bad"));
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
	 ********************************************** Fee -> Insert -> Query
	 ***************************************************************************/
	public int addFee(Fee fee) {

		query = "INSERT INTO fees(fee_crs_id, fee_amount, fee_type, fee_description) VALUES(?, ?, ?, ?)";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, fee.getFeeCrs());
			ps.setInt(2, fee.getFeeAmt());
			ps.setString(3, fee.getFeeType());
			ps.setString(4, fee.getFeeDesc());
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
	 ****************************************** Fee -> Select All -> Query
	 ***************************************************************************/
	public ArrayList<Fee> getAllFees() {

		ArrayList<Fee> listfee = new ArrayList<>();
		query = "SELECT * FROM fees";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				// New Fee object
				Fee fee = new Fee();
				fee.setFeeId(rs.getInt("fee_id"));
				fee.setFeeCrs(rs.getString("fee_crs_id"));
				fee.setFeeAmt(rs.getInt("fee_amount"));
				fee.setFeeType(rs.getString("fee_type"));
				fee.setFeeDesc(rs.getString("fee_description"));

				// Adding to the list
				listfee.add(fee);
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
		return listfee;
	}

	/****************************************************************************
	 ********************************************** Fee -> Update -> Query
	 ***************************************************************************/
	public int updateFee(Fee fee) {
		query = "UPDATE fees SET fee_crs_id = ?, fee_amount = ?, fee_type = ?, fee_description = ? WHERE fee_id = ?";

		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, fee.getFeeCrs());
			ps.setInt(2, fee.getFeeAmt());
			ps.setString(3, fee.getFeeType());
			ps.setString(4, fee.getFeeDesc());
			ps.setInt(5, fee.getFeeId());
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
	 ********************************************** Fee -> Delete -> Query
	 ***************************************************************************/
	public int deleteFee(int id) {
		query = "DELETE FROM fees WHERE fee_id = ?";
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

	public boolean checkDuplicateFeeName(String feeType) {
		query = "SELECT * FROM fees WHERE fee_type = ?";
		try {
			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setString(1, feeType);
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

	public boolean checkDuplicateFeeNameOnEdit(int id, String feeType) {

		String query01 = null;
		PreparedStatement ps01 = null;
		ResultSet rs01 = null;

		myList.clear();
		query01 = "SELECT fee_type from fees WHERE fee_id = ?";
		query = "SELECT * FROM fees WHERE fee_type = ?";

		try {
			ps01 = DbConnection.DbConnect().prepareStatement(query01);
			ps01.setInt(1, id);
			rs01 = ps01.executeQuery();

			if (!rs01.next()) {
				return false;
			} else {

				String currentType = rs01.getString("fee_type");
				ps01.close();

				ps = DbConnection.DbConnect().prepareStatement(query);
				ps.setString(1, feeType);
				rs = ps.executeQuery();

				while (rs.next()) {
					myList.add(rs.getString("fee_type"));
				}

				if (myList.isEmpty()) {
					return true;
				} else if (myList.size() == 1 && myList.contains(currentType)) {
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

	public Fee getFeeById(int id) {
		query = "SELECT * FROM fees WHERE fee_id = ?";

		try {

			ps = DbConnection.DbConnect().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Fee fee = new Fee();
				fee.setFeeId(rs.getInt("fee_id"));
				fee.setFeeCrs(rs.getString("fee_crs_id"));
				fee.setFeeAmt(rs.getInt("fee_amount"));
				fee.setFeeType(rs.getString("fee_type"));
				fee.setFeeDesc(rs.getString("fee_description"));
				return fee;
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
