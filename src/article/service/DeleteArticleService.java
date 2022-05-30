package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public void delete(int no) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Article article = articleDao.selectById(conn, no);
			if (article == null) {
				throw new ArticleNotFoundException();
			}

			articleDao.delete(conn, no);
			contentDao.delete(conn, no);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
