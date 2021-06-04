package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "logweboperation")
public class LogWebOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataOra;
	
	@Column(name = "URLRequest")
	private String url;
	
	
	
	public LogWebOperation() {}
	public LogWebOperation(String url) { this.url = url; }

	
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public Date getDataOra() { return dataOra; }
	public void setDataOra(Date dataOra) { this.dataOra = dataOra; }

	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }

	
	
	@Override
	public String toString() {
		return "LogWebOperation [id=" + id + ", dataOra=" + dataOra + ", url=" + url + "]";
	}
	
}
