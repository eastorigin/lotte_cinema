package com.ktdsuniversity.edu.lottecinema.moviegenre.dao;

import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;

/**
 * MV_GNR 테이블에 CRUD를 수행
 */
public class MvGnrDao {
	
	public void insertNewMovieGenre(String genreId, int movieId, DataAccessHelper dataAccessHelper) {
		// 1. 쿼리 만들기
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO MV_GNR                    ");
		query.append("  (MV_GNR_ID                           ");
		query.append(" , GNR_ID                          ");
		query.append(" , MV_ID)                          ");
		query.append(" VALUES                             ");
		query.append("  (LPAD(MV_GNR_PK_SEQ.NEXTVAL, 5, '0') ");
		query.append(" , ?                               ");
		query.append(" , ?)                               ");
		
		// 2. dataAccessHelper, prepareStatement() 호출
		dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
			pstmt.setString(1, genreId);
			pstmt.setInt(2, movieId);
		});
		
		// 3. dataAccessHelper.executeQuery() 호출
		dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}

}
