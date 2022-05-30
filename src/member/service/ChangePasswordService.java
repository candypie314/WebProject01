package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {

	private MemberDao memberDao = new MemberDao();
	
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;
		try {
			//커넥션 풀에서 커넥션 하나를 받음
			conn = ConnectionProvider.getConnection();
			//자동 커밋 중지시킴
			conn.setAutoCommit(false);
			//memberDao의 selectById메소드(id를 사용해서 해당 회원 정보 추출: select * form member where id =userId)를
			//실행하고 결과로 Member 객체를 반환 받음
			Member member = memberDao.selectById(conn, userId);
			//dao에서 return받은 결과가 null이면(매개변수로 받은 userId를 id로 갖는 회원이 없다면)
			if (member == null) {
				//MemberNotFoundException이라는 사용자 정의 익셉션을 발생시킴
				throw new MemberNotFoundException();
			}
			//Member 객체의 matchPassword메소드를 실행하여 매개변수로 받은 비번과 객체의 필드에 저장된 비번을 비교하여 결과로 boolean값을 받는다.
			if (!member.matchPassword(curPwd)) { //현재 암호와 db에 저장된 암호가 같지 않은 경우
				//InvalidPasswordException라는 사용자 정의 익셉션 발생시킴
				throw new InvalidPasswordException();
			}
			//Member 객체의 changePassword를 메소드 호출: 객체의 비번을 새 비번으로 덮어씀
			member.changePassword(newPwd);
			//memberDao의 update메소드 호출: 새로운 비번 들고 가서 DB에 적용 - 회원정보수정
			memberDao.update(conn, member);
			//변경사항 최종 적용 (변경불가)
			conn.commit();
		} catch (SQLException e) {
			//JdbcUtil의 rollback메소드 호출: DML작업 오류 발생 시 롤백 실행 
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			//JdbcUtil의 close메소드 호출: 
			JdbcUtil.close(conn);
		}
	}
}
