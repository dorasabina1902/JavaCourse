package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Grade;

public interface DaoGrade {

	// Main CRUD
	int addGrade(Grade grade);

	ArrayList<Grade> getAllGrades();

	int updateGrade(Grade grade);

	int deleteGrade(int id);

	// As Required
	void printSQLException(SQLException ex);

	boolean checkDuplicateStdGrade(String gradeName);

	boolean checkDuplicateStdGradeOnEdit(int id, String gradeName);

	Grade getGradeById(int id);
}
