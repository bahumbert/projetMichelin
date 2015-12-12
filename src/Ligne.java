public class Ligne {
	String numeroVersion;
	String idUtilisateur;
	String date;
	String lignes;
	String commentaire;
	
	public Ligne(String ligne) throws Exception{
		String[] split = ligne.split(";");
		
		if (split[4] != null){
		
			this.numeroVersion = split[0];
			this.idUtilisateur = split[1];
			this.date = split[2];
			this.lignes = split[3];
			this.commentaire = split[4];
		}
		else throw new ArrayIndexOutOfBoundsException("Ligne non valide et ignorée");
	}
	
	public String detectionTicket(){
		return null;
	}
	
	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date + " " + this.lignes + " " + this.commentaire;
	}
	
	
}
