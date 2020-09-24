package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.checkEmail;

@Repository
public class AccountRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	//emailがすでに登録済みでないかの確認
	private static final RowMapper<checkEmail> emailRowMapper = (rs,i) ->{
		checkEmail email = new checkEmail();
		email.setEmail(rs.getString("email"));
		return email;
	};

	//ユーザー登録・savingテーブル作成
	public checkEmail setAccount(String name,String password,String email) {
		String emailsql ="select email from users where email = :email";
		SqlParameterSource checkParam = new MapSqlParameterSource().addValue("email", email);

		try {
			checkEmail Checkemail = jdbc.queryForObject(emailsql, checkParam, emailRowMapper);
			return Checkemail;
		}catch(EmptyResultDataAccessException e){
			//usersテーブルへユーザー情報を登録
			String insertsql = "insert into users(name,email,password) values (:name,:email,:password);";
			SqlParameterSource insertparam = new MapSqlParameterSource()
					.addValue("name",name).addValue("email",email).addValue("password", password);
			jdbc.update(insertsql, insertparam);

			return null;

		}


	}

	//ユーザー情報変更
	public checkEmail editAccount(int userid,String name,String email,String password) {

		String emailsql ="select email from users where email = :email";
		SqlParameterSource checkParam = new MapSqlParameterSource().addValue("email", email);

		try {
			checkEmail Checkemail = jdbc.queryForObject(emailsql, checkParam, emailRowMapper);
			return Checkemail;
		}catch(EmptyResultDataAccessException e){
			String updatesql = "update users set name=:name ,email=:email,password=:password where userid = :userid";
			SqlParameterSource updateparam = new MapSqlParameterSource()
					.addValue("name", name).addValue("email", email).addValue("password", password).addValue("userid", userid);
			jdbc.update(updatesql, updateparam);
			return null;
		}

	}


}
