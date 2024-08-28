package com.ktdsuniversity.edu.lottecinema.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectMovie {

	public static void selectAndPrintMovies() {
		
		// 1. Oracle Driver Loading
		//	  Oracle Database에 연결하기 위한 준비작업
		//    ojdbc11.jar > oracle.jdbc.driver.OracleDriver.class
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");			
		}
		catch(ClassNotFoundException e) {
			System.out.println("오라클 드라이버를 찾을 수 없습니다.");
			System.out.println("드라이버의 이름 또는 ojdbc11.jar 파일이 연결되었는지 확인해주세요.");
			return;
		}
		
		// 2. Oracle Database에 연결 : Session 생성
		final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		final String SCHEMA_NAME = "LOTTE_CINEMA";
		final String SCHEMA_PASSWORD = "LOTTE_CINEMA";
		// Connection : Database에 연결한 Session
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(JDBC_URL, SCHEMA_NAME, SCHEMA_PASSWORD);			
			
			// Transaction Manual로 변경
			// Manual Transaction Mode
			connection.setAutoCommit(false);
		}
		catch(SQLException sqle) {
			// Database에 연결하다가 예외가 발생한 상황
			// 연결에 실패하면 쿼리를 실행할 수가 없다
			// 에러가 나면 다시 시도하지 말기: 오라클 계정 정책상 3번 틀리면 계정 잠김
			System.out.println("데이터베이스에 연결할 수 없습니다.");
			System.out.println("URL, SCHEMA, PASSWORD가 올바른지 확인하세요.");
			return;
		}
		
		// 3. 쿼리 준비 및 실행
		
		connection.commit();
		
		connection.rollback();
		
//		StringBuffer query = new StringBuffer();
//		query.append(" SELECT mi.MV_ID                               ");
//		query.append("  	 , mi.MV_TTL                             ");
//		query.append("  	 , TO_CHAR(MI.OPN, 'YYYY-MM-DD') OPN_DT  "); 
//		query.append("  	 , mi.SCRNG_TM                           ");
//		query.append("  	 , al.AGE_LMT_NM                         ");
//		query.append("  	 , mi.PLT                                ");
//		query.append("  	 , g.GNR_NM                              ");
//		query.append("   FROM MV_INFO mi                             ");
//		query.append("  INNER JOIN MV_GNR mg                         ");
//		query.append("  	ON mi.MV_ID = mg.MV_ID                   ");
//		query.append("  INNER JOIN GNR g                             ");
//		query.append("  	ON mg.GNR_ID = g.GNR_ID                  ");
//		query.append("  INNER JOIN AGE_LMT al                        ");
//		query.append("  	ON mi.AGE_LMT_ID = al.AGE_LMT_ID         ");
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = connection.prepareStatement(query.toString());			
//			// SELECT의 결과를 받아옴
//			rs = pstmt.executeQuery();
//		}
//		catch(SQLException sqle) {
//			// 쿼리가 잘못된 경우에 실행
//			System.out.println("쿼리를 실행할 수 없습니다.");
//			System.out.println(sqle.getMessage());
//			
//			if(pstmt != null) {
//				try {
//					pstmt.close();					
//				}
//				catch(SQLException sqle2) {}
//			}
//			
//			try {
//				connection.close();				
//			}
//			catch(SQLException sqle2) {}
//			return;
//		}
		
		// 4. 결과를 출력
//		try {
//			while (rs.next()) {
//				int mvId = rs.getInt("MV_ID");
//				String mvTtl = rs.getString("MV_TTL");
//				String opnDt = rs.getString("OPN_DT");
//				int scrngTm = rs.getInt("SCRNG_TM");
//				String ageLmtNm = rs.getString("AGE_LMT_NM");
//				String plt = rs.getString("PLT");
//				String gnrNm = rs.getString("GNR_NM");
//				
//				System.out.println(mvId + " / " + mvTtl + " / " + opnDt + " / " + scrngTm + " / " + ageLmtNm + " / " + plt + " / " + gnrNm + " / ");
//			}
//		}
//		catch(SQLException sqle) {
//			System.out.println("결과를 가져올 수 없습니다.");
//			System.out.println(sqle.getMessage());
//			sqle.printStackTrace();
//		}
			
		
		// 5. Oracle Database Session 닫음
		//	  Session이 안 닫아지면 메모리 누수 문제가 발생
		//	  메모리 누수가 지속해서 발생할 경우 --> Out of Memory! 예외가 발생하면서 애플리케이션이 종료.
		try {
			rs.close();			
		}
		catch(SQLException sqle) {}
		
		try {
			pstmt.close();
		}
		catch(SQLException sqle) {}
		
		try {
			connection.close();
		}
		catch(SQLException sqle) {}
		
	}
	
	public static void main(String[] args) {
		selectAndPrintMovies();
	}
}
