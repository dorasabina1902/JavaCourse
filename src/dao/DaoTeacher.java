package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Teacher;

public interface DaoTeacher {

	// Main CRUD
	int addTeacher(Teacher tec);

	ArrayList<Teacher> getAllTeachers();

	int updateTeacher(Teacher tec);

	int deleteTeacher(int id);

	// As Required
	void printSQLException(SQLException ex);

	Teacher getTeacherById(int id);
	
	Teacher getTeacherByContact(String contact);

	ArrayList<String> selectDepNames();

	ArrayList<String> selectRoleNames();

	int getDepIdByDepName(String depName);

	int getRoleIdByRoleName(String roleName);

	String getDepNameByDepId(int depId);

	String getRoleNameByRoleId(int roleId);

	boolean checkDuplicateTeacher(String firstName, String contactMe, String email);

	boolean checkDuplicateTeacherOnEdit(int id, String contactMe, String email);
}
