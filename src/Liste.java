import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.DOMException;

import java.awt.FileDialog;

//import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
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
	
	public Liste(Liste l){
		this.liste=(ArrayList<Ligne>) l.liste.clone();
	}
	
	public ArrayList<Ligne> getListe()
	{
		return this.liste;
	}
	
	public Liste(String fichier){
		this.liste = new ArrayList<Ligne>();
		
		String extension = getExtension(fichier);
		
		if(extension.equals("csv")){
			
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
						System.out.println("Ligne non valide et ignor�e");
					}
				}
				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			
		}
		else if (extension.equals("xml")){
			try {
				new ParseXML(fichier, this.liste);
			} catch (DOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new IllegalArgumentException("Fichier non valide");
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
				affichage += "Trouvé " + tmp + "\n";
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
		Liste filtre = new Liste(this);
		Date d1 = new Date();
		Date d2 = new Date();
		Ligne ligne;
		
		if(date1!="")
			d1 = format.parse(date1);
		if(date2!="")
			d2 = format.parse(date2);

		for (Iterator<Ligne> it=filtre.liste.iterator(); it.hasNext();) {
			ligne=it.next();
			
			if(auteur!="")
			{
				if(!(ligne.getIdUtilisateur().contains(auteur)) && (!(ligne.getCommentaire().contains(auteur))))
						it.remove();
			}
		    
		    else if (version!="" && !ligne.getNumeroVersion().contains(version))
		    	it.remove();
		    
		    else if(date1!="" && ligne.getD().compareTo(d1)<=0)
		    	it.remove();
		    
		    else if(date2!="" && ligne.getD().compareTo(d2)>=0)
		    	it.remove();
		}
		return filtre;
	}
	
	private String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
	
	public static int parseTicket(String s)
	{
		int res;
		
		String[] tab =s.split("[a-z]");
		res=Integer.parseInt(tab[tab.length-1]);
		
		return res;
	}
	
	public Liste comparerIdentique(Liste liste2)
	{
		Liste l = new Liste();
		int i=0, j=0;
		int length1=this.liste.size();
		int length2=liste2.liste.size();
		int version1, version2;
		Boolean fin;
		
		version2=parseTicket(liste2.liste.get(j).numeroVersion);
		
		while(i<length1)
		{
			version1=parseTicket(this.liste.get(i).numeroVersion);
			j=0;
			fin =false;
			while((j<length2) && (!fin))
			{
				version2=parseTicket(liste2.liste.get(j).numeroVersion);
				if(version1==version2)
				{
					if(this.liste.get(i).equals(liste2.liste.get(j)))
					{
						l.liste.add(this.liste.get(i));
						fin =true;
					}
				}				
				j++;
			}
			i++;
		}
		return l;
	}
	
	public Liste comparerDifferent(Liste liste2)
	{
		Liste l = new Liste(liste2);
		int i=0, j=0;
		int length1=this.liste.size();
		int length2=l.liste.size();
		int version1, version2;
		Boolean trouve;
		
		version2=parseTicket(l.liste.get(j).numeroVersion);
		
		while(i<length1)
		{
			version1=parseTicket(this.liste.get(i).numeroVersion);
			j=0;
			trouve=false;
			while((j<length2) && (!trouve))
			{
				version2=parseTicket(l.liste.get(j).numeroVersion);
				if(version1==version2)
				{
					if(this.liste.get(i).equals(l.liste.get(j)))
					{
						trouve =true;
						l.liste.remove(j);
					}
					else
					{
						j++;
					}
				}
				else
					j++;
			}
			if(!trouve)
			{
				l.liste.add(this.liste.get(i));
			}
			i++;
		}
		return l;
	}
	
	public void export() throws IOException
	{
		Ligne ligne;
		Set<String> lines = new LinkedHashSet();
		FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.LOAD);
		fd.setDirectory(".");
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String filename = fd.getFile();
		String ext = getExtension(filename);
		String[] split;
		String temp;
		
		if(!ext.equals("txt"))
		{
			filename = filename.replaceFirst("[.][^.]+$", "");
			filename+=".txt";
		}
		
		FileWriter fw = new FileWriter(path+filename,false);
		BufferedWriter output = new BufferedWriter(fw);
		
		for (Iterator<Ligne> it=this.liste.iterator(); it.hasNext();) 
    	{
			ligne=it.next();
			if(!ligne.getTickets().equals(""))
			{
				split=ligne.getTickets().split("\\(");
				split=split[0].split(" ");
				for(String ticket:split)
					//La redondance est supprimée
					lines.add(ticket+"\n");
			}
    	}
		
		for (String line : lines)
		    output.write(line);
		
		output.flush();
		output.close();
	}
	
	public void sauver() throws IOException
	{
		Ligne ligne;
		
		FileDialog fd = new FileDialog(new JFrame(), "Choose a file", FileDialog.LOAD);
		fd.setDirectory(".");
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String filename = fd.getFile();
		//File file = new File(path+filename);
		
		FileWriter fw = new FileWriter(path+filename,false);
		BufferedWriter output = new BufferedWriter(fw);
		String ext = getExtension(filename);
		
		/*if (filename.lastIndexOf(".") > 0) {
		    String ext = filename.substring(filename.lastIndexOf("."));*/
		    if (ext.equals("csv")) 
		    {
		    	for (Iterator<Ligne> it=this.liste.iterator(); it.hasNext();) 
		    	{
					ligne=it.next();
					output.write(ligne.getNumeroVersion()+";"+ligne.getIdUtilisateur()+";"+ligne.getDate()+";"+ligne.getLignes()+";"+ligne.getCommentaire()+"\n");
		    	}
		    }
		    else
		    	if(ext.equals("xml"))
		    	{
		    		for (Iterator<Ligne> it=this.liste.iterator(); it.hasNext();) 
			    	{
						ligne=it.next();
			    	}
		    	}
		//} 
		else {
			//export csv par défault
			System.out.println("défault");
			for (Iterator<Ligne> it=this.liste.iterator(); it.hasNext();) {
				ligne=it.next();
				output.write(ligne.getNumeroVersion()+";"+ligne.getIdUtilisateur()+";"+ligne.getDate()+";"+ligne.getLignes()+";"+ligne.getCommentaire()+"\n");
			}
		}
		
		output.flush();
		output.close();
		
	}

}