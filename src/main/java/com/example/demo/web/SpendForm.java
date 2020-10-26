package com.example.demo.web;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SpendForm {

	private int userid;
	@NotNull
	private int genreid;
	@NotNull
	private String spenddate;
	@NotNull
	private String title;
	@NotNull
	private String price;
	@NotNull
	private String contents;

}
