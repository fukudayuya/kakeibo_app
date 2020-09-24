package com.example.demo.domain;

import lombok.Data;

@Data
public class SavingItem {

	private int userid;
	private int targetSavings;
	private int nowSavings;
	private int totalSavings;
	private int savingStatus;


}
