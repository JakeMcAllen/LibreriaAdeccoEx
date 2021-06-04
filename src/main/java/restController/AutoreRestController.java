package restController;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import dto.AutoreDTO;
import dto.LibroDTO;
import entity.Autore;
import entity.Libro;
import services.AutoreService;
import services.LibroService;


@RestController
@RequestMapping(path = "/utenti")
public class AutoreRestController {

	@Autowired
	private AutoreService as;
	
	@Autowired
	private LibroService ls;
	
	
	/**
	 * 
	 * Url: 	http://localhost:8080/Libreria/libreria/utenti
	 * Metodo:	POST
	 * 
	 * 
	 * Ex input xml:
	 * 
	 *  <AutoreDTO>
     * 		<nome>Pippo</nome>
     * 		<cognome>Baudo</cognome>
     * 		<nazione_residenza>Italia</nazione_residenza>
	 * </AutoreDTO>
	 * 
	 * 
	 * 
	 * ex input json:
	 * 
	 * {
     * 		"nome": "Pippo",
     * 		"cognome": "Baudo",
     * 		"nazione_residenza": "Italia"
	 * }
	 * 
	 * 
	 * 
	 * 
	 * @param a
	 * @return	HttpStatus
	 */
	@PostMapping(consumes = {"Application/xml", "Application/json"})
	public ResponseEntity<Object> registraUtente(@RequestBody AutoreDTO a) {
		
		try {
			
			Autore aOg = new Autore();
			
			if (a.getNome() != null) aOg.setNome( a.getNome() );
			if (a.getCognome() != null) aOg.setCognome( a.getCognome() );
			if (a.getNazioneResidenza() != null) aOg.setNazioneResidenza( a.getNazioneResidenza() );
			
			
			as.registraAutore( aOg );
			
			return new ResponseEntity<Object> (HttpStatus.CREATED);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
			
			ResponseEntity<Object> rs = new ResponseEntity<Object> (headers, HttpStatus.BAD_REQUEST);
			return rs;
		}
	}
	
	
	@PutMapping(path = "/{id}", consumes = {"Application/xml", "Application/json"})
	public ResponseEntity<Object> modificaUtente(@PathVariable int id, @RequestBody AutoreDTO a) {
		try {
			
			Autore aOg = as.leggiDatiAutore(id);
			if (a.getNome() != null) aOg.setNome( a.getNome() );
			if (a.getCognome() != null) aOg.setCognome( a.getCognome() );
			if (a.getNazioneResidenza() != null) aOg.setNazioneResidenza( a.getNazioneResidenza() );
			
			as.modificaDatiAnagraficiAutore(aOg);
			
			return new ResponseEntity<Object> (HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
			
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUtente(@PathVariable int id) {
		try {
			as.eliminaAutore(id);
			
			return new ResponseEntity<Object> (HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
						
			return new ResponseEntity<Object> (headers, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(path = "/{id}", produces= {"Application/xml", "Application/json"})
	public ResponseEntity<AutoreDTO> getAutore(@PathVariable int id) {
		try {
			Autore a = as.leggiDatiAutore(id);
						
			AutoreDTO aDTO = new AutoreDTO();
			aDTO.setId( a.getId() );
			aDTO.setNome( a.getNome() );
			aDTO.setCognome( a.getCognome() );
			aDTO.setNazioneResidenza( a.getNazioneResidenza() );
			
			
			return new ResponseEntity<AutoreDTO> (aDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage() );
			
			return new ResponseEntity<AutoreDTO> (headers, HttpStatus.CONFLICT);
		}
	}
	
	
	@GetMapping(path = "/", produces = {"Application/xml", "Application/json"})
	public ResponseEntity<List<AutoreDTO>> getAllAutori() {
		try {
			List<Autore> lstA = as.leggiAutori();
			List<AutoreDTO> lstAdto =  new ArrayList<AutoreDTO>();
			
			for (Autore a : lstA) {
				AutoreDTO aDTO = new AutoreDTO();
				aDTO.setId( a.getId() );
				aDTO.setNome( a.getNome() );
				aDTO.setCognome( a.getCognome() );
				aDTO.setNazioneResidenza( a.getNazioneResidenza() );
				
				lstAdto.add(aDTO);
			}
			
			
			return new ResponseEntity<List<AutoreDTO>> (lstAdto, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
			
			return new ResponseEntity<List<AutoreDTO>> (headers, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(path = "/{idAutore}/libri", produces = {"Application/xml", "Application/json"})
	public ResponseEntity<List<LibroDTO>> leggiLibriAutore(@PathVariable int idAutore) {		
		try {
			List<Libro> lstL = ls.selezionaLibriPerAutore(idAutore);
			List<LibroDTO> lstDTO = new ArrayList<LibroDTO>();
						
			for (Libro l : lstL) {
				LibroDTO lDTO = new LibroDTO();
				lDTO.setIsbn( l.getIsbn() );
				lDTO.setTitolo( l.getTitolo() );
				lDTO.setDescrizione( l.getDescrizione() );
				lDTO.setCategoria( l.getCategoria().getCategoria() );
				lDTO.setPrezzo( l.getPrezzo() );
				lDTO.setnCopie( l.getnCopie() );
				lDTO.setIdAutore( l.getAutore().getId() );
				
				
				lstDTO.add(lDTO);
			}
			
			return new ResponseEntity<List<LibroDTO>> (lstDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("msgException", e.getMessage());
						
			return new ResponseEntity<List<LibroDTO>> (headers, HttpStatus.NOT_FOUND);
		}
	}
	
	
}
