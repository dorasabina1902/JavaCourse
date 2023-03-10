package model;

import java.io.Serializable;

import org.joda.time.LocalDate;

public class Assignment implements Serializable {

	private static final long serialVersionUID = 1L;

	private int assignment_id;
	private String title;
	private LocalDate release_date;
	private LocalDate deadline;
	private String skill_level;
	private int teacher_id;
	private int department_id;
	
	public Assignment() {
		super();
	}

	public int getAsgId() {
		return assignment_id;
	}

	public void setAsgId(int assignment_id) {
		this.assignment_id = assignment_id;
	}

	public String getAsgTitle() {
		return title;
	}

	public void setAsgTitle(String title) {
		this.title = title;
	}

	public LocalDate getAsgRels() {
		return release_date;
	}

	public void setAsgRels(LocalDate release_date) {
		this.release_date = release_date;
	}

	public LocalDate getAsgDead() {
		return deadline;
	}

	public void setAsgDead(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getAsgLvl() {
		return skill_level;
	}

	public void setAsgLvl(String skill_level) {
		this.skill_level = skill_level;
	}

	public int getAsgTech() {
		return teacher_id;
	}

	public void setAsgTech(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public int getAsgDept() {
		return department_id;
	}

	public void setAsgDept(int department_id) {
		this.department_id = department_id;
	}

}
