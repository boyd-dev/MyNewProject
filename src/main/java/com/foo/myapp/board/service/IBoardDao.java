package com.foo.myapp.board.service;

import java.util.List;

/**
 *
 * @author foo
 *
 */
public interface IBoardDao {

	public int saveBoardContent(BoardVO vo) throws Exception;

	public List<BoardVO> selectBoardList(SearchBoardVO vo) throws Exception;

	public int selectBoardListCount(SearchBoardVO vo) throws Exception;

	public BoardVO selectBoardCntt(SearchBoardVO vo) throws Exception;

	public int updateBoardContent(BoardVO vo) throws Exception;

	public int updateHit(SearchBoardVO vo) throws Exception;

	public int deleteBoard(BoardVO vo) throws Exception;

}
