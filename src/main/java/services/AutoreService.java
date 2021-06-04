package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AutoreDAO;
import entity.Autore;

@Service
public class AutoreService {

	@Autowired
	private AutoreDAO ad;
	
	
	@Transactional
	public void registraAutore(Autore a) { 
		
		for (Autore at : ad.getAllAutori()) 
			if (at.getNome().equals(a.getNome()) 
				&& at.getCognome().equals(a.getCognome()) 
				&& at.getNazioneResidenza().equals(a.getNazioneResidenza())
				) return;
		
		System.out.println(a);
		
		ad.insert(a); 
		
	}
	
	@Transactional
	public void modificaDatiAnagraficiAutore(Autore a) { ad.update(a); }
	
	@Transactional
	public void eliminaAutore(int id) { 
		
		try { selezioneAutorePerLibro(id); } 
		catch (Exception e) { throw new RuntimeException("Non si può eliminare un autore con libri registrati nel sistema "); }
		
		ad.delete(id);
	}
	
	public Autore selezioneAutorePerLibro(int isbn) { 
		try { return ad.selectByIsbn(isbn); } 
		catch (Exception e) { return null; }
	}
	
	public Autore leggiDatiAutore(int id) { 
		try { return ad.selectById(id); } 
		catch (Exception e) { return null; }
	}
	
	public List<Autore> leggiAutori() { return ad.getAllAutori(); }
	
	
}
