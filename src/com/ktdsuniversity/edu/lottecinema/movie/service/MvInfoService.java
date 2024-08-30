package com.ktdsuniversity.edu.lottecinema.movie.service;

import java.util.List;

import com.ktdsuniversity.edu.lottecinema.agelimit.dao.AgeLmtDao;
import com.ktdsuniversity.edu.lottecinema.agelimit.vo.AgeLmtVO;
import com.ktdsuniversity.edu.lottecinema.genre.dao.GnrDao;
import com.ktdsuniversity.edu.lottecinema.genre.vo.GnrVO;
import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.locations.dao.LctnsDao;
import com.ktdsuniversity.edu.lottecinema.locations.vo.LctnsVO;
import com.ktdsuniversity.edu.lottecinema.movie.dao.MvInfoDao;
import com.ktdsuniversity.edu.lottecinema.movie.vo.MvInfoVO;
import com.ktdsuniversity.edu.lottecinema.moviegenre.dao.MvGnrDao;
import com.ktdsuniversity.edu.lottecinema.movielocations.dao.MvLctnsDao;

/**
 * 영화를 CRUD하는 전체 로직
 * 영화를 등록한다
 * 	경우에 따라 장르를 새롭게 등록한다.
 * 			국가를 새롭게 등록한다.
 * 			관람연령제한을 새롭게 등록한다.
 * 			영화를 등록한다.
 * 트랜잭션 관리
 */
public class MvInfoService {
	
	public void readAllMovies() {
		
		DataAccessHelper dataAccessHelper = null;
		
		try {
			dataAccessHelper = new DataAccessHelper();
			
			MvInfoDao mvInfoDao = new MvInfoDao();
			List<MvInfoVO> movieList = mvInfoDao.selectMovies(dataAccessHelper);
			
			movieList.forEach(mv -> {
				System.out.println("영화아이디: " + mv.getMvId());
				System.out.println("영화명: " + mv.getMvTtl());
				
				// 장르
				List<GnrVO> gnrList = mv.getGnrList();
				gnrList.forEach(gnr -> {
					System.out.println("장르: " + gnr.getGnrNm());
				});
				
				// 국가(촬영지)
				List<LctnsVO> lctnsList = mv.getLctnsList();
				lctnsList.forEach(lctns -> {
					System.out.println("촬영지: " + lctns.getLctnsNm());
				});
				
				// 관람등급
				AgeLmtVO ageLmt = mv.getAgeLmtVO();
				System.out.println("관람등급: " + ageLmt.getAgeLmtNm());
				
			});
			
		}catch(RuntimeException re) {
			
		}finally {
			if(dataAccessHelper != null) {
				dataAccessHelper.close();
			}
		}
	}

	public void createNewMovie(MvInfoVO mvInfoVO, GnrVO gnrVO, LctnsVO lctnsVO, AgeLmtVO ageLmtVO) {
		
		DataAccessHelper dataAccessHelper = null;
		
		try {
			// CRUD, 트랜잭션 담당
			dataAccessHelper = new DataAccessHelper();
			
			// 1. 장르 등록
			GnrDao gnrDao = new GnrDao();
			gnrDao.insertNewGenre(gnrVO, dataAccessHelper);
			
			// 2. 국가 등록
			LctnsDao lctnsDao = new LctnsDao();
			lctnsDao.insertNewLocations(lctnsVO, dataAccessHelper);
			
			// 3. 연령제한정보 등록
			AgeLmtDao ageLmtDao = new AgeLmtDao();
			ageLmtDao.insertNewAgeLimit(ageLmtVO, dataAccessHelper);
			
			mvInfoVO.setAgeLmtId(ageLmtVO.getAgeLmtId());
			
			// 4. 영화 등록
			MvInfoDao mvInfoDao = new MvInfoDao();
			mvInfoDao.insertNewMovie(mvInfoVO, dataAccessHelper);
			
			// 5. 영화의 장르 등록
			MvGnrDao mvGnrDao = new MvGnrDao();
			mvGnrDao.insertNewMovieGenre(gnrVO.getGnrId(), mvInfoVO.getMvId(), dataAccessHelper); // 새롭게 추가된 장르 아이디가 뭔지 모르기 때문에
			
			// 6. 영화의 촬영지 등록
			MvLctnsDao mvLctnsDao = new MvLctnsDao();
			mvLctnsDao.insertNewMovieLocations(lctnsVO.getLctnsId(), mvInfoVO.getMvId(), dataAccessHelper);
			
			// 정상적으로 모든 정보를 INSERT 했다면 커밋한다.
			dataAccessHelper.commit(); // 파이프가 꽂혀있다
		}catch(RuntimeException re) { // 앞에 전부 다 롤백해야해서
			re.printStackTrace(); // 에러가 난 경로 정확히 파악
			System.out.println(re.getMessage());
			if(dataAccessHelper != null) {
				dataAccessHelper.rollback();
			}
		}finally {
			if(dataAccessHelper != null) {
				dataAccessHelper.close();
			}
		}
	}
}
