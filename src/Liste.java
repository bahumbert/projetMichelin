import java.util.ArrayList;
//import java.util.Scanner;
import java.io.*;

public class Liste {

	/**
	 * @param args
	 */
	
	ArrayList<Ligne> liste;
	
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
				affichage += tmp + "\n";
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
}
