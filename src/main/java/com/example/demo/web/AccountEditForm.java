package com.example.demo.web;

import lombok.Data;

@Data
public class AccountEditForm {

	private int userid;


	private String name;


	private String email;


	private String password;


	private String checkpassword;
}
