package com.foo.myapp.board.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foo.myapp.board.service.BoardVO;
import com.foo.myapp.board.service.IBoardService;
import com.foo.myapp.board.service.SearchBoardVO;
import com.foo.myapp.login.service.LoginVO;

@Controller
public class BoardGridController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IBoardService service;

    @Resource(name="messageSource")
	private MessageSource messageSource;


    @RequestMapping(value="/board/boardListGrid.do")
	public String boardList(Model model, HttpSession session) throws Exception {
    	return "/board/listGrid";
	}


    //json으로 데이터를 주고 받으려면 @RequestBody, @ResponseBody를 사용한다.
    //이것을 위해서는 <annotation-drvien> 태그 내에 선언하는 <message-converter>를 설정한다.
    //@RequestBody는 전달받은 json 객체의 key-value를 빈에 자동으로 바인딩한다.
	@RequestMapping(value="/board/boardGrid.do", method=RequestMethod.POST)
	@ResponseBody
	public List<?> boardList(@RequestBody SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		LOGGER.debug("Session ID = " + session.getId());
		LOGGER.debug("Board ID = " + boardVO.getBoardId());
		LOGGER.debug("Page No = " + boardVO.getPageNo());
		LOGGER.debug("Search Type = " + boardVO.getSearchType());

		boardVO.setFirstIndex(0);
		Map<String, Object> map = service.selectBoardList(boardVO);

		return (List<?>) map.get("resultList");
	}

	@RequestMapping("/board/addPostingGrid.do")
	public String addPosting(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		return "/board/writeGrid";
	}


	@RequestMapping("/board/boardSaveGrid.do")
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
		    return "/board/writeGrid";
		}

		int result = service.saveBoardContent(boardVO);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("INSERT=" + result);
		}

		return "forward:/board/boardListGrid.do";
	}

	@RequestMapping("/board/boardCnttGrid.do")
	public String boardCntt(@ModelAttribute("searchVO") SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		LOGGER.debug("Article ID = " + boardVO.getCnttId());

		int result = service.updateHit(boardVO); //조회수를 증가시킨다.

		if (result > 0) {
			BoardVO vo = service.selectBoardCntt(boardVO);
			model.addAttribute("result", vo);
		}
		return "/board/readGrid";
	}

	@RequestMapping("/board/boardModGrid.do")
	public String boardModify(@ModelAttribute("postVO") SearchBoardVO boardVO, Model model, HttpSession session) throws Exception {

		BoardVO vo = service.selectBoardCntt(boardVO);
		model.addAttribute("result", vo);

		return "/board/modifyGrid";
	}


	//수정
	@RequestMapping("/board/boardUpdateGrid.do")
	public String boardUpdate(@ModelAttribute("postVO") @Valid BoardVO boardVO, BindingResult bindingResult, Model model, HttpSession session) throws Exception {

		if (bindingResult.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				Iterator<ObjectError> it = bindingResult.getAllErrors().iterator();
				while (it.hasNext()) {
					LOGGER.debug(it.next().getDefaultMessage());
				}
			}
		    return "forward:/board/readGrid";
		}

		int result = service.updateBoardContent(boardVO, (LoginVO)session.getAttribute("userInfo"));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("UPDATE=" + result);
		}
		return "forward:/board/boardListGrid.do";
	}

	//삭제
	@RequestMapping("/board/boardDeleteGrid.do")
	public String boardDelete(@ModelAttribute("postVO") BoardVO boardVO, Model model, HttpSession session) throws Exception {

		int result = service.deleteBoard(boardVO, (LoginVO)session.getAttribute("userInfo"));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DELETE=" + result);
		}

		if (result > 0) {
			return "forward:/board/boardListGrid.do";
		} else {
			model.addAttribute("_message", messageSource.getMessage("fail.common.delete", null, null));
			return "forward:/board/boardModGrid.do";
		}
	}

}
