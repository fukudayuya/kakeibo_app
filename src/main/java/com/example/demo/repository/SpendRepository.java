package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.FindSpend;
import com.example.demo.domain.Genre;
import com.example.demo.domain.Spend;
import com.example.demo.domain.SpendEdit;

@Repository
public class SpendRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;


	//支出ジャンルを取得するローマッパー
	private static final RowMapper<Genre> getGenreRowMapper = (rs,i) ->{

		Genre genre = new Genre();

		genre.setSpendgenreid(rs.getInt("spendgenreid"));
		genre.setSpendgenrename(rs.getString("spendgenrename"));

		return genre;
	};

	//ジャンル別支出一覧を取得
	private static final RowMapper<FindSpend> findSpendRowMapper = (rs,i) ->{
		FindSpend findspend = new FindSpend();

		findspend.setSpendid(rs.getInt("spendid"));
		findspend.setGenreid(rs.getInt("spendgenreid"));
		findspend.setTitle(rs.getString("title"));
		findspend.setUserid(rs.getInt("userid"));
		findspend.setSpenddate(rs.getDate("spenddate"));
		findspend.setPrice(rs.getInt("price"));
		findspend.setContents(rs.getString("contents"));

		return findspend;
	};


	//genreテーブルからジャンルを取得（セレクトボックス用）
	public List<Genre> getGenre(){

		String sql = "select spendgenreid,spendgenrename from spendgenre";
		List<Genre> genre = jdbc.query(sql, getGenreRowMapper);

		return genre;

	}


	//支出情報を登録
	public void SpendAdd(Spend spend) {

		String sql = "insert into spend(spendgenreid,title,userid,spenddate,contents,price)"
				+ "values(:genreid,:title,:userid,:spenddate,:contents,:price);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(spend);

		jdbc.update(sql, param);

	}

	//支出編集情報を登録
		public void SpendUpdate(SpendEdit spend) {

			String sql = "update spend set spendgenreid = :spendgenreid,title = :title,spenddate = :spenddate,contents = :contents,price = :price "
					+ "where spendid = :spendid and userid = :userid;";

			SqlParameterSource param = new BeanPropertySqlParameterSource(spend);

			try {
				jdbc.update(sql, param);
				System.out.println("成功");
			}catch(Exception e){
				System.out.print(e);
			}

		}

	//支出情報削除
		public void SpendDelete(int spendid,int userid) {
			String sql = "delete from spend where spendid = :spendid and userid = :userid";
			SqlParameterSource param = new MapSqlParameterSource().addValue("spendid", spendid).addValue("userid", userid);

			jdbc.update(sql, param);
		}

	//DBに登録済みの支出詳細画面に出力する情報をspendidを元に取り出す
	public FindSpend getTargetSpend(int spendid,int userid){

		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendid =:spendid and userid = :userid; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendid", spendid).addValue("userid", userid);
		FindSpend findTargetSpend = jdbc.queryForObject(sql, param, findSpendRowMapper);

		return findTargetSpend;
	}

	//食費を取得
	public List<FindSpend> getFoodSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 1).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//水道を取得
	public List<FindSpend> getWaterSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 2).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//電気を取得
	public List<FindSpend> getElectricitySpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 3).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//ガスを取得
	public List<FindSpend> getGasSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 4).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}
	//日用品を取得
	public List<FindSpend> getNecessitiesSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 5).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//交通費を取得
	public List<FindSpend> getTraficSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 6).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}
	//交際費を取得
	public List<FindSpend> getEntertainmentSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 7).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//衣服美容費を取得
	public List<FindSpend> getBeautySpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 8).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//健康・医療費を取得
	public List<FindSpend> getHealthSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 9).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//車・バイク費を取得
	public List<FindSpend> getVehicleSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 10).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//教養・教育費を取得
	public List<FindSpend> getEducationalSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 11).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//趣味・娯楽費を取得
	public List<FindSpend> getHobbySpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 12).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//住宅費を取得
	public List<FindSpend> getHouseSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 13).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//通信費を取得
	public List<FindSpend> getCommunicationSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 14).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//税・社会保険を取得
	public List<FindSpend> getTaxSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 15).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//保険を取得
	public List<FindSpend> getInsuranceSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 16).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}

	//その他を取得
	public List<FindSpend> getOtherSpend(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select spendid,spendgenreid,title,userid,spenddate,contents,price from spend where spendgenreid =:spendgenreid and userid = :userid "
				+ "and spenddate between :yearMonth and :yearMonth2 order by spenddate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("spendgenreid", 17).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindSpend> findSpend = jdbc.query(sql, param, findSpendRowMapper);

		return findSpend;
	}
}
