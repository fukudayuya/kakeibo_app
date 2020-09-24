package com.example.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class FindIncome {

	private int incomeid;
	private int userid;
	private int genreid;
	private Date incomedate;
	private String title;
	private int price;
	private String contents;

}
