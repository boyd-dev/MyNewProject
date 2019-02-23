package com.foo.myapp.board.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foo.myapp.board.service.IBoardService;
import com.foo.myapp.board.service.SearchBoardVO;

/**
 * Spring4부터는 @RestController을 사용하여  RESTful 메소드도 가능하다
 * Jackson 메시지 컨버터 등록 필요
 *
 * @author James
 *
 */
@RestController
public class BoardRestController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	 @Autowired
	 private IBoardService service;


	@RequestMapping(value="/board/{boardId}")
	@ResponseBody
	public String boardList(@PathVariable String boardId) throws Exception {

		LOGGER.debug("Board ID = " + boardId);

		SearchBoardVO vo = new SearchBoardVO();
		vo.setBoardId(boardId);
		int cnt = service.selectTotalCountById(vo);
		return String.valueOf(cnt);

	}

}
