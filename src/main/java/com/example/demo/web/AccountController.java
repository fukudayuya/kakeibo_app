package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.domain.checkEmail;
import com.example.demo.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private HttpSession session;

	@Autowired
	private AccountService service;

	@ModelAttribute
	AccountForm setUpForm(){
		return new AccountForm();
	}
	//ユーザー新規登録画面へ
	@RequestMapping("/")
	public static String top() {

		return "newaccount";
	}


	//ユーザー新規登録確認画面へ
	@RequestMapping("/check")
	public String check(@Validated AccountForm form,BindingResult result) {

		String password = form.getPassword();
		String checkPasswprd = form.getCheckpassword();

		if(!(password.equals(checkPasswprd))) {
			ObjectError passwordError = new ObjectError("passwordError","パスワードと確認用パスワードが一致しません。");
			result.addError(passwordError);
			return AccountController.top();
		}

		//formから受け取った値を取得する入れ物
		User user = new User();

		//formの値をuserへコピー
		BeanUtils.copyProperties(form, user);

		session.setAttribute("accountForm", user);

		return "newaccountcheck";
	}

	@RequestMapping("/finish")
	public String finish(@Validated AccountForm form,BindingResult result) {

		User user = (User)session.getAttribute("accountForm");

		String name = user.getName();
		String email = user.getEmail();
		String password = user.getPassword();

		checkEmail CheckEmail = service.setAccount(name, password, email);

		if(CheckEmail != null) {
			ObjectError emailError = new ObjectError("emailError", "入力したメールアドレスはすでに登録されています");
			result.addError(emailError);
			return top();
		}

		session.removeAttribute("accountForm");

		return "newaccountfinish";
	}

	//月曜日はユーザー登録のエラー原因から開始

}
