package com.estore.drugstore.dto;

import com.estore.drugstore.model.User;

public class UserDto {
	private Integer id;
    private String fName;
    private String lName;
    private String email;
    private String password;

    public UserDto(Integer id, String fName, String lName, String email, String password) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}
    public UserDto(User user) {
    	this.setfName(user.getfName());
    	this.setlName(user.getlName());
    	this.setEmail(user.getEmail());
    	this.setPassword(user.getPassword());
    }
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
