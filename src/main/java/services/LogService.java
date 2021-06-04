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
	
	public LogService() { }
	
	public void registraLogElemento(LogWebOperation le) { ld.Insert(le); }
	
	@Transactional
	public List<LogWebOperation> statisticheNelPeriodo(LocalDateTime ldt) { 
	
		if ( ldt == null ) return ld.selectAll();
		
		return ld.select(ldt); 
	}
	
}
