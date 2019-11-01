package com.forezp.util;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><b>Description:</b> 字符串工具类,
 * 继承org.apache.commons.lang3.StringUtils类
 * <p><b>Company:</b> Newtouch
 *
 * @author created by hongda at 2016年3月2日 下午3:37:04
 * @version V0.1
 */
public class IdeaStringUtils{
    /**
     * 判断对象toString是否为空或者为空字符串
     *
     * @param obj
     * @return true or false
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        return isBlank(obj.toString());
    }

    /**
     * 判断对象toString是否不为空
     *
     * @param obj
     * @return true or false
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 将对象转换为字符串
     *
     * @param o 对象
     * @return 字符串
     */
    public static String objToStr(Object o) {
        String str = (o == null) ? "" : o.toString();
        return str.trim();
    }

    /**
     * 格式化路径 将分隔符设定为'/'
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        String temp = "";
        if (path != null) {
            String reg0 = "\\\\+";
            String reg = "\\\\+|/+";
            temp = path.trim().replaceAll(reg0, "/");
            temp = temp.replaceAll(reg, "/");
            if (temp.length() > 1 && temp.endsWith("/")) {
                temp = temp.substring(0, temp.length() - 1);
            }
        }
        // temp = temp.replace('/', File.separatorChar);
        return temp;
    }

    /**
     * 获取对象的有效值
     *
     * @param t        当前值
     * @param defaultT 默认值
     * @return 有效值
     */
    public static <T> T obtainValidValue(final T t, T defaultT) {
        if (t instanceof Short || t instanceof Integer || t instanceof Long) {
            return (t == null || ((Number) t).longValue() == 0) ? defaultT : t;
        } else if (t instanceof String) {
            return isBlank(t) ? defaultT : t;
        } else {
            return t == null ? defaultT : t;
        }
    }


    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 将整形转定长字符串，不够前面补零
     * 比如：123,8 返回:00000123
     *
     * @param i      数字
     * @param length 长度
     * @return 定长字符串
     */
    public static String intToStr(Integer i, Integer length) {
        StringBuffer tmp = new StringBuffer(i.toString());
        int diffLength = length - tmp.length();
        for (int j = 0; j < diffLength; j++) {
            tmp.insert(0, "0");
        }
        return tmp.toString();
    }

    /**
     * 判断字符串是否以指定前缀开始
     * @param string 字符串
     * @param prefix 前缀
     * @return 如果以指定前缀开始返回true，否则false
     */
    /*public static Boolean startsWith(String string, String prefix){
		boolean isStart = true;
		if (string == null || prefix == null || string.length() <= prefix.length()){
			return false;
		}
		
		char [] ch = prefix.toCharArray();
		
		for (int i = 0; i < ch.length; i++) {
			if (string.charAt(0) != ch[i]) {
				isStart = false;
				break;
			}
		}
		return isStart;
	}*/

    /**
     * 判断字符串是否以指定后缀结尾
     * @param string 字符串
     * @param suffix 后缀
     * @return 如果以指定后缀结尾返回true，否则false
     */
	/*public static Boolean endsWith(String string, String suffix){
		boolean isEnd = true;
		if (string == null || suffix == null || string.length() < suffix.length()){
			return false;
		}
		
		char [] ch = suffix.toCharArray();
		int strLen = string.length();
		int chLen = ch.length;
		for (int i = 0; i < chLen; i++) {
			if (string.charAt(strLen+i-chLen) != ch[i]) {
				isEnd = false;
				break;
			}
		}
		return isEnd;
	}*/
	
	/*public static void main(String[] args) {
		String sign = "abc";
		String string = "abcgdsgs";
		
		Long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < 10000000; i ++){
			if(string.charAt(0) == 'a' && string.charAt(1) == 'b' && string.charAt(2) == 'c'){
				
			}
			string.startsWith(sign);
		}
		
		System.out.println(System.currentTimeMillis() - startTime);
	}*/
}
