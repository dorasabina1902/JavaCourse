package model;

import org.joda.time.LocalDate;

public class Admin {

	private int admin_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String password;
	private String address;
	private String gender;
	private LocalDate date_of_birth;
	private String contact;
	private String image_path;
	private int role_id;

	public Admin() {
		super();
	}

	public int getAdmId() {
		return admin_id;
	}

	public void setAdmId(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmFrst() {
		return first_name;
	}

	public void setAdmFrst(String first_name) {
		this.first_name = first_name;
	}

	public String getAdmMid() {
		return middle_name;
	}

	public void setAdmMid(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getAdmLast() {
		return last_name;
	}

	public void setAdmLast(String last_name) {
		this.last_name = last_name;
	}

	public String getAdmMail() {
		return email;
	}

	public void setAdmMail(String email) {
		this.email = email;
	}

	public String getAdmPwd() {
		return password;
	}

	public void setAdmPwd(String password) {
		this.password = password;
	}

	public String getAdmAdd() {
		return address;
	}

	public void setAdmAdd(String address) {
		this.address = address;
	}

	public String getAdmGen() {
		return gender;
	}

	public void setAdmGen(String gender) {
		this.gender = gender;
	}

	public LocalDate getAdmDob() {
		return date_of_birth;
	}

	public void setAdmDob(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getAdmCont() {
		return contact;
	}

	public void setAdmCont(String contact) {
		this.contact = contact;
	}

	public String getAdmImg() {
		return image_path;
	}
	
	public void setAdmImg(String image_path) {
		this.image_path = image_path;
	}
	
	public int getAdmRole() {
		return role_id;
	}

	public void setAdmRole(int role_id) {
		this.role_id = role_id;
	}
}
