package rfsComStatusInstalando;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class validaDAO {

	private static Statement stmt;	
	private static Connection con;

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		validaDAO.con = con;
	}

	public static Statement getStmt() {
		return stmt;
	}

	public static void setStmt(Statement stmt) {
		validaDAO.stmt = stmt;
	}

	public static void acessaDB() throws ClassNotFoundException, SQLException {
		// step1 load the driver class
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// step2 create the connection object
		validaDAO.setCon(DriverManager.getConnection("jdbc:oracle:thin:@10.238.45.165:1521:starprod1",
				"SIGITM_STAR_IMP", "Starimp01!"));

		// step3 create the statement object
		validaDAO.setStmt(con.createStatement());
	}

	public static ResultSet runQuery(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}
	
	public static void commit() throws SQLException {
		stmt.executeQuery("commit");
	}
	
	public static void close() throws SQLException {
		con.close();
		stmt.close();
	}
}
