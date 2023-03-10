package model;

import java.io.Serializable;

public class Grade implements Serializable {

	private static final long serialVersionUID = 1L;

	private int grade_id;
	private String std_grade;
	private double percentage;
	private String remarks;
	
	public Grade() {
		super();
	}

	public int getGrdId() {
		return grade_id;
	}

	public void setGrdId(int grade_id) {
		this.grade_id = grade_id;
	}

	public String getGrdStd() {
		return std_grade;
	}

	public void setGrdStd(String stdGrade) {
		this.std_grade = stdGrade;
	}

	public double getGrdPer() {
		return percentage;
	}

	public void setGrdPer(double percentage) {
		this.percentage = percentage;
	}

	public String getGrdRmk() {
		return remarks;
	}

	public void setGrdRmk(String remarks) {
		this.remarks = remarks;
	}
}
