import java.util.ArrayList;
import java.util.Scanner;
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
				this.liste.add(new Ligne(ligne));
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		
	}
	
	/*public Liste(String numeroVersion, String commentaire){
		System.out.println("new list2");

		liste = new ArrayList<Ligne>();
		
		liste.add(new Ligne(numeroVersion, commentaire));
	}*/
	
	public String toString(){
		
		String affichage = "";
		
		for (Ligne s : liste){
			affichage += s.toString() + "\n";
		}
		return affichage;
	}
	
	public String taille(){
		
		return "Il y a " + this.liste.size() + " lignes dans cette liste.";
		
	}
	
	
	
	public static void main(String[] args) {
		Scanner entree = new Scanner(System.in);
		System.out.println("Veuillez entrer le chemin du fichier :");
		String fichier = entree.nextLine();
		Liste liste = new Liste(fichier);
		
		//Liste liste = new Liste("r84004","GDK : WEOSI-195 With your feet on the air and your head on the ground");
		
		System.out.println(liste.toString());
		System.out.println(liste.taille());
		
		System.out.println(";-)");
		
		entree.close();
	}

}
