package com.formaplus.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
	
	
	private static Connection connect;
	
	private static Connection connectTransa;
	
	private static String url;
	
	private static String login;
	
	private static String password;

	public static Connection getConnection() {
		if(connect == null){
			loadConf();
			try {
				connect = DriverManager.getConnection(url,login,password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect;
	}
	
	public static Connection getTransactionConnection() {
		if(connectTransa == null){
			loadConf();
			try {
				connectTransa = DriverManager.getConnection(url,login,password);
				connectTransa.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectTransa;
	}
	
	private static PreparedStatement getPreparedStatement(String sql, Object... params) throws SQLException {
		PreparedStatement preparedStatement = DbConnector.getConnection().prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject((i + 1), params[i]);
        }
        return preparedStatement;
    }

    public static boolean executeUpdate(String sql, Object... params) throws SQLException {
        return getPreparedStatement(sql, params).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        return getPreparedStatement(sql, params).executeQuery();
    }
	
	
	
	
	private static void loadConf() {
		if(url == null || login == null || password == null) {
			Properties props = new Properties();
			try {
				FileInputStream fs = new FileInputStream("conf.properties");
				props.load(fs);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			//Class.forName(props.getProperty("jdbc.driver.class"));
			url = props.getProperty("jdbc.url");
			login = props.getProperty("jdbc.login");
			password = props.getProperty("jdbc.password");
		}
	}
}
