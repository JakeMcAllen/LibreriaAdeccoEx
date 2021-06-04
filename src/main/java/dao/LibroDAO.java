package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import entity.Libro;

@Component("ld")
public class LibroDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
	
	
	public void insert(Libro l) { em.merge(l); }
	
	public void update(Libro l) { em.merge(l); }
	
	public void delete(int isbn) { 
		Query lbQuery = em.createQuery("DELETE FROM Libro l WHERE l.isbn = ?1"); 
		lbQuery.setParameter(1, isbn);
		lbQuery.executeUpdate();
	}
	
	
	public List<Libro> selectForCategory(String genere) {
		TypedQuery<Libro> lbQuery = em.createQuery("SELECT l FROM Libro l WHERE l.categoria = ?1 ", Libro.class);
		lbQuery.setParameter(1, genere);

		return lbQuery.getResultList();
	}
	
	public List<Libro> selectForAuthor(int idAutore) {
		
		
		TypedQuery<Libro> lbQuery = em.createQuery("SELECT l FROM Autore a JOIN a.lB l WHERE l.autore.id = ?1", Libro.class);
		lbQuery.setParameter(1, idAutore);
		
		List<Libro> ll = lbQuery.getResultList();
		
		return ll;
	}
	
	
	public Libro selectForIsbn(int isbn) { return em.find(Libro.class, isbn); }
	
	
	public List<Libro> selectAllPage(int offset, int page) {
				
		List<Libro> ll = em.createQuery("SELECT l FROM libri l", Libro.class).getResultList();	
		
		List<Libro> ll2 = new ArrayList<Libro> ();
		for (int i = 0 + offset*page; i < offset + offset*page ; i++) { ll2.add(ll.get(i)); }
		
		return ll2;
	}
	
	public Map<String, Integer> selectNBooksForCountry() {
				
		Query lbQuery = em.createQuery("SELECT a.nr, count(*) as num FROM Libro l "
				+ "JOIN Autore a ON (a.id = l.autore.id) "
				+ "GROUP BY a.nr");

		Map<String, Integer> ll = new HashMap<String, Integer>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> lst = lbQuery.getResultList();
		for (Object[] obj : lst) { ll.put(obj[0].toString(), Integer.parseInt( obj[1].toString() ) ); }
		
		return ll;
	}
	
	public List<Libro> getAllLibri() {
		return em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
	}
	
}