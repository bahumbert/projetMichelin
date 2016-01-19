import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ligne {
	String numeroVersion;
	String idUtilisateur;
	String date;
	Date d;
	String lignes;
	String commentaire;
	String ticket;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	
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
			
			t = t.trim();
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
						/*System.out.println("FIN indexCour="+indexCour);
					    System.out.println("FIN index2="+index2);*/
					}
				}
			}
		}
		if (ticket.length() != 0){
			this.ticket = ticket.substring(0,ticket.length()-2); // Suppression de la dernière virgule
			return this.ticket;		
		}
		else return "";
	}

	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date + " " + this.lignes + " " + this.commentaire;
	}
	
	public String getVersion()
	{
		return this.numeroVersion;
	}
	
	public String getUser()
	{
		return this.idUtilisateur;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getLignes()
	{
		return this.lignes;
	}
	
	public String getCommentaire()
	{
		return this.commentaire;
	}
	
	public String getTicket()
	{
		return this.ticket;
	}
	
	public Date getD() throws ParseException
	{
		return this.format.parse(date);
	}
	
}