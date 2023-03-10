package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Student;

public interface DaoStudent {

	// Main CRUD
	int addStudent(Student std);

	ArrayList<Student> getAllStudents();

	int updateStudent(Student std);

	int deleteStudent(int id);

	// As Required
	void printSQLException(SQLException ex);

	Student getStudentById(int id);

	Student getStudentByContact(String contact);

	ArrayList<String> selectDepNames();

	ArrayList<String> selectGradeNames();

	ArrayList<String> selectRoleNames();

	ArrayList<String> selectSectionNames();

	int getDepIdByDepName(String depName);

	int getGradeIdByGrade(String grade);

	int getRoleIdByRoleName(String roleName);

	int getSectionIdByRoomNumber(String roomNumber);

	String getDepNameByDepId(int depId);

	String getGradeByGradeId(int gradeId);

	String getRoleNameByRoleId(int roleId);

	String getSectionBySecId(int secId);

	boolean checkDuplicateStudent(String firstName, String contactMe, String email);

	boolean checkDuplicateStudentOnEdit(int id, String contactMe, String email);
}
