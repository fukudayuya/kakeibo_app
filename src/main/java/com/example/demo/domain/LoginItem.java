package com.example.demo.domain;

import lombok.Data;

@Data
public class LoginItem {

	private int userid;
	private String name;
	private String email;
	private String password;
	private int targetSavings;
	private int nowSavings;
	private int totalSavings;
	private int savingStatus;
	private int savingsid;

}
