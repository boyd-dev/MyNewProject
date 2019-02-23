package com.foo.myapp.board.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

//The serialization interface has no methods or fields and serves
//only to identify the semantics of being serializable.
public class BoardVO implements Serializable {

	/**
	 * 게시판 ID
	 */
	private String boardId = "";

	/**
	 * 게시물 아이디
	 */
	private long cnttId = 0L;

	/**
	 * 게시물 내용
	 */
	@NotEmpty(message="Content is empty")
	private String cnttPost = "";

	/**
	 * 게시자 아이디
	 */
	private String authorId = "";

	/**
	 * 게시자명
	 */
	private String authorNm = "";

	/**
	 * 게시물 제목
	 */
	@NotEmpty(message="Title is empty")
	private String cnttTitle = "";

	/**
	 * 조회수
	 */
	private int cnttHit = 0;

	/**
	 * 패스워드
	 */
	private String cnttPasswd = "";

	/**
	 * 정렬순서
	 */
	private long sortOrdr;

	/**
	 * 수정일시
	 */
	private String updtTm;

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

	public String getUpdtTm() {
		return updtTm;
	}

	public void setUpdtTm(String updtTm) {
		this.updtTm = updtTm;
	}

	public long getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(long sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
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

	public String getAuthorNm() {
		return authorNm;
	}

	public void setAuthorNm(String authorNm) {
		this.authorNm = authorNm;
	}

	public String getCnttTitle() {
		return cnttTitle;
	}

	public void setCnttTitle(String cnttTitle) {
		this.cnttTitle = cnttTitle;
	}

	public int getCnttHit() {
		return cnttHit;
	}

	public void setCnttHit(int cnttHit) {
		this.cnttHit = cnttHit;
	}

	public String getCnttPasswd() {
		return cnttPasswd;
	}

	public void setCnttPasswd(String cnttPasswd) {
		this.cnttPasswd = cnttPasswd;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
