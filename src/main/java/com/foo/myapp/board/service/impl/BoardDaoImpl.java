package com.foo.myapp.board.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.foo.myapp.board.service.BoardVO;
import com.foo.myapp.board.service.IBoardDao;
import com.foo.myapp.board.service.SearchBoardVO;
import com.foo.myapp.common.AbstractDao;

/**
 * 게시판
 * @author foo
 *
 */
@Repository(value="BoardDAO")
public class BoardDaoImpl extends AbstractDao implements IBoardDao {

	@Override
	public int saveBoardContent(BoardVO vo) throws Exception {
		return insert("Board.insertBoard", vo);
	}

	@Override
	public List<BoardVO> selectBoardList(SearchBoardVO vo) throws Exception {
		return selectList("Board.selectBoardList", vo);
	}

	@Override
	public int selectBoardListCount(SearchBoardVO vo) throws Exception {
		return select("Board.selectBoardListCount", vo);
	}

	@Override
	public BoardVO selectBoardCntt(SearchBoardVO vo) throws Exception {
		return select("Board.selectBoardCntt", vo);
	}

	@Override
	public int updateBoardContent(BoardVO vo) throws Exception {
		return update("Board.updateBoard", vo);
	}

	@Override
	public int updateHit(SearchBoardVO vo) throws Exception {
		return update("Board.updateHit", vo);
	}

	@Override
	public int deleteBoard(BoardVO vo) throws Exception {
		return delete("Board.deleteBoard", vo);
	}

}
