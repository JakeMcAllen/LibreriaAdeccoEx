package services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AutoreDAO;
import dao.LibroDAO;
import dao.MagazzinoDAO;
import dao.MagazzinoSt;
import dto.LibroDTO;
import entity.Categoria;
import entity.Libro;
import entity.Magazzino;

@Service
public class LibroService {

	@Autowired
	private LibroDAO ld;
	
	@Autowired
	private MagazzinoDAO md;
	
	@Autowired
	private AutoreDAO ad;
	
	
	/**
	 * Registra un nuovo libro sul database
	 * Se esiste già un libro con gli stessi dati allora viene lanciata una "RuntimeExcepption"
	 * 
	 * 
	 * @param nLb (LibroDTO)		Libro da aggiungere
	 */
	@Transactional
	@Transient
	public void registraNuovoLibro(LibroDTO nLb) { 
		
		Libro lb = new Libro();
		lb.setTitolo( nLb.getTitolo() );
		lb.setDescrizione( nLb.getDescrizione() );
		lb.setCategoria( new Categoria( nLb.getCategoria() ) );
		lb.setnCopie( nLb.getnCopie() );
		lb.setAutore( ad.selectById( nLb.getIdAutore() ) );
		lb.setPrezzo( nLb.getPrezzo() );
		
		
		
		if (ad.selectById( lb.getAutore().getId() ) == null ) throw new RuntimeException("Autore inesistente");
		if (lb.getAutore().getId() == 0) throw new RuntimeException("Specificare Autore");		
		
		
		if (lb.getCategoria().getCategoria().isBlank() 
			|| lb.getCategoria().getCategoria().isEmpty()
				) 
			throw new RuntimeException("Specificare la categoria");
				
		for (Libro l: ld.getAllLibri()) {
			if (l.getTitolo().equals(lb.getTitolo()) 
				&& l.getDescrizione().equals(lb.getDescrizione())
				&& l.getCategoria().getCategoria().equals(lb.getCategoria().getCategoria())
				&& l.getAutore() == lb.getAutore()
					) throw new RuntimeException("Libro già presente");
		}
				
		ld.insert(lb); 
		md.insert( new Magazzino(50, MagazzinoSt.RICHIESTO.getSt(), ld.selectForIsbn(lb.getIsbn()) ) );
		
	}
	
	/**
	 * Modifica i dati di un libro già esistente sul database
	 * 
	 * 
	 * @param nLb (LibroDTO)		Dati del libro da modificare
	 */
	@Transactional
	public void modificaDatiLibro(LibroDTO nLb) { 
	
		Libro lb = ld.selectForIsbn( nLb.getIsbn() );
		lb.setTitolo( nLb.getTitolo() );
		lb.setDescrizione( nLb.getDescrizione() );
		lb.setCategoria( new Categoria( nLb.getCategoria() ) );
		lb.setnCopie( nLb.getnCopie() );
		lb.setAutore( ad.selectById( nLb.getIdAutore() ) );
		lb.setPrezzo( nLb.getPrezzo() );
		
		ld.update(lb); 
		if (lb.getnCopie() < 10) md.insert( new Magazzino(50, MagazzinoSt.RICHIESTO.getSt(), lb ));
		
	}
	
	/**
	 * Libro da eliminare dal database 
	 * 
	 * @param isbn (int)			Id del libro da eliminare
	 */
	@Transactional
	public void eliminaLibro(int isbn) { 
	
		for ( Magazzino m: md.richiesteLibro(isbn) ) {
			if ( m.getStatoStock() == MagazzinoSt.PROCESSATO.getSt() ) {
				throw new RuntimeException("Ordine Processato in magazzino");
			}
		}
				
		ld.delete(isbn); 
	}
	
	/**
	 * Restituisce la lista di tutti i libri di un autore conoscendo il suo id
	 * 
	 * @param idAutore: int 		id dell'autore
	 * @return: List<Libro>			Lista dei libri di untore
	 */
	public List<Libro> selezionaLibriPerAutore (int idAutore) { return ld.selectForAuthor(idAutore); }
	
	/**
	 * Restituice tutti i dati di un libro 
	 * 
	 * @param isbn (int)				Id del libro
	 * @return (libro)				Dati del libro selezionato
	 */
	public Libro leggiDatiLibro(int isbn) { return ld.selectForIsbn(isbn); }
	
	/**
	 * Restituisce i libri del database per una pagina.
	 * I due parametri rispettivamente specificano il nunmero di libri per pagina e la pagina raggiunta.
	 * In questo modo non viene restituita tutta la lista dei libri, ma solo una loro parte.
	 * Questo metodo serve per visualizzare i prodotti su una pagina web. In ogni pagina c'è una parte dei libri.
	 * 
	 * 
	 * @param offset (int)			Numero di libri per pagina. Inizia da zero
	 * @param page (nt)				Numero di pagina raggiunta
	 * @return (List<Libro>) 		Lista dei libri
	 */
	public List<Libro> selezioneTuttiLibri(int offset, int page) { return ld.selectAllPage(offset, page-1); }
	
	/**
	 * Restituisce una lista di tuple. 
	 * Il primo valore della tupla specifica una nazione, mentre la seconda il numero di libri il cui autore è nato in quella nazione
	 * 
	 * 
	 * @return (Map<String, Integer>)	Mappa paese, numero libri
	 */
	public Map<String, Integer> statistichePerNazione() { return ld.selectNBooksForCountry(); }
	
	/**
	 * Richiede che un libro riceva un approvigionamento.
	 * Se esiste una richiesta in corso ( stato = richiesto ), se necessario richiede la quantità di copie richieste per arrivare al numero desiderato
	 * Se esiste una richiesta in corso ( stato = processato ), non sarà possibile fare ulteriori richieste di approvigionamento.
	 * Se esiste già una richiesta in corso  ( stato = disponibile ), non sarà possibile modificare la richiesta in corso, ma sarà possibile fare una nuova richeista di approvigionamento della quantità residura
	 * 
	 * 
	 * @param isbn (int)	Id del libro
	 * @param nCopie (int)	 Numero di copie del libro da ordinare
	 */
	@Transactional
	public void approvigionaLibro(int isbn, int nCopie) {
		List<Magazzino> lst = md.richiesteLibro(isbn);
		boolean addAp = true;
		
		for (Magazzino mgz : lst) {
			
			if ( mgz.getIsbn().getIsbn() == isbn ) {
				
				
				if (mgz.getStatoStock().toString().equals( MagazzinoSt.RICHIESTO.getSt()) ) {
					
					if (mgz.getQuantità() < nCopie) {
						mgz.setQuantità(nCopie);
						md.update(mgz);
					}
					addAp = false;
					break;
				} else if ( mgz.getStatoStock().toString().equals( MagazzinoSt.PROCESSATO.getSt() ) ) { throw new RuntimeException("Ordine in processo");
				} else if ( mgz.getStatoStock().toString().equals( MagazzinoSt.DISPONIBILE.getSt() ) ) {
					int nCopieResidue = mgz.getQuantità() - nCopie;
					
					Magazzino newMagazzino = new Magazzino(nCopieResidue, MagazzinoSt.DISPONIBILE.getSt(), ld.selectForIsbn(isbn) );
					newMagazzino.setId(mgz.getId());
							
					if(nCopieResidue > 0) md.update( newMagazzino );
					addAp = false;
					break;
				}
			}
		}
		
		
		if (addAp)
			md.insert( new Magazzino(nCopie, MagazzinoSt.RICHIESTO.getSt(), this.leggiDatiLibro(isbn) ) );
		
	}
	
	/**
	 * Cambia lo stato di un approvigionamento
	 * Il cambio di stato può avvenire solo da "richiesto" a "processato" oppure da "processato" a "disponibile"
	 * 
	 * Se una transazione cambia stato e in quello stato esiste un approvigiomanto per lo stesso libro queste sono accorpate
	 * 
	 * @param isbn (int)			Id del libro
	 * @param stato (String)		Nuovo stato del libro
	 */
	@Transactional
	public void cambiaStatoRichiesta(int isbn, String stato) {
		List<Magazzino> lst = md.richiesteLibro(isbn);
		
		for (Magazzino mgz : lst) {
			if (mgz.getStatoStock().toString().equals( MagazzinoSt.RICHIESTO.getSt() ) && stato.equals( MagazzinoSt.PROCESSATO.getSt() ) ) {
				mgz.setStatoStock(MagazzinoSt.PROCESSATO.getSt() );
				md.update(mgz);
				break;
			} else if ( mgz.getStatoStock().toString().equals( MagazzinoSt.PROCESSATO.getSt() ) && stato.equals( MagazzinoSt.DISPONIBILE.getSt() ) ) {
				mgz.setStatoStock( MagazzinoSt.DISPONIBILE.getSt() );
				md.update(mgz);
				break;
			}
		}
		
		
		// unisco le entità doppie
		List<Magazzino> lst1 = md.richiesteLibro(isbn);
		
		lst1.removeIf(t -> {
			for (Magazzino m : lst1) {
				if (m.getIsbn() == t.getIsbn() && m.getId() != t.getId() ) {
					
					m.setQuantità( m.getQuantità() + t.getQuantità() );
					md.update(m);
					return true;
				}
			}
			
			return false;
		});
	}
	
	/**
	 * Restituisce una lista di tutti gli approvigionamenti di un libro
	 * 
	 * @param isbn (int) 			id del libro
	 * @return (list<Magazzino>) 	Lista di tutti gli approvigionamenti
	 */
	public List<Magazzino> leggiInfoStock(int isbn) { return md.richiesteLibro(isbn); }
	
	
	/**
	 * Elinima tutte le richieste di approvigionamento di un libro
	 * 
	 * @param isbn (int) 			id del libro
	 */
	@Transactional
	public void chiudiRichiesta(int isbn) {
		List<Magazzino> lst = md.richiesteLibro(isbn);

		for (Magazzino m : lst) {
			if (m.getStatoStock().toString().equals( MagazzinoSt.DISPONIBILE.getSt() )) {
				
				Libro book = ld.selectForIsbn(isbn);
				book.setnCopie(book.getnCopie() + m.getQuantità());
				md.remove(m.getId());
				ld.update(book);
				
				return;
			}
		}
		
		throw new RuntimeException("Non è stato possibile chiudere nessun approvigionamento");
	}
	
	
}
