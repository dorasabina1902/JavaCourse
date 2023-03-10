package model;

import java.io.Serializable;

import org.joda.time.LocalDate;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private int student_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String password;
	private String address;
	private String gender;
	private LocalDate date_of_birth;
	private String attendance;
	private String contact;
	private String image_file_name;
	private int department_id;
	private int grade_id;
	private int role_id;
	private int section_id;

	public Student() {
		super();
	}

	public int getStdId() {
		return student_id;
	}

	public void setStdId(int student_id) {
		this.student_id = student_id;
	}

	public String getStdFrst() {
		return first_name;
	}

	public void setStdFrst(String first_name) {
		this.first_name = first_name;
	}

	public String getStdMid() {
		return middle_name;
	}

	public void setStdMid(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getStdLast() {
		return last_name;
	}

	public void setStdLast(String last_name) {
		this.last_name = last_name;
	}

	public String getStdMail() {
		return email;
	}

	public void setStdMail(String email) {
		this.email = email;
	}

	public String getStdPwd() {
		return password;
	}

	public void setStdPwd(String password) {
		this.password = password;
	}

	public String getStdAdd() {
		return address;
	}

	public void setStdAdd(String address) {
		this.address = address;
	}

	public String getStdGen() {
		return gender;
	}

	public void setStdGen(String gender) {
		this.gender = gender;
	}

	public LocalDate getStdDob() {
		return date_of_birth;
	}

	public void setStdDob(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getStdCont() {
		return contact;
	}

	public void setStdCont(String contact) {
		this.contact = contact;
	}

	public String getStdAttd() {
		return attendance;
	}

	public void setStdAttd(String attendance) {
		this.attendance = attendance;
	}

	public String getStdImg() {
		return image_file_name;
	}

	public void setStdImg(String image_file_name) {
		this.image_file_name = image_file_name;
	}

	public int getStdDept() {
		return department_id;
	}

	public void setStdDept(int department_id) {
		this.department_id = department_id;
	}

	public int getStdGrad() {
		return grade_id;
	}

	public void setStdGrad(int grade_id) {
		this.grade_id = grade_id;
	}

	public int getStdRole() {
		return role_id;
	}

	public void setStdRole(int role_id) {
		this.role_id = role_id;
	}

	public int getStdSect() {
		return section_id;
	}

	public void setStdSect(int section_id) {
		this.section_id = section_id;
	}
}
