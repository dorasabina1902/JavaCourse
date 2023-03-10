package model;

import java.io.Serializable;

import org.joda.time.LocalDate;

public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	private int exam_id;
	private String exam_type;
	private String subject;
	private LocalDate exam_date;
	private String time;
	private String remarks;
	private int department_id;
	private int fee_id;

	public Exam() {
		super();
	}

	public int getExmId() {
		return exam_id;
	}

	public void setExmId(int exam_id) {
		this.exam_id = exam_id;
	}

	public String getExmType() {
		return exam_type;
	}

	public void setExmType(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getExmSub() {
		return subject;
	}

	public void setExmSub(String subject) {
		this.subject = subject;
	}

	public LocalDate getExmDate() {
		return exam_date;
	}

	public void setExmDate(LocalDate exam_date) {
		this.exam_date = exam_date;
	}

	public String getExmTime() {
		return time;
	}

	public void setExmTime(String time) {
		this.time = time;
	}

	public String getExmRmk() {
		return remarks;
	}

	public void setExmRmk(String remarks) {
		this.remarks = remarks;
	}

	public int getExmDept() {
		return department_id;
	}

	public void setExmDept(int department_id) {
		this.department_id = department_id;
	}

	public int getExmFee() {
		return fee_id;
	}

	public void setExmFee(int fee_id) {
		this.fee_id = fee_id;
	}

}
