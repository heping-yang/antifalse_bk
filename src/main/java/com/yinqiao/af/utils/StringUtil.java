/*
 * 文 件 名:  StringUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  字符串处理的Util
 * 修 改 人:  cKF49884
 * 修改时间:  Mar 17, 2014
 * @remark create cKF49884 2014/03/17 R003C12LG03n01
 */
package com.yinqiao.af.utils;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    
    /**
     * 如果字符串为空则返回空字符串
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static String voidNull(String str)
    {
        if (str == null)
        {
            return "";
        }
        return str;
    }
    
    /**
     * 判断字符串是否为空
     * 
     * @param str
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isNull(String str)
    {
        return (str == null || "".equals(str.trim())) ? true : false;
    }
    
    /**
     * 校验是否为数字
     * 
     * @param str 校验的字符串
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isNum(String str)
    {
//        String regex = "^[0-9]+$";
        String regex = "^[0-9]*\\.{0,1}[0-9]*$";
        return str.matches(regex);
    }
    
    /**
     * 校验是否为指定长度的数字
     * 
     * @param str 校验的字符串
     * @param length 校验的长度
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isNum(String str, int length)
    {
        String regex = "^[0-9]{" + length + "}$";
        return str.matches(regex);
    }

    /**
     * 校验浮点型数字是否在指定的长度区间内
     * 
     * @param str 字符串
     * @param min 最小长度
     * @param max 最大长度
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isNum(String str, String min, String max)
    {
        String regex = "^[[0-9](\\.?)[0-9]]{" + min + "," + max + "}$";
        return str.matches(regex);
    }
    /**
     * 校验str是否在最大值和最小值之间
     * 
     * @param str 字符串
     * @param minNum 最小值
     * @param maxNum 最大值
     * @return
     * @remark create cKF49884 2014/07/23 R003C12LG03n01
     */
    public static boolean isbetweenNum(String str, String minNum, String maxNum)
    {
        if (StringUtil.isNull(str) || !StringUtil.isNum(str) 
                || StringUtil.isNull(minNum) || !StringUtil.isNum(minNum)
                || StringUtil.isNull(maxNum) || !StringUtil.isNum(maxNum))
        {
            return false;
        }
        
        if(Integer.parseInt(str) >= Integer.parseInt(minNum) && Integer.parseInt(str) <= Integer.parseInt(maxNum))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Email检查
     * 
     * @param email 要检查的字符串
     * @return boolean 检查结果
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isEmail(String email)
    {
        if (email == null)
        {
            return false;
        }
        String regEx = "([\\w[_-][\\.]]+@+[\\w[_-]]+\\.+[A-Za-z]{2,3})|([\\"
                + "w[_-][\\.]]+@+[\\w[_-]]+\\.+[\\w[_-]]+\\.+[A-Za-z]{2,3})|"
                + "([\\w[_-][\\.]]+@+[\\w[_-]]+\\.+[\\w[_-]]+\\.+[\\w[_-]]+" + "\\.+[A-Za-z]{2,3})";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }
    
    /**
     * 按指定长度，省略字符串部分字符
     * 
     * @param String 被处理字符串
     * @param length 保留字符串长度
     * @param more 后缀字符串
     * @return 省略后的字符串
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static String subString(String string, int length, String more)
    {
        if (null == string || string.getBytes().length < length)
        {
            return string;
        }
        StringBuffer sb = new StringBuffer();
        int moreLen = more.length();
        int count = 0;
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++)
        {
            char tempChar = charArray[i];
            count += byteLength(tempChar);
            
            if (count < length - moreLen)
            {
                sb.append(tempChar);
            }
            else if (count == length - moreLen)
            {
                sb.append(tempChar);
                break;
            }
            else if (count > length - moreLen)
            {
                break;
            }
        }
        sb.append(more);
        return sb.toString();
    }
    
    /**
     * 计算字符长度 中文字符2，英文 1
     * 
     * @param chr
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    private static int byteLength(char chr)
    {
        if (Integer.toHexString(chr).length() == 4)
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }
    
    /**
     * 查看字符串有多少个字节
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static int getLength(String str)
    {
        int num = 0;
        String strb = "";
        for (int i = 0; i < str.length(); i++)
        {
            strb = str.substring(i, i + 1);
            if (strb.getBytes().length == 1)
            {
                num = num + 1;
            }
            else
            {
                num = num + 2;
            }
        }
        return num;
    }
    
    /**
     * 校验是否为手机号码
     * 
     * @param str 校验的字符串
     * @return
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isMobile(String str)
    {
        String regex = "^1\\d{10}$";
        String regex1 = "^15[0-9]\\d{8}$";
        return str.matches(regex) || str.matches(regex1);
    }
    
    /**
     * 判断是否为电话号码(含固话，手机号)
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @remark create lWX212232 2014/ R003C12LG03n01
     */
    public static boolean isTelNumber(String str)
    {
        if (null == str)
        {
            return false;
        }
        // 验证电话号码(可以是手机或座机，座机可以有区号，区号后可以有-)
        // 座机，无区号
        String immobile1Reg = "^[1-9]\\d{6,7}$";
        
        // 座机，有区号，之后带-
        String immobile2Reg = "^0\\d{2,3}-\\d{7,8}$";
        
        // 座机，有区号，之后不带-
        String immobile3Reg = "^0\\d{2,3}\\d{7,8}$";
        
        // 手机
        String mobileReg = "^1\\d{10}$";
        Pattern p1 = Pattern.compile(immobile1Reg);
        Pattern p2 = Pattern.compile(immobile2Reg);
        Pattern p3 = Pattern.compile(immobile3Reg);
        Pattern p4 = Pattern.compile(mobileReg);
        return p1.matcher(str).matches() || p2.matcher(str).matches() || p3.matcher(str).matches()
                || p4.matcher(str).matches();
    }
    
    /**
     * 特殊字符检查
     * 
     * @param str 被检查字符串
     * @return result 检查后的结果
     * @remark create cKF49884 2014/03/06 R003C12LG03n01
     */
    public static boolean isNicName(String nicName)
    {
        if (nicName == null)
        {
            return false;
        }
        String regEx = "^[\\w|\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(nicName);
        return matcher.matches();
    }

    /**
     * 为现有的URL组装参数 主要是在添加参数时分割是应该是?还是&的区别
     * @param url 原始URL
     * @param paramName 参数名称
     * @param paramValue 参数值
     * @return
     * @see [类、类#方法、类#成员]
     * @remark create cKF49884 2014/ R003C12LG03n01
     */
    public static String addParamToUrl(String url, String paramName, String paramValue)
    {
         if(StringUtil.isNull(url))
         {
             return "";
         }
         StringBuffer newURL = new StringBuffer(url);
         
         //判断原URL中是否包含?
         if(newURL.indexOf("?") == -1)
         {
             newURL.append("?");
         }
         else
         {
             newURL.append("&");
         }
         
         //判断参数值是否为null
         if(StringUtil.isNull(paramValue))
         {
             paramValue = "";
         }
         
         //将参数组装到URL中
         newURL.append(paramName).append("=").append(paramValue);
         return newURL.toString();
    }
    
    /**
     * 校验简单密码
     * 简单密码包括：
     * 123456|654321|111111|222222|333333|444444|555555|666666|777777|888888|999999|000000
     * @param pwd 密码
     * @return  
     * @remark create z00263642 2014/3/10 R003C12LG03n01
     */
    public static boolean simplePwdChk(String pwd) {
        String reg = "(123456|654321|111111|222222|333333|444444|555555|666666|777777|888888|999999|000000)";
        return pwd.matches(reg);
    }
    
    /**
     * 校验邮编
     * @param postCode 邮编
     * @return true:校验成功;false:校验失败
     * @see [类、类#方法、类#成员]
     * @remark create l00278840 2015/02/02
     */
    public static boolean isPostCode(String postCode)
    {
    	if (null == postCode)
        {
            return false;
        }
        String regEx = "^[1-9][0-9]{5}";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(postCode);
        return matcher.matches();
    }
    
    /**
	 * 获取当前时间序列号
	 * 
	 * @return
	 */
	public static String getTimeSerial() {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateformat1.format(new Date());
	}
	
    /**
     * 格式化日期
     * 
     * @param pattern
     * @param date
     * @return
     */
    public static String formatDate(String pattern, Date date)
    {
        String s = "";
        
        if (null != date)
        {
            s = new SimpleDateFormat(pattern).format(date);
        }
        // else 缺省值 ""
        
        return s;
    }
    
    /**
     * 为当前日期增加指定的分钟
     * 
     * @param addNum 增加的分钟数
     * @return String 增加秒后yyyyMMddHHmmss格式的日期
     * @throws ParseException 日期格式异常
     * add by zWX182682 2015/06/23 支付机构需要知道订单超时时间算出与订单时间的差值
     */
    public static String addSecondDate(int addNum) throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = df.parse(formatDate("yyyyMMddHHmmss", new Date()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, addNum);
        return df.format(cal.getTime());
    }
    
    /**
     * 将手机号码替换成136****0000
     * @param str
     * @return
     */
    public static String hidenTelNum(String str){
    	if(str == null || str.length() < 11){
    		return str;
    	}else{
    		return str.substring(0,str.length()-(str.substring(3)).length())+"****"+str.substring(7);
    	}
    }
    /**
	 * 支付宝服务窗请求参数处理
	 * @param paramMap
	 * @return
	 */
	public static String getParam(Map<String, String> paramMap){
		 if (paramMap == null) {
		      return null;
		 }else {
			paramMap.remove("charset");
			StringBuffer content = new StringBuffer();
			List<String> keys = new ArrayList<String>(paramMap.keySet());
		    Collections.sort(keys);
		    content.append("charset=UTF-8&");
		    for (int i = 0; i < keys.size(); i++) {
		      String key = (String)keys.get(i);
		      @SuppressWarnings("deprecation")
		      String value = URLEncoder.encode((String)paramMap.get(key));
		      content.append((i == 0 ? "" : "&") + key + "=" + value);
		    }

		    return content.toString();
		}
	}
	public static Map stringParamToMap(String paramsStr){
		Map params = new HashMap();
		if (checkNull(paramsStr)){
			//	
		}else{
			String[] beforeParams = paramsStr.split("&");
			for(String paramStr :  beforeParams){
				String[] param = paramStr.split("=");
				if (param.length < 2){
					params.put(param[0], "");
				}else{
					params.put(param[0], param[1]);
				}
			}
		}
		return params;
	}
	public static boolean checkNull(String checkStr){
		if (checkStr == null || "".equals(checkStr)){
			return true;
		}
		return false;
	}
	public static List stringParamToList(String paramsStr) {
		List params = new ArrayList();
		if (checkNull(paramsStr)){
			//	
		}else{
			String[] beforeParams = paramsStr.split("&");
			int i = 0;
			for(String paramStr :  beforeParams){
				String[] param = paramStr.split("=");
				params.add(i, param[0]);
				i ++;
			}
		}
		return params;
	}
	
	public static String getCurrentBillCycle() {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMdd");
		return dateformat1.format(new Date());
	}
	//为空处理
	public static String parseNullString(Object parse){
		if (parse == null){
			return "";
		}else{
			return parse.toString();
		}
	}
}
