package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();
	//한 페이지 당 페이지 개수
	private int size = 5;

	public ArticlePage getArticlePage(int pageNum) {
		//try~with~resource: 입출력 객체 자동으로 닫아줌
		//try (Connection conn = ConnectionProvider.getConnection()) {
		//Connection conn = null;
		try {
			Connection conn = ConnectionProvider.getConnection();
			System.out.println("5. " + this.getClass() + ">>>> dao로 간다");
			int total = articleDao.selectCount(conn);
			System.out.println("7. " + this.getClass() + "total>>>>" + total);
			System.out.println("8. " + this.getClass() + "dao로 다시 간다");
			List<Article> content = articleDao.select(
					conn, (pageNum - 1) * size, size);
			System.out.println("8. " + this.getClass() + "dao로 다녀옴, 리스트 하나 들고 옴" + content);
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
