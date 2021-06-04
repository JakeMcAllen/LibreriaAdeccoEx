package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import entity.Autore;
import entity.Magazzino;

@Component("md")
public class MagazzinoDAO {
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
	
	
	public List<Magazzino> richiesteLibro(int isbn) {
		
		TypedQuery<Magazzino> qr = em.createQuery("SELECT m FROM Magazzino m WHERE m.isbn.isbn = ?1", Magazzino.class);
		qr.setParameter(1, isbn);
		List<Magazzino> lst = qr.getResultList();
		return lst;
		
	}
	
	public List<Magazzino> getAllMagazzino() { return em.createQuery("SELECT m FROM Magazzino m", Magazzino.class).getResultList(); } 
	
	public void insert(Magazzino m) { em.merge(m); }

	public void update(Magazzino m) { em.merge(m); }
	
	public void remove(int id) { 
		Query qr = em.createQuery("DELETE FROM Magazzino m WHERE m.id= ?1");
		qr.setParameter(1, id);
		
		qr.executeUpdate(); 
	}
	
	
	
}
