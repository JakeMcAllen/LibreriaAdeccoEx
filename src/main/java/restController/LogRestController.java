package restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LogDTO;
import entity.LogWebOperation;
import services.LogService;

@RestController
@RequestMapping(path = "/losa")
public class LogRestController {

	@Autowired
	private LogService lgS;
	
	/**
	 * 
	 * Url: 	http://localhost:8080/Libreria/libreria/losa/stat
	 * Metodo: 	Get
	 * 
	 * 
	 * @return Una lista completa di tutti i log
	 */
	@GetMapping(path = "/stat" )
	public ResponseEntity<List<LogDTO>> getAllLog() {
		
		try {
			List<LogWebOperation> lstLg = lgS.statisticheNelPeriodo(null);
			List<LogDTO> lstlDTO = new ArrayList<LogDTO>();
			
			for (LogWebOperation lwo : lstLg) {
				LogDTO lDTO = new LogDTO();
				lDTO.setId( lwo.getId() );
				lDTO.setDataOra( lwo.getDataOra() );
				lDTO.setUrl( lwo.getUrl() );
				
				
				lstlDTO.add(lDTO);
			}
			
			
			return new ResponseEntity<List<LogDTO>> (lstlDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders header = new HttpHeaders();
			header.add("msgException", e.getMessage());
			
			System.out.println("2: " + e);
			
			return new ResponseEntity<List<LogDTO>> (header, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
