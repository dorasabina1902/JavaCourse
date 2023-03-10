package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import model.Assignment;

public interface DaoAssignment {

	// Main CRUD
	int addAssignment(Assignment asg);

	ArrayList<Assignment> getAllAssignments();

	int updateAssignment(Assignment asg);

	int deleteAssignment(int id);

	// As Required
	void printSQLException(SQLException ex);

	Assignment getAssignmentById(int id);

	ArrayList<String> selectTechNames();

	ArrayList<String> selectDepNames();

	int getDepIdByDepName(String depName);

	int getTecIdByTechName(String techName);

	String getDepNameByDepId(int depId);

	String getTechNameByTechId(int tecId);

	boolean checkDuplicateAssignment(String asgTitle, LocalDate rlsDate);

	boolean checkDuplicateAssignmentOnEdit(int id, String asgTitle, LocalDate rlsDate);

}
