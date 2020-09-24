package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.FindSpend;
import com.example.demo.domain.Genre;
import com.example.demo.domain.Spend;
import com.example.demo.domain.SpendEdit;
import com.example.demo.repository.SpendRepository;

@Service
public class SpendService {

	@Autowired
	private SpendRepository repository;

	public List<Genre> getGenre(){

		return repository.getGenre();

	}


	//DBへ支出情報登録
	public void SpendAdd(Spend spend) {
		repository.SpendAdd(spend);
	}

	//DBへ変更情報登録
	public void SpendUpdate(SpendEdit spend) {
		repository.SpendUpdate(spend);
	}

	//DBから選択支出情報削除
	public void SpendDelete(int spendid,int userid) {
		repository.SpendDelete(spendid, userid);
	}

	//DBに登録済みの支出情報をspendidを元に取り出す
	public FindSpend getTargetSpend(int spendid,int userid){
		return repository.getTargetSpend(spendid, userid);
	}

	//食費を取得
	public List<FindSpend> getFoodSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getFoodSpend(userid,yearMonth,yearMonth2);
	}

	//水道を取得
	public List<FindSpend> getWaterSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getWaterSpend(userid,yearMonth,yearMonth2);
	}

	//電気を取得
	public List<FindSpend> getElectricitySpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getElectricitySpend(userid,yearMonth,yearMonth2);
	}

	//ガスを取得
	public List<FindSpend> getGasSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getGasSpend(userid,yearMonth,yearMonth2);
	}

	//日用品を取得
	public List<FindSpend> getNecessitiesSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getNecessitiesSpend(userid,yearMonth,yearMonth2);
	}

	//交通費を取得
	public List<FindSpend> getTraficSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getTraficSpend(userid,yearMonth,yearMonth2);
	}

	//交通費を取得
	public List<FindSpend> getEntertainmentSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getEntertainmentSpend(userid,yearMonth,yearMonth2);
	}

	//衣服・美容費を取得
	public List<FindSpend> getBeautySpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getBeautySpend(userid,yearMonth,yearMonth2);
	}

	//健康・医療費を取得
	public List<FindSpend> getHealthSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getHealthSpend(userid,yearMonth,yearMonth2);
	}

	//車・バイク費を取得
	public List<FindSpend> getVehicleSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getVehicleSpend(userid,yearMonth,yearMonth2);
	}

	//教養・教育費を取得
	public List<FindSpend> getEducationalSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getEducationalSpend(userid,yearMonth,yearMonth2);
	}

	//趣味・娯楽費を取得
	public List<FindSpend> getHobbySpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getHobbySpend(userid,yearMonth,yearMonth2);
	}

	//住宅費を取得
	public List<FindSpend> getHouseSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getHouseSpend(userid,yearMonth,yearMonth2);
	}

	//通信費を取得
	public List<FindSpend> getCommunicationSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getCommunicationSpend(userid,yearMonth,yearMonth2);
	}

	//税・社会保険を取得
	public List<FindSpend> getTaxSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getTaxSpend(userid,yearMonth,yearMonth2);
	}

	//保険を取得
	public List<FindSpend> getInsuranceSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getInsuranceSpend(userid,yearMonth,yearMonth2);
	}

	//その他を取得
	public List<FindSpend> getOtherSpend(int userid,Date yearMonth,Date yearMonth2){
		return repository.getOtherSpend(userid,yearMonth,yearMonth2);
	}


}
