package com.ktdsuniversity.edu.lottecinema.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB에 접근을 도와주는 역할
 * CRUD처리
 * 
 */
public class DataAccessHelper {

	/**
	 * 데이터베이스에 연결을 맺어주는 객체
	 * 트랜잭션 처리도 담당
	 */
	private Connection connection;
	/**
	 * 실행될 쿼리를 준비하고 데이터베이스에서 쿼리를 실행시키는 객체
	 */
	private PreparedStatement pstmt;
	/**
	 * SELECT 쿼리의 결과를 받아오는 객체
	 */
	private ResultSet rs;
	
	// 1. 오라클 드라이버 로딩
	public DataAccessHelper() {// 서비스가 사용. 하나라도 에러나면 롤백시키려고
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 데이터베이스 시험에 나옴!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!			
		}
		catch(ClassNotFoundException cnfe) { 
			System.out.println("오라클에 연결하기 위한 드라이버가 존재하지 않습니다.");
			System.out.println("ojdb11.jar 파일이 클래스패스에 연결 되어있는지 확인해주세요.");
			throw new RuntimeException("오라클 드라이버가 존재하지 않습니다. 데이터베이스에 연결할 수 없습니다."); // 예외를 던져야 밑에가 실행이 안 된다
		}
		
		this.connectDatabase();
	}
	
	// 2. 커넥션을 맺음 (Session 생성)
	private void connectDatabase() {
		final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		final String SCHEMA_NAME = "LOTTE_CINEMA";
		final String SCHEMA_PASSWORD = "LOTTE_CINEMA";
		
		try {
			this.connection = DriverManager.getConnection(JDBC_URL, SCHEMA_NAME, SCHEMA_PASSWORD);
			this.connection.setAutoCommit(false); // Manual Commit으로 설정
		} catch (SQLException sqle) {
			System.out.println("데이터베이스에 연결할 수 없습니다.");
			System.out.println("URL, SCHEMA, PASSWORD를 확인해주세요.");
			throw new RuntimeException(sqle.getMessage());
		}
	}
	
	// 3. 쿼리를 준비하고 실행함
	public void preparedStatement(String query, BindingParameter bindingParameter) { // DAO가 사용
		try {
			this.pstmt = this.connection.prepareStatement(query);
			if(bindingParameter != null) {
				bindingParameter.bind(this.pstmt);
			}
		} catch (SQLException sqle) {
			System.out.println("쿼리를 실행할 수 없습니다.");
			System.out.println("쿼리가 올바른지 확인해주세요.");
			throw new RuntimeException(sqle.getMessage());
		}
	}
	
	// 4. 실행된 쿼리의 결과를 받아 처리함
	public void executeQuery(SQLType sqlType, FetchRow fetchRow) { // DAO가 사용
		try {
			if(sqlType == SQLType.SELECT) {
				this.rs = this.pstmt.executeQuery();
				while(this.rs.next()) {
					fetchRow.fetch(rs);
				}
			}else {
				this.pstmt.executeUpdate();
			}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
			throw new RuntimeException(sqle.getMessage());
		}
	}
	
	
	// 5. 파이프(Connection, PreparedStatement, ResultSet) 닫음
	public void close() { // 서비스가 사용
		if(this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException sqle) {} // SQLExcepton이 checkedException이어서 try-catch 필요
		}
		
		if(this.pstmt != null) {
			try {
				this.pstmt.close();
			} catch (SQLException sqle) {}  // checkedException은 할 게 없다
		}
		
		if(this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException sqle) {}
		}
	}
	
	// 6. 모든 쿼리가 정상 실행되었으면 커밋한다.
	public void commit() { // 서비스가 사용
		try {
			this.connection.commit();
		} catch (SQLException sqle) {
			System.out.println("커밋할 수 없습니다.");
			System.out.println(sqle.getMessage());
		}
	}
	
	// 7. 쿼리 실행 중 에러가 발생했으면 롤백한다.
	public void rollback() { // 서비스가 사용
		if(this.connection != null) { // 이거 안 하면 NullPointException 터짐.
			try {
				this.connection.rollback();
			} catch (SQLException sqle) {
				System.out.println("롤백할 수 없습니다.");
				System.out.println(sqle.getMessage());
			}
		}
	}
}
