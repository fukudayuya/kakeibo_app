package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.checkEmail;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	//ユーザー登録
	public checkEmail setAccount(String name,String password,String email) {

		return repository.setAccount(name, password, email);
	}

	//ユーザー変更
	public checkEmail editAccount(int userid,String name,String email,String password) {
		return repository.editAccount(userid, name, email, password);
	}


}
