package dto;

import java.util.Date;

public class LogDTO {
	
	private int id;
	private Date dataOra;
	private String url;



	public LogDTO() {}

	public LogDTO(int id, Date dataOra, String url) {
		this.id = id;
		this.dataOra = dataOra;
		this.url = url;
	}

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
	@Override
	public String toString() {
		return "LogDTO [id=" + id + ", dataOra=" + dataOra + ", url=" + url + "]";
	}
	
}
