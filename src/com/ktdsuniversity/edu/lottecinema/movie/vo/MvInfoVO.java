package com.ktdsuniversity.edu.lottecinema.movie.vo;

import java.util.List;

import com.ktdsuniversity.edu.lottecinema.agelimit.vo.AgeLmtVO;
import com.ktdsuniversity.edu.lottecinema.genre.vo.GnrVO;
import com.ktdsuniversity.edu.lottecinema.locations.vo.LctnsVO;

/**
 * MV_INFO 테이블과 매칭되는 객체
 * VO: Value Object ==> 변하지 않는 값을 관리하는 객체
 * 
 * MV_INFO 테이블에 존재하는 모든 컬럼들을 멤버변수로 정의
 */
public class MvInfoVO {
	
	// Ctrl + Shift + y => 알파벳 대문자를 소문자로 변경
	private int mvId;
	private String ageLmtId;
	private String mvTtl;
	private String opn;
	private int scrngTm;
	private String pstr;
	private String plt;
	
	// 하나의 영화에는 여러 개의 장르가 있다.
	// MvInfoVO has a List<GnrVO>
	private List<GnrVO> gnrList;
	
	// 하나의 영화에는 어려 개의 국가(촬영지)가 있다.
	// MvInfoVO has a List<LctnsVO>
	private List<LctnsVO> lctnsList;
	
	// 하나의 영화에는 하나의 관람제한등급이 있다.
	// MvInfoVO has a AgeLmtVO
	private AgeLmtVO ageLmtVO;
	
	// Alt + Shift + S Alt + A
	
	public List<GnrVO> getGnrList() {
		return gnrList;
	}
	public void setGnrList(List<GnrVO> gnrList) {
		this.gnrList = gnrList;
	}
	public List<LctnsVO> getLctnsList() {
		return lctnsList;
	}
	public void setLctnsList(List<LctnsVO> lctnsList) {
		this.lctnsList = lctnsList;
	}
	public AgeLmtVO getAgeLmtVO() {
		return ageLmtVO;
	}
	public void setAgeLmtVO(AgeLmtVO ageLmtVO) {
		this.ageLmtVO = ageLmtVO;
	}
	public int getMvId() {
		return mvId;
	}
	public void setMvId(int mvId) {
		this.mvId = mvId;
	}
	public String getAgeLmtId() {
		return ageLmtId;
	}
	public void setAgeLmtId(String ageLmtId) {
		this.ageLmtId = ageLmtId;
	}
	public String getMvTtl() {
		return mvTtl;
	}
	public void setMvTtl(String mvTtl) {
		this.mvTtl = mvTtl;
	}
	public String getOpn() {
		return opn;
	}
	public void setOpn(String opn) {
		this.opn = opn;
	}
	public int getScrngTm() {
		return scrngTm;
	}
	public void setScrngTm(int scrngTm) {
		this.scrngTm = scrngTm;
	}
	public String getPstr() {
		return pstr;
	}
	public void setPstr(String pstr) {
		this.pstr = pstr;
	}
	public String getPlt() {
		return plt;
	}
	public void setPlt(String plt) {
		this.plt = plt;
	}

}
