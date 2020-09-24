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

import com.example.demo.domain.LoginItem;
import com.example.demo.domain.checkEmail;
import com.example.demo.service.AccountService;

@Controller
@RequestMapping("/accountEdit")
public class AccountEditController {

	@Autowired
	private HttpSession session;

	@Autowired
	private AccountService service;

	@ModelAttribute
	AccountEditForm setUpForm() {
		return new AccountEditForm();
	}

	@RequestMapping("/")
	public String AccountEdit() {
		LoginItem edit = (LoginItem)session.getAttribute("loginItem");

		if(edit == null) {
			return "redirect:/login/";
		}

		session.setAttribute("accountEdit", edit);

		return "AccountEdit";
	}

	@RequestMapping("/check")
	public String accountEditcheck(@Validated AccountEditForm form,BindingResult result) {

		String password = form.getPassword();
		String checkpassword = form.getCheckpassword();

		System.out.println("userid(check):"+form.getUserid());

		if(!password.equals(checkpassword)) {
			ObjectError error = new ObjectError("passwordcheckerror","パスワードと確認用パスワードが一致しません。" );
			result.addError(error);
			return AccountEdit();
		}

		LoginItem user = new LoginItem();

		BeanUtils.copyProperties(form, user);

		session.setAttribute("accountEdit", user);

		return "AccountEditCheck";
	}

	@RequestMapping("/finish")
	public String accountEditCheck(@Validated AccountEditForm form,BindingResult result) {

		int userid = form.getUserid();
		String name = form.getName();
		String email = form.getEmail();
		String password = form.getPassword();

		System.out.println("userid(check):"+form.getUserid());

		checkEmail checkemail = service.editAccount(userid, name, email, password);

		if(checkemail != null) {
			ObjectError error = new ObjectError("checkemailerror", "入力したメールアドレスはすでに登録されています");
			result.addError(error);

			return AccountEdit();

		}

		LoginItem user = new LoginItem();
		BeanUtils.copyProperties(form, user);

		session.removeAttribute("accountEdit");
		session.removeAttribute("loginItem");
		session.setAttribute("loginItem", user);

		return "AccountEditFinish";
	}
}
