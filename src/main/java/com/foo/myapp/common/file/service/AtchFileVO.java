package com.foo.myapp.common.file.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AtchFileVO implements Serializable {

	private String atchFileId = "";

	private String[] fileIds;

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String[] getFileIds() {
		return fileIds;
	}

	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
}
