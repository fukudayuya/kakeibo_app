package com.example.demo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.FindIncome;
import com.example.demo.domain.Income;
import com.example.demo.domain.IncomeEdit;
import com.example.demo.domain.IncomeGenre;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.autoIncomeInfo;
import com.example.demo.service.AutoInputService;
import com.example.demo.service.IncomeService;

//収入登録画面に表示するプルダウンの中身が取り出せているかの確認

@Controller
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	private HttpSession session;

	@Autowired
	private IncomeService service;

	@Autowired
	private AutoInputService autoservice;

	@ModelAttribute
	IncomeForm setUpForm() {
		return new IncomeForm();
	}

	//収入登録画面へ遷移
	@RequestMapping("/add")
	public String IncomeAdd(Model model) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

		if(loginItem == null) {
			return "redirect:/login/";
		}

		//ジャンルのプルダウンメニューに表示するデータを取得
		List<IncomeGenre> genre = new ArrayList<>();

		genre = service.getGenre();

		model.addAttribute("genreList",genre);

		List<autoIncomeInfo> AutoIncomeList = new ArrayList<>();

		//DBに番号1の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo1 = new autoIncomeInfo();
		AutoIncomeInfo1 = autoservice.getAutoIncome(loginItem.getUserid());

			if(AutoIncomeInfo1 != null ) {
				//中身があったらまとめてリストへ
				AutoIncomeList.add(AutoIncomeInfo1);
			}else {
				//データがない場合はステータスを1として新規登録
				autoIncomeInfo AutoIncomeInfox = new autoIncomeInfo();
				AutoIncomeList.add(AutoIncomeInfox);
			}

		//DBに番号2の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo2 = new autoIncomeInfo();
		AutoIncomeInfo2 = autoservice.getAutoIncome2(loginItem.getUserid());

			if(AutoIncomeInfo2 != null ) {
				//中身があったらモデルスコープへ
				AutoIncomeList.add(AutoIncomeInfo2);
			}else {
				//データがない場合はステータスを1として新規登録
				autoIncomeInfo AutoIncomeInfo2x = new autoIncomeInfo();
				AutoIncomeList.add(AutoIncomeInfo2x);
			}

		//DBに番号3の収入自動登録情報があるか確認
		autoIncomeInfo AutoIncomeInfo3 = new autoIncomeInfo();
		AutoIncomeInfo3 = autoservice.getAutoIncome3(loginItem.getUserid());

			if(AutoIncomeInfo3 != null ) {
				//中身があったらモデルスコープへ
				System.out.println("AutoIncome3へイン");
				AutoIncomeList.add(AutoIncomeInfo3);
			}else {
				//データがない場合はステータスを1として新規登録
				autoIncomeInfo AutoIncomeInfo3x = new autoIncomeInfo();
				AutoIncomeList.add(AutoIncomeInfo3x);
			}

		//ダイアログで表示するデータ
		model.addAttribute("autoincomelist",AutoIncomeList);


		return "IncomeAdd";
	}

	//収入登録し、TOPへ遷移（アラート出したい）
		@RequestMapping("/addcomplete")
		public String spend(@Validated IncomeForm form) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			Date incomedate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				incomedate = sdf.parse(form.getIncomedate());
			}catch(ParseException e) {
				e.printStackTrace();
			}

			//formの値をdomainにセット
			Income income = new Income();
			income.setGenreid(form.getGenreid());
			income.setTitle(form.getTitle());
			income.setUserid(form.getUserid());
			income.setSpenddate(incomedate);
			income.setContents(form.getContents());
			income.setPrice(form.getPrice());

			service.IncomeAdd(income);

			return "incomecomplete";
		}


	//収入詳細画面へ遷移
		@RequestMapping("/details")
		public String IncomeDetails(Model model,FindSpendDateForm form) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			int userid = loginItem.getUserid();

			final LocalDate today = LocalDate.now();

			int year = today.getYear();
			int month = today.getMonthValue();

			//検索の年月リスト作成
			List<Integer> selectSerchYear = new ArrayList<>();
			List<Integer> selectSerchMonth = new ArrayList<>();

			for(int i = year; i < year+20; i++) {
				selectSerchYear.add(i);
			}
			model.addAttribute("selectyear",selectSerchYear);

			for(int i = 1; i< 13; i++) {
				selectSerchMonth.add(i);
			}
			model.addAttribute("selectmonth",selectSerchMonth);

			if(form.getYear() != 0 && form.getMonth() != 0 ) {
				year = form.getYear();
				month = form.getMonth();
				model.addAttribute("selectedyear",year);
				model.addAttribute("selectedmonth",month);
			}else {
				model.addAttribute("selectedyear",year);
				model.addAttribute("selectedmonth",month);
			}

			String find1 = year + "-" + month + "-" + "01";
			String find2 = year + "-" + month + "-" + "31";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date yearMonth = null;
			Date yearMonth2 = null;

			try {
				yearMonth = sdf.parse(find1);
				yearMonth2 = sdf.parse(find2);
			}catch(ParseException e) {
				System.out.println(e);
			}


			//給与データを取得
			List<FindIncome> salary = new ArrayList<>();
			salary=service.getSalaryIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("salaryList",salary);

			//賞与データを取得
			List<FindIncome> bonus = new ArrayList<>();
			bonus=service.getBonusIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("bonusList",bonus);

			//副業データを取得
			List<FindIncome> side = new ArrayList<>();
			side=service.getSideIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("sideList",side);

			//年金データを取得
			List<FindIncome> pension = new ArrayList<>();
			pension=service.getPensionIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("pensionList",pension);

			//配当データを取得
			List<FindIncome> dividend = new ArrayList<>();
			dividend=service.getDividendIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("dividendList",dividend);

			//不動産データを取得
			List<FindIncome> realeState = new ArrayList<>();
			realeState=service.getRealestateIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("realestateList",realeState);

			//その他データを取得
			List<FindIncome> other = new ArrayList<>();
			other=service.getOtherIncome(userid,yearMonth,yearMonth2);
			model.addAttribute("otherIncomeList",other);

			model.addAttribute("year", year);
			model.addAttribute("month", month);
			return "IncomeDetails";
		}

		//収入編集・削除画面へ遷移
		@RequestMapping("/edit/{incomeid}")
		public String spendEdit(@PathVariable int incomeid,Model model) {
			System.out.println("spendId:"+ incomeid);

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			//ジャンルのプルダウンメニューに表示するデータを取得
			List<IncomeGenre> genre = new ArrayList<>();
			genre = service.getGenre();
			model.addAttribute("genreList",genre);

			//htmlで「編集」「削除」ボタンを押したデータの取得
			int userid = loginItem.getUserid();
			FindIncome findTargetIncome = service.getTargetIncome(incomeid, userid);
			model.addAttribute("findTargetIncome",findTargetIncome);

			List<autoIncomeInfo> AutoIncomeList = new ArrayList<>();

			//DBに番号1の収入自動登録情報があるか確認
			autoIncomeInfo AutoIncomeInfo1 = new autoIncomeInfo();
			AutoIncomeInfo1 = autoservice.getAutoIncome(loginItem.getUserid());

				if(AutoIncomeInfo1 != null ) {
					//中身があったらまとめてリストへ
					AutoIncomeList.add(AutoIncomeInfo1);
				}else {
					//データがない場合はステータスを1として新規登録
					autoIncomeInfo AutoIncomeInfox = new autoIncomeInfo();
					AutoIncomeList.add(AutoIncomeInfox);
				}

			//DBに番号2の収入自動登録情報があるか確認
			autoIncomeInfo AutoIncomeInfo2 = new autoIncomeInfo();
			AutoIncomeInfo2 = autoservice.getAutoIncome2(loginItem.getUserid());

				if(AutoIncomeInfo2 != null ) {
					//中身があったらモデルスコープへ
					AutoIncomeList.add(AutoIncomeInfo2);
				}else {
					//データがない場合はステータスを1として新規登録
					autoIncomeInfo AutoIncomeInfo2x = new autoIncomeInfo();
					AutoIncomeList.add(AutoIncomeInfo2x);
				}

			//DBに番号3の収入自動登録情報があるか確認
			autoIncomeInfo AutoIncomeInfo3 = new autoIncomeInfo();
			AutoIncomeInfo3 = autoservice.getAutoIncome3(loginItem.getUserid());

				if(AutoIncomeInfo3 != null ) {
					//中身があったらモデルスコープへ
					System.out.println("AutoIncome3へイン");
					AutoIncomeList.add(AutoIncomeInfo3);
				}else {
					//データがない場合はステータスを1として新規登録
					autoIncomeInfo AutoIncomeInfo3x = new autoIncomeInfo();
					AutoIncomeList.add(AutoIncomeInfo3x);
				}

			//ダイアログで表示するデータ
			model.addAttribute("autoincomelist",AutoIncomeList);


			return "IncomeEdit";
		}

		//収入編集登録
		@RequestMapping("/editcomplete")
		public String spendEditComp(@Validated IncomeEditForm form) {
			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			Date spenddate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				spenddate = sdf.parse(form.getIncomedate());
			}catch(ParseException e) {
				e.printStackTrace();
			}

			//formの値をdomainにセット
			IncomeEdit income = new IncomeEdit();
			income.setIncomeid(form.getIncomeid());
			income.setIncomegenreid(form.getIncomegenreid());
			income.setTitle(form.getTitle());
			income.setUserid(form.getUserid());
			income.setIncomedate(spenddate);
			income.setContents(form.getContents());
			income.setPrice(form.getPrice());

			service.IncomeUpdate(income);

			return "incomeeditcomplete";
		}

		//支出削除登録
		@RequestMapping("/deletecomplete/{incomeid}")
		public String incomeDeleteComp(@PathVariable int incomeid) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			int userid = loginItem.getUserid();

			service.IncomeDelete(incomeid, userid);

			System.out.println(incomeid+"デリート実行");
			return "redirect:/income/details";
		}


}
