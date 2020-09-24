package com.example.demo.web;

import lombok.Data;

@Data
public class IncomeEditForm {

	private int incomeid;
	private int userid;
	private int incomegenreid;
	private String incomedate;
	private String title;
	private int price;
	private String contents;

}
