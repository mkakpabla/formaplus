package com.formaplus.dao.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.formaplus.dao.models.Formation;
import com.formaplus.utils.Db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class FormationRepository implements IRepository<Formation> {
	
	private Connection connection;
	
	
	public FormationRepository() {
		connection = Db.getConnection();
	}

	@Override
	public List<Formation> GetAll() {
		List<Formation> formations = new ArrayList<Formation>();
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM formations"); ResultSet result = stm.executeQuery()) {
			while (result.next()) {
				Formation formation = new Formation();
				CheckBox checkBox = new CheckBox(result.getString("lib_forma"));
				checkBox.setSelected(false);
				formation.setIdFormation(result.getInt("id_forma"));
				formation.setLibFormation(result.getString("lib_forma"));
				formation.setDureeFormation(result.getInt("duree_forma"));
				formation.setPrixFormation(result.getDouble("prix_forma"));
				formation.setCheckBoxFormation(checkBox);
				formations.add(formation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return formations;
	}
	
	public ObservableList<Formation> GetAllWhereSessionId(int sessionId) {
		ObservableList<Formation> formations = FXCollections.observableArrayList();
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM formation_session, formations, sessions WHERE formation_session.id_forma = formations.id_forma and formation_session.id_session = sessions.id_session and formation_session.id_session = ?")) {
			stm.setInt(1, sessionId);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				Formation formation = new Formation();
				CheckBox checkBox = new CheckBox(result.getString("lib_forma"));
				checkBox.setSelected(true);
				
				formation.setIdFormation(result.getInt("id_forma"));
				formation.setLibFormation(result.getString("lib_forma"));
				formation.setDureeFormation(result.getInt("duree_forma"));
				formation.setPrixFormation(result.getDouble("prix_forma"));
				formation.setCheckBoxFormation(checkBox);
				formations.add(formation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return formations;
	}
	
	
	public ObservableList<Formation> GetAllWithCheckBox() {
		ObservableList<Formation> formations = FXCollections.observableArrayList();
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM formations"); ResultSet result = stm.executeQuery()) {
			while (result.next()) {
				Formation formation = new Formation();
				CheckBox checkBox = new CheckBox(result.getString("lib_forma"));
				formation.setIdFormation(result.getInt("id_forma"));
				formation.setLibFormation(result.getString("lib_forma"));
				formation.setDureeFormation(result.getInt("duree_forma"));
				formation.setPrixFormation(result.getDouble("prix_forma"));
				formation.setCheckBoxFormation(checkBox);
				formations.add(formation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return formations;
	}

	@Override
	public Formation GetById(int id) {
		Formation formation = new Formation();
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM formations WHERE id_forma = ?")) {
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			if (result.next()) {
				formation.setIdFormation(result.getInt("id_forma"));
				formation.setLibFormation(result.getString("lib_forma"));
				formation.setDureeFormation(result.getInt("duree_forma"));
				formation.setPrixFormation(result.getDouble("prix_formation"));
				return formation;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean Save(Formation obj) {
		
		if(obj.getIdFormation() == 0) {
			try(PreparedStatement pstm = connection.prepareStatement("INSERT INTO formations(lib_forma, duree_forma, prix_forma) VALUES(?,?,?)")) {
				pstm.setString(1, obj.getLibFormation());
				pstm.setInt(2, obj.getDureeFormation());
				pstm.setDouble(3, obj.getPrixFormation());
				return pstm.executeUpdate() > 0;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			try(PreparedStatement pstm = connection.prepareStatement("UPDATE formations SET lib_forma = ?, duree_forma = ?, prix_forma = ? WHERE id_forma = ?")) {
				pstm.setString(1, obj.getLibFormation());
				pstm.setInt(2, obj.getDureeFormation());
				pstm.setDouble(3, obj.getPrixFormation());
				pstm.setInt(4,obj.getIdFormation());
				return pstm.executeUpdate() > 0;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean Delete(int id) {
		try(PreparedStatement stm = connection.prepareStatement("DELETE FROM formations WHERE id_forma = ?")) {
			stm.setInt(1, id);
			return stm.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
