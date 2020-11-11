package com.example.demo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.FindSpend;
import com.example.demo.domain.Genre;
import com.example.demo.domain.HistoryData;
import com.example.demo.domain.LoginItem;
import com.example.demo.domain.Spend;
import com.example.demo.domain.SpendEdit;
import com.example.demo.domain.autoSpendInfo;
import com.example.demo.service.AutoInputService;
import com.example.demo.service.SpendService;

@Controller
@RequestMapping("/spend")
public class SpendController {

	@Autowired
	private HttpSession session;

	@Autowired
	private SpendService service;

	@Autowired
	private AutoInputService autoservice;

	@ModelAttribute
	SpendForm setUpForm() {
		return new SpendForm();
	}

	public static LinkedHashMap<HistoryData, Long> sortMapByValue(Map<HistoryData, Long> counts) {
		List<Map.Entry<HistoryData, Long>> entries = new LinkedList<>(counts.entrySet());
		Collections.sort(entries,(o1, o2) -> o1.getValue().compareTo(o2.getValue()));

		LinkedHashMap<HistoryData, Long> result = new LinkedHashMap<>();
		for(Map.Entry<HistoryData, Long> entry : entries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	//支出登録画面へ遷移
	@RequestMapping("/add")
	public String spendAdd(Model model) {

		LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

		if(loginItem == null) {
			return "redirect:/login/";
		}

		//ジャンルのプルダウンメニューに表示するデータを取得
		List<Genre> genre = new ArrayList<>();

		genre = service.getGenre();

		model.addAttribute("genreList",genre);

		List<autoSpendInfo> AutoSpendList = new ArrayList<>();

		//登録履歴の取得
		List<HistoryData> historyData = service.getHistory(loginItem.getUserid());
		//リストの値と出現個数を取得
		Map<HistoryData, Long> counts = historyData.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		//出現回数順に並び替え
		Map<HistoryData, Long> sortcounts = new HashMap<>();
		sortcounts = sortMapByValue(counts);//sortMapByValueで並び替え
		for(Map.Entry<HistoryData, Long> entry : sortcounts.entrySet()) {
			System.out.println("Key:" + entry.getKey() + ",value:" + entry.getValue());
		}

		List<HistoryData> souseList = new ArrayList<>(sortcounts.keySet());//MapをListに変換。個数の少ない順で並んでいる。
		Collections.reverse(souseList);
		int index = 0;
		if(souseList.size() < 8) {
			index = souseList.size();
		}else {
			index = 8;
		}

		List<HistoryData> subSouseList = souseList.subList(0, index);

		System.out.println("履歴個数確認:"+ souseList);
		System.out.println("履歴個数絞り込み:"+ subSouseList);

		model.addAttribute("historyList", subSouseList);

		//DBに番号1の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo1 = new autoSpendInfo();
		AutoSpendInfo1 = autoservice.getAutoSpend1(loginItem.getUserid());

		if(AutoSpendInfo1 != null ) {
			//中身があったらリストへへ
			AutoSpendList.add(AutoSpendInfo1);
		}else {
			//データがない場合はステータスを1として新規登録
			autoSpendInfo AutoSpendInfox = new autoSpendInfo();
			AutoSpendList.add(AutoSpendInfox);
		}

		//DBに番号2の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo2 = new autoSpendInfo();
		AutoSpendInfo2 = autoservice.getAutoSpend2(loginItem.getUserid());

		if(AutoSpendInfo2 != null ) {
			//中身があったらリストへへ
			AutoSpendList.add(AutoSpendInfo2);
		}else {
			//データがない場合はステータスを1として新規登録
			autoSpendInfo AutoSpendInfo2x = new autoSpendInfo();
			AutoSpendList.add(AutoSpendInfo2x);
		}

		//DBに番号3の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo3 = new autoSpendInfo();
		AutoSpendInfo3 = autoservice.getAutoSpend3(loginItem.getUserid());

		if(AutoSpendInfo3 != null ) {
			//中身があったらリストへへ
			AutoSpendList.add(AutoSpendInfo3);
		}else {
			//データがない場合はステータスを1として新規登録
			autoSpendInfo AutoSpendInfo3x = new autoSpendInfo();
			AutoSpendList.add(AutoSpendInfo3x);
		}

		//DBに番号4の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo4 = new autoSpendInfo();
		AutoSpendInfo4 = autoservice.getAutoSpend4(loginItem.getUserid());

		if(AutoSpendInfo4 != null ) {
			//中身があったらリストへへ
			AutoSpendList.add(AutoSpendInfo4);
		}else {
			//データがない場合はステータスを1として新規登録
			autoSpendInfo AutoSpendInfo4x = new autoSpendInfo();
			AutoSpendList.add(AutoSpendInfo4x);
		}

		//DBに番号5の支出自動登録情報があるか確認
		autoSpendInfo AutoSpendInfo5 = new autoSpendInfo();
		AutoSpendInfo5 = autoservice.getAutoSpend5(loginItem.getUserid());

		if(AutoSpendInfo5 != null ) {
			//中身があったらリストへへ
			AutoSpendList.add(AutoSpendInfo5);
		}else {
			//データがない場合はステータスを1として新規登録
			autoSpendInfo AutoSpendInfo5x = new autoSpendInfo();
			AutoSpendList.add(AutoSpendInfo5x);
		}

		model.addAttribute("autospendlist",AutoSpendList);

		return "SpendAdd";
	}



		//支出登録し、完了画面へ遷移
		@RequestMapping("/addcomplete")
		public String spend(SpendForm form) {
			System.out.println("/spend/addcomplete");
			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			Date spenddate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				spenddate = sdf.parse(form.getSpenddate());
			}catch(ParseException e) {
				e.printStackTrace();
				return "/spend/add";
			}

			//値段をstringからintへ
			int price = Integer.parseInt(form.getPrice());

			//formの値をdomainにセット
			Spend spend = new Spend();
			spend.setGenreid(form.getGenreid());
			spend.setTitle(form.getTitle());
			spend.setUserid(form.getUserid());
			spend.setSpenddate(spenddate);
			spend.setContents(form.getContents());
			spend.setPrice(price);

			System.out.println("/spend/addcomplete3");

			service.SpendAdd(spend);

			System.out.println("/spend/addcomplete4");
			return "spendcomplete";
		}


	//支出詳細画面へ遷移
		@RequestMapping("/details")
		public String spendDetails(Model model,FindSpendDateForm form) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			int userid = loginItem.getUserid();

			final LocalDate today = LocalDate.now();

			int year = today.getYear();
			int month = today.getMonthValue();

			if(form.getYear() != 0 && form.getMonth() != 0 ) {
				year = form.getYear();
				month = form.getMonth();
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

			//プルダウンのジャンルを取得
			List<Genre> genre = new ArrayList<>();

			genre = service.getGenre();

			model.addAttribute("genreList",genre);


			//食費データを取得
			List<FindSpend> food = new ArrayList<>();
			food=service.getFoodSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("foodSpendList",food);

			//水道データを取得
			List<FindSpend> water = new ArrayList<>();
			water=service.getWaterSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("waterSpendList",water);

			//電気データを取得
			List<FindSpend> electricity = new ArrayList<>();
			electricity=service.getElectricitySpend(userid,yearMonth,yearMonth2);
			model.addAttribute("electricitySpendList",electricity);

			//ガスデータを取得
			List<FindSpend> gas = new ArrayList<>();
			gas=service.getGasSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("gasSpendList",gas);

			//日用品データを取得
			List<FindSpend> necessities = new ArrayList<>();
			necessities=service.getNecessitiesSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("necessitiesSpendList",necessities);

			//交通費データを取得
			List<FindSpend> trafic = new ArrayList<>();
			trafic=service.getTraficSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("traficSpendList",trafic);

			//交際費データを取得
			List<FindSpend> entertainment = new ArrayList<>();
			entertainment=service.getEntertainmentSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("entertainmentSpendList",entertainment);

			//衣服・美容データを取得
			List<FindSpend> beauty = new ArrayList<>();
			beauty=service.getBeautySpend(userid,yearMonth,yearMonth2);
			model.addAttribute("beautySpendList",beauty);

			//医療・健康データを取得
			List<FindSpend> health = new ArrayList<>();
			health=service.getHealthSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("healthSpendList",health);

			//車・バイクデータを取得
			List<FindSpend> vehicle = new ArrayList<>();
			vehicle=service.getVehicleSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("vehicleSpendList",vehicle);

			//教養・教育データを取得
			List<FindSpend> educational = new ArrayList<>();
			educational=service.getEducationalSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("educationSpendList",educational);

			//趣味・娯楽データを取得
			List<FindSpend> hobby = new ArrayList<>();
			hobby=service.getHobbySpend(userid,yearMonth,yearMonth2);
			model.addAttribute("hobbySpendList",hobby);

			//住宅データを取得
			List<FindSpend> house = new ArrayList<>();
			house=service.getHouseSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("houseSpendList",house);

			//通信データを取得
			List<FindSpend> communication = new ArrayList<>();
			communication=service.getCommunicationSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("communicationSpendList",communication);

			//税・社会保険データを取得
			List<FindSpend> tax = new ArrayList<>();
			tax=service.getTaxSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("taxSpendList",tax);

			//保険データを取得
			List<FindSpend> insurance = new ArrayList<>();
			insurance=service.getInsuranceSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("insuranceSpendList",insurance);

			//その他データを取得
			List<FindSpend> other = new ArrayList<>();
			other=service.getOtherSpend(userid,yearMonth,yearMonth2);
			model.addAttribute("otherSpendList",other);

			model.addAttribute("year", year);
			model.addAttribute("month", month);

			return "SpendDetails";
		}

		//支出編集・削除画面へ遷移
		@RequestMapping("/edit/{spendid}")
		public String spendEdit(@PathVariable int spendid,Model model) {
			System.out.println("spendId:"+ spendid);

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			//ジャンルのプルダウンメニューに表示するデータを取得
			List<Genre> genre = new ArrayList<>();
			genre = service.getGenre();
			model.addAttribute("genreList",genre);

			//htmlで「編集」「削除」ボタンを押したデータの取得
			int userid = loginItem.getUserid();
			FindSpend findTargetSpend = service.getTargetSpend(spendid, userid);
			model.addAttribute("findTargetSpend",findTargetSpend);

			List<autoSpendInfo> AutoSpendList = new ArrayList<>();

			//登録履歴の取得
			List<HistoryData> historyData = service.getHistory(loginItem.getUserid());
			//リストの値と出現個数を取得
			Map<HistoryData, Long> counts = historyData.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
			//出現回数順に並び替え
			Map<HistoryData, Long> sortcounts = new HashMap<>();
			sortcounts = sortMapByValue(counts);//sortMapByValueで並び替え
			for(Map.Entry<HistoryData, Long> entry : sortcounts.entrySet()) {
				System.out.println("Key:" + entry.getKey() + ",value:" + entry.getValue());
			}

			List<HistoryData> souseList = new ArrayList<>(sortcounts.keySet());//MapをListに変換。個数の少ない順で並んでいる。
			Collections.reverse(souseList);
			int index = 0;
			if(souseList.size() < 8) {
				index = souseList.size();
			}else {
				index = 8;
			}

			List<HistoryData> subSouseList = souseList.subList(0, index);

			System.out.println("履歴個数確認:"+ souseList);
			System.out.println("履歴個数絞り込み:"+ subSouseList);

			model.addAttribute("historyList", subSouseList);

			//DBに番号1の支出自動登録情報があるか確認
			autoSpendInfo AutoSpendInfo1 = new autoSpendInfo();
			AutoSpendInfo1 = autoservice.getAutoSpend1(loginItem.getUserid());

			if(AutoSpendInfo1 != null ) {
				//中身があったらリストへへ
				AutoSpendList.add(AutoSpendInfo1);
			}else {
				//データがない場合はステータスを1として新規登録
				autoSpendInfo AutoSpendInfox = new autoSpendInfo();
				AutoSpendList.add(AutoSpendInfox);
			}

			//DBに番号2の支出自動登録情報があるか確認
			autoSpendInfo AutoSpendInfo2 = new autoSpendInfo();
			AutoSpendInfo2 = autoservice.getAutoSpend2(loginItem.getUserid());

			if(AutoSpendInfo2 != null ) {
				//中身があったらリストへへ
				AutoSpendList.add(AutoSpendInfo2);
			}else {
				//データがない場合はステータスを1として新規登録
				autoSpendInfo AutoSpendInfo2x = new autoSpendInfo();
				AutoSpendList.add(AutoSpendInfo2x);
			}

			//DBに番号3の支出自動登録情報があるか確認
			autoSpendInfo AutoSpendInfo3 = new autoSpendInfo();
			AutoSpendInfo3 = autoservice.getAutoSpend3(loginItem.getUserid());

			if(AutoSpendInfo3 != null ) {
				//中身があったらリストへへ
				AutoSpendList.add(AutoSpendInfo3);
			}else {
				//データがない場合はステータスを1として新規登録
				autoSpendInfo AutoSpendInfo3x = new autoSpendInfo();
				AutoSpendList.add(AutoSpendInfo3x);
			}

			//DBに番号4の支出自動登録情報があるか確認
			autoSpendInfo AutoSpendInfo4 = new autoSpendInfo();
			AutoSpendInfo4 = autoservice.getAutoSpend4(loginItem.getUserid());

			if(AutoSpendInfo4 != null ) {
				//中身があったらリストへへ
				AutoSpendList.add(AutoSpendInfo4);
			}else {
				//データがない場合はステータスを1として新規登録
				autoSpendInfo AutoSpendInfo4x = new autoSpendInfo();
				AutoSpendList.add(AutoSpendInfo4x);
			}

			//DBに番号5の支出自動登録情報があるか確認
			autoSpendInfo AutoSpendInfo5 = new autoSpendInfo();
			AutoSpendInfo5 = autoservice.getAutoSpend5(loginItem.getUserid());

			if(AutoSpendInfo5 != null ) {
				//中身があったらリストへへ
				AutoSpendList.add(AutoSpendInfo5);
			}else {
				//データがない場合はステータスを1として新規登録
				autoSpendInfo AutoSpendInfo5x = new autoSpendInfo();
				AutoSpendList.add(AutoSpendInfo5x);
			}

			model.addAttribute("autospendlist",AutoSpendList);

			return "SpendEdit";
		}

		//支出編集登録
		@RequestMapping("/editcomplete")
		public String spendEditComp(SpendEditForm form) {
			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			Date spenddate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				spenddate = sdf.parse(form.getSpenddate());
			}catch(ParseException e) {
				e.printStackTrace();
			}

			int price = Integer.parseInt(form.getPrice());

			//formの値をdomainにセット
			SpendEdit spend = new SpendEdit();
			spend.setSpendid(form.getSpendid());
			spend.setSpendgenreid(form.getSpendgenreid());
			spend.setTitle(form.getTitle());
			spend.setUserid(form.getUserid());
			spend.setSpenddate(spenddate);
			spend.setContents(form.getContents());
			spend.setPrice(price);

			service.SpendUpdate(spend);

			return "spendeditcomplete";
		}

		//支出削除登録
		@RequestMapping("/deletecomplete/{spendid}")
		public String spendDeleteComp(@PathVariable int spendid) {

			LoginItem loginItem = (LoginItem)session.getAttribute("loginItem");

			if(loginItem == null) {
				return "redirect:/login/";
			}

			int userid = loginItem.getUserid();

			service.SpendDelete(spendid, userid);

			System.out.println(spendid+"デリート実行");
			return "redirect:/spend/details";
		}


}
