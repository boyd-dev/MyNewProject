package com.foo.myapp.user.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Kim.S.W.
 *<br/>
 * JPA에서 RDB의 테이블과 유사한 개념으로 RDB에 persistable한 클래스를 entity클래스라고 한다.
 * 자바 클래스로 (객제지향적인)테이블을 정의한다고 생각한다.
 * 파라미터가 없는 기본 생성자만 허용
 */
@Entity
@Table(name="T_MEMBER")
public class User implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -1619107991224248129L;


	/**
	 * 아이디
	 */
	@Id
	@Column(name="MBER_ID", length=20)
	private String mberId;

	/**
	 * 이름
	 */
	@Column(name="MBER_NM", length=50)
	private String mberNm;

	/**
	 * 암호
	 */
	@Column(name="PASSWORD", length=200)
	private String passwd;

	/**
	 * 암호힌트
	 */
	@Column(name="PASSWORD_HINT", length=100)
	private String passwordHint;

	/**
	 * 이메일
	 */
	@Column(name="MBER_EMAIL", length=50)
	private String mberEmail;

	/**
	 * 가입일
	 */
	@Column(name="SUBSCR_DT", length=8)
	private String subscrDt;

	@Column(name="DEL_YN", columnDefinition="CHAR(1)")
	private String delYn = "N"; //default value

	@Column(name="INST_TM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date instTm;

	@Column(name="INST_ID", length=20)
	private String instId;

	@Column(name="UPDT_TM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updtTm;

	@Column(name="UPDT_ID", length=20)
	private String updtId;



	public String getMberId() {
		return mberId;
	}




	public void setMberId(String mberId) {
		this.mberId = mberId;
	}




	public String getMberNm() {
		return mberNm;
	}




	public void setMberNm(String mberNm) {
		this.mberNm = mberNm;
	}




	public String getPasswd() {
		return passwd;
	}




	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}




	public String getPasswordHint() {
		return passwordHint;
	}




	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}




	public String getMberEmail() {
		return mberEmail;
	}




	public void setMberEmail(String mberEmail) {
		this.mberEmail = mberEmail;
	}




	public String getSubscrDt() {
		return subscrDt;
	}




	public void setSubscrDt(String subscrDt) {
		this.subscrDt = subscrDt;
	}




	public String getDelYn() {
		return delYn;
	}




	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}




	public Date getInstTm() {
		return instTm;
	}




	public void setInstTm(Date instTm) {
		this.instTm = instTm;
	}




	public String getInstId() {
		return instId;
	}




	public void setInstId(String instId) {
		this.instId = instId;
	}




	public Date getUpdtTm() {
		return updtTm;
	}




	public void setUpdtTm(Date updtTm) {
		this.updtTm = updtTm;
	}




	public String getUpdtId() {
		return updtId;
	}




	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}




	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
