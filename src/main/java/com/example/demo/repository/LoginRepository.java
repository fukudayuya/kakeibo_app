package com.example.demo.repository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.LoginItem;
@Repository
public class LoginRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	//ログイン用ローマッパー
	private static final RowMapper<LoginItem> loginRowMapper = (rs,i) ->{

		LoginItem loginItem = new LoginItem();

		loginItem.setUserid(rs.getInt("userid"));
		loginItem.setName(rs.getString("name"));
		loginItem.setEmail(rs.getString("email"));
		loginItem.setPassword(rs.getString("password"));

		return loginItem;

	};

	//ログインユーザー情報取得
	public LoginItem Login(String email,String password) {
		String sql = "select userid,name,email,password from users where email=:email and password=:password;";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("email", email).addValue("password", password);
		try {
			LoginItem loginItem = jdbc.queryForObject(sql, param, loginRowMapper);
			return loginItem;
		}catch(DataAccessException e) {
			return null;
		}

	}






}
