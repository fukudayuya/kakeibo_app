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

import com.example.demo.domain.FindIncome;
import com.example.demo.domain.Income;
import com.example.demo.domain.IncomeEdit;
import com.example.demo.domain.IncomeGenre;

@Repository
public class IncomeRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;


	//収入ジャンルを取得するローマッパー
	private static final RowMapper<IncomeGenre> getGenreRowMapper = (rs,i) ->{

		IncomeGenre genre = new IncomeGenre();

		genre.setIncomegenreid(rs.getInt("incomegenreid"));
		genre.setIncomegenrename(rs.getString("incomegenrename"));

		return genre;
	};

	//ジャンル別収入一覧を取得
	private static final RowMapper<FindIncome> findIncomeRowMapper = (rs,i) ->{
		FindIncome findincome = new FindIncome();

		findincome.setIncomeid(rs.getInt("incomeid"));
		findincome.setGenreid(rs.getInt("incomegenreid"));
		findincome.setTitle(rs.getString("title"));
		findincome.setUserid(rs.getInt("userid"));
		findincome.setIncomedate(rs.getDate("incomedate"));
		findincome.setPrice(rs.getInt("price"));
		findincome.setContents(rs.getString("contents"));

		return findincome;
	};


	//genreテーブルからジャンルを取得（セレクトボックス用）
	public List<IncomeGenre> getGenre(){

		String sql = "select incomegenreid,incomegenrename from incomegenre";
		List<IncomeGenre> genre = jdbc.query(sql, getGenreRowMapper);

		return genre;

	}


	//収入情報を登録
	public void IncomeAdd(Income income) {

		String sql = "insert into income(incomegenreid,title,userid,incomedate,contents,price)"
				+ "values(:genreid,:title,:userid,:spenddate,:contents,:price);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(income);

		jdbc.update(sql, param);

	}

	//収入編集情報を登録
		public void IncomeUpdate(IncomeEdit income) {

			String sql = "update income set incomegenreid = :incomegenreid,title = :title,incomedate = :incomedate,contents = :contents,price = :price "
					+ "where incomeid = :incomeid and userid = :userid;";

			SqlParameterSource param = new BeanPropertySqlParameterSource(income);

			try {
				jdbc.update(sql, param);
				System.out.println("成功");
			}catch(Exception e){
				System.out.print(e);
			}

		}

	//収入情報削除
		public void IncomeDelete(int incomeid,int userid) {
			String sql = "delete from income where incomeid = :incomeid and userid = :userid";
			SqlParameterSource param = new MapSqlParameterSource().addValue("incomeid", incomeid).addValue("userid", userid);

			jdbc.update(sql, param);
		}

	//DBに登録済みの収入詳細情報を編集・削除する情報をspendidを元に取り出す
	public FindIncome getTargetIncome(int incomeid,int userid){

		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomeid =:incomeid and userid = :userid; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomeid", incomeid).addValue("userid", userid);
		FindIncome findTargetSpend = jdbc.queryForObject(sql, param, findIncomeRowMapper);

		return findTargetSpend;
	}

	//給与を取得
	public List<FindIncome> getSalaryIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+ "and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 1).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findincome = jdbc.query(sql, param, findIncomeRowMapper);

		return findincome;
	}

	//賞与を取得
	public List<FindIncome> getBonusIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 2).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findincome= jdbc.query(sql, param, findIncomeRowMapper);

		return findincome;
	}

	//副業を取得
	public List<FindIncome> getSideIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 3).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findIncome = jdbc.query(sql, param, findIncomeRowMapper);

		return findIncome;
	}

	//年金を取得
	public List<FindIncome> getPensionIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 4).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findIncome = jdbc.query(sql, param, findIncomeRowMapper);

		return findIncome;
	}
	//配当を取得
	public List<FindIncome> getDividendIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 5).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findIncome = jdbc.query(sql, param, findIncomeRowMapper);

		return findIncome;
	}

	//不動産を取得
	public List<FindIncome> getRealestateIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 6).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findIncome = jdbc.query(sql, param, findIncomeRowMapper);

		return findIncome;
	}

	//その他を取得
	public List<FindIncome> getOtherIncome(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select incomeid,incomegenreid,title,userid,incomedate,contents,price from income where incomegenreid =:incomegenreid and userid = :userid "
				+"and incomedate between :yearMonth and :yearMonth2 order by incomedate; ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("incomegenreid", 7).addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);

		List<FindIncome> findIncome = jdbc.query(sql, param, findIncomeRowMapper);

		return findIncome;
	}
}
