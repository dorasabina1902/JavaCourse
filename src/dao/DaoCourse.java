package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;

public interface DaoCourse {

	// Main CRUD
	int addCourse(Course exm);

	ArrayList<Course> getAllCourses();

	int updateCourse(Course exm);

	int deleteCourse(int id);

	// As Required
	void printSQLException(SQLException ex);
	
	ArrayList<String> selectFeeTypes();
	
	ArrayList<String> selectDepNames();
	
	int getDepIdByDepName(String depName);

	int getFeeIdByFeeType(String feeType);
	
	String getDepNameByDepId(int depId);
	
	String getFeeTypeByFeeId(int feeId);
	
	boolean checkDuplicateCourse(String courseTitle);

	boolean checkDuplicateCourseOnEdit(int id, String courseTitle);
	
	Course getCourseById(int id);
}
