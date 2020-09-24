package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

public class UserEdit {

	@Getter
	@Setter
	private int userid;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private String checkpassword;
}
