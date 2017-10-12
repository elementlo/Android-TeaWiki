package com.zlz.helpers;

import java.util.HashMap;

public class appURLFinal {
	public static final String BASE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType";
	public static final String HEADER_PIC = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
	public static final String HEAD_LINES = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines";
	public static final String CONTENT_URL="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent";
	public static HashMap<String, String> getTOUTIAO(int i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("page", i + "");
		hm.put("rows", 15 + "");
		return hm;
	}

	public static HashMap<String, String> getBAIKE(int i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("type", 16 + "");
		hm.put("page", i + "");
		hm.put("rows", 15 + "");
		return hm;
	}

	public static HashMap<String, String> getZIXUN(int i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("type", 52 + "");
		hm.put("page", i + "");
		hm.put("rows", 15 + "");
		return hm;
	}

	public static HashMap<String, String> getJINGYING(int i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("type", 53 + "");
		hm.put("page", i + "");
		hm.put("rows", 15 + "");
		return hm;
	}

	public static HashMap<String, String> getSHUJU(int i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("type", 54 + "");
		hm.put("page", i + "");
		hm.put("rows", 15 + "");
		return hm;
	}
	public static HashMap<String, String> getCONTENT(String i) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("id", i + "");
		return hm;
	}
}
