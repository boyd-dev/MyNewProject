package com.foo.myapp.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.foo.myapp.board.service.BoardVO;
import com.foo.myapp.board.service.IBoardDao;
import com.foo.myapp.board.service.IBoardService;
import com.foo.myapp.board.service.SearchBoardVO;
import com.foo.myapp.common.AbstractService;
import com.foo.myapp.common.exception.BizException;
import com.foo.myapp.idgnr.service.IdGnrService;
import com.foo.myapp.login.service.LoginVO;

/**
 * 게시판
 *
 * @author foo
 *
 */
@Service(value="boradService")
public class BoardServiceImpl extends AbstractService implements IBoardService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource(name="idGnrService")
	private IdGnrService idGnrService;

	@Resource(name="BoardDAO")
	private IBoardDao boardDAO;

	@Override
	public int saveBoardContent(BoardVO vo) throws Exception {

		int result = 0;

		try {

			//ID생성
			Long id = (Long)idGnrService.getNextId();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ID=" + String.valueOf(id.longValue()));
			}
			vo.setCnttId(id);
			result = boardDAO.saveBoardContent(vo);

			//throw processException("fail.common.msg", null, Locale.KOREA);
		} catch (BizException be) {
			System.out.println(be.getMessage());
		}

		return result;
	}

	@Override
	public Map<String, Object> selectBoardList(SearchBoardVO searchVO) throws Exception {

		List<BoardVO> result = boardDAO.selectBoardList(searchVO);

		int cnt = boardDAO.selectBoardListCount(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return (Map<String, Object>) map;
	}

	@Override
	public BoardVO selectBoardCntt(SearchBoardVO searchVO) throws Exception {
		return boardDAO.selectBoardCntt(searchVO);
	}

	@Override
	public int updateBoardContent(BoardVO vo, LoginVO userInfo) throws Exception {

		int result = 0;

		try {

			//수정 권한 검사
			if (userInfo.getMberId().equals(vo.getAuthorId())) {
				result = boardDAO.updateBoardContent(vo);
			} else {
				throw processException("fail.common.update", null, null);
			}


		} catch (BizException be) {
			System.out.println(be.getMessage());
		}

		return result;
	}

	@Override
	public int updateHit(SearchBoardVO vo) throws Exception {

		int result = 0;

		try {
			result = boardDAO.updateHit(vo);

		} catch (BizException be) {
			System.out.println(be.getMessage());
		}

		return result;
	}

	@Override
	public int deleteBoard(BoardVO vo, LoginVO userInfo) throws Exception {

		int result = 0;

		try {

			//삭제 권한 검사
			if (userInfo.getMberId().equals(vo.getAuthorId())) {
				result = boardDAO.deleteBoard(vo);
			} else {
				throw processException("fail.common.delete", null, null);
			}

		} catch (BizException be) {
			System.out.println(be.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public int selectTotalCountById(SearchBoardVO searchVO) throws Exception {
		int cnt = boardDAO.selectBoardListCount(searchVO);
		return cnt;
	}

}
