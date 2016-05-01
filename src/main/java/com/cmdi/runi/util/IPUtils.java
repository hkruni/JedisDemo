package com.cmdi.runi.util;

public class IPUtils {
	// 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	// 将十进制整数形式转换成127.0.0.1形式的ip地址
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}

	/**
	 * @date：2016-4-29
	 * @author：hukai
	 * @param @param ipAddress
	 * @param @return 同ipToLong
	 */
	public static int ipToScore(String ipAddress) {
		int score = 0;
		for (String v : ipAddress.split("\\.")) {
			score = score * 256 + Integer.parseInt(v, 10);
		}
		return score;
	}

	public static void main(String[] args) {
		String ipStr = "58.50.24.78";
		long longIp = IPUtils.ipToLong(ipStr);
		long longIp1 = IPUtils.ipToScore(ipStr);
		System.out.println("longIp整数形式为：" + longIp);
		System.out.println("longIp1整数形式为：" + longIp1);
		System.out.println("整数" + longIp + "转化成字符串IP地址："
				+ IPUtils.longToIP(longIp));
		// ip地址转化成二进制形式输出
		System.out.println("二进制形式为：" + Long.toBinaryString(longIp));
	}
}
