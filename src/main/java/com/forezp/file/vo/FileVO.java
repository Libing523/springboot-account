package com.forezp.file.vo;

import java.util.Date;


/**
 * 
    * @ClassName: FileVO
    * @Description: 文件属性实体类
    * @author lishuaibing
    * @date 2018年1月3日 下午1:39:41
    *
 */
public class FileVO  {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String topic;
	
	private String fileKey;
	
	private String fileName;
	
	private Date uploadDate;
	
	private Integer userId;
	
	private byte[] fileData;
	
	private String filePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

	
	
	
}
