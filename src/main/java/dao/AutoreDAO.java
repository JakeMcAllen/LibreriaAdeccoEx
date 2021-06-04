package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import entity.Autore;
import entity.Libro;

@Component("ad")
public class AutoreDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
	
	
	public void insert(Autore a) { em.persist(a); }
	
	public Autore selectById(int id) { return em.find(Autore.class, id); }
	
	public Autore selectByIsbn(int isbn) { return em.find(Libro.class, isbn).getAutore(); }
	
	public void update(Autore a) { em.merge(a); }
	
	public void delete(int id) { 
		Query qr = em.createQuery("DELETE FROM Autore a WHERE a.id = ?1");
		qr.setParameter(1, id);
		qr.executeUpdate(); 
	}
	
	
	public List<Autore> getAllAutori() {
		
		return em.createQuery("SELECT a FROM Autore a", Autore.class).getResultList();		
		
	}
	
	
}
