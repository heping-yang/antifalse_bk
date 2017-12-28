package com.yinqiao.af.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtils {
    
    //内置秘钥
    public static final String aplicationSecret = "waiter";
    


    public static List<String> convert(Collection<Map.Entry<String,CharSequence>> entries) {
        List<String> result = new ArrayList<String>(entries.size());

        for (Map.Entry<String,CharSequence> entry : entries) {
            result.add(entry.getKey() + "=" + entry.getValue());
        }
        return result;
    }

    public static String generateSignature( Collection<Map.Entry<String,CharSequence>> entrieSet, String appSecret) {
        // 把Set转换成一个list，进行排序
        List<String> params = SignatureUtils.convert(entrieSet);

        Collections.sort(params);
        StringBuffer buffer = new StringBuffer();

        for (String param : params) {
            buffer.append(param);
        }
        buffer.append(appSecret);

        String md5String = buffer.toString();

        return SignatureUtils.md5Encode( md5String );
    }

    /**
     * This function is used for md5 a string variable.
     * 
     * @param originStr
     * @return
     */
    public static String md5Encode(String originStr) {
        String md5String = "";
        StringBuffer buffer = new StringBuffer();
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(originStr.getBytes("UTF-8"));

            for (byte b : bytes) {
                buffer.append(Integer.toHexString((b & 0xf0) >>> 4));
                buffer.append(Integer.toHexString(b & 0x0f));
            }
            md5String = buffer.toString();
        } catch (java.security.NoSuchAlgorithmException nsae) {
            System.err.println ("MD5 does not appear to be supported" + nsae);
        } catch (UnsupportedEncodingException usee) {
            System.err.println ("MD5 does not appear to be supported" + usee);
        }
        
        return md5String;
    }

    
    public static boolean validateSignature(Map<String, String> paramMap, String signature)
    {
    	paramMap.remove("sig");
    	System.out.println(paramMap.toString());
        TreeMap<String, CharSequence> formatParams = new TreeMap<String, CharSequence>();
        
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue ;
            }
            formatParams.put(entry.getKey(), entry.getValue().toString());
        }
        
        String sig = SignatureUtils.generateSignature(formatParams.entrySet(), aplicationSecret);
        System.out.println("小二生成签名：sig="+sig);
        return signature.equals(sig);
    }
    
    public static String getSignature(Map<String, String> paramMap){
        TreeMap<String, CharSequence> formatParams = new TreeMap<String, CharSequence>();
        
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue ;
            }
            formatParams.put(entry.getKey(), entry.getValue().toString());
        }
        
        return SignatureUtils.generateSignature(formatParams.entrySet(), aplicationSecret);
    }
    
    
    public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		map.put("orderId", "123456789");
		map.put("productId", "001");
		map.put("productName", "网上开户");
		map.put("fee", "800");
		map.put("shareCode", "98A36B78500A6D97D22768DDBBE151B9540218D899EDC1953C788003D076D740");
		map.put("telnum", "13888888888");
		String sig = SignatureUtils.getSignature(map);
		System.out.println(sig);
		map.put("sig", sig);
		System.out.println(SignatureUtils.validateSignature(map, sig));
	}
}
