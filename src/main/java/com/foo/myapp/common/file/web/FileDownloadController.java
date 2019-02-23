package com.foo.myapp.common.file.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;
//import egovframework.com.cmm.util.EgovUserDetailsHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foo.myapp.common.file.service.FileVO;
import com.foo.myapp.common.file.service.IFileMngService;
import com.foo.myapp.utils.ResourceCloseHelper;

/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일      	수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2009.03.25  	이삼섭          최초 생성
 *   2014.02.24		이기하          IE11 브라우저 한글 파일 다운로드시 에러 수정
 *
 * Copyright (C) 2009 by MOPAS  All right reserved.
 * </pre>
 */
@Controller
public class FileDownloadController {

	@Resource(name = "fileMngService")
	private IFileMngService fileMngService;

	/**
	 * 브라우저 구분 얻기.
	 *
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 *
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDisposition(String filename, String prefix, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		//크롬에서 쉼표가 들어간 파일명이 중복헤더 오류를 내므로 다음과 같이 처리한다.
		String dispositionPrefix = prefix + "; filename=\"";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20"); //공백은 플러스(+)로 바뀌므로 다시 공백으로 변경한다.
		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				//ASCII문자코드에서 마지막 문자 ~(126)
				//이 문자보다 크다면 URL인코딩을 수행한다. 한글이 인코딩된다.
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();

		} else {
			throw new IOException("Not supported browser");
		}

		//크롬에서 쉼표가 들어간 파일명이 중복헤더 오류를 내므로 다음과 같이 처리한다.
		//response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename + "\"");

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}


	/**
	 * 첨부파일로 등록된 파일에 대하여 다운로드를 제공한다.
	 *
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/fileDownload.do")
	public void fileDownload(@RequestParam Map<String, Object> commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileId = (String) commandMap.get("fileId");

		//나중에 적용하기로 한다.
		//Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
		Boolean isAuthenticated = true;

		if (isAuthenticated) {

			FileVO fileVO = new FileVO();
			fileVO.setFileId(fileId);
			FileVO fvo = fileMngService.selectFile(fileVO);

			File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());
			int fSize = (int) uFile.length();

			if (fSize > 0) {
				String mimetype = "application/x-msdownload";

				//response.setBufferSize(fSize);	// OutOfMemeory 발생
				response.setContentType(mimetype);

				//Content-Disposition을 브라우저별로 처리한다.
				//response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") + "\"");
				setDisposition(fvo.getOrignlFileNm(), "attachment", request, response);

				response.setContentLength(fSize);

				/*
				 * FileCopyUtils.copy(in, response.getOutputStream());
				 * in.close();
				 * response.getOutputStream().flush();
				 * response.getOutputStream().close();
				 */
				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());

					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (IOException ex) {
					// 다음 Exception 무시 처리
					// Connection reset by peer: socket write error
					ex.printStackTrace();
				} finally {
					ResourceCloseHelper.close(in, out);
				}

			} else {
				//response.setContentType("application/x-msdownload");
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");

				PrintWriter printwriter = response.getWriter();


				printwriter.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">");
				printwriter.println("<head>");
				printwriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
				printwriter.println("<title>Error</title>");
				printwriter.println("</head>");
				printwriter.println("<body>");
				printwriter.println("<b>Something went wrong. Please contact the site administrator.</b>");
				printwriter.println("<br/>Could not get file name:<br/>" + fvo.getOrignlFileNm());
				printwriter.println("</body>");
				printwriter.println("</html>");

				printwriter.flush();
				printwriter.close();
			}
		}
	}

	//filepond용 첨부파일 목록 조회
	@RequestMapping(value = "/common/fileList.do")
	public void fileList(@RequestParam(value="fileId", required=true) String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		FileVO fvo = new FileVO();
		fvo.setFileId(fileId);
		fvo = fileMngService.selectFile(fvo);

		File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());

		//filepond에서는 inline으로 설정하여 전송
		setDisposition(fvo.getOrignlFileNm(), "inline", request, response);

		//String dispositionPrefix = "inline; filename=\"";
		//String fileName = fvo.getOrignlFileNm(); //한글이 없는 경우
		//response.setHeader("Content-Disposition", dispositionPrefix + fileName + "\"");

		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream(uFile));
			out = new BufferedOutputStream(response.getOutputStream());

			FileCopyUtils.copy(in, out);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			ResourceCloseHelper.close(in, out);
		}

	}


}
