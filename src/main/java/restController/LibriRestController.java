package restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AutoreDTO;
import dto.LibroDTO;
import dto.MagazzinoDTO;
import entity.Autore;
import entity.Libro;
import entity.Magazzino;
import services.LibroService;

@RestController
@RequestMapping(path = "/books")
public class LibriRestController {
	
	@Autowired
	private LibroService ls;
	
	
	
	
	
	/**
	 * Url: 	http://localhost:8080/Libreria/libreria/books/
	 * Metodo: 	Post
	 * 
	 * Ex input xml:
	 * 
	 * <Libro>
     * 		<categoria>Azione</categoria>
     * 		<prezzo>30.0</prezzo>
     * 		<n_copie>70</n_copie>
     * 		<id_autore>4</id_autore>
     * 		<titolo>James Bonnd</titolo>
     *  	<descrizione>Spionaggio e azione</descrizione>
	 *  </Libro>
	 * 
	 * 
	 * Ex input Json:
	 * 
	 * {
	 *	    "titolo": "In fondo al mar",
	 *	    "categoria": "Fantasy",
	 *	    "prezzo": 40.0,
	 *	    "nCopie": 40,
	 *	    "id_autore": 9,
     *		"descrizione": "Per bambini"
	 *	}
	 * 
	 * 
	 * 
	 * 
	 * @param l
	 * @return 	HttpStatus
	 */
	@PostMapping(path="/", consumes = {"application/xml", "application/json"} )
	public ResponseEntity<Object> registraLibro(@RequestBody LibroDTO l) {
				
		try {
			ls.registraNuovoLibro(l);

			return new ResponseEntity<Object> (HttpStatus.CREATED);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
						
			return new ResponseEntity<Object> (headers, HttpStatus.CONFLICT);
		}
		
	}

	@PutMapping(path = "/{isbn}", consumes = {"application/xml", "application/json"} )
	public ResponseEntity<Object> modificaLibro(@PathVariable int isbn, @RequestBody LibroDTO l) {
		
		
		try {
			l.setIsbn(isbn);
			
			ls.modificaDatiLibro(l);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
			
			System.out.println("e: " + e );
			
			ResponseEntity<Object> rs = new ResponseEntity<Object>(headers, HttpStatus.NOT_FOUND);
			return rs;
		}
		
	}
	
	
	@DeleteMapping(path = "/{isbn}")
	public ResponseEntity<Object> removeLibro(@PathVariable int isbn) {
				
		try {
			ls.eliminaLibro(isbn);
			return new ResponseEntity<Object> (HttpStatus.OK);
			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
						
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@GetMapping(path = "/{isbn}",  produces = {"application/xml", "application/json"})
	public ResponseEntity<LibroDTO> getLibroByIsbn(@PathVariable int isbn) {
				
		try {
			Libro l = ls.leggiDatiLibro(isbn);
			
			LibroDTO lDTO = new LibroDTO();
			lDTO.setIsbn( l.getIsbn() );
			lDTO.setTitolo( l.getTitolo() );
			lDTO.setDescrizione( l.getDescrizione() );
			lDTO.setCategoria( l.getCategoria().getCategoria() );
			lDTO.setPrezzo( l.getPrezzo() );
			lDTO.setnCopie( l.getnCopie() );
			lDTO.setIdAutore( l.getAutore().getId() );
			
			
			return new ResponseEntity<LibroDTO>( lDTO, HttpStatus.OK);
			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
			
			System.out.println(e);
						
			ResponseEntity<LibroDTO> rs = new ResponseEntity<LibroDTO>(headers, HttpStatus.NOT_FOUND);
			return rs;
			
		}
	}
	
	
	@GetMapping(path = "/{country}/stat", produces = {"Application/xml", "Application/json"}) 
	public ResponseEntity<Integer> getMapNumeroLibriPerCountry(@PathVariable String country) {
				
		try {
			Map<String, Integer> lstC = ls.statistichePerNazione();
						
			int valR = 0;		
			for ( Entry<String, Integer> entry : lstC.entrySet() )				
				if (entry.getKey().toString().equals(country)) valR = entry.getValue();
			
			return new ResponseEntity<Integer> ( valR, HttpStatus.OK);			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
						
			return new ResponseEntity< Integer> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@GetMapping(path = "/{isbn}/author", produces = {"Application/xml", "Application/json"}) 
	public ResponseEntity<AutoreDTO> getListLibriByAutor(@PathVariable int isbn ) {
		
		try {
			Autore a = ls.leggiDatiLibro(isbn).getAutore();
			
			AutoreDTO aDTO = new AutoreDTO();
			aDTO.setId( a.getId() );
			aDTO.setNome( a.getNome() );
			aDTO.setCognome( a.getCognome() );
			aDTO.setNazioneResidenza( a.getNazioneResidenza() );

			
			return new ResponseEntity<AutoreDTO> (aDTO, HttpStatus.OK);
			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
						
			return new ResponseEntity<AutoreDTO> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	/**
	 * 
	 * ex input xml :
	 * 
	 *  <val> 860 </val>
	 *  
	 *  
	 * ex input json:
	 * 
	 * { "val": 500 }
	 * 
	 * 
	 */
	@PostMapping( path = "/{isbn}/warehouse", consumes = {"Application/xml", "Application/json"} )
	public ResponseEntity<Object> approvigionaLibro(@PathVariable int isbn, @RequestBody Map<String, Integer> val) {

		int num = val.entrySet().stream().findFirst().get().getValue();
		
		try {
			ls.approvigionaLibro(isbn, num );
			return new ResponseEntity<Object> ( HttpStatus.OK );
			
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
			
			System.err.println("e: " + e);
						
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	
	@PutMapping(path = "/{isbn}/warehouse", consumes = {"Application/xml", "Application/json"} )
	public ResponseEntity<Object> evolviApprovigionamento(@PathVariable int isbn, @RequestBody String nStato) {
		
		try {
			 ls.cambiaStatoRichiesta(isbn, nStato);
			 
			return new ResponseEntity<Object> ( HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
			
			System.out.println(e);
			
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@DeleteMapping(path = "/{isbn}/warehouse" )
	public ResponseEntity<Object> eliminaApprovigionamento(@PathVariable int isbn) {
		
		try {
			 ls.chiudiRichiesta(isbn);
			 
			return new ResponseEntity<Object> ( HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
			
			System.out.println(e);
			
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	
	@GetMapping(path = "/{isbn}/warehouse")
	public ResponseEntity<List<MagazzinoDTO>> getApprovigionamentiLibri(@PathVariable int isbn) {
		
		try {
			 List<Magazzino> lst = ls.leggiInfoStock(isbn);
			 List<MagazzinoDTO> lstMDTO = new ArrayList<MagazzinoDTO>();
			 
			 for (Magazzino m : lst) {
				 MagazzinoDTO mDTO = new MagazzinoDTO();
				 mDTO.setId(m.getId());
				 mDTO.setQuantita(m.getQuantità());
				 mDTO.setStatoStock(m.getStatoStock());
				 mDTO.setIsbn(m.getIsbn().getIsbn());
				 
				 lstMDTO.add(mDTO);
			}
			 
			return new ResponseEntity<List<MagazzinoDTO>> (lstMDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
			
			System.out.println(e);
			
			return new ResponseEntity<List<MagazzinoDTO>> (headers, HttpStatus.NOT_FOUND);
		}
		
	}
		
}
