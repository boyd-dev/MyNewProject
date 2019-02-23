package com.foo.myapp.utils;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.foo.myapp.common.file.service.FileVO;
import com.foo.myapp.idgnr.service.IdGnrService;

/**
 * @Class Name  : EgovFileMngUtil.java
 * @Description : 파일 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.02.13       이삼섭                  최초 생성
 *   2011.08.09       서준식                  utl.fcc패키지와 Dependency제거를 위해 getTimeStamp()메서드 추가
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 02. 13
 * @version 1.0
 * @see
 *
 */
@Component(value="FileUtil")
public class FileUtil {

	public static final int BUFF_SIZE = 2048;

	@Resource(name="idGnrService")
	private IdGnrService idGnrService;

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 *
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String fileId = "";

		//추가
		//일자별로 폴더를 만들어서 넣는다.
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String strDate = fmt.format(new Date());

		if ("".equals(storePath) || storePath == null) {
			storePathString = GlobalProperties.getProperty("Globals.fileStorePath") + strDate + "/";
		} else {
			storePathString = GlobalProperties.getProperty(storePath);
		}

		if ("".equals(atchFileId) || atchFileId == null) {
			fileId = idGnrService.getNextStringId();
		} else {
			fileId = atchFileId;
		}

		File saveFolder = new File(WebUtil.filePathBlackList(storePathString));

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			//--------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			//--------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			////------------------------------------

			int index = orginFileName.lastIndexOf(".");
			//String fileName = orginFileName.substring(0, index);
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + getTimeStamp() + fileKey; //원본 파일명을 일정한 형식으로 변경한다.
			long size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = storePathString + File.separator + newName;
				file.transferTo(new File(WebUtil.filePathBlackList(filePath)));
			}

			fvo = new FileVO();
			fvo.setFileId(fileId);
			fvo.setOrignlFileNm(orginFileName);
			fvo.setFileStreCours(storePathString);
			fvo.setFileExtsn(fileExt);
			fvo.setFileSize(Long.toString(size));
			fvo.setStreFileNm(newName);

			result.add(fvo);

			fileKey++;
		}

		return result;
	}


	public void deleteFile(String fileStreCours, String streFileNm) throws Exception {

		String path = fileStreCours + streFileNm;
		try {

			File f = new File(WebUtil.filePathBlackList(path));
			if (f.isFile()) {
				f.delete();
			}

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}


	/**
	 * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	 *
	 * @param
	 * @return Timestamp 값
	 * @see
	 */
	private static String getTimeStamp() {

		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}
}
