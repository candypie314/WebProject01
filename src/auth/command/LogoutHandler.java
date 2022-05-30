package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		//로그아웃: 세션을 지워준다
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		res.sendRedirect(req.getContextPath() + "/index.jsp");
		return null;
	}

}
