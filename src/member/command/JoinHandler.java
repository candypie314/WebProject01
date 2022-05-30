package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

//join.do를 통해 접근한 회원가입용 폼 입력값 검사 클래스
public class JoinHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	private JoinService joinService = new JoinService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
		//index.jsp에서 회원가입 눌렀을 때 (gEt 방식으로 들어옴)
		if (req.getMethod().equalsIgnoreCase("GET")) {
			//아래 있는 processForm 메소드 실행하고 결과 반환
			return processForm(req, res);
		//joinForm.jsp에서 가입 버튼 눌렀을 때(post 방식으로 들어옴)
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			//아래 있는 processSubmit 메소드 실행하고 결과 반환
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	//index.jsp에서 회원가입 눌렀을 때 joinForm.jsp의 경로를 반환
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	//joinForm.jsp에서 가입 버튼 눌렀을 때
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		//form에서 넘어온 값(request)의 error 체크 -> map을 만들어서 기록 -> 
		//에러가 있으면 에러 체크 결과(map)와 함게 다시 form으로 되돌려줌. 
		//JoinRequest 객체 생성하고 joinForm.jsp에서 넘겨준 request에 들어 있는 값을 넣어줌
		JoinRequest joinReq = new JoinRequest();
		joinReq.setId(req.getParameter("id"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		
		//폼 입력값 검증의 결과를 담기 위한 맵 생성
		Map<String, Boolean> errors = new HashMap<>();
		/* jsp가 맵에서 값을 뽑을 수 있도록 request에 errors라는 이름으로 맵을 부착 */ 
		req.setAttribute("errors", errors);
		
		//회원가입 폼 null, passwr 불일치 검증: 검증 결과를 errors Map에 입력
		joinReq.validate(errors);
		
		//입력 폼의 어떤 항목에 값이 입력되지 않았거나, password가 불일치하면 입력 폼으로 다시 보낸다.
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		//입력 폼 값에 문제가 없다.
		try {
			//폼 입력값을 joinReq 오브젝트로 만들어서 joinService 클래스의 join 메소드로 넘겨주고 실행
			joinService.join(joinReq);
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateIdException e) {
			//join 메소드에서 id 중복체크에 걸리면 errors에 기록하고 가입 폼 반환
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

}
