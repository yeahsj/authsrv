package net.suntec.oauthsrv.action.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 大众点评签名工具
 * @当前版本： 1.0
 * @创建时间: 2014-11-14 下午02:50:41
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public final class DianPingSignUtil {
	/**
	 * localhost:8080/auth/dianping/sign?_appUrl=http://api.dianping.com/v1/
	 * business
	 * /find_businesses&_method=GET&region=长宁区&limit=20&sort=7&offset_type
	 * =0&keyword
	 * =泰国菜&format=json&city=上海&has_coupon=1&category=美食&longitude=121.420033
	 * &radius=2000&has_deal=1&latitude=31.21524
	 **/
	public static String signParams(Map<String, String[]> requestMap, String appKey, String secret) {
		// if (requestMap.containsKey("_appUrl")) {
		// requestMap.remove("_appUrl");
		// }
		// if (requestMap.containsKey("_method")) {
		// requestMap.remove("_method");
		// }
		Map<String, String[]> newMap = new HashMap<String, String[]>();

		StringBuilder stringBuilder = new StringBuilder();
		String[] keys = requestMap.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {
			if (!"_appUrl".equals(key) && !"_method".equals(key)) {
				String[] values = requestMap.get(key);
				newMap.put(key, values);
				for (String value : values) {
					stringBuilder.append(key).append(value);
				}
			}
		}
		String codes = stringBuilder.append(secret).toString();
		String sign = DigestUtils.shaHex(codes).toUpperCase();

		stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
		for (Entry<String, String[]> entry : newMap.entrySet()) {
			String[] values = entry.getValue();
			for (String value : values) {
				stringBuilder.append('&').append(entry.getKey()).append('=').append(value);
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * localhost:8080/auth/dianping/sign?_appUrl=http://api.dianping.com/v1/
	 * business
	 * /find_businesses&_method=GET&region=长宁区&limit=20&sort=7&offset_type
	 * =0&keyword
	 * =泰国菜&format=json&city=上海&has_coupon=1&category=美食&longitude=121.420033
	 * &radius=2000&has_deal=1&latitude=31.21524
	 **/
	public static String signParams(HttpServletRequest req, Map<String, String[]> requestMap, String appKey,
			String secret) {
		// if (requestMap.containsKey("_appUrl")) {
		// requestMap.remove("_appUrl");
		// }
		// if (requestMap.containsKey("_method")) {
		// requestMap.remove("_method");
		// }
		Map<String, String> newMap = new HashMap<String, String>();

		StringBuilder stringBuilder = new StringBuilder();
		String[] keys = requestMap.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {
			if (!"_appUrl".equals(key) && !"_method".equals(key)) {
				String value = req.getParameter(key);
				newMap.put(key, value);
				stringBuilder.append(key).append(value);
			}
		}
		String codes = stringBuilder.append(secret).toString();
		String sign = DigestUtils.shaHex(codes).toUpperCase();

		stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
		for (Entry<String, String> entry : newMap.entrySet()) {
			String value = entry.getValue();
			stringBuilder.append('&').append(entry.getKey()).append('=').append(value);
		}
		return stringBuilder.toString();
	}

	public static String getSign(Map<String, String> requestMap, String secret) {
		requestMap.remove("_appUrl");
		requestMap.remove("_method");
		StringBuilder stringBuilder = new StringBuilder();
		String[] keys = requestMap.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {
			stringBuilder.append(key).append(requestMap.get(key));
		}
		String codes = stringBuilder.append(secret).toString();
		String sign = DigestUtils.shaHex(codes).toUpperCase();
		return sign;
	}

	public static String signUrlParam(Map<String, String> requestMap, String appKey, String sign) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);

		for (Entry<String, String> entry : requestMap.entrySet()) {
			stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		String conStr = "&";
		String con2Str = "=";
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("format", "json");
		paramMap.put("city", "上海");
		paramMap.put("latitude", "31.21524");
		paramMap.put("longitude", "121.420033");
		paramMap.put("category", "美食");
		paramMap.put("region", "长宁区");
		paramMap.put("limit", "20");
		paramMap.put("radius", "2000");
		paramMap.put("offset_type", "0");
		paramMap.put("has_coupon", "1");
		paramMap.put("has_deal", "1");
		paramMap.put("keyword", "泰国菜");
		paramMap.put("sort", "7");
		String[] keys = paramMap.keySet().toArray(new String[0]);
		for (String key : keys) {
			stringBuilder.append(conStr).append(key).append(con2Str).append(paramMap.get(key));
		}

		System.out.println(stringBuilder);
	}
}
