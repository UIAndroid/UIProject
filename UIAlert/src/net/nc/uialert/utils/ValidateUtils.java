package net.nc.uialert.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：ValidateUtils<br>
 * 类描述：匹配工具<br>
 * 创建人：howtoplay<br>
 */
public class ValidateUtils {
	
	/**
	 * 匹配手机号码是否正确
	 * 添加者：howtoplay
	 * @param phone 输入的号码
	 * @return
	 * boolean
	 */
	public static boolean isMobile(String phone) {
		if (phone.length() != 11) {
			return false;
		}
		if (phone.startsWith("1") && (!phone.startsWith("10"))
				&& (!phone.startsWith("11")) && (!phone.startsWith("12"))
				&& (!phone.startsWith("16")) && (!phone.startsWith("19"))) {
			return matches("^[0-9]\\d*$", phone);
		} else {
			return false;
		}
	}
	
	public static boolean isEmail(String email) {
		if (email.length() < 6) {
			return false;
		}
		return matches(
				"^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
				email);
	}

	/**
	 * 匹配正则
	 * 添加者：howtoplay
	 * @param regex 正则表达式
	 * @param input 输入的内容
	 * @return
	 * boolean
	 */
	private static boolean matches(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

}
