package java.com.skht777.jdbcwrapper;
/**
 * 
 */


import java.io.Closeable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author skht777
 *
 */
public class Database implements Closeable{
	
	private Connection con;
	
	public class QueryBuilder implements Closeable {
		private Statement stm;
		
		public QueryBuilder(Connection con) {
			try {
				stm = con.createStatement();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		public ResultSet query(String query) throws SQLException {
			return stm.executeQuery(query);
		}
		
		public void transaction(String query) throws SQLException {
			stm.executeUpdate(query);
		}

		@Override
		public void close() {
			try {
				if(stm != null) stm.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Database(String uri, String subProtcol) {
		try {
			con = DriverManager.getConnection("jdbc:" + subProtcol + ":" + uri);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public QueryBuilder getQueryBuilder() {
		return new QueryBuilder(con);
	}

	@Override
	public void close() {
		try {
			if(con != null) con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
