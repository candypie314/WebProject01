package member.model;

import java.util.Date;

public class Member {

	private String id;
	private String name;
	private String password;
	private Date regDate;

	public Member(String id, String name, String password, Date regDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	/* 입출력 객체(DTO)를 다룰 때 즉시 사용하기 쉽도록 꼭 필요한 메소드를 작성 */
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
	//새로운 pwd를 받아서 update / setter	
	//현재 객체의 비번을 매개변수로 받은 비번으로 덮어씀
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}

}
