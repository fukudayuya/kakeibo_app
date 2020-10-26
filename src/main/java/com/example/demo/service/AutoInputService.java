package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.autoIncomeInfo;
import com.example.demo.domain.autoInput;
import com.example.demo.domain.autoSpendInfo;
import com.example.demo.repository.AutoInputRepository;

@Service
public class AutoInputService {

	@Autowired
	private AutoInputRepository repository;

	//DBに番号１の自動収入登録情報があるかの確認
	public autoIncomeInfo getAutoIncome(int id){
		return repository.getAutoIncome1(id);
	}

	//DBに番号2の自動収入登録情報があるかの確認
	public autoIncomeInfo getAutoIncome2(int id){
		return repository.getAutoIncome2(id);
	}

	//DBに番号3の自動収入登録情報があるかの確認
	public autoIncomeInfo getAutoIncome3(int id){
		return repository.getAutoIncome3(id);
	}

	//DBへ自動登録収入情報初期登録
	public void autoIncomeNewAdd(autoInput input) {
		repository.autoIncomeNewAdd(input);
	}

	//DBへ自動登録収入情報更新登録
	public void autoIncomeUpdate(autoInput input) {
		repository.autoIncomeUpdate(input);
	}

	//DBに番号１の自動支出登録情報があるかの確認
	public autoSpendInfo getAutoSpend1(int id){
		return repository.getAutoSpend1(id);
	}

	//DBに番号2の自動支出登録情報があるかの確認
	public autoSpendInfo getAutoSpend2(int id){
		return repository.getAutoSpend2(id);
	}

	//DBに番号3の自動支出登録情報があるかの確認
	public autoSpendInfo getAutoSpend3(int id){
		return repository.getAutoSpend3(id);
	}

	//DBに番号4の自動支出登録情報があるかの確認
	public autoSpendInfo getAutoSpend4(int id){
		return repository.getAutoSpend4(id);
	}

	//DBに番号5の自動支出登録情報があるかの確認
	public autoSpendInfo getAutoSpend5(int id){
		return repository.getAutoSpend5(id);
	}




	//DBへ自動登録支出情報初期登録
	public void autoSpendNewAdd(autoInput input) {
		repository.autoSpendNewAdd(input);
	}

	//DBへ自動登録支出情報更新登録
	public void autoSpendUpdate(autoInput input) {
		repository.autoSpendUpdate(input);
	}
}
