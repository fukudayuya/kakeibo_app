package com.example.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class IncomeEdit {

	private int incomeid;
	private int userid;
	private int incomegenreid;
	private Date incomedate;
	private String title;
	private int price;
	private String contents;
}
