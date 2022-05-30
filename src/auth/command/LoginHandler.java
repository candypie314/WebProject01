package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	private LoginService loginService = new LoginService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		//요청이 loginform에서 온 것이 아니다 - 
		if (req.getMethod().equalsIgnoreCase("GET")) {
			//login form 경로 반환
			return processForm(req, res);
		//요청이 login form에서 왔으므로 폼의 내용을 검사하고 이상 없으면 로그인 프로세스 진행(dao 호출)
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		if (id == null || id.isEmpty())
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			User user = loginService.login(id, password);
			//로그인 성공 - 세션에 속성 authUser와 속성값 회원정보를 붙여준다.
			req.getSession().setAttribute("authUser", user);
			//index.jsp 페이지로 돌아감
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
