package com.foo.myapp.board.service;

import java.util.Map;

import com.foo.myapp.login.service.LoginVO;

public interface IBoardService {

	/**
	 * 게시글 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int saveBoardContent(BoardVO vo) throws Exception;

	/**
	 * 게시글 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public <T> Map<String, T> selectBoardList(SearchBoardVO searchVO) throws Exception;

	/**
	 * 게시글 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public BoardVO selectBoardCntt(SearchBoardVO searchVO) throws Exception;

	/**
	 * 게시글 수정
	 * @param vo
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int updateBoardContent(BoardVO vo, LoginVO userInfo) throws Exception;

	/**
	 * 조회수 증가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateHit(SearchBoardVO vo) throws Exception;

	/**
	 * 게시글 삭제
	 * @param vo
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteBoard(BoardVO vo, LoginVO userInfo) throws Exception;


	/**
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectTotalCountById(SearchBoardVO vo) throws Exception;


}
