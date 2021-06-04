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
	
	/**
	 * Registra un nuovo utente sul  database.
	 * L'utente non deve già esistere. Efettua un controllo su tutti i parametri.
	 * 
	 * @param a (Autore) 		Auotore che viene aggiunto alla lista
	 */
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
	
	/**
	 * Modifica i dati di un autore nel database
	 * 
	 * @param a (Autore) 		Autore i cui dati vengono modificati 
	 */
	@Transactional
	public void modificaDatiAnagraficiAutore(Autore a) { ad.update(a); }
	
	/**
	 * Elimina i dati di un autore attraverso il suo id
	 * 
	 * @param id (int)			L'id dell'autore da eliminare
	 */
	@Transactional
	public void eliminaAutore(int id) { 
		
		try { selezioneAutorePerLibro(id); } 
		catch (Exception e) { throw new RuntimeException("Non si può eliminare un autore con libri registrati nel sistema "); }
		
		ad.delete(id);
	}
	
	/**
	 * Seleziona l'autore di attraverso l'id di un libro
	 * 
	 * @param isbn (int)			L'id del libro
	 * @return (Auotre) 			L'autore o null
	 */
	public Autore selezioneAutorePerLibro(int isbn) { 
		try { return ad.selectByIsbn(isbn); } 
		catch (Exception e) { return null; }
	}
	
	/**
	 * Leggi tutti i dati di un autore
	 * 
	 * @param id (int) 			Dati dell'autore
	 * @return (Autore)			L'autore o null
	 */
	public Autore leggiDatiAutore(int id) { 
		try { return ad.selectById(id); } 
		catch (Exception e) { return null; }
	}
	
	/**
	 * Restituisce una lista di tutti gli autori registrati nel database
	 * 
	 * @return (List<Autore>)	Lista degli autori
	 */
	public List<Autore> leggiAutori() { return ad.getAllAutori(); }
	
	
}
