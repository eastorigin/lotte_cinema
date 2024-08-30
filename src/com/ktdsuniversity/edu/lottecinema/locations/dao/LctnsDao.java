package com.ktdsuniversity.edu.lottecinema.locations.dao;

import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;
import com.ktdsuniversity.edu.lottecinema.locations.vo.LctnsVO;

/**
 * LCTNS 테이블에 CRUD를 수행
 */
public class LctnsDao {

	public void insertNewLocations(LctnsVO newLocationsData, DataAccessHelper dataAccessHelper) {
		
		String  newPkValueQuery = "SELECT LPAD(LCTNS_PK_SEQ.NEXTVAL, 5, '0') LCTNS_PK FROM DUAL";
		dataAccessHelper.preparedStatement(newPkValueQuery, null);
		dataAccessHelper.executeQuery(SQLType.SELECT, rs -> {
			String newPk = rs.getString("LCTNS_PK");
			newLocationsData.setLctnsId(newPk);
		});
		
		// 1. 쿼리 만들기
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO LCTNS                    ");
		query.append("  (LCTNS_ID                           ");
		query.append(" , LCTNS_NM)                          ");
		query.append(" VALUES                             ");
		query.append("  (? ");
		query.append(" , ?)                               ");
		
		// 2. dataAccessHelper, prepareStatement() 호출
		dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
			pstmt.setString(1, newLocationsData.getLctnsId());
			pstmt.setString(2, newLocationsData.getLctnsNm()); // 서비스는 에러가 발생했는지 안 했는지 모름
		});
		
		// 3. dataAccessHelper.executeQuery() 호출
		dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}
}
