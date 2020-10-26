package com.example.demo.config;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.FortuneData;


public class FortuneResource implements Serializable{

//	private Object horoscope = new LinkedHashMap<>();
//
//	public Object getHoroscope() {
//		return horoscope;
//	}
//
//	public void setHoroscope(Object horoscope) {
//		this.horoscope = horoscope;
//	}



	private Map<String, List<FortuneData>> horoscope = new LinkedHashMap<>();

	public Map<String, List<FortuneData>> getHoroscope() {
		return horoscope;
	}

	public void setHoroscope(Map<String, List<FortuneData>> horoscope) {
		this.horoscope = horoscope;
	}






}
