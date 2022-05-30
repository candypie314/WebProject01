package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	/* DBCPInitListener가 만든 커넥션 풀에서 커넥션 하나를 꺼내 반환 */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:apache:commons:dbcp:board");
    }
}
