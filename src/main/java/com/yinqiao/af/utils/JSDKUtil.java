package com.yinqiao.af.utils;

import java.security.MessageDigest;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JSDKUtil {
	private static Logger log = LoggerFactory.getLogger(JSDKUtil.class);

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private static String[] strs = new String[]
			{
             "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
             "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
            };
	
	/**
	 * �����ַ�
	 *
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * ������ת����ʮ����Ƶ��ַ���ʽ
	 * @param bytes
	 * @return
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) { 			
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	/**
	 * �����ַ� By MD5
	 *
	 * @param str
	 * @return String
	 */
	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(PublicConstants.ENCODE_ALGORITHM_MD5);
			messageDigest.update(str.getBytes("UTF-8"));
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	 /**
	  * ��������ַ�       
	  * @return
	  */
    public static String getNoncestr(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        int length = strs.length;
        for (int i = 0; i < 15; i++)
        {
            sb.append(strs[r.nextInt(length - 1)]);
        }
        return sb.toString();
    }
    
    /**
     * ���ʱ���
     * @return
     */
    public static long getTimestamp(){
        return (System.currentTimeMillis())/1000;
    }
    
    /**
     * ��ȡ����ǩ��
     * @param jsapi_ticket
     * @param noncestr
     * @param timestamp
     * @param url
     * @return
     */
    /*public static String getSignatureString(String url){
    	String noncestr = getNoncestr();
    	long timestamp = getTimestamp();
    	String jsapi_ticket = AccessUtil.getTicketFromDB();
    	String result  = "";
    	StringBuilder string1Builder = new StringBuilder();
    	log.info("Get signatrue by noncestr:{}, timestamp{}, jsapi_ticket{}, url:{}",noncestr,timestamp,jsapi_ticket,url);
        string1Builder.append("jsapi_ticket=").append(jsapi_ticket).append("&")
                      .append("noncestr=").append(noncestr).append("&")
                      .append("timestamp=").append(timestamp).append("&")
                      .append("url=").append(url.indexOf("#") >= 0 ? url.substring(0, url.indexOf("#")) : url);
//        return encode(PublicConstants.ENCODE_ALGORITHM_SHAR1, string1Builder.toString())
//        		+","+ noncestr+"," +timestamp ;
        //AddSHA1
        try {
        	System.out.println(AddSHA1.eccryptSHA1(string1Builder.toString()));
			result = AddSHA1.eccryptSHA1(string1Builder.toString())
					+","+ noncestr+"," +timestamp+","+Configuration.APPID;
			System.out.println(result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result ;
    }*/
    
    /*public static String getTicketTimeNumString(String url){
    	String noncestr = getNoncestr();
    	long timestamp = getTimestamp();
    	String jsapi_ticket = AccessUtil.getTicket();
    	//SHA1 sha1 = new SHA1();
    	return jsapi_ticket+","+noncestr+","+timestamp;
    }*/
}
