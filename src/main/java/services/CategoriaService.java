package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CategoriaDAO;
import entity.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO cd;
	
	public void registraCategoria(String categoria) throws Exception { cd.insert( new Categoria(categoria) ); }
	
	public void eliminaCategoria(String categoria) throws Exception { cd.delete(categoria); }
		
}
