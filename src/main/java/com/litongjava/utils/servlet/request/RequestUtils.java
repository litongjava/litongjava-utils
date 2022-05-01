package com.litongjava.utils.servlet.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by litong on 2018/4/7 0007. HttpServletRequest utils
 */

public class RequestUtils {
	/**
	 * 返回 HttpServletRequest中的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割,存储在 x-forwarded-for中
			// "***.***.***.***".length()= 15
			if (ipAddress != null && ipAddress.length() > 15) {
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		return ipAddress;
	}

	/**
	 * output key and value of reuest package
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
  public static Map<String, String[]> paramaterToString(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> e : parameterMap.entrySet()) {
			System.out.println(e.getKey());
			System.out.println(e.getValue());
		}
		return parameterMap;
	}

	/**
	 * 获取request中的字符串
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestString(HttpServletRequest request) throws IOException {
		String method = request.getMethod();
		// GET
		if (method.equals("GET")) {
			String retval = getGetString(request);
			return retval;
		}
		// POST
		if (method.equals("POST")) {
			String retval = getPostString(request);
			return retval;
		}

		return null;
	}

	/**
	 * 获取Get请求中字符串
	 * 
	 * @param request
	 * @return
	 */
	private static String getGetString(HttpServletRequest request) {
		String queryString = request.getQueryString();
		if (queryString == null) {
			return null;
		}
		try {
			queryString = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		queryString = queryString.replaceAll("%22", "\"");
		return queryString;
	}

	/**
	 * 获取POST请求中字符串
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static String getPostString(HttpServletRequest request) throws IOException {
		byte[] buf = getRequestPostBytes(request);
		String encoding = request.getCharacterEncoding();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		String retval = new String(buf, encoding);
		return retval;
	}

	/**
	 * 获取Post请求中的数据流
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLen = request.getContentLength();
		byte[] buf = new byte[contentLen];
		for (int i = 0; i < contentLen;) {
			ServletInputStream ins = request.getInputStream();
			int readLen = ins.read(buf, i, contentLen - i);
			if (readLen == -1) {
				break;
			}
			i += readLen;
		}
		return buf;
	}
}