package com.foo.myapp.board.service;

import java.io.Serializable;

/**
 * 게시판 목록에서 조회할 때 사용
 * 특정 게시글 하나 조회할 때 사용
 * @author foo
 *
 */
public class SearchBoardVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8487255874761574231L;

	/**
	 * 조회 유형
	 */
	private String searchType = "";

	/**
	 * 조회 조건
	 */
	private String searchKey = "";


	/**
	 * 게시판 ID
	 */
	private String boardId = "";

	/**
	 * 게시물 아이디
	 */
	private long cnttId = 0L;

	/**
	 * 작성자
	 */
	private String authorId = "";

	/**
	 * 게시물 제목
	 */
	private String cnttTitle = "";


	/**
	 * 게시물 내용
	 */
	private String cnttPost = "";


	/**
	 * 현재 페이지 번호
	 */
	private int pageNo = 1;

	/**
	 * firstIndex
	 *
	 */
    private int firstIndex = 1;

    /**
     * lastIndex
     */
    private int lastIndex = 1;

    /**
     * recordCountPerPage
     * 페이징이 없는 경우에도 처음 10건만 가져온다.
     */
    private int recordCountPerPage = 10;


    /**
     * 첨부파일ID
     */
    private String atchFileId;


    public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getCnttTitle() {
		return cnttTitle;
	}

	public void setCnttTitle(String cnttTitle) {
		this.cnttTitle = cnttTitle;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public long getCnttId() {
		return cnttId;
	}

	public void setCnttId(long cnttId) {
		this.cnttId = cnttId;
	}

	public String getCnttPost() {
		return cnttPost;
	}

	public void setCnttPost(String cnttPost) {
		this.cnttPost = cnttPost;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}



}
