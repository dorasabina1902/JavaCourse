package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Department;

public interface DaoDepartment {

	// Main CRUD
	int addDepartment(Department dep);

	ArrayList<Department> getAllDepartments();

	int updateDepartment(Department dep);

	int deleteDepartment(int id);

	// As Required
	void printSQLException(SQLException ex);

	boolean checkDuplicateDepName(String depName);

	boolean checkDuplicateDepNameOnEdit(int id, String depName);

	Department getDepById(int id);
}
