package model;

import java.io.Serializable;

import org.joda.time.LocalDate;

public class Teacher implements Serializable {

	private static final long serialVersionUID = 1L;

	private int teacher_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String password;
	private String address;
	private String gender;
	private LocalDate date_of_birth;
	private String contact;
	private int hours;
	private String image_file_name;
	private int department_id;
	private int role_id;

	public Teacher() {
		super();
	}

	public int getTecId() {
		return teacher_id;
	}

	public void setTecId(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTecFrst() {
		return first_name;
	}

	public void setTecFrst(String first_name) {
		this.first_name = first_name;
	}

	public String getTecMid() {
		return middle_name;
	}

	public void setTecMid(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getTecLast() {
		return last_name;
	}

	public void setTecLast(String last_name) {
		this.last_name = last_name;
	}

	public String getTecMail() {
		return email;
	}

	public void setTecMail(String email) {
		this.email = email;
	}

	public String getTecPwd() {
		return password;
	}

	public void setTecPwd(String password) {
		this.password = password;
	}

	public String getTecAdd() {
		return address;
	}

	public void setTecAdd(String address) {
		this.address = address;
	}

	public String getTecGen() {
		return gender;
	}

	public void setTecGen(String gender) {
		this.gender = gender;
	}

	public LocalDate getTecDob() {
		return date_of_birth;
	}

	public void setTecDob(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getTecCont() {
		return contact;
	}

	public void setTecCont(String contact) {
		this.contact = contact;
	}

	public int getTecHrs() {
		return hours;
	}

	public void setTecHrs(int hours) {
		this.hours = hours;
	}

	public String getTecImg() {
		return image_file_name;
	}

	public void setTecImg(String image_file_name) {
		this.image_file_name = image_file_name;
	}

	public int getTecDept() {
		return department_id;
	}

	public void setTecDept(int department_id) {
		this.department_id = department_id;
	}

	public int getTecRole() {
		return role_id;
	}

	public void setTecRole(int role_id) {
		this.role_id = role_id;
	}

}
