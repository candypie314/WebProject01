package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;

public class WriteArticleHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			System.out.println("4. " + this.getClass()+">>req.getMethod()>> " + req.getMethod());
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			System.out.println("4. " + this.getClass()+">>req.getMethod()>> " + req.getMethod());
			return processSubmit(req, res);
		} else {
			//get, post 아닌 (ajax 등)으로 오면 안 받음
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		//form에서 넘어오는 데이터의 에러(null 등)를 체크해서 에러를 등록하기 위한 map 객체 생성 
		Map<String, Boolean> errors = new HashMap<>();
		//리퀘스트에 errors라는 속성을 만들고 그 속성값으로 errors 맵을 붙인다.
		req.setAttribute("errors", errors);
		
		//리퀘스트에 포함된 세션으로부터 authUser 속성 값(User 객체: 회원정보)을 가져와 저장
		User user = (User)req.getSession(false).getAttribute("authUser");
		//createWriteRequest 메소드를 사용해서 WriteRequest 객체(user(writer), title, content) 생성
		WriteRequest writeReq = createWriteRequest(user, req);
		//생성한 WriteRequest 객체의 validate 메소드를 실행(errors 맵을 넘겨주고 title을 체크해서 error가 있으면 입력)
		writeReq.validate(errors);
		//errors 맵이 비어 있지 않다면(에러가 있다면, title이 null 혹은 ""이면) 입력폼으로 이동
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		//에러가 없을 경우 writeService 객체의 write 메소드를 실행(앞에서 생성한 WriteRequest 객체 넘겨줌)
		int newArticleNo = writeService.write(writeReq);
		
		req.setAttribute("newArticleNo", newArticleNo);
		
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}

	private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
		return new WriteRequest(
				new Writer(user.getId(), user.getName()),
				req.getParameter("title"),
				req.getParameter("content"));
	}
}
