package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import entity.Categoria;

@Repository("cd")
public class CategoriaDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
		
	
	public void insert(Categoria ct) { em.persist(ct); }
	
	public void delete(String ct) { em.remove(ct); }
	
}
