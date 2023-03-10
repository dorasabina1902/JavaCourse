package model;

import java.io.Serializable;

public class Section implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int section_id;
	private String room_number;
	private int no_of_students;
	private int no_of_seats;
	private String rating;
	private String active_status;
	
	public Section() {
		super();
	}

	public int getSecId() {
		return section_id;
	}

	public void setSecId(int section_id) {
		this.section_id = section_id;
	}

	public String getSecRoom() {
		return room_number;
	}

	public void setSecRoom(String room_number) {
		this.room_number = room_number;
	}

	public int getSecStud() {
		return no_of_students;
	}

	public void setSecStud(int no_of_students) {
		this.no_of_students = no_of_students;
	}

	public int getSecSeat() {
		return no_of_seats;
	}

	public void setSecSeat(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

	public String getSecRate() {
		return rating;
	}

	public void setSecRate(String rating) {
		this.rating = rating;
	}

	public String getSecStat() {
		return active_status;
	}

	public void setSecStat(String active_status) {
		this.active_status = active_status;
	}
}
