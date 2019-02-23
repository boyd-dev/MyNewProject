package com.foo.myapp.common.file.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.foo.myapp.common.AbstractService;
import com.foo.myapp.common.file.service.AtchFileVO;
import com.foo.myapp.common.file.service.FileVO;
import com.foo.myapp.common.file.service.IFileMngDao;
import com.foo.myapp.common.file.service.IFileMngService;
import com.foo.myapp.idgnr.service.IdGnrService;

@Service("fileMngService")
public class FileMngServiceImpl extends AbstractService implements IFileMngService {

	@Resource(name="fileMngeDAO")
	private IFileMngDao fileMngDAO;

	@Resource(name="idGnrService")
	private IdGnrService idGnrService;


	/**
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 */
	public void insertFile(FileVO fvo) throws Exception {
		fileMngDAO.insertFile(fvo);
	}


	@Override
	public List<FileVO> selectFileList(FileVO fvo) throws Exception {
		return fileMngDAO.selectFileList(fvo);
	}


	@Override
	public void deleteFile(FileVO fvo) throws Exception {
		fileMngDAO.deleteFile(fvo);
	}

	@Override
	public FileVO selectFile(FileVO fvo) throws Exception {
		return fileMngDAO.selectFile(fvo);
	}

	@Override
	public String updateFileAttchId(AtchFileVO fvo) throws Exception {

		String atchFileId = String.valueOf(idGnrService.getNextId());

		fvo.setAtchFileId(atchFileId);
		int result = fileMngDAO.updateFileAttchId(fvo);

		if (result > 0) {
			return atchFileId;
		} else {
			return null;
		}
	}

}
