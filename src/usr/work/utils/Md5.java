package usr.work.utils;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class Md5 {
	
	
	public static String encode(String str){
		String newstr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return newstr;
	}
}
