package services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.LogDAO;
import entity.LogWebOperation;

@Service
public class LogService {

	@Autowired
	private LogDAO ld;
	
	/**
	 * Regista un nuovo log di un utente
	 * 
	 * @param le ( LogWebOperation ) Log di un utente
	 */
	public void registraLogElemento(LogWebOperation le) { ld.Insert(le); }
	
	/**
	 * Richiede tutti i log di una data 
	 * Se la data è null restituisce tutti i log
	 * 
	 * @param ldt (LocalDateTime) 		Data da controllare
	 * @return (List<LocalDateTime>)  	Lista di tutti i log
	 */
	@Transactional
	public List<LogWebOperation> statisticheNelPeriodo(LocalDateTime ldt) { 
	
		if ( ldt == null ) return ld.selectAll();
		
		return ld.select(ldt); 
	}
	
}
