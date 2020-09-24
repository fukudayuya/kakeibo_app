package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.LoginItem;
import com.example.demo.service.SavingService;

@Controller
@RequestMapping("/saving")
public class SavingController {

	@Autowired
	private HttpSession session;

	@Autowired
	private SavingService service;

	@ModelAttribute
	SavingForm SetUpForm() {
		return new SavingForm();
	}


	//目標貯金額登録画面
	@RequestMapping("/add")
	public String saving() {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

		if(loginItem == null) {
			return "redirect:/login/";
		}

		return "SavingAdd";
	}

	//「目標金額設定ボタン」押下時の目標貯金額登録処理
	@RequestMapping("/addcomplete")
	public String addcomplete(SavingForm form) {
		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		int userid = loginItem.getUserid();
		int targetSavings = form.getTargetSaving();
		int savingStatus = loginItem.getSavingStatus();
		int savingsid = loginItem.getSavingsid();

		System.out.println("savingCont/savinItem:"+loginItem.getSavingStatus());

		if(savingStatus == 0) {//savingtable未登録時
			service.savingIns(userid, targetSavings);

		}else if(savingStatus == 2) {
			//savingtable目標金額達成し更新時
			//更新登録時、savingテーブルにセッションのsavingsidを元に入力情報を入れる。現在の貯金額は0で登録。スタッツを１で登録
			service.savingUpdate(savingsid, targetSavings);
		}

		return "redirect:/top/";
	}


	//目標貯金額変更画面
	@RequestMapping("/edit")
	public String savingEdit() {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

		if(loginItem == null) {
			return "redirect:/login/";
		}

		return "SavingEdit";
	}

	//「目標金額変更ボタン」押下時の目標貯金額変更処理
	@RequestMapping("/editcomplete")
	public String editcomplete(SavingForm form) {
		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		int userid = loginItem.getUserid();
		int targetSavings = form.getTargetSaving();

		System.out.println("savingCont/savinItem:"+loginItem.getSavingStatus());

		//savingtable編集時
		service.savingEdit(userid, targetSavings);

		System.out.println("savingCont/savinItem２:"+loginItem.getSavingStatus());
		return "redirect:/top/";
	}



}
