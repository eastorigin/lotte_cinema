package com.ktdsuniversity.edu.lottecinema.genre.dao;

import com.ktdsuniversity.edu.lottecinema.genre.vo.GnrVO;
import com.ktdsuniversity.edu.lottecinema.helper.DataAccessHelper;
import com.ktdsuniversity.edu.lottecinema.helper.SQLType;

/**
 * GNR 테이블에 CRUD를 수행
 */
public class GnrDao {

	public void insertNewGenre(GnrVO newGenreData , DataAccessHelper dataAccessHelper) {
		
		String newPkValueQuery = "SELECT LPAD(GNR_PK_SEQ.NEXTVAL, 5, '0') GNR_PK FROM DUAL";
		dataAccessHelper.preparedStatement(newPkValueQuery, null);
		dataAccessHelper.executeQuery(SQLType.SELECT, rs -> {
			String newPk = rs.getString("GNR_PK");
			newGenreData.setGnrId(newPk);
		});
		
		// 1. 쿼리 만들기
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO GNR                    ");
		query.append("  (GNR_ID                           ");
		query.append(" , GNR_NM)                          ");
		query.append(" VALUES                             ");
		query.append("  (? ");
		query.append(" , ?)                               ");
		
		// 2. dataAccessHelper, prepareStatement() 호출
		dataAccessHelper.preparedStatement(query.toString(), pstmt -> { // preparedStatement가 bind 호출하니까 예외처리 해야함.
			pstmt.setString(1, newGenreData.getGnrId());
			pstmt.setString(2, newGenreData.getGnrNm()); // 서비스는 에러가 발생했는지 안 했는지 모름
		});
		
		// 3. dataAccessHelper.executeQuery() 호출
		dataAccessHelper.executeQuery(SQLType.INSERT, null);
	}
}
// Service 안에 DataAccessHelper 안에 DAO 안에 BindingParameter.
// 예외를 BindingParameter 에서 DataAccessHelper 그리고 DAH에서 Service로 던짐
