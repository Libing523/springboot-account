package com.forezp.file.imp;

import java.io.InputStream;

import com.forezp.file.IFileImpExpService;
import com.forezp.file.vo.ImpExpResult;



/**
 * 
    * @ClassName: IFileImportService
    * @Description: 文件导入服务接口
    * @author lishuaibing
    * @date 2018年1月3日 下午4:00:15
    *
    * @param <R>
 */
public interface IFileImportService<R> extends IFileImpExpService {

	/**
	 * 
	    * @Title: importExcel
	    * @Description: 导入Excel文件 
	    * @param  file  文件
	    * @param  isXlsx 是否XLSX文件，是：true， 否：false
	    * @return  导出数据总数
	 */
	ImpExpResult<R> importExcel(InputStream file, boolean isXlsx) throws Exception;
}
