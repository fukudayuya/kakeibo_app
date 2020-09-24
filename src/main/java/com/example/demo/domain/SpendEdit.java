package com.example.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SpendEdit {

	private int spendid;
	private int userid;
	private int spendgenreid;
	private Date spenddate;
	private String title;
	private int price;
	private String contents;
}
