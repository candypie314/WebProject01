package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		System.out.println("4. " + this.getClass() + ">>>>" + pageNo);
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		System.out.println("10. " + this.getClass() + ">>articlePage>>" + articlePage);
		req.setAttribute("articlePage", articlePage);
		return "/WEB-INF/view/listArticle.jsp";
	}
	

}
