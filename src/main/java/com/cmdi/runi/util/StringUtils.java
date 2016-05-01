package com.cmdi.runi.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.cmdi.runi.model.constant.Constant;

public class StringUtils {

	/**
	 * @date：2016-4-24
	 * @author：hukai
	 * @param @param e
	 * @param @return todo：打印异常
	 */
	public static String printStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalise(String str) {
		return org.springframework.util.StringUtils.capitalize(str);
	}

	/**
	 * 把数组按指定分割符号链接
	 * 
	 * @param ints
	 * @return
	 */
	public static String join(Integer[] ints, String joinChar) {
		if (ints == null || ints.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Integer i : ints) {
			sb.append(i);
			sb.append(joinChar);
		}
		return sb.toString().substring(0, sb.length() - joinChar.length());

	}

	/**
	 * 把 1,2,3,4转成int数组
	 */
	public static Integer[] parseArr(String intStr) {
		if (intStr == null || intStr.length() == 0) {
			return null;
		}
		String[] strs = intStr.split("[,]");
		Integer[] iarr = new Integer[strs.length];
		for (int i = 0; i < strs.length; i++) {
			iarr[i] = Integer.valueOf(strs[i]);
		}
		return iarr;
	}

	/**
	 * @date：2016-4-30
	 * @author：hukai
	 * @param： todo：数组按照中文进行排序
	 */
	public static void sortByChinese(String[] str) {
		Arrays.sort(str, Collator.getInstance(java.util.Locale.CHINA));
	}

	/**
	 * @date：2016-4-30
	 * @author：hukai
	 * @param： todo：中文字符串转拼音
	 */
	public static String getPingYin(String src) {

		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
			// System.out.println(t4);
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * @date：2016-4-30
	 * @author：hukai
	 * @param： todo：英文字符串转为分值,对中文不起作用
	 */
	public static long stringToScore(String string, boolean ignoreCase) {
		if (ignoreCase) {
			string = string.toLowerCase();
		}

		List<Integer> pieces = new ArrayList<Integer>();
		for (int i = 0; i < Math.min(string.length(), 6); i++) {
			pieces.add((int) string.charAt(i));
		}
		while (pieces.size() < 6) {
			pieces.add(-1);
		}

		long score = 0;
		for (int piece : pieces) {
			score = score * 257 + piece + 1;
		}

		return score * 2 + (string.length() > 6 ? 1 : 0);
	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：二进制字符串转十进制
	 */
	public static int twoToTen(String two) {
		int s = Integer.valueOf(two);
		int sum = 0;
		int i = 0;
		while (s != 0) {
			sum = (int) (sum + s % 10 * (Math.pow(2, i)));
			s = s / 10;
			i++;
		}
		return sum;
	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：十进制整型转二进制字符串
	 */
	public static String tenToTwo(Integer i) {
		return Integer.toBinaryString(i);
	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：根据标签整型值获取标签内容
	 */
	public static String getTag(Integer tagInt) {
		String tag = tenToTwo(tagInt);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tag.length(); i++) {
			if ((tag.charAt(i) - '0') == 1)
				result.append(Constant.house_tag[i] + ";");
		}
		return result.toString();
	}

	public static void main(String[] args) {

	}
}
