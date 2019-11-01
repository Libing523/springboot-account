package com.forezp.file;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forezp.file.imp.IFileImportService;
import com.forezp.file.vo.ExcelModelRowVO;
import com.forezp.file.vo.ExcelModelVO;
import com.forezp.file.vo.ImpExpResult;



/**
 * 
 * @ClassName: AbstractExcelImportTemplateService
 * @Description: 导入Excel文件模板类，导入文件Excel文件功能都可以集成该类
 * @author lishuaibing
 * @date 2018年1月3日 下午4:51:08
 *
 * @param <T>
 * @param <R>
 */
public abstract class AbstractExcelImportTemplateService<T, R> extends ExcelReader
		implements IFileImportService<R> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractExcelImportTemplateService.class);
	/**
	 * 问题分隔符
	 */
	protected final static String SEPARATOP = "#@#";

	/**
	 * 行下标，Excel文件从第1行开始
	 */
	private int rowNo = 0;

	public AbstractExcelImportTemplateService() {

	}

	public AbstractExcelImportTemplateService(InputStream ips, boolean isXlsx) {
		super(ips, isXlsx);
	}

	/**
	 * 
	 * @Title: fileToEntity
	 * @Description: Excel文件转换成 实体
	 * @param
	 * @return List<T> 实体类
	 * @throws Exception
	 */
	protected List<T> fileToEntity() throws Exception {
		List<T> entityList = null;
		try {
			entityList = this.getExcelData();
		} catch (Exception e) {
			throw e;
		}
		return entityList;
	}

	/**
	 * 
	 * @Description: 设置过滤字段(即跳过的字段)
	 * @param filterField
	 *            过滤字段
	 * @return String 过滤字段
	 */
	protected abstract String getFilterField(String filterField);

	/**
	 * 
	 * @Description: 定义模型类，即需要将数据转换成对应的实体类对象
	 * @param @return
	 * @return T
	 */
	protected abstract T getEntity();

	/**
	 * 
	 * @Description: 获取Excel文件列与实体类的映射
	 * @param
	 * @return Map<String,ExcelModelVO> 映射结果对象
	 */
	protected abstract Map<String, ExcelModelVO> getExcelFieldtoEntityMapping();

	/**
	 * 
	 * @Description: 获取合并列字段
	 * @param
	 * @return Map<String,Boolean>
	 */
	protected abstract Map<String, Boolean> getMergeColField();

	/**
	 * 
	 * @Description: 验证导入数据
	 * @param importDataList
	 *            导入数据列表
	 * @return void
	 */
	protected abstract void validate(List<T> importDataList);

	/**
	 * 
	 * @Description: 批量导入数据
	 * @param importDataList
	 *            导入数据列表
	 * @return ImpExpResult<T> 导入数据提示信息
	 */
	protected abstract ImpExpResult<T> batchImport(List<T> importDataList);

	@SuppressWarnings("unchecked")
	@Override
	public ImpExpResult<R> importExcel(InputStream file, boolean isXlsx) throws Exception {
		super.setInputStream(file);
		super.setXlsx(isXlsx);
		@SuppressWarnings("unused")
		ImpExpResult<R> impExpResul = new ImpExpResult<R>();
		try {
			List<T> importDataList = fileToEntity();
			this.validate(importDataList);
			impExpResul = (ImpExpResult<R>) batchImport(importDataList);
		} catch (Exception e) {
			logger.error("导入Excel文件失败！" + e.getMessage(), e);
			throw e;
		} finally {
			// 不管导入成功或者失败，最终行号都必须为0，否则会记录上一次的行号
			rowNo = 0;
		}
		return impExpResul;
	}

	/**
	 * 
	 * @Description: 覆盖基类的方法
	 * @param @return
	 * @return T
	 * @throws Exception
	 */
	public T nextRow() throws Exception {
		T entity = this.getEntity();
		if (rowNo > this.getRowsCount()) {
			return null;
		}
		// excel行数据，列名和value的map
		Map<String, Object> rowMap = this.getRow(rowNo);
		if (rowMap == null) {
			rowNo++;
			return null;
		}
		// entity 属性和Excel列名的对应关系
		Map<String, ExcelModelVO> fieldAndExcelConlumnMap = this.getExcelFieldtoEntityMapping();
		if (fieldAndExcelConlumnMap == null) {
			throw new RuntimeException("初始化错误：未定义属性和excel列名的对应关系");
		}

		Iterator<String> it = fieldAndExcelConlumnMap.keySet().iterator();
		Integer currentRow = rowNo + 1;

		Map<Integer, Map<String, ExcelModelVO>> colData = new HashMap<Integer, Map<String, ExcelModelVO>>();
		Map<String, Object> srcFieldMap = null;
		while (it.hasNext()) {
			// enity的属性名
			String field = it.next();
			if (isContain(field)) {
				continue;
			}

			// excel列名称
			ExcelModelVO excelModel = fieldAndExcelConlumnMap.get(field);
			// 得到excel列对应的值
			Object value = rowMap.get(excelModel.getColCName());

			// 检查标题行
			if (rowNo == 0) {
				if (!rowMap.containsKey(excelModel.getColCName())) {
					throw new RuntimeException(
							"第" + currentRow + "行，没有找到“" + excelModel.getColCName() + "”,请使用正确的模板导入！");
				} else {
					continue;
				}
			}
			// entity给属性赋值
			if (rowNo > 0 && !"".equals(value)) {
				this.setProperty(entity, field, value);
			}

			excelModel.setRowIndex(currentRow);
			if (colData.isEmpty()) {
				Map<String, ExcelModelVO> col = new HashMap<String, ExcelModelVO>();
				col.put(excelModel.getColEName(), excelModel);
				colData.put(currentRow, col);
			} else {
				Map<String, ExcelModelVO> colValue = colData.get(currentRow);
				colValue.put(excelModel.getColEName(), excelModel);
			}

			// 需要和并列才会被执行，合并列字段的值以列名+行号暂存。
			if (rowNo > 0 && isMergeColFiled()) {
				boolean isFindMergeColField = this.isFindMergeColField(field);
				if (isFindMergeColField) {
					if (srcFieldMap == null) {
						srcFieldMap = new HashMap<String, Object>();
					}
					String colEName = excelModel.getColEName();
					int rowIndex = excelModel.getRowIndex();
					srcFieldMap.put(colEName + rowIndex, value);
				}
			}
			// 需要和并列才会被执行，合并列字段的值以列名+行号暂存。

		}
		if (rowNo > 0) {
			if (isMergeColFiled()) {
				String mergeColDestFieldKey = this.getMergeColDestFieldKey();
				String mergeColSrcFiledVal = this.getMergeColSrcFieldVal(currentRow, srcFieldMap);
				this.setProperty(entity, mergeColDestFieldKey, mergeColSrcFiledVal);
			}

			ExcelModelRowVO excelModelRowVO = new ExcelModelRowVO();
			excelModelRowVO.setRowNo(currentRow);
			excelModelRowVO.setRowData(colData);
			this.setProperty(entity, "excelModelRowVO", excelModelRowVO);
		}
		rowNo++;
		this.setRowNo(rowNo);

		// 判断如果返回的是空行，则返回空 
		if (isEmpty(entity, "id,money,rowNo,rowData,excelModelRowVO")) {
			return null;
		}
		return entity;
	}

	/**
	 * 
	 * @Description: 设置属性值
	 * @param entity
	 *            bean对象
	 * @param field
	 *            字段名称
	 * @param value
	 *            字段值
	 * @return void
	 */
	private void setProperty(T entity, String field, Object value) {
		try {
			BeanUtils.setProperty(entity, field, value);
		} catch (Exception e) {
			logger.error("设置属性失败");
			throw new RuntimeException("设置属性失败!");
		}
	}

	/**
	 * 
	 * @Description: 设置从哪一行开始读数据
	 * @param rowNo
	 * @return void
	 */
	private void setRowNo(int rowNo) {
		this.rowNo = rowNo;

	}

	/**
	 * 
	 * @Description: 将下标移动到第一行数据
	 * @param
	 * @return void
	 */
	public void moveIndexToFristRow() {
		rowNo = 0;
	}

	/**
	 * 
	 * @Description: 获取Excel数据
	 * @return List<T> Excel数据
	 */
	private List<T> getExcelData() throws Exception {
		// 上传一个文件异常后再进行另一个文件上传时会发现不读取新文件，因此在每次上传文件时都进行读取。
		this.open();
		int i = 1;
		List<T> list = new ArrayList<T>();
		this.moveIndexToFristRow();
		while (hashNext()) {
			T model = this.nextRow();
			if (i > 1 && model == null) {
				logger.error("第" + i + "行数据为空!");
				continue;
			}
			if (model != null) {
				list.add(model);
			}
			i++;
		}
		return list;
	}

	/**
	 * 
	 * @Description: 判断是否有下一行
	 * @return boolean
	 */
	private boolean hashNext() {
		if (this.getWorkbook() == null) {
			this.open();
		}
		if (rowNo < this.getRowsCount()) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 
	    * @Description: 判断bean类中属性值是否都为空 
	    * @param  bean Bean对象
	    * @param  notValidateFields 不验证字段(跳出实体中不需要检查的字段)
	    * @return boolean true:所有字段都为空，false:否
	 */
	protected boolean isEmpty(Object bean, String notValidateFields) {
		Field[] fields = bean.getClass().getDeclaredFields();
		boolean isEmptyField = this.isEmptyField(bean,fields,notValidateFields);
		if(!isEmptyField) {
			return isEmptyField;
		}
		fields = bean.getClass().getSuperclass().getDeclaredFields();
		isEmptyField = this.isEmptyField(bean,fields,notValidateFields);
		return isEmptyField;
	}
	
	/**
	 * 
	    * @Description: 判断字段对象是否为空
	    * @param  bean Bean对象
	    * @param  fields 对象或基类对象
	    * @param  notValidateFields 不验证字段(跳出实体中不需要检查的字段)
	    * @return boolean
	 */
	private boolean isEmptyField(Object bean, Field[] fields, String notValidateFields) {
		boolean isEmpty = true;//JavaBean中的属性值都为空，则为true,否则为false
		
		for(Field field : fields) {
			field.setAccessible(true);
			try {
				if("serialVersionUID".equals(field.getName())) {
					continue;
				}else if("".equals(notValidateFields) && notValidateFields.indexOf(field.getName()) != -1) {
					//找到不需要验证的字段跳出，否则继续进行
					continue;
				}else {
					Object value = field.get(bean);
					if(value!=null && !"".equals(value)) {
						isEmpty = false;
						break;
					}
				}
				
			}catch(IllegalArgumentException e) {
				logger.error("参数异常！",e);
				throw new RuntimeException("参数异常");
			}catch(IllegalAccessException e) {
				logger.error("访问权限异常",e);
				throw new RuntimeException("访问权限异常");
			}
		}
		
		return isEmpty;
	}

	/**
	 * 
	    * @Description: 是否合并列字段
	    * @return boolean 合并列字段 true-是；false-否
	 */
	private boolean isMergeColFiled() {
		Map<String, Boolean> mergeColFieldMap = this.getMergeColField();
		if(mergeColFieldMap == null || mergeColFieldMap.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	    * @Description: 是否找到合并列字段 
	    * @param  colField 列字段
	    * @return boolean true-是;false-否
	 */
	private boolean isFindMergeColField(String colField) {
	
		Map<String,Boolean> mergeColFieldMap = this.getMergeColField();
		if(mergeColFieldMap.containsKey(colField)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	    * @Description: 获取合并列目标字段的key 
	    * @return String 合并列目标字段的key
	 */
	private String getMergeColDestFieldKey() {
		//获取合并字段
		Map<String,Boolean> mergeColFieldMap = this.getMergeColField();
		//获取目标字段
		String destFieldKey = null;
		
		//是否存在目标字段
		boolean isExistDestField = false;
		Iterator<Map.Entry<String, Boolean>> colFieldIt = mergeColFieldMap.entrySet().iterator();
		while(colFieldIt.hasNext()) {
			Map.Entry<String, Boolean> colFieldEntity = colFieldIt.next();
			Boolean colFieldVal = colFieldEntity.getValue();
			if(colFieldVal) {
				isExistDestField = true;
				destFieldKey = colFieldEntity.getKey();
				break;
			}
		}
		if(!isExistDestField) {
			throw new RuntimeException("getMergeColField方法中未找到目标字段key");
		}
		return destFieldKey;
	}
	/**
	 * 
	    * @Description: 获取合并列原字段值 
	    * @param  currentRow 
	    * @param  srcField 原字段值
	    * @return String 原字段值(以问题1...N排列，问题间隔符用#@#)
	 */
	private String getMergeColSrcFieldVal(Integer currentRow, Map<String, Object> srcField) {
		StringBuilder srcFieldVal = null;
		ExcelModelVO excelModel = new ExcelModelVO();
		for(int colIndex = 0;colIndex < srcField.size();colIndex++) {
			String colEName = excelModel.getColIndexName(colIndex);
			Object srcFldValue = srcField.get(colEName+currentRow);
			if(srcFldValue == null || String.valueOf(srcFldValue).isEmpty()) {
				srcFldValue = "";
			}
			if(srcFieldVal == null) {
				srcFieldVal = new StringBuilder();
				srcFieldVal.append(srcFldValue);
			}else {
				srcFieldVal.append(SEPARATOP).append(srcFldValue);
			}
			
		}
		return srcFieldVal.toString();
	}

	
	/**
	 * 
	    * @Description: 获取分割问题 
	    * @param  question 问题(以间隔符“#@#”分割问题)
	    * @return String[] 分割问题
	 */
	protected String[] getSeparatorQuestion(String question) {
		String[] splitQuestion = question.split(SEPARATOP);
		return splitQuestion;
	}
	
	/**
	 * 
	    * @Description: 获取Excel模型数据 
	    * @param  colData 列数据
	    * @param  colName 列名字
	    * @return ExcelModelVO Excel模型数据
	 */
	protected ExcelModelVO getExcelModelVOData(Map<String,ExcelModelVO> colData,String colName) {
		ExcelModelVO excelModelVO = null;
		Iterator<Map.Entry<String, ExcelModelVO>> colIt = colData.entrySet().iterator();
		while(colIt.hasNext()) {
			Map.Entry<String, ExcelModelVO> colEntry = colIt.next();
			String colKey = colEntry.getKey();
			if(colName.equals(colKey)) {
				excelModelVO = colEntry.getValue();
				break;
			}else {
				continue;
			}
			
		}
		return excelModelVO;
	}
	
	/**
	 * 
	    * @Description: 判断值是否为是或者否
	    * @param  value 
	    * @return boolean 
	 */
	protected boolean isYesORNo(String value) {
		if("Y".equals(value) || "N".equals(value)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	    * @Description: 获取列数据 
	    * @param  rowNo 行号
	    * @param  rowData 行数据
	    * @return Map<String,ExcelModelVO> 行数据
	 */
	protected Map<String,ExcelModelVO> getColData(Integer rowNo,Map<Integer,Map<String,ExcelModelVO>> rowData){
		Map<String,ExcelModelVO> colData = null;
		Iterator<Map.Entry<Integer, Map<String, ExcelModelVO>>> it = rowData.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Map<String, ExcelModelVO>> rowEntry = it.next();
			if(rowEntry.getKey() == rowNo) {
				colData = rowEntry.getValue();
				break;
			}else {
				continue;
			}
		}
		return colData;
	}
	
	/**
	 * 
	    * @Description: 获取列位置的英文字母 
	    * @param  colData 列数据
	    * @param  colEName 列字段英文名称
	    * @return String 列位置英文名称
	 */
	protected String getColIndexName(Map<String,ExcelModelVO> colData,String... colEName) {
		StringBuilder colIndexNameBuild = new StringBuilder();
		for(int i=0;i<colEName.length;i++) {
			String eName = colEName[i];
			ExcelModelVO excelModel = getExcelModelVOData(colData, eName);
			Integer colIndex = excelModel.getColIndex();
			String colIndexName = excelModel.getColIndexName(colIndex);
			
			if(i == colEName.length - 1) {
				colIndexNameBuild.append(colIndexName);
			}else {
				colIndexNameBuild.append(colIndexName).append("/");
			}
		}
		return colIndexNameBuild.toString();
	}
	
	/**
	 * 
	    * @Description: 获取列的中文名称 
	    * @param  colData 列数据
	    * @param  colEName 列字段英文字母
	    * @return String 列的中文名称
	 */
	protected String getColCName(Map<String,ExcelModelVO> colData,String...colEName) {
		
		StringBuilder colCNameBuild = new StringBuilder();
		for(int i=0;i<colEName.length;i++) {
			String eName = colEName[i];
			ExcelModelVO excelModel = getExcelModelVOData(colData, eName);
			String colCName = excelModel.getColCName();
			if(i == colEName.length - 1) {
				colCNameBuild.append(colCName);
			}else {
				colCNameBuild.append(colCName).append("、");
			}
		}
		return colCNameBuild.toString();
	}
	
	/**
	 * 
	    * @Description: 获取列的中文名称
	    * @param  colData 列数据
	    * @param  colENameMap 列字段英文名称
	    * @return String 列的中文名称
	 */
	protected String getColCName(Map<String,ExcelModelVO> colData,Map<String,Object> colENameMap) {
		StringBuilder colCNameBuild = new StringBuilder();
		Iterator<Map.Entry<String, ExcelModelVO>> it = colData.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ExcelModelVO> entry = it.next();
			String eName = entry.getKey();
			ExcelModelVO excelModel = getExcelModelVOData(colData, eName);
			String colCName = excelModel.getColCName();
			if(colCNameBuild.length() == 0) {
				colCNameBuild.append(colCName);
			}else {
				colCNameBuild.append(colCName).append("、");
			}
		}
		return colCNameBuild.toString();
	}
	
	/**
	 * 
	    * @Description: 显示错误信息(抛异常显示) 
	    * @param  rowNo 行号
	    * @param  colData 列数据
	    * @param  message 提示信息
	    * @param  colEName 列字段英文名字
	 */
	protected void showErrorMessage(Integer rowNo,Map<String,ExcelModelVO> colData,String message,String...colEName) {
		String colCName = getColIndexName(colData, colEName);
		throw new RuntimeException("第" + rowNo + "行," + colCName + "列" + message );
	}
	
	/**
	 * 
	    * @Description: 显示错误信息(不抛异常显示) 
	    * @param  rowNo 行号
	    * @param  colData 列数据
	    * @param  message 提示信息
	    * @param  colENameMap 列字段英文名称
	    * @return String 返回的错误提示信息
	 */
	protected String showErrorMessage(Integer rowNo,Map<String,ExcelModelVO> colData,String message,Map<String ,Object> colENameMap) {
		
		StringBuilder returnMessage = new StringBuilder();
		returnMessage.append("第").append(rowNo).append("行,");
		String colName = getColCName(colData, colENameMap);
		returnMessage.append(colName);
		returnMessage.append(message);
		
		return returnMessage.toString();
	}
	
/**
 * 
    * @Description: 是否包含过滤字段 
    * @param @param field 字段
    * @return boolean true-是,false-否
 */
	private boolean isContain(String field) {
		String filterField = getFilterField(field);
		if(filterField == null || "".equals(filterField)) {
			return false;
		}
		if(field.indexOf(filterField) != -1) {
			return true;
		}
		return false;
	}

}
