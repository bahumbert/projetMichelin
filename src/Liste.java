import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
//import java.util.Scanner;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Liste 
{
	
	ArrayList<Ligne> liste;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	
	public Liste()
	{
		liste=new ArrayList<Ligne>();
	}
	
	public ArrayList<Ligne> getListe()
	{
		return this.liste;
	}
	
	public Liste(String fichier){
		this.liste = new ArrayList<Ligne>();
			
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			
			System.out.println("new list");
			
			while ((ligne=br.readLine())!=null){
				try {
					this.liste.add(new Ligne(ligne));
				}
				catch (Exception e){
					System.out.println("Ligne non valide et ignorée"/* + e.toString()*/);
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public String toString(){
		String affichage = "";
		
		for (Ligne s : this.liste){
			affichage += s.toString() + "\n";
		}
		return affichage;
	}
	
	public String taille(){
		return "Il y a " + this.liste.size() + " lignes dans cette liste.";
	}
	
	public String detectionTickets(String tickets){
		
		String affichage = "";
		String tmp;
		for (Ligne t : this.liste){
			
			tmp = t.detectionTickets(tickets);
			
			if (tmp != "" && !tmp.contains(",")){
				affichage += "Trouvé" + tmp + "\n";
			}
			else if (tmp != "" && tmp.contains(",")){
				affichage += "!! Conflit !! => " + tmp + "\n";
			}
			else if (tmp == ""){
				affichage += "Aucun ticket repéré dans cette ligne\n";
			}
		}
		return affichage;
	}
	
	public Liste filtres (String auteur, String date1, String date2,String version) throws ParseException
	{
		Liste filtre = new Liste();
		filtre.liste = this.liste;
		Date d1 = new Date();
		Date d2 = new Date();
		Ligne ligne;
		
		if(date1!="")
			d1 = format.parse(date1);
		if(date2!="")
			d2 = format.parse(date2);

		for (Iterator<Ligne> it=filtre.liste.iterator(); it.hasNext();) {
			ligne=it.next();
		    if (auteur!="" && !(ligne.getUser().contains(auteur)))
		        it.remove();
		    
		    else if (version!="" && !ligne.getVersion().contains(version))
		    	it.remove();
		    
		    else if(date1!="" && ligne.getD().compareTo(d1)<=0)
		    	it.remove();
		    
		    else if(date2!="" && ligne.getD().compareTo(d2)>=0)
		    	it.remove();
		}
		return filtre;
	}
}
