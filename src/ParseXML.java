import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXML {
	public ParseXML(String fichier, ArrayList<Ligne> liste) throws DOMException, Exception {
        /*
         * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
         */
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
            /*
             * Etape 2 : création d'un parseur
             */
            final DocumentBuilder builder = factory.newDocumentBuilder();
				
		    /*
		     * Etape 3 : création d'un Document
		     */
		    final Document document = builder.parse(new File(fichier));
				
		    //Affiche du prologue
		    /*System.out.println("*************PROLOGUE************");
		    System.out.println("version : " + document.getXmlVersion());
		    System.out.println("encodage : " + document.getXmlEncoding());		
	            System.out.println("standalone : " + document.getXmlStandalone());*/
						
		    /*
		     * Etape 4 : récupération de l'Element racine
		     */
		    final Element racine = document.getDocumentElement();
			
		    //Affichage de l'élément racine
		    /*System.out.println("\n*************RACINE************");
		    System.out.println(racine.getNodeName());*/
			
		    /*
		     * Etape 5 : récupération des lignes
		     */
		    final NodeList racineNoeuds = racine.getChildNodes();
		    final int nbRacineNoeuds = racineNoeuds.getLength();
				
		    for (int i = 0; i<nbRacineNoeuds; i++) {
		        if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
		            final Element logentry = (Element) racineNoeuds.item(i);
					
			    //Affichage d'une ligne
			    /*System.out.println("\n*************LOGENTRY************");
			    System.out.println("revision : " + logentry.getAttribute("revision"));*/
				
		    	    /*
			     * Etape 6 : des données
			     */
			    final Element author = (Element) logentry.getElementsByTagName("author").item(0);
			    final Element date = (Element) logentry.getElementsByTagName("date").item(0);
			    final Element msg = (Element) logentry.getElementsByTagName("msg").item(0);

			    //Affichage des données
			    /*System.out.println("author : " + author.getTextContent());
			    System.out.println("date : " + date.getTextContent());
			    System.out.println("msg : " + msg.getTextContent());*/

			    liste.add(new Ligne(logentry.getAttribute("revision")+";"+author.getTextContent()+";"+date.getTextContent()+";1;"+msg.getTextContent()));

		        }
		    }			
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }		
    }
}
