package dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import entity.LogWebOperation;

@Component("lgd")
public class LogDAO {
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
		
	
	public void Insert(LogWebOperation i) { em.persist(i); }
	
	public List<LogWebOperation> select(LocalDateTime start) {
		
		TypedQuery<LogWebOperation> dtLWO = em.createQuery("SELECT lwo FROM LogWebOperation lwo WHERE lwo.dataOra= ?", LogWebOperation.class);
		dtLWO.setParameter(0, start);
		
		List<LogWebOperation> lstLWO = dtLWO.getResultList();
		return lstLWO;
		
	}
	
	public List<LogWebOperation> select(String status) {
		
		TypedQuery<LogWebOperation> dtLWO = em.createQuery("SELECT lwo FROM LogWebOperation lwo WHERE lwo.URLRequest=" + status, LogWebOperation.class);
		List<LogWebOperation> lstLWO = dtLWO.getResultList();
		return lstLWO;
		
	}

	public List<LogWebOperation> select(LocalDateTime start, String status) {
		
		TypedQuery<LogWebOperation> dtLWO = em.createQuery("SELECT lwo FROM LogWebOperation lwo WHERE lwo.dataOra=" + start + " lwo.URLRequest=" + status, LogWebOperation.class);
		List<LogWebOperation> lstLWO = dtLWO.getResultList();
		return lstLWO;
		
	}
	
	public List<LogWebOperation> selectAll() {		
		return em.createQuery("SELECT lwo FROM LogWebOperation lwo", LogWebOperation.class).getResultList();
	}
	
}
