package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.FindIncome;
import com.example.demo.domain.Income;
import com.example.demo.domain.IncomeEdit;
import com.example.demo.domain.IncomeGenre;
import com.example.demo.repository.IncomeRepository;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository repository;

	public List<IncomeGenre> getGenre(){

		return repository.getGenre();

	}


	//DBへ収入情報登録
	public void IncomeAdd(Income income) {
		repository.IncomeAdd(income);
	}

	//DBへ収入変更情報登録
	public void IncomeUpdate(IncomeEdit income) {
		repository.IncomeUpdate(income);
	}

	//DBから選択収入情報削除
	public void IncomeDelete(int incomeid,int userid) {
		repository.IncomeDelete(incomeid, userid);
	}

	//DBに登録済みの支出情報をspendidを元に取り出す
	public FindIncome getTargetIncome(int income,int userid){
		return repository.getTargetIncome(income, userid);
	}

	//給与を取得
	public List<FindIncome> getSalaryIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getSalaryIncome(userid,yearMonth,yearMonth2);
	}

	//賞与を取得
	public List<FindIncome> getBonusIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getBonusIncome(userid,yearMonth,yearMonth2);
	}

	//副業を取得
	public List<FindIncome> getSideIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getSideIncome(userid,yearMonth,yearMonth2);
	}

	//年金を取得
	public List<FindIncome> getPensionIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getPensionIncome(userid,yearMonth,yearMonth2);
	}

	//配当を取得
	public List<FindIncome> getDividendIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getDividendIncome(userid,yearMonth,yearMonth2);
	}

	//不動産を取得
	public List<FindIncome> getRealestateIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getRealestateIncome(userid,yearMonth,yearMonth2);
	}

	//その他を取得
	public List<FindIncome> getOtherIncome(int userid,Date yearMonth,Date yearMonth2){
		return repository.getOtherIncome(userid,yearMonth,yearMonth2);
	}


}
