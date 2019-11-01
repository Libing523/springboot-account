package com.forezp.enums;

/**
 * 
* @ClassName: AccountStatusEnum 
* @Description: 错误代码枚举值 
* @author lishuaibing 
* @date 2018年1月12日 下午5:13:15 
*
 */
public enum AccountStatusEnum implements EnumBase {

	/**************文件上传,下载******************/
	AC7901("AC7901", "传入的文件为空!"),
	AC7902("AC7902", "文件格式不正确!"),
	AC7906("AC7906", "文件导出失败!"),
	AC7907("AC7907", "文件导入失败!"),

	
	/**************访问数据库******************/
	AC7903("AC7903", "访问数据库异常!"),
	AC7904("AC7904", "文件格式不正确!"),
	AC7905("AC7905", "文件格式不正确!");
	
	
	private String code;
	private String label;

	AccountStatusEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	@Override
	public String toCode() {
		return this.code;
	}

	@Override
	public String toLabel() {
		return this.label;
	}

	@Override
	public boolean equals(String code) {
		return this.code.equals(code);
	}
}
