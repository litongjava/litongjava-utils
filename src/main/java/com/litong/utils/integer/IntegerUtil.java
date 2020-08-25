package com.litong.utils.integer;

/**
 * @author litong
 * @date 2019年4月19日_下午1:31:02
 * @version 1.0
 */
public class IntegerUtil {

	/**
	 * convert byte to hex string
	 * 
	 * @param b
	 * @return
	 */
	public static StringBuffer toHexString(byte b) {
		StringBuffer stringBuilder = new StringBuffer();
		int temp = b & 0xFF;
		String hexString = Integer.toHexString(temp);
		if (hexString.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hexString);
		return stringBuilder;
	}

	/**
	 * Convert byte[] to hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
