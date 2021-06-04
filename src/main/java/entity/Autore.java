package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "autorelibri")
public class Autore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Nome")
	private String nome;
	@Column(name = "Cognome")
	private String cognome;
	@Column(name = "NazioneResidenza")
	private String nr;
	
	@OneToMany(mappedBy = "autore", fetch=FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	private List<Libro> lB = new ArrayList<Libro> ();
	
	
	
	public Autore() {}

	public Autore(String nome, String cognome, String nazioneResidenza) {
		this.nome = nome;
		this.cognome = cognome;
		this.nr = nazioneResidenza;
	}
	
	

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getCognome() { return cognome; }
	public void setCognome(String cognome) { this.cognome = cognome; }

	public String getNazioneResidenza() { return nr; }
	public void setNazioneResidenza(String nazioneResidenza) { nr = nazioneResidenza; }

	
	
	
	@Override
	public String toString() {
		return "Autore [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", NazioneResidenza=" + nr + "]";
	}	
	
}
