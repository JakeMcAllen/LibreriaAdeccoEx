package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AutoreDTO")
public class AutoreDTO {

	private int id;
	private String nome;
	private String cognome;
	private String NazioneResidenza;
	
	public AutoreDTO() {}

	public AutoreDTO(int id, String nome, String cognome, String nazioneNascita) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		NazioneResidenza = nazioneNascita;
	}
	
	

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getCognome() { return cognome; }
	public void setCognome(String cognome) { this.cognome = cognome; }

	public String getNazioneResidenza() { return NazioneResidenza; }
	public void setNazioneResidenza(String nazioneNascita) { NazioneResidenza = nazioneNascita; }

	@Override
	public String toString() {
		return "LibroDTO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", NazioneNascita=" + NazioneResidenza + "]"; 
	}
	
}
