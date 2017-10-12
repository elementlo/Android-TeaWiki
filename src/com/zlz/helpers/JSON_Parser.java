package com.zlz.helpers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.zlz.classentity.ContentData;
import com.zlz.classentity.ContentDataList;
import com.zlz.classentity.MyData;
import com.zlz.classentity.Tea;

public class JSON_Parser {
	public static List<Tea> parseJSON(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			List<Tea> datas=new ArrayList<Tea>();
			Tea tea;
			for(int i=0;i<jsonArray.length();i++){
				tea=new Tea();
				JSONObject object = (JSONObject) jsonArray.get(i);
				tea.setId(object.getString("id"));
				tea.setTitle(object.getString("title"));
				tea.setDescription(object.getString("description"));
				tea.setSource(object.getString("source"));
				tea.setCreate_time(object.getString("create_time"));
				tea.setNickname(object.getString("nickname"));
				tea.setWap_thumb(object.getString("wap_thumb"));
				datas.add(tea);
			}
			return datas;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static MyData parseJSONHeader(String str) {
		MyData data;
		Gson gson=new Gson();
		data=gson.fromJson(str, MyData.class);
		return data;
	}
	public static ContentDataList parseJSONContent(String str){
		ContentDataList condatalist;
		Gson gson=new Gson();
		condatalist=gson.fromJson(str, ContentDataList.class);
		return condatalist;
	}
	}

