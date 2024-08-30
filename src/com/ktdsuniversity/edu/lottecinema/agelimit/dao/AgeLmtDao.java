package com.ktdsuniversity.edu.lottecinema.agelimit.dao;

import com.ktdsuniversity.edu.lottecinema.agelimit.vo.AgeLmtVO;
import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;

/**
 * AGE_LMT 테이블에 CRUD를 수행
 */
public class AgeLmtDao {
	
	public void insertNewAgeLimit(AgeLmtVO newAgeLimitData, DataAccessHelper dataAccessHelper) {
		
		String newPkValueQuery = "SELECT LPAD(AGE_LMT_PK_SEQ.NEXTVAL, 5, '0') AGE_LMT_PK FROM DUAL";
		dataAccessHelper.preparedStatement(newPkValueQuery, null);
		dataAccessHelper.executeQuery(SQLType.SELECT, rs -> {
			String newPk = rs.getString("AGE_LMT_PK");
			newAgeLimitData.setAgeLmtId(newPk);
		});
		// 1. 쿼리 만들기
				StringBuffer query = new StringBuffer();
				query.append(" INSERT INTO AGE_LMT                    ");
				query.append("  (AGE_LMT_ID                           ");
				query.append(" , AGE_LMT_NM)                          ");
				query.append(" VALUES                             ");
				query.append("  (? ");
				query.append(" , ?)                               ");
				
				// 2. dataAccessHelper, prepareStatement() 호출
				dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
					pstmt.setString(1, newAgeLimitData.getAgeLmtId());
					pstmt.setString(2, newAgeLimitData.getAgeLmtNm()); // 서비스는 에러가 발생했는지 안 했는지 모름
				});
				
				// 3. dataAccessHelper.executeQuery() 호출
				dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}

}
