public class Ligne {
	String numeroVersion;
	String idUtilisateur;
	String date;
	String lignes;
	String commentaire;
	
	public Ligne(String ligne){
		
		String[] split = ligne.split(";");
		this.numeroVersion = split[0];
		this.idUtilisateur = split[1];
		this.date = split[2];
		this.lignes = split[3];
		this.commentaire = split[4];
		
		
	}
	
	/*public Ligne(String numeroVersion, String commentaire){
		
		this.numeroVersion = numeroVersion;
		this.commentaire = commentaire;
		
	}*/
	
	public String detectionTicket(){
		
		return null;
		
	}
	
	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date+ " " + this.lignes+ " " + this.commentaire;
	}
	
	
}
