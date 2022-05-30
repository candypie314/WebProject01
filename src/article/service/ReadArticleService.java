package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.connection.ConnectionProvider;

public class ReadArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public ArticleData getArticle(int articleNum, boolean increaseReadCount) {
		/* try ~ with문: 입출력 객체 등 열린 객체를 닫아주지 않아도 사용 후에 저절로 닫힘 */
		try (Connection conn = ConnectionProvider.getConnection()){
			//게시글 번호로 게시글 조회하여 객체로 반환 받음
			Article article = articleDao.selectById(conn, articleNum);
			if (article == null) {
				throw new ArticleNotFoundException();
			}
			ArticleContent content = contentDao.selectById(conn, articleNum);
			if (content == null) {
				throw new ArticleContentNotFoundException();
			}
			if (increaseReadCount) {
				articleDao.increaseReadCount(conn, articleNum);
			}
			return new ArticleData(article, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
