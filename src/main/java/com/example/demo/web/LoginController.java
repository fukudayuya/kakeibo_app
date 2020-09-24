package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.LoginItem;
import com.example.demo.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService service;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	//ログイン画面へ
	@RequestMapping("/")
	public String login() {
		session.removeAttribute("loginItem");
		session.removeAttribute("savingItem");
		return "loginpage";
	}

	//ログインしてトップページへ
	@RequestMapping("/check")
	public String check(@Validated LoginForm form,BindingResult result) {

		String email = form.getEmail();
		String password = form.getPassword();

		LoginItem loginItem = service.Login(email, password);
		if(loginItem == null) {
			ObjectError error = new ObjectError("loginError", "ログイン情報に誤りがあります");
			result.addError(error);
			return login();
		}
		session.setAttribute("loginItem", loginItem);

		return "redirect:/top/";
	}

	@RequestMapping("/logout")
	public String logout() {

		session.removeAttribute("loginItem");

		return login();
	}

}
