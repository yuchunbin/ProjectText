package com.chunbin.app.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 解析json字符串
 * @author chenxin
 * 创建时间：2017年4月7日上午10:16:30
 */
public class GsonUtil {
	private static Gson gson = new Gson();

	/**
	 * java对象转json
	 * 
	 * @param ob
	 * @return
	 */
	public static String tojson(Object ob) {
		return GsonUtil.gson.toJson(ob);
	}

	/**
	 * 获取JsonObject
	 * 
	 * @param json
	 * @return
	 */
	private static JsonObject parseJson(String json) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		return jsonObj;
	}

	/**
	 * 根据json字符串返回Map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json) {
		Map<String, Object> result=null;  
		try{ 
			json=StringEscapeUtils.unescapeHtml4(json) ;
			result=GsonUtil.toMap(GsonUtil.parseJson(json));
		}catch(Exception ex){
			result=null;
			ex.printStackTrace();
		}
		if(result==null){
			try{
				result=GsonUtil.toMap(GsonUtil.parseJson(json));
			}catch(Exception ex){
				result=null;
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 根据json字符串返回Map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMapForRenda(String json) {
		Map<String, Object> result=null;  
		try{
			if(StringUtils.isEmpty(json)==false){ 
				json=StringEscapeUtils.unescapeHtml4(json) ;
				result=GsonUtil.toMap(GsonUtil.parseJson(json));
			}
		}catch(Exception ex){
			result=null;
			ex.printStackTrace();
		}
		if(result==null){
			try{
				result=GsonUtil.toMap(GsonUtil.parseJson(json));
			}catch(Exception ex){
				result=null;
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * 
	 * @param json
	 * @return
	 */
	private static Map<String, Object> toMap(JsonObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
		String val = "";
		for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter
				.hasNext();) {
			Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof JsonArray)
				map.put(key, toList((JsonArray) value));
			else if (value instanceof JsonObject)
				map.put(key, toMap((JsonObject) value));
			else {
				val = value.toString();
				if (val.startsWith("\"")) {
					val = val.substring(1, val.length() - 1);
				}
				map.put(key, val);
			}
		}
		return map;
	}

	/**
	 * 将JSONArray对象转换成List集合
	 * 
	 * @param json
	 * @return
	 */
	private static List<Object> toList(JsonArray json) {
		List<Object> list = new ArrayList<Object>();
		String val = "";
		for (int i = 0; i < json.size(); i++) {
			Object value = json.get(i);
			if (value instanceof JsonArray) {
				list.add(toList((JsonArray) value));
			} else if (value instanceof JsonObject) {
				list.add(toMap((JsonObject) value));
			} else {
				val = value.toString();
				if (val.startsWith("\"")) {
					val = val.substring(1, val.length() - 1);
				}
				list.add(val);
			}
		}
		return list;
	}

	/**
	 * 返回List集合
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static List<Object> toList(String jsonstr) {
		JsonParser parser = new JsonParser();
		JsonArray json = parser.parse(jsonstr).getAsJsonArray();
		List<Object> list = toList(json);
		return list;
	}
	
	public static void main(String[] args) {
//		String json = "{\"status\":0,\"info\":\"请求处理成功\",\"result\":{\"table\":[{\"data1\":\"上层区域名1\",\"data2\":\"区域名1\",\"data3\":\"上柜率98%\"},{\"data1\":\"上层区域名\",\"data2\":\"区域名2\",\"data3\":\"上柜率7%\"}],\"chart\":[{\"column\":\"区域名1\",\"data\":\"30\"},{\"column\":\"区域名2\",\"data\":\"50\"}],\"map\":{\"points\":[{\"lon\":\"119.310987 \",\"lat\":\"26.074476\",\"info\":{\"people\":\"专管员\",\"manage\":\"客户经理\",\"condition\":\"状态\",\"liable\":\"法定责任人\",\"license\":\"许可证号\",\"name\":\"零售户名称\",\"address\":\"零售户地址\"}},{\"lon\":\"118.5856603\",\"lat\":\"26.174476\",\"info\":{\"people\":\"专管员\",\"manage\":\"客户经理\",\"condition\":\"状态\",\"liable\":\"法定责任人\",\"license\":\"许可证号\",\"name\":\"零售户名称\",\"address\":\"零售户地址\"}}],\"grids\":[{\"data\":\"[[[119.2,26.1],[119.2,26.2],[119.3,26.2],[119.3,26.1],[119.2,26.1]]]\",\"center\":{\"lat\":\"26.1\",\"lon\":\"119.2\"},\"info\":{\"level\":\"0\"}}],\"regions\":[{\"data\":\"[[119.252242,26.101505],[119.250021,26.102703],[119.260158,26.079368],[119.277302,26.065698],[119.30383,26.077447],[119.318297,26.079076],[119.318888,26.075446],[119.326333,26.075],[119.322435,26.101829],[119.313023,26.124792],[119.298541,26.129305],[119.287717,26.124954],[119.271541,26.126625],[119.266749,26.121382],[119.269885,26.109787],[119.252242,26.101505]]\",\"center\":{\"lat\":\"26.101505\",\"lon\":\"119.252242\"},\"info\":{\"level\":\"0\"}}]}}}";
//		Map<String,Object> map = GsonUtil.toMap(json);
//		System.out.println((String)map.get("status"));
		BigDecimal bigDecimal_balance = new BigDecimal("29.6");
		System.out.println(bigDecimal_balance);
	}
}
