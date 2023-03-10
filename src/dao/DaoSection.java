package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Section;

public interface DaoSection {

	// Main CRUD
	int addSection(Section section);

	ArrayList<Section> getAllSections();

	int updateSection(Section section);

	int deleteSection(int id);

	// As Required
	void printSQLException(SQLException ex);

	boolean checkDuplicateRoom(String roomNumber);

	boolean checkDuplicateRoomOnEdit(int id, String roomNumber);

	Section getSecById(int id);
	
//	String getSectionStat(int id);
}
