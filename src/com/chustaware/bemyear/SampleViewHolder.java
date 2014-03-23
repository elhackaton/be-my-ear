package com.chustaware.bemyear;

import android.widget.CheckBox;
import android.widget.TextView;


public class SampleViewHolder {

	private TextView tvName;
	private CheckBox cbEnabled;
	
	public SampleViewHolder() {
		
	}
	
	public SampleViewHolder(TextView tvName, CheckBox cbEnabled) {
		this.tvName = tvName;
		this.cbEnabled = cbEnabled;
	}

	
	public TextView getTvName() {
		return tvName;
	}

	
	public void setTvName(TextView tvName) {
		this.tvName = tvName;
	}

	
	public CheckBox getCbEnabled() {
		return cbEnabled;
	}

	
	public void setCbEnabled(CheckBox cbEnabled) {
		this.cbEnabled = cbEnabled;
	}
	
	

}
