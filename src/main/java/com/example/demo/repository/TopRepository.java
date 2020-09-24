package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.GetConfirm;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.SerchSaving;
import com.example.demo.domain.incomeTotal;
import com.example.demo.domain.spendTotal;

@Repository
public class TopRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate template;

	//支出トータル額の取得
	private static final RowMapper<spendTotal> spendTotalRowMapper = (rs,i) ->{
		spendTotal totalItem = new spendTotal();

		totalItem.setSpendSumTotal(rs.getInt("sumtotal"));

		return totalItem;

	};

	//収入トータル額の取得
		private static final RowMapper<incomeTotal> incomeTotalRowMapper = (rs,i) ->{
			incomeTotal totalIncomeItem = new incomeTotal();

			totalIncomeItem.setIncomeSumTotal(rs.getInt("incometotal"));

			return totalIncomeItem;

		};



	//savingテーブルにuserid,targetSavings,nowSavings,totalSavings,savingStatus情報があるか確認するローマッパー
	private static final RowMapper<LoginItem> savingItemRowMapper = (rs,i) ->{
		LoginItem loginItem = new LoginItem();

		loginItem.setUserid(rs.getInt("userid"));
		loginItem.setName(rs.getString("name"));
		loginItem.setEmail(rs.getString("email"));
		loginItem.setPassword(rs.getString("password"));
		loginItem.setTargetSavings(rs.getInt("targetSavings"));
		loginItem.setNowSavings(rs.getInt("nowSavings"));
		loginItem.setTotalSavings(rs.getInt("totalSavings"));
		loginItem.setSavingStatus(rs.getInt("savingStatus"));
		loginItem.setSavingsid(rs.getInt("savingsid"));

		return loginItem;

	};

	//目標金額達成状況確認
	private static final RowMapper<SerchSaving> serchSavingsRowMapper = (rs,i) ->{
		SerchSaving saving = new SerchSaving();

		saving.setTargetsavings(rs.getInt("targetsavings"));
		saving.setNowsavings(rs.getInt("nowsavings"));

		return saving;

	};

	//収支確定ステータス、収支確定idの取得
	private static final RowMapper<GetConfirm> getConfirmRowMapper = (rs,i) ->{
		GetConfirm confirmGet = new GetConfirm();

		confirmGet.setConfirmid(rs.getInt("confirmid"));
		confirmGet.setConfirmStatus(rs.getInt("confirmStatus"));

		return confirmGet;
	};


	//useridからsavingテーブルの情報を取得。savingテーブルに情報を登録していない場合はnullを返却
	public LoginItem findSaving(int userid) {
		//innerjoinでLoginItemに格納するユーザー情報とsaving情報を取得
		String sql = "select users.userid,name,email,password,targetSavings,nowSavings,totalSavings,savingStatus,savingsid from users inner join savings " +
				"on users.userid = savings.userid where savings.userid = :userid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid);
		try {
			LoginItem loginItem = jdbc.queryForObject(sql, param, savingItemRowMapper);

			return loginItem;
		}catch(DataAccessException e) {
			return null;//savingテーブルにデータがない場合
		}

	}

	//支出の合計額を取得
	public spendTotal sumSpend(int userid,Date yearMonth,Date yearMonth2) {
		String sql ="select sum(price) as sumtotal from spend where userid = :userid and spenddate between :yearMonth and :yearMonth2 ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);
		spendTotal totalSpend = jdbc.queryForObject(sql, param, spendTotalRowMapper);

		return totalSpend;

	}

	//収入の合計額を取得
	public incomeTotal sumIncome(int userid,Date yearMonth,Date yearMonth2) {
		String sql ="select sum(price) as incometotal from income where userid = :userid and incomedate between :yearMonth and :yearMonth2 ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("yearMonth", yearMonth).addValue("yearMonth2", yearMonth2);
		incomeTotal totalIncome = jdbc.queryForObject(sql, param, incomeTotalRowMapper);

		return totalIncome;
	}

	//支出グラフのラベルを取得
	public List<String> label(){
		String sql = "select spendgenrename from spendgenre;";
		List<String> label = template.queryForList(sql, String.class);

		return label;

	}

	//支出グラフのデータを取得
	public List<Integer> spendData(int userid,Date yearMonth,Date yearMonth2){
		String sql = "select COALESCE(price,0) from "
				+ "(select spendgenreid,sum(price) as price from spend where userid = :userid and spenddate between :yearMonth and :yearMonth2 "
				+ "group by spendgenreid order by spendgenreid) as sumgroup "
				+ "right join spendgenre on spendgenre.spendgenreid = sumgroup.spendgenreid;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("yearMonth", yearMonth)
				.addValue("yearMonth2", yearMonth2);
		List<Integer> data = jdbc.queryForList(sql,param,Integer.class);

		return data;
	}

	//収支確定ステータス取得
	public GetConfirm findConfirm(int userid,Date confirmDate) {
		String sql = "select confirmid,confirmstatus from confirm where userid = :userid and confirmdate = :confirmDate";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("confirmDate", confirmDate);
		GetConfirm confirm = jdbc.queryForObject(sql, param, getConfirmRowMapper);

		return confirm;
	}

	//収支確定ステータスを新規登録し1(確定済)にする＆savingsテーブルの貯金データ更新(現在の貯金額に反映する)
	public void setConfirm(int userid,Date confirmdate,int confirmprice,int savingsid) {
					//対象年月の収支情報を新規登録し、確定する処理
		String sql = "insert into confirm(userid,confirmdate,confirmstatus) values(:userid,:confirmdate,1);"
				//sessionのsavingsidを元に収支情報を貯金データを更新
				+ "update savings set nowsavings = nowsavings + :confirmprice,totalsavings = totalsavings + :confirmprice "
				+ "where savingsid = :savingsid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("confirmdate", confirmdate)
				.addValue("confirmprice", confirmprice).addValue("savingsid", savingsid);
		jdbc.update(sql, param);

	}

	//収支確定ステータスを新規登録し1(確定済)にする＆savingsテーブルの貯金データ更新(現在の貯金額に反映しない)
		public void setConfirm2(int userid,Date confirmdate,int confirmprice,int savingsid) {
						//対象年月の収支情報を新規登録し、確定する処理
			String sql = "insert into confirm(userid,confirmdate,confirmstatus) values(:userid,:confirmdate,1);"
					//sessionのsavingsidを元に収支情報を貯金データを更新
					+ "update savings set totalsavings = totalsavings + :confirmprice "
					+ "where savingsid = :savingsid";
			SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid).addValue("confirmdate", confirmdate)
					.addValue("confirmprice", confirmprice).addValue("savingsid", savingsid);
			jdbc.update(sql, param);

		}

	//confirmidが0ではなく(過去に収支登録済)収支確定ステータスを更新登録し1(確定済)にする際(現在の貯金額への反映はあり)
	public void updateConfirm(int confirmid,int confirmprice,int savingsid) {
					//対象年月の収支情報を更新し、確定する処理
		String sql = "update confirm set confirmStatus = 1 where confirmid = :confirmid;"
				//sessionのsavingsidを元に収支情報を貯金データを更新
				+ "update savings set nowsavings = nowsavings + :confirmprice,totalsavings = totalsavings + :confirmprice "
				+ "where savingsid = :savingsid;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("confirmid", confirmid).addValue("confirmprice", confirmprice)
				.addValue("savingsid", savingsid);;
		jdbc.update(sql, param);

	}

	//confirmidが0ではなく(過去に収支登録済)収支確定ステータスを更新登録し1(確定済)にする際(現在の貯金額への反映はなし)
		public void updateConfirm2(int confirmid,int confirmprice,int savingsid) {
						//対象年月の収支情報を更新し、確定する処理
			String sql = "update confirm set confirmStatus = 1 where confirmid = :confirmid;"
					//sessionのsavingsidを元に収支情報を貯金データを更新
					+ "update savings set totalsavings = totalsavings + :confirmprice "
					+ "where savingsid = :savingsid;";

			SqlParameterSource param = new MapSqlParameterSource().addValue("confirmid", confirmid).addValue("confirmprice", confirmprice)
					.addValue("savingsid", savingsid);;
			jdbc.update(sql, param);

		}

	//目標金額達成状況確認
	public SerchSaving savingResult(int savingsid) {
		String sql ="select targetsavings,nowsavings from savings where savingsid = :savingsid ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("savingsid", savingsid);
		SerchSaving serch = jdbc.queryForObject(sql, param, serchSavingsRowMapper);
		return serch;

	}

	//目標達成時、savingstatusを２に変更(現在の貯金額への反映はなし)
	public void savingStatusUpdate(int savingsid) {
		String sql = "update savings set savingstatus = 2 where savingsid = :savingsid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("savingsid", savingsid);
		jdbc.update(sql, param);
	}

	//収支確定ステータスを解除(0)にする(現在の貯金額への反映はする)
	public void unlockConfirm2(int confirmid,int confirmprice,int savingsid) {
		String sql = "update confirm set confirmstatus = 0 where confirmid = :confirmid;"
				+ "update savings set totalsavings = totalsavings - :confirmprice "
				+ "where savingsid = :savingsid;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("confirmid", confirmid)
				.addValue("confirmprice", confirmprice).addValue("savingsid", savingsid);
		jdbc.update(sql, param);
	}

	//収支確定ステータスを解除(0)にする
		public void unlockConfirm(int confirmid,int confirmprice,int savingsid) {
			String sql = "update confirm set confirmstatus = 0 where confirmid = :confirmid;"
					+ "update savings set nowsavings = nowsavings - :confirmprice,totalsavings = totalsavings - :confirmprice "
					+ "where savingsid = :savingsid;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("confirmid", confirmid)
					.addValue("confirmprice", confirmprice).addValue("savingsid", savingsid);
			jdbc.update(sql, param);
		}

	//収支確定ステータスを解除時、目標金額達成していない場合、savingstatusを1に更新
	public void savingStatusDowndate(int savingsid) {
		String sql = "update savings set savingstatus = 1 where savingsid = :savingsid;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("savingsid", savingsid);
		jdbc.update(sql, param);
	}

}
