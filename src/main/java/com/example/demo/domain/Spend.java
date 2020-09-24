package com.example.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Spend {

	private int userid;
	private int genreid;
	private Date spenddate;
	private String title;
	private int price;
	private String contents;
}
