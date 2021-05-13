package com.formaplus.dao.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.formaplus.dao.models.Utilisateur;
import com.formaplus.utils.Db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UtilisateurRepository implements IRepository<Utilisateur> {
	
	public Connection connection;
	
	
	public UtilisateurRepository() {
		connection = Db.getConnection();
	}

	@Override
	public List<Utilisateur> GetAll() {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM utilisateurs"); ResultSet resultSet = stm.executeQuery()) {
			Utilisateur u;
			while (resultSet.next()) {
				u = new Utilisateur();
				u.setIdUtr(resultSet.getInt(1));
				u.setNomCompUtr(resultSet.getString("nom_comp_utr"));
				u.setLoginUtr(resultSet.getString("login_utr"));
				u.setMdpUtr(resultSet.getString("mdp_utr"));
				u.setRoleUtr(resultSet.getString("role_utr"));
				listeUtilisateurs.add(u);
			}
			return listeUtilisateurs;
		} catch (SQLException e) {
			System.err.println(e);
			return listeUtilisateurs;
		}
		
	}
	
	public ObservableList<Utilisateur> search(String key) {
		ObservableList<Utilisateur> listeUtilisateurs = FXCollections.observableArrayList();
		
		try(Statement stm = connection.createStatement(); ResultSet resultSet = stm.executeQuery("SELECT * FROM utilisateurs WHERE nom_comp_utr LIKE '%" + key +"%' OR role_utr LIKE '%" + key +"%'")) {
			Utilisateur u;
			while (resultSet.next()) {
				u = new Utilisateur();
				u.setIdUtr(resultSet.getInt(1));
				u.setNomCompUtr(resultSet.getString("nom_comp_utr"));
				u.setLoginUtr(resultSet.getString("login_utr"));
				u.setMdpUtr(resultSet.getString("mdp_utr"));
				u.setRoleUtr(resultSet.getString("role_utr"));
				listeUtilisateurs.add(u);
			}
			return listeUtilisateurs;
		} catch (SQLException e) {
			System.err.println(e);
			return listeUtilisateurs;
		}
		
	}

	@Override
	public Utilisateur GetById(int id) {
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM utilisateurs where id_utr = ?")) {
			stm.setInt(1, id);
			ResultSet resultSet = stm.executeQuery();
			Utilisateur u = new Utilisateur();
			u.setIdUtr(resultSet.getInt(1));
			u.setNomCompUtr(resultSet.getString("nom_comp_utr"));
			u.setLoginUtr(resultSet.getString("login_utr"));
			u.setMdpUtr(resultSet.getString("mdp_utr"));
			u.setRoleUtr(resultSet.getString("role_utr"));
			return u;
		} catch (SQLException e) {
			System.err.println(e);
			
		}
		return null;
	}
	
	public Utilisateur login(String login) {
		try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM utilisateurs where login_utr = ?")) {
			stm.setString(1, login);
			ResultSet resultSet = stm.executeQuery();
			if(resultSet.next()) {
				Utilisateur u = new Utilisateur();
				u.setIdUtr(resultSet.getInt(1));
				u.setNomCompUtr(resultSet.getString("nom_comp_utr"));
				u.setLoginUtr(resultSet.getString("login_utr"));
				u.setMdpUtr(resultSet.getString("mdp_utr"));
				u.setRoleUtr(resultSet.getString("role_utr"));
				return u;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			
		}
		return null;
	}

	@Override
	public boolean Save(Utilisateur obj) {
		// TODO Auto-generated method stub
		if(obj.getIdUtr() == 0) {
			try(PreparedStatement pstm = connection.prepareStatement("INSERT INTO utilisateurs(nom_comp_utr,login_utr, mdp_utr, role_utr) VALUES(?,?,?,?)")) {
				pstm.setString(1, obj.getNomCompUtr());
				pstm.setString(2, obj.getLoginUtr());
				pstm.setString(3, obj.getMdpUtr());
				pstm.setString(4, obj.getRoleUtr());
				return pstm.executeUpdate() > 0;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			String sql = "UPDATE utilisateurs SET nom_comp_utr = ?, login_utr = ?, role_utr = ? ";
			if(!obj.getMdpUtr().isEmpty())  sql += " , mdp_utr = ?";
			
			sql += " WHERE id_utr = ?";
			try(PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, obj.getNomCompUtr());
				pstm.setString(2, obj.getLoginUtr());
				if(!obj.getMdpUtr().isEmpty()) {
					pstm.setString(3, obj.getRoleUtr());
					pstm.setString(4, obj.getMdpUtr());
					pstm.setInt(5, obj.getIdUtr());
				} else {
					pstm.setString(3, obj.getRoleUtr());
					pstm.setInt(4, obj.getIdUtr());
				}
				
				
				return pstm.executeUpdate() > 0;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean Delete(int id) {
		try {
			PreparedStatement stm = connection.prepareStatement("DELETE FROM utilisateurs WHERE id_utr = ?");
			stm.setInt(1, id);
			return stm.execute();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

}
