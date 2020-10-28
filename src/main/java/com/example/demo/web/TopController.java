package com.example.demo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestOperations;

import com.example.demo.config.FortuneResource;
import com.example.demo.domain.FortuneData;
import com.example.demo.domain.FortuneSignData;
import com.example.demo.domain.GetConfirm;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.SerchSaving;
import com.example.demo.domain.TopYearMonth;
import com.example.demo.domain.incomeTotal;
import com.example.demo.domain.spendTotal;
import com.example.demo.service.TopService;

@Controller
@RequestMapping("/top")
public class TopController {

	@Autowired
	private HttpSession session;

	@Autowired
	private TopService service;

	@Autowired
	private RestOperations restOperations;

	public static String toStr(LocalDateTime localDateTime,String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(dateTimeFormatter);
	}

	@RequestMapping("/")
	public String toppage(Model model,TopSerchForm form) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}
		int userid = loginItem.getUserid();



		//支出情報表示
		final LocalDate today = LocalDate.now();

		int year = today.getYear();
		int month = today.getMonthValue();

		String date = toStr(LocalDateTime.now(),"yyyy/MM/dd");//占う日付(今日)
//		int sign = 0;//星座コード
		FortuneResource resource = restOperations.getForObject("http://api.jugemkey.jp/api/horoscope/free/"+date, FortuneResource.class);
		List<FortuneData> fortuneData = resource.getHoroscope().get(date);
//		model.addAttribute("fortuneData",fortuneData);

		List<FortuneSignData> signData = new ArrayList<>();//セレクトボックス用

		for(int i = 0;i < 12;i++) {
			FortuneSignData fortuneSigneData = new FortuneSignData();
			fortuneSigneData.setSignvalue(i);
			fortuneSigneData.setSign(fortuneData.get(i).getSign());
			signData.add(fortuneSigneData);
		}

		model.addAttribute("signData",signData);
//
//			System.out.println("json星座取れてる？:"+fortuneData.get(i).getSign());
//		System.out.println("json取れてる？:"+fortuneData.get(0).);


		//top検索機能つけた際に使用
		if(form.getYear() != 0 && form.getMonth() != 0 ) {
			year = form.getYear();
			month = form.getMonth();
		}

		TopYearMonth yearmonth = new TopYearMonth();
		yearmonth.setYear(year);
		yearmonth.setMonth(month);
		model.addAttribute("yearmonth",yearmonth);

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

		spendTotal spendtotal = service.sumSpend(userid, yearMonth, yearMonth2);

		model.addAttribute("spendtotal",spendtotal);

		//収入合計取得
		incomeTotal incometotal = service.sumIncome(userid, yearMonth, yearMonth2);

		model.addAttribute("incometotal",incometotal);

		int sumTotal = spendtotal.getSpendSumTotal();
		int incomeTotal = incometotal.getIncomeSumTotal();

		//収支計算しスコープへ
		model.addAttribute("balance",incomeTotal - sumTotal);

		//支出グラフ用のラベルを取得
		List<String> label = new ArrayList<String>();
		label=service.label();
		String spendlabel[] = label.toArray(new String[label.size()]);
		model.addAttribute("label", spendlabel);

		//支出グラフのデータを取得
		List<Integer> data = service.spendData(userid, yearMonth, yearMonth2);
		Integer spendData[] = data.toArray(new Integer[data.size()]);
		model.addAttribute("data", spendData);

		//収支確定ステータスの取得
		SimpleDateFormat sdfConfirm = new SimpleDateFormat("yyyy-MM");

		String findconfirmdate = year + "-" + month;

		Date findConfirmDate = null;//対象年月のみの日付データ

		try {
			findConfirmDate = sdfConfirm.parse(findconfirmdate );
		}catch(ParseException e) {
			System.out.println(e);
		}

		System.out.println("収支確定日付情報:"+findConfirmDate);

		GetConfirm confirmstatus = new GetConfirm();

		try {
			//収支確定情報がDBにある場合
			 confirmstatus = service.findConfirm(userid, findConfirmDate);
		}catch(EmptyResultDataAccessException e){
			//収支確定情報がDBにない場合confirmidを0、confirmstatusを0として入れる。
			 confirmstatus.setConfirmid(0);
			 confirmstatus.setConfirmStatus(0);
		}

		System.out.println("収支確定ステータス："+ confirmstatus);

		model.addAttribute("confirmstatus",confirmstatus);//収支登録データ情報
		model.addAttribute("confirmDate",findconfirmdate );//収支確定情報対象年月

		//目標金額表示
		try {
			LoginItem item= (LoginItem)service.findSaving(userid);

			session.setAttribute("loginItem", item);
			System.out.println("/top/名前情報:" + item.getName());

			return "toppage";

		}catch(NullPointerException e) {
			//savingtable未登録の場合、sessionのsavingItemのsavingStatusのみに0の値をセットする。
			int statue = 0;
			loginItem.setSavingStatus(statue);
			session.setAttribute("loginItem", loginItem);
			return "toppage";
		}


	}

	//ユーザー情報変更or自動登録情報登録
	@RequestMapping("/selectpage")
	public String selectpage() {
		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		if(loginItem == null) {
			return "redirect:/login/";
		}

		return "selectpage";
	}

	//収支確定・解除処理
	@RequestMapping("/confirm")
	public String incomeSpendConfirm(ConfirmForm form) {


		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");
		int userid = loginItem.getUserid();
		int savingsid = loginItem.getSavingsid();

		//savingsテーブルがない人は収支確定をさせない。savingテーブルがない人のsavingstatusは0
		if(loginItem.getSavingStatus() == 0) {
			return "redirect:/top/";
		}

		Date formConfirmDate = null;//対象年月のみの日付データ
		SimpleDateFormat sdfConfirm = new SimpleDateFormat("yyyy-MM");
		try {
			formConfirmDate = sdfConfirm.parse(form.getConfirmdate());
		}catch(ParseException e) {
			System.out.println(e);
		}
		System.out.println(formConfirmDate);

		System.out.println("収支確定ボタン押下！値は"+form.getConfirmstatus());


		if(form.getConfirmstatus() == 0) {//収支未確定時の処理
			//confirmStatusを1で登録,confirmidがある場合はupdate、ない場合(0の時)は新規登録
			if(form.getConfirmid() == 0) {
				//収支確定情報を新規登録
				if(form.getCheck() != 1) {
					//収支結果を現在の貯金額に反映させる場合
					service.setConfirm(userid,formConfirmDate,form.getConfirmprice(),savingsid);
				}else {
					//収支結果を現在の貯金額に反映させない場合
					service.setConfirm2(userid, formConfirmDate, form.getConfirmprice(), savingsid);
				}
				//目標金額に達したかの計算処理
				SerchSaving saving = service.savingResult(savingsid);
				int targetsaving = saving.getTargetsavings();
				int nowsaving = saving.getNowsavings();
				if(targetsaving <= nowsaving) {
					//目標金額を達成した場合,savingstatusを2に
					service.savingStatusUpdate(savingsid);
				}



			} else {
				//収支確定情報をupdate登録
				if(form.getCheck() != 1) {
					//収支結果を現在の貯金額に反映させる場合
					service.updateConfirm(form.getConfirmid(), form.getConfirmprice(), savingsid);
				}else {
					//収支結果を現在の貯金額に反映させない場合
					service.updateConfirm2(form.getConfirmid(), form.getConfirmprice(), savingsid);
				}
				//目標金額に達したかの計算処理
				SerchSaving saving = service.savingResult(savingsid);
				int targetsaving = saving.getTargetsavings();
				int nowsaving = saving.getNowsavings();

				if(targetsaving <= nowsaving) {
					//目標金額を達成した場合,savingstatusを2に
					service.savingStatusUpdate(savingsid);
				}

			}

		}else {//収支確定解除の処理
			//confirmStatusを0で登録,貯金データから対象年月の収支額を引く。
			if(form.getCheck() != 1) {
				//現在の貯金額に反映する場合
				service.unlockConfirm(form.getConfirmid(), form.getConfirmprice(), savingsid);
			}else {
				//現在の貯金額に反映させない場合
				service.unlockConfirm2(form.getConfirmid(), form.getConfirmprice(), savingsid);
			}
			//目標金額に達したかの計算処理
			SerchSaving saving = service.savingResult(savingsid);
			int targetsaving = saving.getTargetsavings();
			int nowsaving = saving.getNowsavings();

			System.out.println("目標金額:"+targetsaving);
			System.out.println("貯金金額:"+nowsaving);
			if(targetsaving <= nowsaving) {
				//目標金額を達成した場合,savingstatusを2に
				service.savingStatusUpdate(savingsid);
			}else {
				//目標金額を達成していない場合,savingstatusを1に
				service.savingStatusDowndate(savingsid);
			}
		}


		return "redirect:/top/";
	}






}
