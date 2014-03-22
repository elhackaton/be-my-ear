package com.chustaware.bemyear;

import java.util.Date;


public class SampleDate {
	
	private int id;
	private Date date;
	
	public SampleDate(int id, Date date){
		this.id = id;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
