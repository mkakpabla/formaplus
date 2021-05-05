package com.formaplus.dao.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.formaplus.dao.models.Formation;
import com.formaplus.dao.models.Session;
import com.formaplus.utils.Db;

public class SessionRepository implements IRepository<Session> {
	
	
private Connection connection;
	
	
	public SessionRepository() {
		connection = Db.getConnection();
	}

	@Override
	public List<Session> GetAll() {
		try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessions")) {
			ResultSet result = ps.executeQuery();
			List<Session> sessions = new ArrayList<Session>();
			while (result.next()) {
				Session s = new Session();
				s.setIdSession(result.getInt("id_session"));
				s.setLibSession(result.getString("lib_session"));
				s.setDateDebut(result.getDate("date_debut").toLocalDate());
				s.setDateFin(result.getDate("date_fin").toLocalDate());
				s.setFormations(new FormationRepository().GetAllWhereSessionId(result.getInt("id_session")));
				sessions.add(s);
			}
			return sessions;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Session GetById(int id) {
		
		return null;
	}
	
	
	public boolean RemoveFormation(int idSession, int idFormation) {
		try(PreparedStatement pstm = connection.prepareStatement("DELETE FROM formation_session WHERE id_forma = ? AND id_session = ?")) {
			pstm.setInt(1, idFormation);
			pstm.setInt(2, idSession);
			return pstm.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		}
	}

	@Override
	public boolean Save(Session obj) {
		try(PreparedStatement pstm = connection.prepareStatement("INSERT INTO sessions(lib_session, date_debut, date_fin) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, obj.getLibSession());
			pstm.setString(2, obj.getDateDebut().toString());
			pstm.setString(3, obj.getDateFin().toString());
			pstm.executeUpdate();
			ResultSet r = pstm.getGeneratedKeys();
			if(r.next()) {
				int id = r.getInt(1);
				try(PreparedStatement ps2 = connection.prepareStatement("INSERT INTO formation_session(id_forma, id_session) VALUES(?,?)")) {
					for(Formation forma: obj.getFormations()) {
						ps2.setInt(1, forma.getIdFormation());
						ps2.setInt(2, id);
						ps2.addBatch();
					}
					ps2.executeBatch();
					return true;
				}
			}
			
		} catch (SQLException e) {
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
