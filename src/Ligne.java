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
		//System.out.println(split[0]+ " " + split[1] + " " + split[2] +" "+ split[3]);
		if (split.length >= 5 && split[4] != null){
		//System.out.println(split[4]);
			this.numeroVersion = split[0];
			this.idUtilisateur = split[1];
			this.date = split[2];
			this.lignes = split[3];
			this.commentaire = split[4];
		}
		else throw new ArrayIndexOutOfBoundsException("Ligne non valide et ignorée");
	}
	
	public String detectionTickets(String tickets){
		
		String listTickets = "";
		String[] split = tickets.split(",");
		String commentaire = "";
		//System.out.println("COUCOU");
		for (String ticketCour : split){
			
			ticketCour = ticketCour.trim();
			//System.out.println("FOR");
			
			int indexCour = 0;
			int index2 = 0;
			
			while(indexCour-ticketCour.length() != -1 && index2 != -1 && index2 < this.commentaire.length()-1){						// && ticket == ""
			
				//System.out.println(this.commentaire.length());
				commentaire = this.commentaire.substring(index2);
				//System.out.println(commentaire);
				indexCour = commentaire.indexOf(ticketCour) + ticketCour.length();
				
				if(indexCour-ticketCour.length() != -1){
										
					//System.out.println("indexCour !=-1 ="+indexCour);
					//System.out.println("index2 ="+index2);
					
					Pattern p = Pattern.compile("^[-]{1}[0-9]{1,}");
					Matcher m = p.matcher(commentaire.substring(indexCour));
					//System.out.println(commentaire.substring(indexCour));
					//System.out.println(m.toString());
					
					if (m.find()){
						
						//System.out.println("TROUVE ind=" + indexCour + " end=" + m.end());
						//System.out.println(commentaire.substring(indexCour,indexCour+m.end()));
						
						String ticket = ticketCour + commentaire.substring(indexCour,indexCour+m.end());
						
						if (!listTickets.contains(ticket)){
							listTickets += ticket + ", ";
						}
					    
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
		if (listTickets.length() != 0){
			this.ticket = listTickets.substring(0,listTickets.length()-2); // Suppression de la dernière virgule
			return this.ticket;		
		}
		else return "";
	}

	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date + " " + this.lignes + " " + this.commentaire;
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
	
	public String getNumeroVersion() {
		return this.numeroVersion;
	}

	public void setNumeroVersion(String numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	public String getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public SimpleDateFormat getFormat() {
		return this.format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public void setLignes(String lignes) {
		this.lignes = lignes;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getD() throws ParseException
	{
		return this.format.parse(date);
	}

}