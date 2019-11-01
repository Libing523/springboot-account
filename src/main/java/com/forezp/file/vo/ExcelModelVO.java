package com.forezp.file.vo;


/**
 * 
    * @ClassName: ExcelModelVO
    * @Description: Excel文件模型VO
    * @author lishuaibing
    * @date 2018年1月3日 下午4:33:26
    *
 */
public class ExcelModelVO {

	private static final long serialVersionUID = 1L;

	private Integer rowIndex;	//行位置
	
	private Integer colIndex;	//列位置
	
	private enum colIndexName {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,AS,AT,AU,AV,AW,AX,AY,AZ};	//列位置的英文名称
	
	private String colEName;	//列字段英文名称
	
	private String colCName;	//列字段中文名称
	
	public ExcelModelVO() {
	}
	
	public ExcelModelVO(Integer rowIndex, Integer colIndex, String colCName) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.colCName = colCName;
	}

	public ExcelModelVO(Integer colIndex, String colEName, String colCName) {
		this.colIndex = colIndex;
		this.colEName = colEName;
		this.colCName = colCName;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * 获取列位置的英文名称
	 * @param colIndex	列位置从0开始
	 * @return	列的英文名称
	 */
	public String getColIndexName(Integer colIndex) {
		int maxCol = colIndexName.values().length;
		if (colIndex > maxCol) {
			throw new RuntimeException("more than col index, max col is " + maxCol);
		}
		
		String name = null;
		for (int i = 0; i < maxCol; i++) {
			if (i == colIndex) {
				name = colIndexName.values()[i].name();
				break;
			} else {
				continue;
			}
		}
		return name;
	}

	public String getColEName() {
		if (colEName == null) {
			colEName = this.getColIndexName(this.colIndex);
		}
		return colEName;
	}

	public void setColEName(String colEName) {
		this.colEName = colEName;
	}

	public String getColCName() {
		return colCName;
	}

	public void setColCName(String colCName) {
		this.colCName = colCName;
	}
	
}
