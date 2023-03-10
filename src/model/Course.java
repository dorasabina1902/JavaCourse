package model;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	private int course_id;
	private String title;
	private double credits;
	private String remarks;
	private int department_id;
	private int fee_id;

	public Course() {
		super();
	}

	public int getCosId() {
		return course_id;
	}

	public void setCosId(int course_id) {
		this.course_id = course_id;
	}

	public String getCosTitle() {
		return title;
	}

	public void setCosTitle(String title) {
		this.title = title;
	}

	public double getCosCrd() {
		return credits;
	}

	public void setCosCrd(double credits) {
		this.credits = credits;
	}

	public String getCosRmk() {
		return remarks;
	}

	public void setCosRmk(String remarks) {
		this.remarks = remarks;
	}

	public int getCosDept() {
		return department_id;
	}

	public void setCosDept(int department_id) {
		this.department_id = department_id;
	}

	public int getCosFee() {
		return fee_id;
	}

	public void setCosFee(int fee_id) {
		this.fee_id = fee_id;
	}
}
