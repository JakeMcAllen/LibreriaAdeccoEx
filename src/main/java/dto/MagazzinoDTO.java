package dto;

public class MagazzinoDTO {
	
	private int id;
	private int quantita;
	private String statoStock;
	private int isbn;
	
	
	
	public MagazzinoDTO() {}

	public MagazzinoDTO(int id, int quantita, String statoStock, int isbn) {
		this.id = id;
		this.quantita = quantita;
		this.statoStock = statoStock;
		this.isbn = isbn;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getStatoStock() {
		return statoStock;
	}

	public void setStatoStock(String statoStock) {
		this.statoStock = statoStock;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	
	
	@Override
	public String toString() {
		return "MagazzinoDTO [id=" + id + ", quantita=" + quantita + ", statoStock=" + statoStock + ", isbn=" + isbn
				+ "]";
	}
	
	
}
