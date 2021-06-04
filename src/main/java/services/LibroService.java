package services;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AutoreDAO;
import dao.LibroDAO;
import dao.MagazzinoDAO;
import dao.MagazzinoSt;
import dto.LibroDTO;
import entity.Autore;
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
		
		System.out.println("adde: " + lb);
		
		ld.insert(lb); 
		
		System.out.println("Magazzinno: " + lb.getIsbn());
		
		md.insert( new Magazzino(50, MagazzinoSt.RICHIESTO.getSt(), ld.selectForIsbn(lb.getIsbn()) ) );
		
	}
	
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
	
	@Transactional
	public void eliminaLibro(int isbn) { 
	
		for ( Magazzino m: md.richiesteLibro(isbn) ) {
			if ( m.getStatoStock() == MagazzinoSt.PROCESSATO.getSt() ) {
				throw new RuntimeException("Ordine Processato in magazzino");
			}
		}
				
		ld.delete(isbn); 
	}
	
	public List<Libro> selezionaLibriPerAutore (int idAutore) { return ld.selectForAuthor(idAutore); }
	
	@Transactional
	public Libro leggiDatiLibro(int isbn) { return ld.selectForIsbn(isbn); }
	
	public List<Libro> selezioneTuttiLibri(int offset, int page) { return ld.selectAllPage(offset, page-1); }
	
	public Map<String, Integer> statistichePerNazione() { System.out.println("enter"); return ld.selectNBooksForCountry(); }
	
	
	@Transactional
	public void approvigionaLibro(int isbn, int nCopie) {
		List<Magazzino> lst = md.richiesteLibro(isbn);
		boolean addAp = true;
		
		for (Magazzino mgz : lst) {
			
			if ( mgz.getIsbn().getIsbn() == isbn ) {
				
				
				if (mgz.getStatoStock().toString().equals( MagazzinoSt.RICHIESTO.getSt()) ) {
					System.out.println("(mgz.getQuantità() < nCopie): " + (mgz.getQuantità() < nCopie) );
					
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
		
		
		// se devo crearne una nuova
//		if (addAp)
//			md.insert( new Magazzino(nCopie, MagazzinoSt.RICHIESTO.getSt(), this.leggiDatiLibro(isbn) ) );
		
	}
	
	@Transactional
	public void cambiaStatoRichiesta(int isbn, String stato) {
		List<Magazzino> lst = md.richiesteLibro(isbn);
		
		for (Magazzino mgz : lst) {
			if (mgz.getStatoStock().toString().equals( MagazzinoSt.RICHIESTO.getSt() ) && stato.equals( MagazzinoSt.RICHIESTO.getSt() ) ) {
				mgz.setStatoStock(MagazzinoSt.PROCESSATO.getSt() );
				md.update(mgz);
				break;
			} else if ( mgz.getStatoStock().toString().equals( MagazzinoSt.PROCESSATO.getSt() ) && stato.equals( MagazzinoSt.PROCESSATO.getSt() ) ) {
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
	
	
	public List<Magazzino> leggiInfoStock(int isbn) { return md.richiesteLibro(isbn); }
	
	
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
