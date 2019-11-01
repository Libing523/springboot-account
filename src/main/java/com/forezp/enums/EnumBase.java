package com.forezp.enums;

/**
 * 
* @ClassName: EnumBase 
* @Description: 枚举扩展接口 
* @author lishuaibing 
* @date 2018年1月12日 下午5:12:50 
*
 */
public interface EnumBase {
	/**
	 * 获取Code
	 */
	String toCode();

	/**
	 * 获取Label
	 */
	String toLabel();

	/**
	 * 判断code是否相等
	 * @param code 枚举值code
	 * @return true | false
	 */
	boolean equals(String code);
}
