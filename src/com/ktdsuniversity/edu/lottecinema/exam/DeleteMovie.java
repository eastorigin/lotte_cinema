package com.ktdsuniversity.edu.lottecinema.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMovie {
	public static void deleteAndPrintMovies() {
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
		StringBuffer query = new StringBuffer();
		query.append(" DELETE                         ");
		query.append("   FROM GNR                     ");
		query.append("  WHERE GNR_ID = '00014'        ");

		
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			pstmt = connection.prepareStatement(query.toString());			
			insertCount = pstmt.executeUpdate(); // Insert 실행
			
			// Insert 성공
			connection.commit();
		}
		catch(SQLException sqle) {
			// Insert가 실패
			try {
				connection.rollback();				
			}
			catch(SQLException sqle2) {}
			System.out.println("SQL을 실행할 수 없습니다.");
			System.out.println("SQL을 올바르게 작성했는지 확인해보세요.");
			System.out.println(sqle.getMessage());
		}
		
		// 4. 정상적으로 처리가 되었는가 확인
		if (insertCount > 0) {
			System.out.println("DELETE가 성공적으로 처리되었습니다.");
		}
		
		// 5. Oracle Database Session 닫음
		//	  Session이 안 닫아지면 메모리 누수 문제가 발생
		//	  메모리 누수가 지속해서 발생할 경우 --> Out of Memory! 예외가 발생하면서 애플리케이션이 종료.
		if(pstmt != null) {
			try {
				pstmt.close();
			}
			catch(SQLException sqle) {}			
		}
		
		try {
			connection.close();
		}
		catch(SQLException sqle) {}
	}
	public static void main(String[] args) {
		deleteAndPrintMovies();
	}
}