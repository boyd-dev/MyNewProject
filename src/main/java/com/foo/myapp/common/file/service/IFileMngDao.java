package com.foo.myapp.common.file.service;

import java.util.List;


public interface IFileMngDao {

	public String insertFiles(List<?> fileList) throws Exception;

	public void insertFile(FileVO vo) throws Exception;

	public void deleteFile(FileVO fvo) throws Exception;

	public List<FileVO> selectFileList(FileVO vo) throws Exception;

	public FileVO selectFile(FileVO fvo) throws Exception;

	public int updateFileAttchId(AtchFileVO vo) throws Exception;

}
