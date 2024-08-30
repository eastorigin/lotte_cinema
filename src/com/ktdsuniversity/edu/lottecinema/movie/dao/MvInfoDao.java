package com.ktdsuniversity.edu.lottecinema.movie.dao;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniversity.edu.lottecinema.agelimit.vo.AgeLmtVO;
import com.ktdsuniversity.edu.lottecinema.genre.vo.GnrVO;
import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;
import com.ktdsuniversity.edu.lottecinema.locations.vo.LctnsVO;
import com.ktdsuniversity.edu.lottecinema.movie.vo.MvInfoVO;

/**
 * MV_INFO 테이블에 CRUD 작업 수행
 */
public class MvInfoDao {
	
	public List<MvInfoVO> selectMovies(DataAccessHelper dataAccessHelper) {
		List<MvInfoVO> movieList = new ArrayList<>();
		
		StringBuffer query = new StringBuffer();
		// 1. 셀렉트쿼리 준비
		query.append(" 	SELECT mi.MV_ID                               ");
		query.append(" 	     , mi.MV_TTL                              ");
		query.append(" 	 	 , TO_CHAR(mi.OPN, 'YYYY-MM-DD') OPN_DT   ");
		query.append(" 	 	 , mi.SCRNG_TM                            ");
		query.append(" 	 	 , mi.AGE_LMT_ID                          ");
		query.append(" 	 	 , al.AGE_LMT_NM                          ");
		query.append(" 	 	 , mi.PSTR                                ");
		query.append(" 	 	 , mi.PLT                                 ");
		query.append(" 	 	 , mg.GNR_ID                              ");
		query.append(" 	 	 , g.GNR_NM                               ");
		query.append(" 	 	 , ml.LCTNS_ID                            ");
		query.append(" 	 	 , l.LCTNS_NM                             ");
		query.append(" 	  FROM MV_INFO mi                             ");
		query.append("   INNER JOIN MV_GNR mg                         ");
		query.append("    	ON mi.MV_ID = mg.MV_ID                    ");
		query.append(" 	 INNER JOIN GNR g                             ");
		query.append("    	ON mg.GNR_ID = g.GNR_ID                   ");
		query.append(" 	 INNER JOIN AGE_LMT al                        ");
		query.append("    	ON mi.AGE_LMT_ID = al.AGE_LMT_ID          ");
		query.append(" 	 INNER JOIN MV_LCTNS ml                       ");
		query.append("    	ON mi.MV_ID = ml.MV_LCTNS_ID              ");
		query.append(" 	 INNER JOIN LCTNS l                           ");
		query.append(" 		ON ml.LCTNS_ID = l.LCTNS_ID               ");
		
		dataAccessHelper.preparedStatement(query.toString(), null);
		
		// 2. 셀렉트 쿼리를 실행해서 결과를 movieList에 add하는 코드.
		dataAccessHelper.executeQuery(SQLType.SELECT, rs -> {
			int mvId = rs.getInt("MV_ID");
			String mvTtl = rs.getString("MV_TTL");
			String opnDt = rs.getString("OPN_DT");
			int scrngTm = rs.getInt("SCRNG_TM");
			String ageLmtId = rs.getString("AGE_LMT_ID");
			String ageLmtNm = rs.getString("AGE_LMT_NM");
			String pstr = rs.getString("PSTR");
			String plt = rs.getString("PLT");
			String gnrId = rs.getString("GNR_ID");
			String gnrNm = rs.getString("GNR_NM");
			String lctnsId = rs.getString("LCTNS_ID");
			String lctnsNm = rs.getString("LCTNS_NM");
			
			MvInfoVO mvInfoVO = new MvInfoVO();
			movieList.add(mvInfoVO);
			
			mvInfoVO.setMvId(mvId);
			mvInfoVO.setMvTtl(mvTtl);
			mvInfoVO.setOpn(opnDt);
			mvInfoVO.setScrngTm(scrngTm);
			mvInfoVO.setAgeLmtId(ageLmtId);
			mvInfoVO.setPstr(pstr);
			mvInfoVO.setPlt(plt);
			
			List<GnrVO> gnrList = new ArrayList<>();
			mvInfoVO.setGnrList(gnrList);
			
			GnrVO gnrVO = new GnrVO();
			gnrVO.setGnrId(gnrId);
			gnrVO.setGnrNm(gnrNm);
			
			gnrList.add(gnrVO);
			
			List<LctnsVO> lctnsList = new ArrayList<>();
			mvInfoVO.setLctnsList(lctnsList);
			
			LctnsVO lctnsVO = new LctnsVO();
			lctnsVO.setLctnsId(lctnsId);
			lctnsVO.setLctnsNm(lctnsNm);
			
			lctnsList.add(lctnsVO);
			
			AgeLmtVO ageLmtVO = new AgeLmtVO();
			mvInfoVO.setAgeLmtVO(ageLmtVO);
			
			ageLmtVO.setAgeLmtId(ageLmtId);
			ageLmtVO.setAgeLmtNm(ageLmtNm);
			
		});
		
		return movieList;
	}

	
	public void insertNewMovie(MvInfoVO newMovieData, DataAccessHelper dataAccessHelper) {
		String newPkValueQuery = "SELECT MV_INFO_PK_SEQ.NEXTVAL MV_INFO_PK FROM DUAL";
		dataAccessHelper.preparedStatement(newPkValueQuery, null);
		dataAccessHelper.executeQuery(SQLType.SELECT, rs -> {
			int newPk = rs.getInt("MV_INFO_PK");
			newMovieData.setMvId(newPk);
		});
		
		// 1. 쿼리 만들기
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO MV_INFO                        ");
		query.append(" (MV_ID                                     ");
		query.append(" , AGE_LMT_ID                               ");
		query.append(" , MV_TTL                                   ");
		query.append(" , OPN                                      ");
		query.append(" , SCRNG_TM                                 ");
		query.append(" , PSTR                                     ");
		query.append(" , PLT)                                     ");
		query.append(" VALUES                                     ");
		query.append("  (?                                        ");
		query.append(" , ?                                        ");
		query.append(" , ?                                        ");
		query.append(" , TO_DATE(?, 'YYYY-MM-DD')                 ");
		query.append(" , ?                                        ");
		query.append(" , ?                                        ");
		query.append(" , ?)                                       ");
		
		// 2. dataAccessHelper, prepareStatement() 호출
		dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
			pstmt.setInt(1, newMovieData.getMvId());
			pstmt.setString(2, newMovieData.getAgeLmtId()); // 서비스는 에러가 발생했는지 안 했는지 모름
			pstmt.setString(3, newMovieData.getMvTtl());
			pstmt.setString(4, newMovieData.getOpn());
			pstmt.setInt(5, newMovieData.getScrngTm());
			pstmt.setString(6, newMovieData.getPstr());
			pstmt.setString(7, newMovieData.getPlt());
		});
		
		// 3. dataAccessHelper.executeQuery() 호출
		dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}
}
