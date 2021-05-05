package com.formaplus.dao.repositories;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.utils.Db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InscriptionRepository implements IRepository<Inscription> {
	
	private Connection connection;
	
	public InscriptionRepository() {
		connection = Db.getConnection();
	}

	@Override
	public ObservableList<Inscription> GetAll() {
		try(PreparedStatement p = connection.prepareStatement("SELECT * FROM full_inscriptions"); ResultSet rset = p.executeQuery()) {
			ObservableList<Inscription> inscriptions = FXCollections.observableArrayList();
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
				
				Formation formation = new Formation();
				formation.setIdFormation(rset.getInt("id_forma"));
				formation.setLibFormation(rset.getString("lib_forma"));
				formation.setDureeFormation(rset.getInt("duree_forma"));
				formation.setPrixFormation(rset.getDouble("prix_forma"));
				
				Inscription insc = new Inscription();
				insc.setEtudiant(etudiant);
				insc.setFormation(formation);
				insc.setIdInsc(rset.getInt("id_insc"));
				insc.setDateInsc(rset.getDate("date_insc").toLocalDate());
				inscriptions.add(insc);
			}
			return inscriptions;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Inscription GetById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Save(Inscription obj) {
		String sql = "INSERT INTO etudiants(nom_etu, prenom_etu, sexe_etu, email_etu, tel_etu, date_naiss_etu, photo_etu, date_ajout) VALUES(?,?,?,?,?,?,?,?)";
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstm.setString(1, obj.getEtudiant().getNomEtu());
			pstm.setString(2, obj.getEtudiant().getPrenomEtu());
			pstm.setString(3, obj.getEtudiant().getSexeEtu());
			pstm.setString(4, obj.getEtudiant().getEmailEtu());
			pstm.setInt(5, obj.getEtudiant().getTelEtu());
			pstm.setString(6, obj.getEtudiant().getDateNaissEtu().toString());
			pstm.setBinaryStream(7, obj.getEtudiant().getPhotoEtu());
			pstm.setString(8, obj.getEtudiant().getDateAjout().toString());
			pstm.executeUpdate();
			try(ResultSet r = pstm.getGeneratedKeys();) {
				if(r.next()) {
					int idEtu =  r.getInt(1);
					sql = "INSERT INTO inscriptions(prix_insc, date_insc, id_forma, id_session, id_etu) VALUES(?,?,?,?,?)";
					try(PreparedStatement p1 = connection.prepareStatement(sql)) {
						p1.setDouble(1, obj.getPrixInsc());
						p1.setString(2, obj.getDateInsc().toString());
						p1.setInt(3, obj.getFormation().getIdFormation());
						p1.setInt(4, obj.getSession().getIdSession());
						p1.setInt(5, idEtu);
						return p1.executeUpdate() > 0;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		}
		return false;
	}

	@Override
	public boolean Delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
