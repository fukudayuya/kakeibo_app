package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.autoIncomeInfo;
import com.example.demo.domain.autoInput;
import com.example.demo.domain.autoSpendInfo;

@Repository
public class AutoInputRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	//自動収入登録情報取得
		private static final RowMapper<autoIncomeInfo> findAutoIncomeRowmapper = (rs,i) ->{
			autoIncomeInfo autoIncome = new autoIncomeInfo();

			autoIncome.setIncomegenreid(rs.getInt("incomegenreid"));
			autoIncome.setTitle(rs.getString("title"));
			autoIncome.setContents(rs.getString("contents"));
			autoIncome.setPrice(rs.getInt("price"));

			return autoIncome;
		};

	//自動支出登録情報取得
		private static final RowMapper<autoSpendInfo> findAutoSpendRowmapper = (rs,i) ->{
			autoSpendInfo autoSpend = new autoSpendInfo();

			autoSpend.setSpendgenreid(rs.getInt("Spendgenreid"));
			autoSpend.setTitle(rs.getString("title"));
			autoSpend.setContents(rs.getString("contents"));
			autoSpend.setPrice(rs.getInt("price"));

			return autoSpend;
		};

	//番号１の自動登録収入情報を取得
	public autoIncomeInfo getAutoIncome1(int id){
		String sql = "select incomegenreid,title,contents,price from autoinput where userid = :id and inputsetid = 1;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		try {
		autoIncomeInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoIncomeRowmapper);
			return AutoIncomeInfo;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	//番号2の自動登録収入情報を取得
	public autoIncomeInfo getAutoIncome2(int id){
		String sql = "select incomegenreid,title,contents,price from autoinput where userid = :id and inputsetid = 2;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		try {
			autoIncomeInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoIncomeRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
	}

	//番号3の自動登録収入情報を取得
	public autoIncomeInfo getAutoIncome3(int id){
		String sql = "select incomegenreid,title,contents,price from autoinput where userid = :id and inputsetid = 3;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		try {
			autoIncomeInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoIncomeRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
	}

	//自動登録収入情報を初期登録
	public void autoIncomeNewAdd(autoInput input) {

		String sql = "insert into autoinput(inputsetid,incomegenreid,title,userid,contents,price)"
				+ "values(:inputsetid,:genreid,:title,:userid,:contents,:price);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(input);

		jdbc.update(sql, param);

	}

	//自動登録収入情報を更新登録
		public void autoIncomeUpdate(autoInput input) {

			String sql = "update autoinput set inputsetid = :inputsetid,incomegenreid = :genreid,title = :title,contents = :contents,price = :price"
					+ " where userid = :userid and inputsetid = :inputsetid;";

			SqlParameterSource param = new BeanPropertySqlParameterSource(input);

			jdbc.update(sql, param);

		}

	//番号１の自動登録支出情報を取得
		public autoSpendInfo getAutoSpend1(int id){
			String sql = "select spendgenreid,title,contents,price from autospend where userid = :id and inputsetid = 1;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			try {
			autoSpendInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoSpendRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
		}

		//番号2の自動登録支出情報を取得
		public autoSpendInfo getAutoSpend2(int id){
			String sql = "select spendgenreid,title,contents,price from autospend where userid = :id and inputsetid = 2;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			try {
				autoSpendInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoSpendRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
		}

		//番号3の自動登録支出情報を取得
		public autoSpendInfo getAutoSpend3(int id){
			String sql = "select spendgenreid,title,contents,price from autospend where userid = :id and inputsetid = 3;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			try {
				autoSpendInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoSpendRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
		}

		//番号4の自動登録支出情報を取得
		public autoSpendInfo getAutoSpend4(int id){
			String sql = "select spendgenreid,title,contents,price from autospend where userid = :id and inputsetid = 4;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			try {
				autoSpendInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoSpendRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
		}

		//番号5の自動登録支出情報を取得
		public autoSpendInfo getAutoSpend5(int id){
			String sql = "select spendgenreid,title,contents,price from autospend where userid = :id and inputsetid = 5;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			try {
				autoSpendInfo AutoIncomeInfo = jdbc.queryForObject(sql, param, findAutoSpendRowmapper);
				return AutoIncomeInfo;
			}catch(EmptyResultDataAccessException e) {
				return null;
			}
		}

		//自動登録収入情報を初期登録
		public void autoSpendNewAdd(autoInput input) {

			String sql = "insert into autospend(inputsetid,spendgenreid,title,userid,contents,price)"
					+ "values(:inputsetid,:genreid,:title,:userid,:contents,:price);";

			SqlParameterSource param = new BeanPropertySqlParameterSource(input);

			jdbc.update(sql, param);

		}

		//自動登録収入情報を更新登録
		public void autoSpendUpdate(autoInput input) {

			String sql = "update autospend set inputsetid = :inputsetid,spendgenreid = :genreid,title = :title,contents = :contents,price = :price"
						+ " where userid = :userid and inputsetid = :inputsetid;";

			SqlParameterSource param = new BeanPropertySqlParameterSource(input);

			jdbc.update(sql, param);

		}




}
