package com.foo.myapp.board.web;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.foo.myapp.board.service.BoardVO;
import com.foo.myapp.board.service.IBoardService;
import com.foo.myapp.board.service.SearchBoardVO;
import com.foo.myapp.idgnr.service.IdGnrService;
import com.foo.myapp.login.service.LoginVO;
import com.foo.myapp.tag.Paging;
import com.foo.myapp.utils.GlobalProperties;

/**
 * 일반 게시판 처리용 컨트롤러
 * 첨부파일 게시판 컨트롤러와 구분하기로 한다.
 * @author foo
 *
 */
@Controller
public class BoardController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static final int PAGE_RECORD_SIZE = Integer.parseInt(GlobalProperties.getProperty("Board.PageRecordSize"));
	private static final int PAGE_LIST_SIZE = Integer.parseInt(GlobalProperties.getProperty("Board.PageListSize"));


	//@Resource 는 자바 어노테이션이고 @Service 는 스프링 어노테이션이다. 두 개는 동일하다.
	//@Resource 는 빈의 이름 또는 타입으로 찾는다.
    @Resource(name="idGnrService")
    private IdGnrService idGnrService;


    @Autowired
    private IBoardService service;

    /*
    @Resource(name="boardService")
    private IBoardService service;
    */

    @Resource(name="messageSource")
	private MessageSource messageSource;


	@RequestMapping("/board/boardList.do")
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
		model.addAttribute("pagingInfo", pagingInfo);

		//ModelAndView 차이점?
		//return "/board/list";

		ModelAndView mav = new ModelAndView("/board/list");
		return mav;
	}

	//신규등록 화면으로 이동
	//@ModelAttribute는 @MVC에 속한 어노테이션으로 이렇게 표시되면 커맨드 빈을 자동으로 만들어주거나 해당 이름의 빈이 넘어오면 BoardVO에 바인딩해준다.
	//화면의 Spring form 태그와 함께 사용한다.
	@RequestMapping("/board/addPosting.do")
	public String addPosting(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		return "/board/write";
	}


	@RequestMapping("/board/boardCntt.do")
	public String boardCntt(@ModelAttribute("searchVO") SearchBoardVO boardVO, Model model, HttpSession session, HttpServletRequest request) throws Exception {

		LOGGER.debug("Board ID = " + boardVO.getBoardId());
		LOGGER.debug("Article ID = " + boardVO.getCnttId());

		int result = service.updateHit(boardVO); //조회수를 증가시킨다.

		if (result > 0) {
			BoardVO vo = service.selectBoardCntt(boardVO);
			model.addAttribute("result", vo);
		}

		return "/board/read";
	}

	@RequestMapping("/board/boardMod.do")
	public String boardModify(@ModelAttribute("postVO") SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		BoardVO vo = service.selectBoardCntt(boardVO);
		model.addAttribute("result", vo);

		return "/board/modify";
	}

	//신규등록
	@RequestMapping("/board/boardSave.do")
	public String boardSave(@ModelAttribute("postVO") @Valid BoardVO boardVO, BindingResult bindingResult, Model model, HttpSession session) throws Exception {

		LoginVO loginVO = (LoginVO)session.getAttribute("userInfo");
		if (loginVO != null) {
			boardVO.setAuthorId(loginVO.getMberId());
		}

		if (LOGGER.isDebugEnabled()) LOGGER.debug(boardVO.toString());

		if (bindingResult.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				Iterator<ObjectError> it = bindingResult.getAllErrors().iterator();
				while (it.hasNext()) {
					LOGGER.debug(it.next().getDefaultMessage());
				}
			}
		    return "/board/write";
		}

		int result = service.saveBoardContent(boardVO);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("INSERT=" + result);
		}

		return "forward:/board/boardList.do"; //forward: request정보가 그대로 유지되면서 내부적으로 해당 매핑에 전달
	}



	//수정
	@RequestMapping("/board/boardUpdate.do")
	public String boardUpdate(@ModelAttribute("postVO") @Valid BoardVO boardVO, BindingResult bindingResult, Model model, HttpSession session) throws Exception {

		if (bindingResult.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				Iterator<ObjectError> it = bindingResult.getAllErrors().iterator();
				while (it.hasNext()) {
					LOGGER.debug(it.next().getDefaultMessage());
				}
			}
		    return "/board/modify";
		}

		int result = service.updateBoardContent(boardVO, (LoginVO)session.getAttribute("userInfo"));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("UPDATE=" + result);
		}

		if (result > 0) {
			return "forward:/board/boardList.do";
		} else {
			model.addAttribute("_message", messageSource.getMessage("fail.common.update", null, null));
			return "forward:/board/boardMod.do";
		}
	}

	//삭제
	@RequestMapping("/board/boardDelete.do")
	public String boardDelete(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		int result = service.deleteBoard(boardVO, (LoginVO)session.getAttribute("userInfo"));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DELETE=" + result);
		}

		if (result > 0) {
			return "forward:/board/boardList.do";
		} else {
			model.addAttribute("_message", messageSource.getMessage("fail.common.delete", null, null));
			return "forward:/board/boardMod.do";
		}
	}




//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		NumberFormat numberFormat = NumberFormat.getInstance();
//		numberFormat.setGroupingUsed(false);
//	    binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, numberFormat, true));
//	}


}
