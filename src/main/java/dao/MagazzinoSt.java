package dao;

public enum MagazzinoSt {
	RICHIESTO("richiesto"), DISPONIBILE("disponibile"), PROCESSATO("processato");
	
	private MagazzinoSt(final String str) {
		this.str = str;
	}
	
	private String str;
	
	public String getSt () { return this.str; }
}
