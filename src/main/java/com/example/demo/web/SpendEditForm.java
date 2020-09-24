package com.example.demo.web;

import lombok.Data;

@Data
public class SpendEditForm {

	private int spendid;
	private int userid;
	private int spendgenreid;
	private String spenddate;
	private String title;
	private String price;
	private String contents;

}
