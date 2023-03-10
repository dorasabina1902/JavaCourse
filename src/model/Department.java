package model;

import java.io.Serializable;

public class Department implements Serializable{

	private static final long serialVersionUID = 1L;
	private int department_id;
	private String dep_name;
	private int no_of_teachers;
	private String description;
	
	public Department() {
		super();
	}
	
//	public Department (int id, String name, int tech, String desc) {
//		super();
//		this.department_id = id;
//		this.dep_name = name;
//		this.no_of_teachers = tech;
//		this.description = desc;
//	}
	
	public int getDepId() {
		return department_id;
	}
	
	public void setDepId(int id) {
		this.department_id = id;
	}
	
	public String getDepName() {
		return dep_name;
	}
	
	public void setDepName(String name) {
		this.dep_name = name;
	}
	
	public int getDepTech() {
		return no_of_teachers;
	}
	
	public void setDepTech(int num) {
		this.no_of_teachers = num;
	}
	
	public String getDepDesc() {
		return this.description;
	}
	
	public void setDepDesc(String des) {
		this.description = des;
	}
}
