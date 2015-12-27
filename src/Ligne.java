import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public String detectionTickets(String tickets){
		
		String ticket = "";
		String[] split = tickets.split(",");
		String commentaire = "";
		
		for (String t : split){
			
			//System.out.println("FOR");
			
			int indexCour = 0;
			int index2 = 0;
			
			while(indexCour != -1 && index2 != -1 && index2 < this.commentaire.length()-1){						// && ticket == ""
			
				commentaire = this.commentaire.substring(index2);
				//System.out.println(commentaire);
				indexCour = commentaire.indexOf(t);
				
				if(indexCour != -1){
										
					//System.out.println("indexCour !=-1 ="+indexCour);
					
					Pattern p = Pattern.compile("[0-9]{1,}+");
					Matcher m = p.matcher(commentaire.substring(indexCour));
					//System.out.println(commentaire.substring(indexCour));
					//System.out.println(m.toString());
					
					if (m.find()){
						
						//System.out.println("TROUVE ind=" + indexCour + " end=" + m.end());
						//System.out.println(commentaire.substring(indexCour,indexCour+m.end()));
					    ticket += commentaire.substring(indexCour,indexCour+m.end()) + ", ";
					    
					    index2+= indexCour + m.end();
					    //System.out.println("FIN indexCour="+indexCour);
					    //System.out.println("FIN index2="+index2);
					}
					else {
						index2+= indexCour+1;
						System.out.println("FIN indexCour="+indexCour);
					    System.out.println("FIN index2="+index2);
					}
				}
			}
		}
		if (ticket.length() != 0){
			return ticket.substring(0,ticket.length()-2);		// Suppression de la dernière virgule
		}
		else return "";
	}

	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date + " " + this.lignes + " " + this.commentaire;
	}
	
	
}
