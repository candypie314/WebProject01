package article.command;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Article;
import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		DeleteArticleService das = new DeleteArticleService();
		
		int articleNo = Integer.parseInt(req.getParameter("no"));
		
		das.delete(articleNo);
		
		
		return "/WEB-INF/view/deleteArticleSuccess.jsp";
	}
	
	
}
