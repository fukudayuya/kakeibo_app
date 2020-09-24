package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.GetConfirm;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.SerchSaving;
import com.example.demo.domain.incomeTotal;
import com.example.demo.domain.spendTotal;
import com.example.demo.repository.TopRepository;

@Service
public class TopService {

	@Autowired
	private TopRepository repository;

	//貯金額取得
	public LoginItem findSaving(int userid) {
		return repository.findSaving(userid);
	}

	//支出グラフのラベル
	public List<String> label(){
		return repository.label();
	}

	//支出グラフのデータ
	public List<Integer> spendData(int userid,Date yearMonth,Date yearMonth2){
		return repository.spendData(userid, yearMonth, yearMonth2);
	}

	//支出合計額取得
	public spendTotal sumSpend(int userid,Date yearMonth,Date yearMonth2) {
		return repository.sumSpend(userid, yearMonth, yearMonth2);
	}

	//収入合計額取得
	public incomeTotal sumIncome(int userid,Date yearMonth,Date yearMonth2) {
		return repository.sumIncome(userid, yearMonth, yearMonth2);
	}

	//収支確定ステータスの取得
	public GetConfirm findConfirm(int userid,Date confirmDate) {
		return repository.findConfirm(userid, confirmDate);
	}

	//収支確定ステータスを新規登録し1(確定済)にする際。現在の貯金額は反映。
	public void setConfirm(int userid,Date confirmdate,int confirmprice,int savingsid) {
		repository.setConfirm(userid, confirmdate,confirmprice,savingsid);
	}

	//収支確定ステータスを新規登録し1(確定済)にする際。現在の貯金額は反映しない。
		public void setConfirm2(int userid,Date confirmdate,int confirmprice,int savingsid) {
			repository.setConfirm2(userid, confirmdate,confirmprice,savingsid);
		}

	//confirmidが0ではなく(過去に収支登録済)収支確定ステータスを更新登録し1(確定済)にする際。現在の貯金額は反映。
	public void updateConfirm(int confirmid,int confirmprice,int savingsid) {
		repository.updateConfirm(confirmid, confirmprice, savingsid);
	}

	//confirmidが0ではなく(過去に収支登録済)収支確定ステータスを更新登録し1(確定済)にする際。現在の貯金額は反映しない。
		public void updateConfirm2(int confirmid,int confirmprice,int savingsid) {
			repository.updateConfirm2(confirmid, confirmprice, savingsid);
		}

	//収支確定ステータスを解除(0)にする。現在の貯金額は反映。
	public void unlockConfirm(int confirmid,int confirmprice,int savingsid) {
		repository.unlockConfirm(confirmid, confirmprice, savingsid);
	}

	//収支確定ステータスを解除(0)にする。現在の貯金額は反映しない。
		public void unlockConfirm2(int confirmid,int confirmprice,int savingsid) {
			repository.unlockConfirm2(confirmid, confirmprice, savingsid);
	}

	//目標金額達成状況
	public SerchSaving savingResult(int savingsid) {
		return repository.savingResult(savingsid);
	}

	//目標金額達成状況確認
	public void savingStatusUpdate(int savingsid) {
		repository.savingStatusUpdate(savingsid);
	}

	//収支確定ステータス解除後、目標金額達成していない場合、savingstatusを1に更新
	public void savingStatusDowndate(int savingsid) {
		repository.savingStatusDowndate(savingsid);
	}

}
