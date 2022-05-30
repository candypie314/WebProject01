package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

//서비스 : 컨트롤러의 호출을 받아 매개변수로 받은 값 등을 이용해 과제 처리 알고리즘
// -- 주로 dao를 호출하여 db에 데이터를 입출력하고 그 결과를 처리하여 컨트롤러로 반환

//id 중복 체크하고 아니면 회원으로 등록(db에 insert)
public class JoinService {
	
	private MemberDao memberDao = new MemberDao();
	
	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//id 중복 체크: 중복이면 롤백하고 익셉션 처리
			Member member = memberDao.selectById(conn, joinReq.getId());
			if (member != null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			System.out.println("join>>>> gogo");
			//아니면 db에 저장(DTO인 Member 객체 생성하고 값 넣어서 dao의 insert 호출)
			memberDao.insert(conn, 
					new Member(
							joinReq.getId(), 
							joinReq.getName(), 
							joinReq.getPassword(), 
							new Date())
					);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
