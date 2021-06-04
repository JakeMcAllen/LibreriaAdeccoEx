package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CategoriaDAO;
import entity.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO cd;
	
	/**
	 * Registra una nuova categiria sul database
	 * 
	 * @param categoria (String)			Nome della categoria
	 */
	public void registraCategoria(String categoria) { cd.insert( new Categoria(categoria) ); }
	
	/**
	 * Elimina una categoria dal database
	 * 
	 * @param categoria (String)			Nome della categoria
	 */
	public void eliminaCategoria(String categoria) { cd.delete(categoria); }
		
}
