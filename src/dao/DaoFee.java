package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Fee;

public interface DaoFee {

	// Main CRUD
	int addFee(Fee fee);

	ArrayList<Fee> getAllFees();

	int updateFee(Fee fee);

	int deleteFee(int id);

	// As Required
	void printSQLException(SQLException ex);

	boolean checkDuplicateFeeName(String feeType);

	boolean checkDuplicateFeeNameOnEdit(int id, String feeType);

	Fee getFeeById(int id);
}
