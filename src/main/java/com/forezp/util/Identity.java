package com.forezp.util;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author fanff
 */
public class Identity {

	private static SecureRandom random = new SecureRandom();

	/** 初始值 */
	private static int serial = 0;
	/** 最大序列值：999 **/
	private static final int MAX_SERIAL = 999;
	/** 长度：12 **/
	private static final int SEQUENCE_LENTH = 12;
	/** 长度：6 **/
	private static final int SEQUENCE_LENTH_NO = 6;

	/**
	 * 并发同步生成UUID
	 */
	public static synchronized String syncUUID() {
		return asyncUUID();
	}
	
	/**
	 * 异步方式UUID
	 */
	public static String asyncUUID() {
		StringBuilder ret = new StringBuilder();
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dfDate.setLenient(false);
		ret.append(dfDate.format((new GregorianCalendar()).getTime()));

		DecimalFormat dfNum = new DecimalFormat("000");
		ret.append(dfNum.format(serial++));

		if (serial > MAX_SERIAL) {
			serial = 0;
		}

		UUID uuid = UUID.randomUUID();
		ret.append(uuid.toString().replace("-", "").subSequence(0, SEQUENCE_LENTH));
		return ret.toString();
	}
	
	/**
	 * 异步方式17位UUID，目前作为券码
	 */
	public static String asyncSevenTeenUUID() {
		StringBuilder ret = new StringBuilder();
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyymmss");
		dfDate.setLenient(false);
		ret.append(dfDate.format((new GregorianCalendar()).getTime()));

		DecimalFormat dfNum = new DecimalFormat("000");
		ret.append(dfNum.format(serial++));

		if (serial > MAX_SERIAL) {
			serial = 0;
		}

		UUID uuid = UUID.randomUUID();
		ret.append(uuid.toString().replace("-", "").subSequence(0, SEQUENCE_LENTH_NO));
		return ret.toString();
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
}
