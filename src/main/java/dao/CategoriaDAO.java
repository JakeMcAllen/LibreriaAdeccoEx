package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import entity.Categoria;

@Repository("cd")
public class CategoriaDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager em;
		
	
	public void insert(Categoria ct) throws Exception { em.persist(ct); }
	
	public void delete(String ct) throws Exception { em.remove(ct); }
	
}
