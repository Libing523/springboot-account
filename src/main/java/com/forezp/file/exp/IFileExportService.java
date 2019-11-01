package com.forezp.file.exp;

import javax.servlet.http.HttpServletResponse;

import com.forezp.file.IFileImpExpService;
import com.forezp.file.vo.FileExportVO;


/**
 * 
    * @ClassName: IFileExportService
    * @Description: 文件导出服务接口
    * @author lishuaibing
    * @date 2018年1月3日 下午1:35:15
    *
 */
public interface IFileExportService extends IFileImpExpService{

	/**
	 * 
	    * @Title: setFileExportVO
	    * @Description: 设置文件导出对象
	    * @param  fileExportVO    文件导出对象
	    * @return void    返回类型
	 */
	void setFileExportVO(FileExportVO fileExportVO);
	
	/**
	 * 
	    * @Title: getFileExportVO
	    * @Description: 获取文件导出对象
	    * @param     参数
	    * @return FileExportVO    文件导出对象
	 */
	FileExportVO getFileExportVO();
	
	/**
	 * 
	    * @Title: export
	    * @Description: 导出
	    * @param  response 相应请求对象
	    * @param  fileExportVO    文件导出对象
	    * @return void    返回类型
	 */
	void export(HttpServletResponse response,FileExportVO fileExportVO);
	
	
	
}
