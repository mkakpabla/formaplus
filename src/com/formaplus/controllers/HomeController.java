package com.formaplus.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.formaplus.dao.DbConnector;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane mainAnchorePane;

	@FXML
	private BarChart<String, Integer> inscBarChart;

	@FXML
	private CategoryAxis inscBarChartX;

	@FXML
	private NumberAxis inscBarChartY;
	
	@FXML
    private Label etuLabel;

    @FXML
    private Label inscLabel;

    @FXML
    private Label sessionsLabel;
	

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Series<String, Integer> serie = new Series<>();
		try {
			ResultSet  rset =  DbConnector.executeQuery("SELECT s.lib_session, COUNT(i.id_insc) as count FROM inscriptions i, sessions s WHERE i.id_session = s.id_session GROUP BY s.lib_session");
			while(rset.next()) {
				serie.getData().add(new Data<String, Integer>(rset.getString("lib_session"), rset.getInt("count")));
			}
			inscBarChart.getData().addAll(serie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.countEtudiant();
		this.countInscriptions();
		this.countSessions();
		
	}
	
	private void countEtudiant() {
		try(ResultSet rset = DbConnector.executeQuery("SELECT count(*) as etu_count FROM etudiants")) {
			if(rset.next()) etuLabel.setText(rset.getString("etu_count"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void countInscriptions() {
		try(ResultSet rset = DbConnector.executeQuery("SELECT count(*) as insc_count FROM inscriptions")) {
			if(rset.next()) inscLabel.setText(rset.getString("insc_count"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void countSessions() {
		try(ResultSet rset = DbConnector.executeQuery("SELECT count(*) as session_count FROM sessions")) {
			if(rset.next()) sessionsLabel.setText(rset.getString("session_count"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
