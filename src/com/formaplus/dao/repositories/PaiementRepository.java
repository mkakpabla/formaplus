package com.formaplus.dao.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.formaplus.dao.DbConnector;
import com.formaplus.dao.models.Etudiant;
import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Inscription;
import com.formaplus.dao.models.Paiement;
import com.formaplus.dao.models.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Michel
 *
 */
public class PaiementRepository {
	
	
	
	public ObservableList<Paiement> getAll() {
		ObservableList<Paiement> paiements = FXCollections.observableArrayList();
		try(ResultSet rset = DbConnector.executeQuery("SELECT * from full_inscriptions f, paiements p WHere f.id_insc = p.id_insc")) {
			while(rset.next()) {
				Paiement pay = new Paiement();
				pay.setDatePay(rset.getDate("date_pay").toLocalDate());
				pay.setMontantPay(rset.getDouble("montant_pay"));
				pay.setIdPay(rset.getInt("id_pay"));
				
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
				pay.setEtudiant(etudiant);
				
				
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
				insc.setPrixInsc(rset.getDouble("prix_insc"));
				
				
				Session session = new Session();
				session.setIdSession(rset.getInt("id_session"));
				session.setLibSession(rset.getString("lib_session"));
				session.setDateDebut(rset.getDate("date_debut").toLocalDate());
				session.setDateFin(rset.getDate("date_fin").toLocalDate());
				insc.setSession(session); 
				pay.setInscription(insc);
				paiements.add(pay);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paiements;
	}
	
	public int insert(Paiement pay) {
		int idPay = 0;
		try(PreparedStatement ps = DbConnector.getConnection().prepareStatement("INSERT INTO paiements(montant_pay, date_pay, id_insc) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
			ps.setDouble(1, pay.getMontantPay());
			ps.setString(2, pay.getDatePay().toString());
			ps.setInt(3, pay.getInscription().getIdInsc());
			if(ps.executeUpdate() > 0) {
				ResultSet rset = ps.getGeneratedKeys();
				if(rset.next()) idPay = rset.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idPay;
	}

	
	public boolean delete(int idPay) {
		try {
			return DbConnector.executeUpdate("DELETE FROM paiements WHERE id_pay = ?", idPay);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
