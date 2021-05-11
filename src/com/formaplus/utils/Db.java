package com.formaplus.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
	
	private static Connection connect;
	
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
