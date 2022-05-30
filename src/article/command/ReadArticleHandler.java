package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		//request에 파라미터 즉 "?no=값"을 달고 온다는 의미
		String noVal = req.getParameter("no");
		String name = req.getParameter("name");
		int articleNum = Integer.parseInt(noVal);
		boolean increaseReadCount = true;
		User user = (User) req.getSession().getAttribute("authUser");
		//로그인된 사람이 글 쓴 사람이랑 같으면 조회수 올려주지 마라 //내가 보면 조회 수 안 올라감
		if(user != null && user.getName().equals(name)) {
			increaseReadCount = false;
		}
		try {
			ArticleData articleData = readService.getArticle(articleNum, increaseReadCount);
			req.setAttribute("articleData", articleData);
			return "/WEB-INF/view/readArticle.jsp";
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(487,"내용없어");
			return null;
		}
	}

}
