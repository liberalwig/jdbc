//2021.12.27(월)13:40 수업-2022.01.09(일)21:56-2022.01.10(월)00:57 재수강. 목적: 우리가 만든 쿼리문을 오라클에 전달하는 프로그램을 만든다

package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorApp {

	public static void main(String[] args) {
		// 0. import java.sql.*; jdbc-java를 import시키는 과정
		Connection conn = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null; --안 쓸거라 삭제하고 05if문에서도 삭제
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩: Scanner 올라가는 것처럼 올리기
			Class.forName("oracle.jdbc.drvier.OracleDriver");

			
			
			// 2. Connection 얻어오기 : "전화선 내놔" String url의 localhost에는 IP넣고 그 뒤는 ID, PW.
			// DriveManager가 없이 때문에 import시켜서 오류 없애기.
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("접속 성공"); //try-catch문을 아래에 썼으므로 성공했을 시에는 "접속 성공"이 출력될 것임.

			
			
			// 3. SQL문 준비 / 바인딩 / 실행 : insert문 넣는 부분으로 사실상 특이점. 우리 프로그램은 시작하면 '이문열'한row만. java통해 쿼리문 짜므로 String형.
			String query = "";

			// query = query + "문자열"     =      'query += "문자열"'
			query = query + " insert into author "; // query += "insert into author"와 동일.
			query = query + " values(seq_author_id.nextval, ?, ?) "; // 이 행에서의 우항의 query는 윗행인 "insert to author"란. --띄어쓰기 주의!
			System.out.println(query);

			// 문자열 쿼리문으로 만들기. pstmt라는 클래스를 만들어서 거기에 쿼리문을 날리는 과정.
			pstmt = conn.prepareStatement(query);

			// 바인딩: '?'자리에 데이터를 넣어주는 작업
			pstmt.setString(1, "이문열"); //첫번째 물음표의 데이터
			pstmt.setString(2, "경북 영양"); //두번째 물음표의 데이터
			//int형의 데이터를 삽입할 경우 pstmt.setInt이라 쓰면 되겠지
			pstmt.setString(1, "박경리");
			pstmt.setString(2, "경남 통영");
			pstmt.setString(1, "유시민");
			pstmt.setString(2, "17대 국회의원");
			pstmt.setString(1, "기안84");
			pstmt.setString(2, "기안동 84년생");
			pstmt.setString(1, "강풀");
			pstmt.setString(2, "온라인만화가 1세대");
			pstmt.setString(1, "김영하");
			pstmt.setString(2, "알쓸신잡");
			
			//실행
			int count = pstmt.executeUpdate(); //위에 쿼리문바인딩을 1건해서 이걸 실행해보면 1이라고 뜰 것임. 확인차 삽입하는 행. 
			//다만 정확히 확인하고 싶기 때문에 확인은 sql developer로 select문 출력해서 확인할 계획.
			
			
			// 4.결과처리
			System.out.println(count + " 건이 저장되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	}

}
v