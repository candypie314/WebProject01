package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class DeleteMemberService {

private MemberDao memberDao = new MemberDao();
	
	public void deleteMember(DeleteMemberRequest delMemReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			System.out.println("여기까지가 끝인가보오1");
			//id 중복 체크: 중복이면 롤백하고 익셉션 처리
			Member member = memberDao.selectById(conn, delMemReq.getId());
			System.out.println("여기까지가 끝인가보오2");
			if (member==null || (!member.getPassword().equals(delMemReq.getPassword()))) {
				System.out.println("여기까지가 끝인가보오3");
				JdbcUtil.rollback(conn);//사실 할 필요없음
				throw new DuplicateIdException();
			}
			
				
			System.out.println("delMemReq>>>> gogo");
			//중복이 아니면 insert해서 join. DB에 저장 (DTO인 Member 객체 생성하고 값 넣어서 DAO에 insert메소드 호출)
			memberDao.delete(conn, member);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
