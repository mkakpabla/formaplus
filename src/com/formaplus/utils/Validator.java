package com.formaplus.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	
	
	private List<String> errors;
	
	private boolean isValid = true;
	
	
	
	
	public Validator() {
		// TODO Auto-generated constructor stub
		this.errors = new ArrayList<String>();
	}
	
	
	
	
	public List<String> getErrors() {
		return errors;
	}


	public boolean isValid() {
		return isValid;
	}
	
	
	public Validator notEmpty(String value, String message) {
		if(value.isEmpty()) {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator notNull(Object obj, String message) {
		if (obj == null) {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator minLength(String value, int length, String message) {
		if(value.length() < length) {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator maxLength(String value, int length, String message) {
		if(value.length() > length) {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator isPastDate(LocalDate value, String message) {
		if(value == null) {
			this.isValid = false;
			errors.add(message);
		}else if(value.isAfter(LocalDate.now())) {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}


	public Validator isNumber(String value, String message) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(value);
		try {
			if(m.find() && m.group().equals(value)) {
				//this.isValid = true;
			} else {
				this.isValid = false;
				errors.add(message);
			}
		} catch (Exception e) {
			this.isValid = false;
			errors.add(message);
		}
		
		return this;
	}
	
	
	public Validator isAlpha(String value, String message) {
		Pattern p = Pattern.compile("[\\p{L}]+");
		Matcher m = p.matcher(value);
		if(m.find() && m.group().equals(value)) {
			
		} else {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator isAlphaNum(String value, String message) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		Matcher m = p.matcher(value);
		if(m.find() && m.group().equals(value)) {
			
		} else {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	public Validator isEmail(String value, String message) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 
		Pattern p = Pattern.compile(regex);

		//Pattern p = Pattern.compile("^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$");
		Matcher m = p.matcher(value);
		if(!m.matches()) {
			this.isValid = false;
			errors.add(message);
		} 
		return this;
	}
	
	public Validator isPassword(String value, String message) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		Matcher m = p.matcher(value);
		if(m.find() && m.group().equals(value)) {
			
		} else {
			this.isValid = false;
			errors.add(message);
		}
		return this;
	}
	
	

}
