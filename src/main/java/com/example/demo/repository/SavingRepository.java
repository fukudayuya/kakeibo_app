package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SavingRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	//新規登録時、savingテーブルにセッションのuseridと入力情報を入れる。現在の貯金額と合計貯金額は0で登録。スタッツを１で登録。
	public void savingIns(int userid,int targetSavings) {
		String sql = "insert into savings (userid,targetSavings,nowSavings,totalSavings,savingStatus) values (:userid,:targetSavings,0,0,1);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid",userid).addValue("targetSavings", targetSavings);
		jdbc.update(sql, param);
	}

	//目標達成後の更新登録時、savingテーブルにセッションのsavingsidを元に入力情報を入れる。現在の貯金額は0で登録。スタッツを１で登録。
	public void savingUpdate(int savingsid,int targetSavings) {
		String sql = "update savings set targetsavings = :targetSavings,nowsavings = 0,savingstatus = 1 where savingsid = :savingsid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("savingsid",savingsid).addValue("targetSavings", targetSavings);
		jdbc.update(sql, param);
	}

	//変更登録時、savingテーブルにセッションのuseridを引数に入力情報を入れる。現在の貯金額と合計貯金額は変更なしで登録。スタッツを１で登録。
		public void savingEdit(int userid,int targetSavings) {
			String sql = "update savings set targetSavings = :targetSavings where userid = :userid;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("userid",userid).addValue("targetSavings", targetSavings);
			jdbc.update(sql, param);
		}

}
