import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*import java.util.regex.Matcher;
import java.util.regex.Pattern;*/

public class Ligne {
	String numeroVersion;
	String idUtilisateur;
	String date;
	Date d;
	String lignes;
	String commentaire;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	
	public String getVersion()
	{
		return numeroVersion;
	}
	
	public String getUser()
	{
		return idUtilisateur;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getLignes()
	{
		return lignes;
	}
	
	public String getCommentaire()
	{
		return commentaire;
	}
	
	public Date getD() throws ParseException
	{
		return format.parse(date);
	}
	
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
		/*String[] split = tickets.split(",");
		int index;
		
		for (String t : split){
			
			index = this.commentaire.indexOf(t);
			
			if (index != -1 && ticket == ""){
				//System.out.println("COUCOU2");
				Pattern p = Pattern.compile(".*" +t + "-[0-9]{1,}.*");
				//System.out.println(t + "-[0-9]{1,}");
				Matcher m = p.matcher(this.commentaire);
				//System.out.println(this.commentaire);
				//System.out.println(m.toString());
				if (m.matches())
				{
					//System.out.println("COUCOU");
				    ticket = m.group(0);
				}
				//ticket = this.commentaire.substring(index,this.commentaire.indexOf(t+"-[1-9]+"));
			}
			else if (index != -1 && ticket != ""){
				//System.out.println("COUCOU2");
				Pattern p = Pattern.compile(".*" +t + "-[0-9]{1,}.*");
				//System.out.println(t + "-[0-9]{1,}");
				Matcher m = p.matcher(this.commentaire);
				//System.out.println(this.commentaire);
				//System.out.println(m.toString());
				
				if (m.matches())
				{
					//System.out.println("COUCOU");
					ticket += ", " + m.group(0);
				}
			}
		}*/
		return ticket;
	}
	
	public String toString(){
		return this.numeroVersion + " " + this.idUtilisateur + " " + this.date + " " + this.lignes + " " + this.commentaire;
	}
	
}
