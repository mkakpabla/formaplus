package com.formaplus.dao.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Session;
import com.formaplus.utils.Db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EtudiantRepository implements IRepository<Etudiant> {
	
	
	private Connection connection;
	
	public EtudiantRepository() {
		connection = Db.getConnection();
	}

	@Override
	public ObservableList<Etudiant> GetAll() {
		try(PreparedStatement p = connection.prepareStatement("SELECT * FROM etudiants"); ResultSet rset = p.executeQuery()) {
			
			ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();
			while(rset.next()) {
				Etudiant etudiant = new Etudiant();
				etudiant.setIdEtu(rset.getInt("id_etu"));
				etudiant.setNomEtu(rset.getString("nom_etu"));
				etudiant.setPrenomEtu(rset.getString("prenom_etu"));
				etudiant.setEmailEtu(rset.getString("email_etu"));
				etudiant.setSexeEtu(rset.getString("sexe_etu"));
				etudiant.setTelEtu(rset.getInt("tel_etu"));
				etudiant.setDateNaissEtu(rset.getDate("date_naiss_etu").toLocalDate());
				etudiant.setDateAjout(rset.getDate("date_ajout").toLocalDate());
				etudiant.setPhotoEtu(rset.getBinaryStream("photo_etu"));
				etudiants.add(etudiant);
			}
			return etudiants;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public ObservableList<Etudiant> getAllWhereSession(Session session) {
		String sql = "select * from full_inscriptions WHERE id_session = ?  GROUP By id_etu";
		ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, session.getIdSession());
			ResultSet rset = ps.executeQuery();
			while(rset.next()) {
				Etudiant etudiant = new Etudiant();
				etudiant.setIdEtu(rset.getInt("id_etu"));
				etudiant.setNomEtu(rset.getString("nom_etu"));
				etudiant.setPrenomEtu(rset.getString("prenom_etu"));
				etudiant.setEmailEtu(rset.getString("email_etu"));
				etudiant.setSexeEtu(rset.getString("sexe_etu"));
				etudiant.setTelEtu(rset.getInt("tel_etu"));
				etudiant.setDateNaissEtu(rset.getDate("date_naiss_etu").toLocalDate());
				etudiant.setDateAjout(rset.getDate("date_ajout").toLocalDate());
				etudiant.setPhotoEtu(rset.getBinaryStream("photo_etu"));
				etudiants.add(etudiant);
			}
			return etudiants;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return etudiants;
	}
	

	@Override
	public Etudiant GetById(int id) {
		try(PreparedStatement p = connection.prepareStatement("SELECT * FROM etudiants WHERE id_etu = ?")) {
			p.setInt(1, id);
			
			try(ResultSet rset = p.executeQuery()) {
				if(rset.next()) {
					Etudiant etudiant = new Etudiant();
					etudiant.setIdEtu(rset.getInt("id_etu"));
					etudiant.setNomEtu(rset.getString("nom_etu"));
					etudiant.setPrenomEtu(rset.getString("prenom_etu"));
					etudiant.setEmailEtu(rset.getString("email_etu"));
					etudiant.setSexeEtu(rset.getString("sexe_etu"));
					etudiant.setTelEtu(rset.getInt("tel_etu"));
					etudiant.setDateNaissEtu(rset.getDate("date_naiss_etu").toLocalDate());
					etudiant.setDateAjout(rset.getDate("date_ajout").toLocalDate());
					etudiant.setPhotoEtu(rset.getBinaryStream("photo_etu"));
					return etudiant;
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
	public int Insert(Etudiant obj) {
		try(PreparedStatement pstm = connection.prepareStatement("INSERT INTO etudiants(nom_etu, prenom_etu, sexe_etu, email_etu, tel_etu, date_naiss_etu, photo_etu, date_ajout) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, obj.getNomEtu());
			pstm.setString(2, obj.getPrenomEtu());
			pstm.setString(3, obj.getSexeEtu());
			pstm.setString(4, obj.getEmailEtu());
			pstm.setInt(5, obj.getTelEtu());
			pstm.setString(6, obj.getDateNaissEtu().toString());
			pstm.setString(8, obj.getDateAjout().toString());
			pstm.executeUpdate();
			try(ResultSet r = pstm.getGeneratedKeys();) {
				if(r.next()) {
					return r.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
			
		}
		return 0;
	}

	@Override
	public boolean Delete(int id) {
		try(PreparedStatement p = connection.prepareStatement("DELETE FROM etudiants WHERE id_etu = ?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean Save(Etudiant obj) {
		String query = "UPDATE etudiants SET nom_etu = ?, prenom_etu = ?, sexe_etu = ?, email_etu = ?, tel_etu = ?, date_naiss_etu = ? ";
		if(obj.getPhotoEtu() != null) query += ", photo_etu = ?";
		query += " WHERE id_etu = ?";
		try(PreparedStatement pstm = connection.prepareStatement(query)) {
			pstm.setString(1, obj.getNomEtu());
			pstm.setString(2, obj.getPrenomEtu());
			pstm.setString(3, obj.getSexeEtu());
			pstm.setString(4, obj.getEmailEtu());
			pstm.setInt(5, obj.getTelEtu());
			pstm.setString(6, obj.getDateNaissEtu().toString());
			if(obj.getPhotoEtu() != null) {
				pstm.setBinaryStream(7, obj.getPhotoEtu());
				pstm.setInt(8, obj.getIdEtu());
			} else pstm.setInt(7, obj.getIdEtu());
			
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasInscription(int id) {
		try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM inscriptions WHERE id_etu = ?")) {
			pstm.setInt(1, id);
			return pstm.executeQuery().next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
