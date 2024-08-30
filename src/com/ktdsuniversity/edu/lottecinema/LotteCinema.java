package com.ktdsuniversity.edu.lottecinema;

import com.ktdsuniversity.edu.lottecinema.agelimit.vo.AgeLmtVO;
import com.ktdsuniversity.edu.lottecinema.genre.vo.GnrVO;
import com.ktdsuniversity.edu.lottecinema.locations.vo.LctnsVO;
import com.ktdsuniversity.edu.lottecinema.movie.service.MvInfoService;
import com.ktdsuniversity.edu.lottecinema.movie.vo.MvInfoVO;

public class LotteCinema {
	
	public static void selectAllMovies() {
		MvInfoService mvInfoService = new MvInfoService();
		mvInfoService.readAllMovies();
	}

	public static void insertNewMovie() {
		MvInfoService mvInfoService = new MvInfoService();
		
		MvInfoVO mvInfoVO = new MvInfoVO();
		mvInfoVO.setMvTtl("새로운 영화");
		mvInfoVO.setOpn("2024-08-30");
		mvInfoVO.setScrngTm(120);
		mvInfoVO.setPstr("https://posterurl.com/pic.jpg");
		mvInfoVO.setPlt("완전히 새로운 영화!!");
		
		GnrVO gnrVO = new GnrVO();
		gnrVO.setGnrNm("새로운 장르");
		
		LctnsVO lctnsVO = new LctnsVO();
		lctnsVO.setLctnsNm("새로운 국가");
		
		AgeLmtVO ageLmtVO = new AgeLmtVO();
		ageLmtVO.setAgeLmtNm("5세 이상 관람가");
		
		mvInfoService.createNewMovie(mvInfoVO, gnrVO, lctnsVO, ageLmtVO);
	}
	
	public static void main(String[] args) {
		// insertNewMovie();
		selectAllMovies();
	}
}
 
