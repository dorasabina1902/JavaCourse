package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import model.Exam;

public interface DaoExam {

	// Main CRUD
	int addExam(Exam exam);

	ArrayList<Exam> getAllExams();

	int updateExam(Exam exam);

	int deleteExam(int id);

	// As Required
	void printSQLException(SQLException ex);
	
	ArrayList<String> selectFeeTypes();
	
	ArrayList<String> selectDepNames();
	
	int getDepIdByDepName(String depName);

	int getFeeIdByFeeType(String feeType);
	
	String getDepNameByDepId(int depId);
	
	String getFeeTypeByFeeId(int feeId);
	
	boolean checkDuplicateExam(String examType, String examSub, LocalDate examDate);

	boolean checkDuplicateExamOnEdit(int id, String examType, String examSub, LocalDate examDate);
	
	Exam getExamById(int id);
}
