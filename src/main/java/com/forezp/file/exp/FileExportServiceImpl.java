package com.forezp.file.exp;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.forezp.file.ExportExcelHelp;
import com.forezp.file.vo.FileExportVO;


/**
 * 文件导出模板服务接口实现类
 * 
 * @author lishuaibing
 * @date 2017-7-18
 *
 */
@Service
public class FileExportServiceImpl implements IFileExportService {

	private Logger logger = LoggerFactory.getLogger(FileExportServiceImpl.class);
	
	private FileExportVO fileExportVO;	//文件导出实体
	
	@Override
	public void setFileExportVO(FileExportVO fileExportVO) {
		this.fileExportVO = fileExportVO;
	}
	@Override
	public FileExportVO getFileExportVO() {
		return this.fileExportVO;
	}
	
	/**
	 * @see cn.com.spdbccc.ehr.file.service.exp.IFileExportService#export(HttpServletResponse, FileExportEntity)
	 */
	@Override
	public void export(HttpServletResponse response, FileExportVO fileExportVO) {
		//执行写文件
		setFileExportVO(fileExportVO);
		exportFile();
		
		//从输出流输出数据文件
		ServletOutputStream out = ExportExcelHelp.getServletOutputStream(response, fileExportVO.getFileName());
		byte[] exportData = getFileExportVO().getFileData();
		ExportExcelHelp.output(out, exportData);
	}

	/**
	 * 导出文件
	 * 
	 * <p>1、获取模板文件
	 * <p>2、获取导出数据
	 * <p>3、数据写入文件
	 * 
	 */
	private void exportFile() {
		//验证参数是否设置
		if ("".equals(getFileExportVO().getFileName())) {
			throw new RuntimeException("请设置导出文件名！");
		} else if ("".equals(getFileExportVO().getTemplateFile())) {
			throw new RuntimeException("请设置模板文件！");
		} else if (getFileExportVO().getExportData() == null || getFileExportVO().getExportData().size() == 0) {
			throw new RuntimeException("请设置导出数据！");
		}
		
		logger.info("-----开始把数据写入模板文件...");
		
		//获取模板文件
		String templateFilePath = getFileExportVO().getTemplateFile();
		
		//获取导出数据
		List<?> exportDataList = getFileExportVO().getExportData();
		
		FileExportVO fileExportEntity = new FileExportVO();
		fileExportEntity.setTemplateFile(templateFilePath);
		fileExportEntity.setExportData(exportDataList);
		
		//把内容放到输出流
		byte[] buffer = writeFile(fileExportEntity);
		getFileExportVO().setFileData(buffer);
		
		logger.info("-----写文件完成.");
	}
	
	/**
	 * 写文件
	 * 
	 * @param fileExportEntity	文件导出参数对象
	 */
	private byte[] writeFile(FileExportVO fileExportVO) {
		byte[] buffer = null;
	/*	String fileName = fileExportVO.getTemplateFile();
		List<? extends BaseEntity> exportDataList = fileExportVO.getExportData();
		
    	//创建XLSTransformer对象  
        XLSTransformer transformer = new XLSTransformer();

        //得到模板文件路径  
        URL url = this.getClass().getClassLoader().getResource("");
        String templateFilePath = url.getPath() + "jxls/" + fileName;
        
        Map<String, Object> beanParams = new HashMap<String, Object>();
        beanParams.put("exportDataList", exportDataList);
        
        try {
        	//获取模板输入流
        	FileInputStream in = new FileInputStream(templateFilePath);
        	
        	//将数据通过模板输入流写到Workbook中
        	Workbook wb = transformer.transformXLS(in, beanParams);
        	
        	//将Workbook放入输出流
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	wb.write(bos);
        	
        	buffer = bos.toByteArray();
        } catch (Exception e) {
        	String errorMsg = "写文件异常！";
        	logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        }*/
    	return buffer;
	}
	
}
