package com.foo.myapp.board.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.foo.myapp.board.service.BoardVO;
import com.foo.myapp.board.service.IBoardService;
import com.foo.myapp.board.service.SearchBoardVO;
import com.foo.myapp.common.auth.UserDetailsHelper;
import com.foo.myapp.common.file.service.AtchFileVO;
import com.foo.myapp.common.file.service.FileVO;
import com.foo.myapp.common.file.service.IFileMngService;
import com.foo.myapp.login.service.LoginVO;
import com.foo.myapp.tag.Paging;
import com.foo.myapp.utils.FileUtil;
import com.foo.myapp.utils.GlobalProperties;

/**
 * 첨부파일이 있는 게시판 처리용 컨트롤러
 * 일반 게시판 컨트롤러와 구분하기로 한다.
 * @author foo
 *
 */
@Controller
public class BoardFileController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static final int PAGE_RECORD_SIZE = Integer.parseInt(GlobalProperties.getProperty("Board.PageRecordSize"));
	private static final int PAGE_LIST_SIZE = Integer.parseInt(GlobalProperties.getProperty("Board.PageListSize"));


	@Autowired
	private IBoardService service;

	//리소스 이름을 지정한 경우에는 이름으로 참조해야 한다.
	@Resource(name="FileUtil")
	private FileUtil fileUtil;

    @Autowired
	private IFileMngService fileMngService;

    @Resource(name="messageSource")
   	private MessageSource messageSource;


    @RequestMapping("/board/boardListFile.do")
	public ModelAndView boardList(@ModelAttribute("searchVO") SearchBoardVO boardVO, Model model, HttpSession session, HttpServletRequest request) throws Exception {

		LOGGER.debug("Session ID = " + session.getId());
		LOGGER.debug("Board ID = " + boardVO.getBoardId());
		LOGGER.debug("Page No = " + boardVO.getPageNo());
		LOGGER.debug("Search Type = " + boardVO.getSearchType());

        //페이징 정보와 페이징 계산을 처리하는 Paging 객체를 생성한다.
		//페이징은 기본적으로 전체건수, 페이지에 표시할 게시물 수, 페이지 하단에 표시하는 페이지 탭(페이지 이동)의 수, 현재 페이지번호가 필요하다.
		Paging pagingInfo = new Paging();
		pagingInfo.setCurrentPageNo(boardVO.getPageNo());
		pagingInfo.setRecordCountPerPage(PAGE_RECORD_SIZE);
		pagingInfo.setPageSize(PAGE_LIST_SIZE);

		LOGGER.debug("firstIndex = " + pagingInfo.getFirstRecordIndex());
		LOGGER.debug("lastIndex = " + pagingInfo.getLastRecordIndex());

		boardVO.setFirstIndex(pagingInfo.getFirstRecordIndex());
		boardVO.setLastIndex(pagingInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(pagingInfo.getRecordCountPerPage());

		Map<String, Object> map = service.selectBoardList(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));

		pagingInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("pagingInfo", pagingInfo);//커스텀 태그인 PagingTag에 전달

		ModelAndView mav = new ModelAndView("/board/listFile");
		return mav;
	}

    //신규등록 화면으로 이동, 파일첨부가 가능한 쓰기 화면으로 이동한다.
	@RequestMapping("/board/addPostingFile.do")
	public String addPostingFile(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		return "/board/writeFile";
	}

	//파일업로드만 처리
	//filepond는 파일을 하나씩 즉시 업로드하는 형태(디폴트)이므로 파일만 처리하는 메소드를 만들기로 한다.
	@RequestMapping("/board/boardSaveFile.do")
	@ResponseBody
	public String boardSaveFile(MultipartHttpServletRequest multipartReq, Model model, HttpSession session) throws Exception {

		String userId = "";
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		if (loginVO != null) {
			userId = loginVO.getMberId();
		}

		//한 번에 파일이 여러 개 업로드되는 경우
		//filepond에서 하나씩 전송되므로 실제로는 파일이 1개만 전송된다.
		Map<String, MultipartFile> files = multipartReq.getFileMap();

//			if (!files.isEmpty()) {
//				Iterator<String> itf = files.keySet().iterator();
//				String key = "";
//				LOGGER.debug("UPLOAD--------------------------------------------------------");
//				while(itf.hasNext()) {
//					key = itf.next();
//					LOGGER.debug(key + "=" + files.get(key).getOriginalFilename());
//				}
//			}

		String uniqueFileId = ""; //화면으로 리턴해주는 파일ID, filepond는 이 정보가 필요하다.
		if (!files.isEmpty()) {

			//실제 파일명을 시스템에서 정한 형식으로 파일명을 바꾸어 정해진 경로에 업로드시킨다.
			List<FileVO> fileVOs = new ArrayList<FileVO>();
			fileVOs = fileUtil.parseFileInf(files, "BOARD0002_", 0, "", ""); //prefix는 특별한 의미가 없다.

			if (fileVOs != null) {

				//filepond에서 하나씩 업로드하므로 루프는 1회 수행된다.
				for (FileVO f : fileVOs) {

				    f.setUploadUserId(userId);
				    if (LOGGER.isDebugEnabled()) {
					    LOGGER.debug(f.getOrignlFileNm() + ":" + f.getStreFileNm() + ":" + f.getFileExtsn() + ":" + f.getFileSize() + ":" + f.getFileStreCours());
				    }

					//파일 정보 테이블에 인서트한다.
				    //나중에 이 파일 정보와 게시물 정보를 연결시켜 줄 것이다.
					fileMngService.insertFile(f);
					uniqueFileId = f.getFileId();
				}
			}
		}

		//해당 파일ID를 리턴한다. filepond에서 정한 형식은 text/plain이다.
		//@ResponseBody로 설정하여 문자열만 전송되도록 한다.
		return uniqueFileId;
	}

	//신규등록 파일 정보와 함께 처리
	@RequestMapping("/board/boardSaveAttchFile.do")
	public String boardSaveAttchFile(@ModelAttribute("postVO") BoardVO boardVO,
			                         Model model,
			                         HttpServletRequest req,
			                         HttpSession session) throws Exception {


		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		if (loginVO != null) {
			boardVO.setAuthorId(loginVO.getMberId());
		}

		if (LOGGER.isDebugEnabled()) LOGGER.debug(boardVO.toString());

		//파일 정보와 게시물 정보를 연결시켜 주려면 이미 업로드된 파일ID를 전달받아야 한다.
		//여러 개의 파일ID들이 전달되므로 IN절을 사용하여 해당 파일 정보를 업데이트하고 마스터 정보에도 전달한다.
		String[] filepondFileIds = req.getParameterValues("filepondFileId");
//			for (String s : filepondFileIds) {
//				LOGGER.debug(s);
//			}
		if (filepondFileIds.length > 0) {
			AtchFileVO atchFileVO = new AtchFileVO();
			atchFileVO.setFileIds(filepondFileIds);
			String atchFileId = fileMngService.updateFileAttchId(atchFileVO);
			boardVO.setAtchFileId(atchFileId); //마스터 테이블에 업데이트를 위해서 이 정보가 필요하다.
		}

		int result = service.saveBoardContent(boardVO);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("INSERT=" + result);
		}

		return "forward:/board/boardListFile.do"; //forward: request정보가 그대로 유지되면서 내부적으로 해당 매핑에 전달
	}

	//신규등록 업로드된 파일삭제
	//아직 마스터 데이터는 인서트되지 않은 상태에서 파일정보만 삭제
	//파일ID만 있으면 되므로 @RequestParam을 사용하기로 한다.
	@RequestMapping("/board/boardDeleteFile.do")
	@ResponseBody
	public String boardDeleteFiles(@RequestParam(value="fileId", required=true) String fileId, Model model, HttpSession session) throws Exception {

		if (LOGGER.isDebugEnabled()) LOGGER.debug("File ID=" + fileId);

		FileVO fileVO = new FileVO();
		fileVO.setFileId(fileId);
		FileVO fvo = fileMngService.selectFile(fileVO);

		//디스크에서 물리적인 파일을 먼저 삭제한다.
		fileUtil.deleteFile(fvo.getFileStreCours(), fvo.getStreFileNm());

		//파일 정보를 삭제한다.
		fileMngService.deleteFile(fvo);

		return "File removed!"; //의미는 없다.
	}


	@RequestMapping("/board/boardCnttFile.do")
	public String boardCntt(@ModelAttribute("searchVO") SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		LOGGER.debug("Article ID = " + boardVO.getCnttId());

		int result = service.updateHit(boardVO); //조회수를 증가시킨다.

		if (result > 0) {
			BoardVO vo = service.selectBoardCntt(boardVO);
			model.addAttribute("result", vo);

			//첨부파일이 있다면 파일 목록도 조회한다.
			if (vo.getAtchFileId() != null) {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(vo.getAtchFileId());
				List<FileVO> fileList = fileMngService.selectFileList(fvo);
				model.addAttribute("fileList", fileList);
			}
		}
		return "/board/readFile";
	}

	//수정화면으로 이동한다.
	@RequestMapping("/board/boardModFile.do")
	public String boardModify(@ModelAttribute("postVO") SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		BoardVO vo = service.selectBoardCntt(boardVO);
		model.addAttribute("result", vo);

		//첨부파일 정보 조회
		FileVO fvo = new FileVO();
		fvo.setAtchFileId(vo.getAtchFileId());
		List<FileVO> fileList = fileMngService.selectFileList(fvo);
		model.addAttribute("fileList", fileList);

		return "/board/modifyFile";
	}

	//filepond에 업로도된 파일 목록을 표시해주기 위한 메소드
	@RequestMapping(value = "/board/fileList.do")
	public String fileList(@RequestParam(value="fileId", required=true) String fileId, HttpServletResponse response) throws Exception {

		LOGGER.debug("File ID=" + fileId);
		return "forward:/common/fileList.do"; //파일정보는 FileDownloadController에서 처리하기로 한다.
	}

	//수정한 내용을 저장한다.
	//내용을 수정했을 수도 있고 파일을 추가했을 수도 있다.
	@RequestMapping("/board/boardUpdateFile.do")
	public String boardUpdate(@ModelAttribute("postVO") BoardVO boardVO, BindingResult bindingResult, Model model, HttpServletRequest req, HttpSession session) throws Exception {

		//여러 개의 파일ID들이 전달되므로 IN절을 사용하여 해당 파일 정보를 업데이트하고 마스터 정보에도 전달한다.
		String[] filepondFileIds = req.getParameterValues("filepondFileId");

		if (filepondFileIds.length > 0) {
			AtchFileVO atchFileVO = new AtchFileVO();
			atchFileVO.setFileIds(filepondFileIds);
			//수정의 경우에는 새로운 atchFileId로 모두 업데이트 하기로 한다.
			String atchFileId = fileMngService.updateFileAttchId(atchFileVO);

			if (atchFileId != null) boardVO.setAtchFileId(atchFileId); //마스터 테이블에도 이 정보가 필요하다.
		}

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int result = service.updateBoardContent(boardVO, loginVO);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("UPDATE=" + result);
		}

		return "forward:/board/boardListFile.do";
	}

	//삭제
	@RequestMapping("/board/boardDeleteWithFiles.do")
	public String boardDelete(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		//TODO 디스크의 물리적인 파일도 삭제한다.
		//

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int result = service.deleteBoard(boardVO, loginVO);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DELETE=" + result);
		}

		if (result > 0) {
			return "forward:/board/boardListFile.do";
		} else {
			model.addAttribute("_message", messageSource.getMessage("fail.common.delete", null, null));
			return "forward:/board/boardModFile.do";
		}
	}


}
