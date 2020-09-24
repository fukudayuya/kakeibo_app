package com.example.demo.web;

import lombok.Data;

@Data
public class SpendForm {

	private int userid;
	private int genreid;
	private String spenddate;
	private String title;
	private String price;
	private String contents;

}
