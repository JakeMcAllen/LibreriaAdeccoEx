package dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

import entity.Categoria;

@XmlRootElement(name = "LibroDTO")
@JsonRootName("Libro")
public class LibroDTO {
	
	private int isbn;
	private String Titolo;
	private String Descrizione;

	private String categoria;
	private double prezzo;
	private int nCopie;
	
	private int idAutore;
	
	
	
	public LibroDTO() {}

	public LibroDTO(int isbn, String Titolo, String descrizione, String categoria, double prezzo, int nCopie, int idAutore) {
		this.isbn = isbn;
		this.Titolo = Titolo;
		this.Descrizione = descrizione;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.nCopie = nCopie;
		this.idAutore = idAutore;
	}
	

	public int getIsbn() { return isbn; }
	public void setIsbn(int isbn) { this.isbn = isbn; }

	public String getTitolo() { return Titolo; }
	public void setTitolo(String titolo) { Titolo = titolo; }

	public String getDescrizione() { return Descrizione; }
	public void setDescrizione(String descrizione) { Descrizione = descrizione; }

	public String getCategoria() { return categoria; }
	public void setCategoria(String categoria) { this.categoria = categoria; }

	public double getPrezzo() { return prezzo; }
	public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

	public int getnCopie() { return nCopie; }
	public void setnCopie(int nCopie) { this.nCopie = nCopie; }

	public int getIdAutore() { return idAutore; }
	public void setIdAutore(int idAutore) { this.idAutore = idAutore; }


	@Override
	public String toString() {
		return "LibroDTO [isbn=" + isbn + ", Titolo=" + Titolo + ", Descrizione=" + Descrizione + ", categoria="
				+ categoria + ", prezzo=" + prezzo + ", nCopie=" + nCopie + ", idAutore=" + idAutore + "]";
	}
	

}
