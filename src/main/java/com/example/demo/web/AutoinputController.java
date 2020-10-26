package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Genre;
import com.example.demo.domain.IncomeGenre;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.autoIncomeInfo;
import com.example.demo.domain.autoInput;
import com.example.demo.domain.autoSpendInfo;
import com.example.demo.service.AutoInputService;
import com.example.demo.service.IncomeService;
import com.example.demo.service.SpendService;

@Controller
@RequestMapping("/autoinput")
public class AutoinputController {

	@Autowired
	private HttpSession session;

	@Autowired
	private IncomeService service;

	@Autowired
	private AutoInputService autoservice;

	@Autowired
	private SpendService autpspendservice;

	@ModelAttribute
	autoInputForm setUpForm(){
		return new autoInputForm();
	}
	//自動入力情報登録トップページへ
	@RequestMapping("/")
	public String autoinputtop() {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}

		return "autoinputTop";
	}


	//収入自動入力情報登録ページへ
	@RequestMapping("/income")
	public String autoIncome(Model model) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}

		//ジャンルのプルダウンメニューに表示するデータを取得
		List<IncomeGenre> genre = new ArrayList<>();

		genre = service.getGenre();

		model.addAttribute("genreList",genre);

		//DBに番号1の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo1 = new autoIncomeInfo();
		AutoIncomeInfo1 = autoservice.getAutoIncome(loginItem.getUserid());

		if(AutoIncomeInfo1 != null ) {
			//中身があったらモデルスコープへ
			System.out.println("AutoIncome1へイン");
			model.addAttribute("AutoIncomeInfo1", AutoIncomeInfo1);
			model.addAttribute("input1status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			autoIncomeInfo AutoIncomeInfox = new autoIncomeInfo();
			model.addAttribute("AutoIncomeInfo1", AutoIncomeInfox);
			model.addAttribute("input1status",1);
		}

		//DBに番号2の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo2 = new autoIncomeInfo();
		AutoIncomeInfo2 = autoservice.getAutoIncome2(loginItem.getUserid());

		if(AutoIncomeInfo2 != null ) {
			//中身があったらモデルスコープへ
			System.out.println("AutoIncome2へイン");
			model.addAttribute("AutoIncomeInfo2", AutoIncomeInfo2);
			model.addAttribute("input2status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			autoIncomeInfo AutoIncomeInfo2x = new autoIncomeInfo();
			model.addAttribute("AutoIncomeInfo2", AutoIncomeInfo2x);
			model.addAttribute("input2status",1);
		}

		//DBに番号3の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo3 = new autoIncomeInfo();
		AutoIncomeInfo3 = autoservice.getAutoIncome3(loginItem.getUserid());

		if(AutoIncomeInfo3 != null ) {
			//中身があったらモデルスコープへ
			System.out.println("AutoIncome3へイン");
			model.addAttribute("AutoIncomeInfo3", AutoIncomeInfo3);
			model.addAttribute("input3status", 0);
		}else {
			//データがない場合はステータスを1として新規登録
			autoIncomeInfo AutoIncomeInfo3x = new autoIncomeInfo();
			model.addAttribute("AutoIncomeInfo3", AutoIncomeInfo3x);
			model.addAttribute("input3status",1);
		}

		return "autoincome";
	}

	//収入自動入力情報登録処理へ
	@RequestMapping("/inputset")
	public String autoIncomeInputset(Model model,autoInputForm form) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}

		//formの値をdomainにセット
		autoInput input = new autoInput();
		input.setGenreid(form.getGenreid());
		input.setTitle(form.getTitle());
		input.setUserid(form.getUserid());
		input.setContents(form.getContents());
		input.setPrice(form.getPrice());
		input.setInputsetid(form.getInputsetid());

		if(form.getInputstatus() == 1) {
			//初期登録時
			autoservice.autoIncomeNewAdd(input);
		}else {
			//更新登録時
			autoservice.autoIncomeUpdate(input);
		}

		return "autoinputcomplete";

	}


	//支出自動入力情報登録TOPページへ
	@RequestMapping("/spend")
	public String autoSpend(Model model) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}

		//ジャンルのプルダウンメニューに表示するデータを取得
		List<Genre> genre = new ArrayList<>();

		genre = autpspendservice.getGenre();

		model.addAttribute("genreList",genre);

		//DBに番号1の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo1 = new autoSpendInfo();
		AutoSpendInfo1 = autoservice.getAutoSpend1(loginItem.getUserid());

		if(AutoSpendInfo1 != null ) {
			//中身があったらモデルスコープへ
			System.out.println("AutoIncome1へイン");
			model.addAttribute("AutoSpendInfo1", AutoSpendInfo1);
			model.addAttribute("input1status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			model.addAttribute("AutoSpendInfo1", new autoSpendInfo());
			model.addAttribute("input1status",1);
		}

		//DBに番号2の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo2 = new autoSpendInfo();
		AutoSpendInfo2 = autoservice.getAutoSpend2(loginItem.getUserid());

		if(AutoSpendInfo2 != null ) {
			//中身があったらモデルスコープへ
			model.addAttribute("AutoSpendInfo2", AutoSpendInfo2);
			model.addAttribute("input2status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			model.addAttribute("AutoSpendInfo2", new autoSpendInfo());
			model.addAttribute("input2status",1);
		}

		//DBに番号3の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo3 = new autoSpendInfo();
		AutoSpendInfo3 = autoservice.getAutoSpend3(loginItem.getUserid());

		if(AutoSpendInfo3 != null ) {
			//中身があったらモデルスコープへ
			model.addAttribute("AutoSpendInfo3", AutoSpendInfo3);
			model.addAttribute("input3status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			model.addAttribute("AutoSpendInfo3", new autoSpendInfo());
			model.addAttribute("input3status",1);
		}

		//DBに番号4の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo4 = new autoSpendInfo();
		AutoSpendInfo4 = autoservice.getAutoSpend4(loginItem.getUserid());

		if(AutoSpendInfo4 != null ) {
			//中身があったらモデルスコープへ
			model.addAttribute("AutoSpendInfo4", AutoSpendInfo4);
			model.addAttribute("input4status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			model.addAttribute("AutoSpendInfo4", new autoSpendInfo());
			model.addAttribute("input4status",1);
		}

		//DBに番号5の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo5 = new autoSpendInfo();
		AutoSpendInfo5 = autoservice.getAutoSpend5(loginItem.getUserid());

		if(AutoSpendInfo5 != null ) {
			//中身があったらモデルスコープへ
			model.addAttribute("AutoSpendInfo5", AutoSpendInfo5);
			model.addAttribute("input5status",0);
		}else {
			//データがない場合はステータスを1として新規登録
			model.addAttribute("AutoSpendInfo5", new autoSpendInfo());
			model.addAttribute("input5status",1);
		}

		return "autospend";
	}

	//支出自動入力情報登録処理へ
		@RequestMapping("/spendset")
		public String autoSpendInputset(Model model,autoInputForm form) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
			if(loginItem == null) {
				return "redirect:/login/";
			}

			//formの値をdomainにセット
			autoInput input = new autoInput();
			input.setGenreid(form.getGenreid());
			input.setTitle(form.getTitle());
			input.setUserid(form.getUserid());
			input.setContents(form.getContents());
			input.setPrice(form.getPrice());
			input.setInputsetid(form.getInputsetid());

			if(form.getInputstatus() == 1) {
				//初期登録時
				autoservice.autoSpendNewAdd(input);
			}else {
				//更新登録時
				autoservice.autoSpendUpdate(input);
			}

			return "autoinputcomplete";

		}

}
