package jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

//web.xml에서 주입 받은 poolConfig를 property 파일로 만들고 드라이버 로딩, 커넥션 풀 생성  
public class DBCPInitListener implements ServletContextListener {
	
	/* 리스너 기본설정 메소드(특정 이벤트-시작에 필요한 정보 처리)-커넥션 풀 생성 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String poolConfig = 
				sce.getServletContext().getInitParameter("poolConfig");
		/* web.xml에서 스트링으로 넘겨받은 초기값을 키값 형태로 사용하기 위해 Properties 파일로 만들 준비 */
		Properties prop = new Properties();
		try {
			/* 키값 형태의 스트링을 한 줄씩 키값으로 읽는 리더를 사용하여 설정값을 빈 프로퍼티 파일에 저장 */ 
			prop.load(new StringReader(poolConfig));
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		loadJDBCDriver(prop);
		initConnectionPool(prop);
	}
	//프로퍼티 파일에서 getProperty메소드를 이용해서 해당 값을 추출해 사용
	private void loadJDBCDriver(Properties prop) {
		String driverClass = prop.getProperty("jdbcdriver");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}
	}
    //properties 이용해서 커넥션 풀(board: max 50) 생성
	private void initConnectionPool(Properties prop) {
		try {
			String jdbcUrl = prop.getProperty("jdbcUrl");
			String username = prop.getProperty("dbUser");
			String pw = prop.getProperty("dbPass");

			ConnectionFactory connFactory = 
					new DriverManagerConnectionFactory(jdbcUrl, username, pw);

			PoolableConnectionFactory poolableConnFactory = 
					new PoolableConnectionFactory(connFactory, null);
			String validationQuery = prop.getProperty("validationQuery");
			if (validationQuery != null && !validationQuery.isEmpty()) {
				poolableConnFactory.setValidationQuery(validationQuery);
			}
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			//poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			int minIdle = getIntProperty(prop, "minIdle", 5);
			poolConfig.setMinIdle(minIdle);
			int maxTotal = getIntProperty(prop, "maxTotal", 50);
			poolConfig.setMaxTotal(maxTotal);

			GenericObjectPool<PoolableConnection> connectionPool = 
					new GenericObjectPool<>(poolableConnFactory, poolConfig);
			poolableConnFactory.setPool(connectionPool);
			/* 풀드라이버 등록 및 생성 */
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)
				DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			/* 풀을 사용할 어플리케이션 이름 등록 */
			String poolName = prop.getProperty("poolName");
			driver.registerPool(poolName, connectionPool);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int getIntProperty(Properties prop, String propName, int defaultValue) {
		String value = prop.getProperty(propName);
		if (value == null) return defaultValue;
		return Integer.parseInt(value);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
