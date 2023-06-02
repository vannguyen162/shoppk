package com.developer.configure;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public class BrowserInfo {
	public static String[] getBrowserInfo(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		String browserName = "Unknown";
		String browserVersion = null;
		if (userAgent != null) {
			if (userAgent.contains("Firefox")) {
				browserName = "Firefox";
			} else if (userAgent.contains("Chrome")) {
				browserName = "Chrome";
			} else if (userAgent.contains("Safari")) {
				browserName = "Safari";
			} else if (userAgent.contains("Edg")) {
				browserName = "Microsoft Edge";
			} else if (userAgent.contains("OPR")) {
				browserName = "Opera";
			} else if (userAgent.contains("Coc Coc")) {
				browserName = "Coc Coc";
			} else if (userAgent.contains("Google")) {
				browserName = "Google";
			} else if (userAgent.contains("SamsungBrowser")) {
				browserName = "Samsung Internet";
			} else if (userAgent.contains("MiuiBrowser")) {
				browserName = "Xiaomi Browser";
			}
			String[] versionInfo = userAgent.split(browserName + "/");
			if (versionInfo.length > 1) {
				browserVersion = versionInfo[1].split("\\s")[0];
			} else {
				System.out.println("Error: userAgent=" + userAgent + ", versionInfo=" + Arrays.toString(versionInfo));
			}
		}
		return new String[] { browserName, browserVersion };
	}
}
