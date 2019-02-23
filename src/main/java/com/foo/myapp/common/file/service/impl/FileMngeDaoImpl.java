package com.foo.myapp.common.file.service.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.foo.myapp.common.AbstractDao;
import com.foo.myapp.common.file.service.AtchFileVO;
import com.foo.myapp.common.file.service.FileVO;
import com.foo.myapp.common.file.service.IFileMngDao;


@Repository("fileMngeDAO")
public class FileMngeDaoImpl extends AbstractDao implements IFileMngDao {


	/**
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 * @param fvo
	 * @throws Exception
	 */
	public void insertFile(FileVO fvo) throws Exception {
		insert("File.insertFile", fvo);
	}

	@Override
	public int updateFileAttchId(AtchFileVO vo) throws Exception {
		return update("File.updateFileAttchId", vo);
	}

	/**
	 * 하나의 파일을 삭제한다.
	 *
	 * @param fvo
	 * @throws Exception
	 */
	public void deleteFile(FileVO fvo) throws Exception {
		delete("File.deleteFile", fvo);
	}

	/**
	 * 파일에 대한 목록을 조회한다.
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> selectFileList(FileVO vo) throws Exception {
		return selectList("File.selectFileList", vo);
	}

	/**
	 * 파일에 대한 상세정보를 조회한다.
	 *
	 * @param fvo
	 * @return
	 * @throws Exception
	 */
	public FileVO selectFile(FileVO fvo) throws Exception {
		return select("File.selectFile", fvo);
	}


	@Override
	public String insertFiles(List<?> fileList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
