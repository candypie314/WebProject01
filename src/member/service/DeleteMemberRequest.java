package member.service;

import java.util.Map;

public class DeleteMemberRequest {
	private String id;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//폼 입력값 검증 - 보통 javascript에서 하는 일을 jsp에서 처리. js에서 하는게 더 낫다. 
		public void validate(Map<String, Boolean> errors) {
			//폼에서 입력받은 값이 있는지 체크하고 없으면 errors에 항목 이름과 TRUE 입력
			checkEmpty(errors, id, "id");
			checkEmpty(errors, password, "password");
			
		}
		
		//폼의 각 항목에 값이 있는지 확인하고 없으면 errors라는 맵에 항목 이름과 True를 입력
		private void checkEmpty(Map<String, Boolean> errors, 
				String value, String fieldName) {
			if (value == null || value.isEmpty())
				errors.put(fieldName, Boolean.TRUE);
		}
}
