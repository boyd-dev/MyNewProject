package com.foo.myapp.common.file.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 파일정보
 * @author foo
 *
 */
@SuppressWarnings("serial")
public class FileVO implements Serializable {


	/**
	 * 파일ID
	 */
	private String fileId = "";

    /**
     * 첨부파일 아이디
     */
	private String atchFileId = "";

    /**
     * 파일확장자
     */
	private String fileExtsn = "";

    /**
     * 파일크기
     */
	private String fileSize = "";


    /**
     * 파일저장경로
     */
	private String fileStreCours = "";

    /**
     * 원파일명
     */

	private String orignlFileNm = "";

    /**
     * 저장파일명
     */
	private String streFileNm = "";


	/**
	 * 업로드한 사용자 ID
	 */
	private String uploadUserId = "";


    public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getAtchFileId() {
		return atchFileId;
	}


	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}


	public String getFileExtsn() {
		return fileExtsn;
	}


	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}


	public String getFileSize() {
		return fileSize;
	}


	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileStreCours() {
		return fileStreCours;
	}


	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}


	public String getOrignlFileNm() {
		return orignlFileNm;
	}


	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}


	public String getStreFileNm() {
		return streFileNm;
	}


	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}


	public String getUploadUserId() {
		return uploadUserId;
	}


	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}


	/**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

}
