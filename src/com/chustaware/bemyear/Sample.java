package com.chustaware.bemyear;


public class Sample {
	
	private int id;
	private String name;
	private String icon; // TODO change type?
	private String pattern; //TODO change type
	private boolean enabled;
	
	public Sample(int id, String nombre, String icono, String pattern, boolean enabled) {
		this.id = id;
		this.name = nombre;
		this.icon = icono;
		this.pattern = pattern;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcono(String icon) {
		this.icon = icon;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
