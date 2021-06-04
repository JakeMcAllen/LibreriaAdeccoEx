package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "libri")
@XmlRootElement(name = "Libro")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int isbn;
	private String Titolo;
	private String Descrizione;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name="categoria")
	private Categoria categoria;
	private double prezzo;
	private int nCopie;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name="idAutore")
	private Autore autore;
	
	
	
	public Libro() {}


	public Libro(String titolo, String descrizione, Categoria categoria, double prezzo, int nCopie, Autore autore) {
		this.Titolo = titolo;
		this.Descrizione = descrizione;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.nCopie = nCopie;
		this.autore = autore;
	}

	
	

	public int getIsbn() {
		return isbn;
	}


	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}


	public String getTitolo() {
		return Titolo;
	}


	public void setTitolo(String titolo) {
		Titolo = titolo;
	}


	public String getDescrizione() {
		return Descrizione;
	}


	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public double getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	public int getnCopie() {
		return nCopie;
	}


	public void setnCopie(int nCopie) {
		this.nCopie = nCopie;
	}


	public Autore getAutore() {
		return autore;
	}


	public void setAutore(Autore autore) {
		this.autore = autore;
	}


	
	
	
	@Override
	public String toString() {
		return "Libri [isbn=" + isbn + ", Titolo=" + Titolo + ", Descrizione=" + Descrizione + ", categoria="
				+ categoria + ", prezzo=" + prezzo + ", nCopie=" + nCopie + ", idAutore=" + autore + "]";
	}
	
}
