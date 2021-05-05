package com.formaplus.dao.repositories;

public class RepositoryFactory {
	
	
	
	private static EtudiantRepository etudiantRepository = null;
	private static PaiementRepository paiementRepository = null;
	private static FormationRepository formationRepository = null;
	private static SessionRepository sessionRepository = null;
	
	
	public static EtudiantRepository getEtudiantRepository()
	{
		if(etudiantRepository == null) {
			etudiantRepository = new EtudiantRepository();
		}
		return etudiantRepository;
	}
	
	public static PaiementRepository getPaiementRepository()
	{
		if(paiementRepository == null) {
			paiementRepository = new PaiementRepository();
		}
		return paiementRepository;
	}
	
	public static FormationRepository getFormationRepository() {
		if(formationRepository == null) {
			formationRepository = new FormationRepository();
		}
		return formationRepository;
	}
	
	public static SessionRepository getSessionRepository() {
		if(sessionRepository == null) {
			sessionRepository = new SessionRepository();
		}
		return sessionRepository;
	}

}
