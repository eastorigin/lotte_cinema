package com.ktdsuniversity.edu.lottecinema.movielocations.dao;

import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;

/**
 * MV_LCTNS 테이블에 CRUD를 수행
 */
public class MvLctnsDao {
	
	public void insertNewMovieLocations(String locationId, int movieId, DataAccessHelper dataAccessHelper) {
		// 1. 쿼리 만들기
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO MV_LCTNS                    ");
		query.append("  (MV_LCTNS_ID                           ");
		query.append(" , LCTNS_ID                              ");
		query.append(" , MV_ID)                                ");
		query.append(" VALUES                                  ");
		query.append("  (LPAD(MV_LCTNS_PK_SEQ.NEXTVAL, 5, '0') ");
		query.append(" , ?                                     ");
		query.append(" , ?)                                    ");
		
		// 2. dataAccessHelper, prepareStatement() 호출
		dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
			pstmt.setString(1, locationId);
			pstmt.setInt(2, movieId);
		});
		
		// 3. dataAccessHelper.executeQuery() 호출
		dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}
}

