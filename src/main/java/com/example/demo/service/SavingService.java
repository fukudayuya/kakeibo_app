package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SavingRepository;

@Service
public class SavingService {

	@Autowired
	private SavingRepository repository;

	public void savingIns(int userid,int targetSavings) {
		repository.savingIns(userid, targetSavings);
	}

	//更新登録時、savingテーブルにセッションのsavingsidを元に入力情報を入れる。現在の貯金額は0で登録。スタッツを１で登録
	public void savingUpdate(int savingsid,int targetSavings) {
		repository.savingUpdate(savingsid, targetSavings);
	}

	public void savingEdit(int userid,int targetSavings) {
		repository.savingEdit(userid, targetSavings);
	}


}
