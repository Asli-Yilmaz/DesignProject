package pkt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSqlConnection {
	static String url= "jdbc:postgresql://localhost:5432/AmazonDb";
	static Connection conn=null;
	static void connect()
	{
		try {
			conn= DriverManager.getConnection(url,"postgres","12345");
			System.out.println("Connected Successfully.");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
	}
	static ResultSet listQuery(String query) {
		try {
			Statement st= conn.createStatement();
			ResultSet rs= st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
		
	}
}
