
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane onglets;
	 
	 private void ajoutOnglet(File f)
	 {
		 int index;
		 try{
			 String nom=f.getName();
			 onglets.addTab(nom, table(new Liste(f.getAbsolutePath())));
			 index=(onglets.getTabCount())-1;
			 onglets.setTabComponentAt(index,new Barre(onglets));
			 onglets.setSelectedIndex(index);
		 }
		 catch(Exception e){
			 throw e;
		 }
	 }
	 
	 private void ajoutOnglet(Liste l)
	 {
		 int index;
		 try{
			 index=(onglets.getTabCount());
			 onglets.addTab("temp", table(l));
			 onglets.setTabComponentAt(index,new Barre(onglets));
			 onglets.setSelectedIndex(index);
		 }
		 catch(Exception e){
			 throw e;
		 }
	 }
	 
	 public Modele getModele()
	 {
		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
	 	JTable t = (JTable) s.getViewport().getView();
	 	Modele m = (Modele) t.getModel();
	 	
	 	return m;
	 }
	 
	 ActionListener MenuListener = new ActionListener() {
		 public void actionPerformed(ActionEvent event) {
			 
			 switch(event.getActionCommand())
			 {
			 case "Importer un fichier":
			 		JFileChooser fc = new JFileChooser(".");
					 FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers csv et xml.", "csv","xml");
			         fc.addChoosableFileFilter(filter);
			         fc.setAcceptAllFileFilterUsed(false);
			         
					 if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
						{
						 	File fichier;
							fichier = fc.getSelectedFile();
							try{
								ajoutOnglet(fichier);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
				break;
				
			 	case "Importer depuis le serveur":
				 		
			 		JFileChooser fac = new JFileChooser(".");
			 		fac.setCurrentDirectory(new java.io.File("."));
			 		fac.setDialogTitle("Repository : ");
			 		
			 		fac.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			 		fac.setAcceptAllFileFilterUsed(false);
			 		
			 		String emplacement = "";
			 	    if (fac.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			 	    	emplacement = fac.getSelectedFile().toString();
			 	    }

			 	    if (emplacement != ""){
				 	    
				 		String cmd[] = {"cmd.exe", "/C", "svn log --xml > svnlog.xml"};
				 		try {
							Runtime r = Runtime.getRuntime();
							
							/*String test = System.getenv("Path");
							System.out.println(test);
							System.out.println(test.length());*/
							final Process p = r.exec(cmd, new String[]{"Path=C:\\Program Files (x86)\\TortoiseSVN\\bin;C:\\Program Files\\TortoiseSVN\\bin;E:\\Applications\\TortoiseSVN/bin"},  new File(emplacement));
							
							BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
							BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
							
	
							System.out.println("Commande :\n");
	
							int count = 0;
							String s;
							String result = "";
								while ((s = stdInput.readLine()) != null) {
									count++;
									result = result + s + "\n";
								}
							
							stdInput.close();
							
								count = 0;
								String err = "";
								while ((s = stdErr.readLine()) != null) {
									count++;
									err = err + s + "\n";
								}
								
							stdErr.close();
							
							//System.out.println("commande =" + cmd.toString() + "\nresult : " + count + " : " + result + " err="+err);
							
							if (err.equals("")){
								File fichier = new File(emplacement+"\\svnlog.xml");
								try{
									ajoutOnglet(fichier);
								}
								catch(Exception e){
									System.out.println("Erreur "+e.toString());
								}
							}
							else {
								JOptionPane.showMessageDialog(onglets, "Erreur : "+err);
							}
				 		}
				 		catch(Exception e) {
							System.out.println("erreur d'execution " + cmd + e.toString());
				        }
					}
				 	break;
				
			 	case "Sauvegarder...":
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour sauvegarder", "Sauvegarder", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			
			 		}
			 		break;
			 		
			 	case "Imprimer...":
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour imprimer", "Imprimer", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			
			 		}
			 		break;
			 		
			 	case "Quitter":
			 		setVisible(false);
			 		dispose();
			 		break;

			 /************ Menu Outils **************/
			 		
			 	case "Ajouter filtre":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit êre ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 			
			 			String author="", day1="",day2="",number="";
			 			String message = "Sélectionnez les filtres souhaités";

				 		JLabel auteur = new JLabel("Auteur");
				 		JTextField champ= new JTextField();
				 		JLabel dates = new JLabel("Dates");;
				 		JXDatePicker date1 = new JXDatePicker();
				 		JXDatePicker date2 = new JXDatePicker();
				 			date1.setFormats(format);
				 			date2.setDate(Calendar.getInstance().getTime());
				 			date2.setFormats(format);
				 		JLabel ticket = new JLabel("Numéro de commit");
				 		JTextField champ2= new JTextField();
				 		
				 		Object [] params ={message,auteur,champ,dates,date1,date2,ticket,champ2};
				 		JOptionPane.showConfirmDialog(null, params, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
				 		if(champ.getText()!=null)
				 			author=champ.getText();

				 		if(date1.getDate()!=null)
				 			day1=format.format(date1.getDate());
				 		
				 		day2=format.format(date2.getDate());
				 		
				 		if(champ2.getText()!=null)
				 			number=champ2.getText();
				 		
				 		Modele m = getModele();
				 		try {
							m.getListe().filtres(author, day1, day2, number);
							m.fireTableDataChanged();
						} catch (ParseException e) {
							e.printStackTrace();
						}
				 	
			 		}
			 	break;
			 	
			 	case "Detection de ticket":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else {
			 			
			 			InputStream ips;
			 			String tickets = "";
						try {
							ips = new FileInputStream("./tickets.txt");
							InputStreamReader ipsr=new InputStreamReader(ips);
							BufferedReader br=new BufferedReader(ipsr);
							//System.out.println("Fichier ouvert");
							
				 			String ligne = "";
				 			
							while ((ligne=br.readLine())!=null){
								tickets += ligne;
							}
							
							br.close(); 
						} 
						catch (FileNotFoundException e) {
							System.out.println("Le fichier des tickets n'a pas �t� trouv�");
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 

			 			String msg = "Entrer le(s) ticket(s) souhait�(s)";
				 		JTextField pattern= new JTextField(tickets);
				 				 		
				 		Object [] parameters ={msg,pattern};
				 		int res = JOptionPane.showConfirmDialog(null, parameters, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
			 			Modele model = getModele();
			 			
				 		if (res == 0 && !pattern.getText().equals("")){
					 		
					 		for (int row = 0; row < model.getRowCount(); row++){
					 			model.setValueAt("", row, 5);
					 		}
					 		
					 		File fichier = new File("./tickets.txt");
					 		try {
								FileWriter fw = new FileWriter(fichier);
								fw.write(pattern.getText());
								fw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 		
					 		
					 		/*String affichage =*/ model.getListe().detectionTickets(pattern.getText());
							
					 		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();
					 		
						    TableCellRenderer renderer2 = new CustomTableCellRenderer();
						
						    t.setDefaultRenderer(Object.class, renderer2);

				 		}
				 		else if (pattern.getText().equals("")){

				 			for (int row = 0; row < model.getRowCount(); row++){
					 			model.setValueAt(" ", row, 5);
					 		}
				 			
				 			JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();
				 			TableCellRenderer renderer2 = new CustomTableCellRenderer();
							
						    t.setDefaultRenderer(Object.class, renderer2);
				 			
				 		}
				 		model.fireTableDataChanged();

			 		}
			 		
			 	break;
			 	
			 	case("Trouver les similitudes"):
				case("Trouver les differences"):
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit êre ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			Modele m = getModele();
			 			JFileChooser filec = new JFileChooser(".");
						FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichiers csv.", "csv");
				        filec.addChoosableFileFilter(filtre);
				        filec.setAcceptAllFileFilterUsed(false);
				         
						if(filec.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
						{
						 	File fichier;
						 	String s;
							fichier = filec.getSelectedFile();

							Liste compare;
							if(event.getActionCommand()=="Trouver les similitudes"){
								compare=m.getListe().comparerIdentique(new Liste(fichier.getAbsolutePath()));
								s="similitudes";
							}
							else{
								compare=m.getListe().comparerDifferent(new Liste(fichier.getAbsolutePath()));
								s="différences";
							}
							
							if(compare.liste.size()==0)
								JOptionPane.showConfirmDialog(null, "Aucunes "+s+" n'ont été trouvées", "Comparaison", JOptionPane.DEFAULT_OPTION);

							else
								ajoutOnglet(compare);
						}
			 		}
				break;
			 }
		 }
	 };
	 
	 private void menu()
	 {
		 JMenuBar menuBar = new JMenuBar();
	 
		 JMenu nouveau = new JMenu("Nouveau");
		 JMenuItem ouvrir = new JMenuItem("Importer un fichier");
		 nouveau.add(ouvrir);
		 ouvrir.addActionListener(MenuListener);
		 	
		 JMenuItem log = new JMenuItem("Importer depuis le serveur");
		 nouveau.add(log);
		 log.addActionListener(MenuListener);
	 
		 JMenuItem sauvegarder = new JMenuItem("Sauvegarder...");
		 sauvegarder.addActionListener(MenuListener);
		 nouveau.add(sauvegarder);
				
		 JMenuItem imprimer = new JMenuItem("Imprimer...");
		 imprimer.addActionListener(MenuListener);
		 nouveau.add(imprimer);
				
		 JMenuItem quitter = new JMenuItem("Quitter");
		 quitter.addActionListener(MenuListener);
		 nouveau.add(quitter);
	 
		 JMenu outils = new JMenu("Outils");
		 	JMenuItem ticket = new JMenuItem("Detection de ticket");
			ticket.addActionListener(MenuListener);
		 	JMenuItem filtres = new JMenuItem("Ajouter filtre");
			filtres.addActionListener(MenuListener);

			JMenu comparer = new JMenu("Comparer");
			JMenuItem egal = new JMenuItem("Trouver les similitudes");
			egal.addActionListener(MenuListener);
			JMenuItem different = new JMenuItem("Trouver les differences");
			different.addActionListener(MenuListener);
			comparer.add(egal);
			comparer.add(different);
			
			outils.add(ticket);
			outils.add(filtres);
			outils.add(comparer);
					
				
		menuBar.add(nouveau);
		menuBar.add(outils);
	 
		setJMenuBar(menuBar);
	}
	 
	 private JScrollPane table(Liste liste)
	{
		JTable table;
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.LEFT);
	    renderer.setBackground(Color.lightGray);
	        
		Modele modele=new Modele(liste);
		table = new JTable(modele);
		table.getTableHeader().setDefaultRenderer(renderer);
		 
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		 
		for (int column = 0; column < table.getColumnCount(); column++)
		{
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		    	TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		    	Component c = table.prepareRenderer(cellRenderer, row, column);
		    	int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		    	preferredWidth = Math.max(preferredWidth, width);

		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            //break;
		        }
		    }
		    tableColumn.setPreferredWidth( preferredWidth +20);
		}
		JScrollPane pane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return pane;
	}
	
	public Fenetre(String name)
	{
		 this.setTitle(name);
		 this.setLocationRelativeTo(null); 
		 
		 JPanel container = new JPanel();
		 onglets  = new JTabbedPane(SwingConstants.TOP);
		 container.add(onglets);
		 
		 BoxLayout bl=new BoxLayout(container,BoxLayout.Y_AXIS);
	     container.setLayout(bl);
		 
         menu();
         
		 this.setContentPane(container);  
		 this.pack();
		 this.setExtendedState(Frame.MAXIMIZED_BOTH);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 

		/*Component test = onglets.getTabComponentAt(0);
		test.setBackground(Color.blue);*/
	}
	
}