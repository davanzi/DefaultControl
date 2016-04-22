package com.controldefault.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	/**
	 * Main para testar o funcionamento da classe
	 */
//	public static void main(String[] args) {
//		try {
//			String password = EncryptionUtil.toPassword("daniel"); 																
//			System.out.println("password = " + password);
//			String password2 = EncryptionUtil.toPassword("dAniel"); 																	
//			System.out.println("password2 = " + password2);
//			String password3 = EncryptionUtil.toPassword("daniel"); 
//			System.out.println("password3 = " + password3);
//			if (!password.equals(password2)) {
//				System.out.println("password2 eh uma senha incorreta");
//			}
//			if (password.equals(password3)) {
//				System.out.println("password3 eh uma senha correta");
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	private static String bytesToHex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			sb.append((Integer.toHexString((b[i] & 0xFF) | 0x100)).substring(1,
					3));
		}
		return sb.toString();
	}

	public static String toPassword(String data)
			throws NoSuchAlgorithmException {
		byte[] mybytes = data.getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] md5digest = md5.digest(mybytes);
		return bytesToHex(md5digest);
	}
}
