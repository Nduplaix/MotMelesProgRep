package graphisme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import grilles.GrilleMots;
import grilles.GrillesMots;
import son.Son;

public class PanneauSelectionGrilleMots extends PanneauGrilleCars {

	private static final long serialVersionUID = 1L;
	
		
	// Les couleurs utilisées pour l'affichage.
	
	final static private int couleurMotsATrouves=5;
	final static private int couleurInfos=3;
	
		
	private int[] indexGrilleMotsSelectionnee;
	private GrilleMots grillesMots[];
	private int ligMotsATrouver=14;
	private int nbLigsMotsATrouver=19;
	
	public PanneauSelectionGrilleMots(int[] indexGrilleMotsSelectionnee){
		super(0,Color.BLACK);
		this.indexGrilleMotsSelectionnee=indexGrilleMotsSelectionnee;
		grillesMots=GrillesMots.getGrillesMots();
		afficheSelection();
		mettreAJourGrille();
		setFocusable(true);
		requestFocusInWindow();
		//initialiseGestionnaireSouris();
		initialiseGestionnaireClavier();
	}
	
	synchronized private void selectionneSuivant(){
		indexGrilleMotsSelectionnee[0]++;
		if (indexGrilleMotsSelectionnee[0]==grillesMots.length)
			indexGrilleMotsSelectionnee[0]=0;
		afficheSelection();
		mettreAJourGrille();
		Son.jouerSonCourt(3);
	}
	
	synchronized private void selectionnePrecedent(){
		indexGrilleMotsSelectionnee[0]--;
		if (indexGrilleMotsSelectionnee[0]==-1)
			indexGrilleMotsSelectionnee[0]=grillesMots.length-1;
		afficheSelection();
		mettreAJourGrille();
		Son.jouerSonCourt(3);
	}
	
	private void afficheSelection(){
		remplirGrille(' ',0,Color.BLACK);
		 afficheInfos();
		 afficheMotsATrouver();
	}
	
	private void afficheMotsATrouver(){
			String mots[]=grillesMots[indexGrilleMotsSelectionnee[0]].getMots();
			int lig=ligMotsATrouver;
			int col=1;
			int maxTailleMot=0;
			String mot;
			for (int i=0;i<mots.length;i++){
				if (lig==ligMotsATrouver+nbLigsMotsATrouver){
					lig=ligMotsATrouver;
					col=col+maxTailleMot+1;
					maxTailleMot=0;
				}
				mot=mots[i];
				if (mot.length()>maxTailleMot)
					maxTailleMot=mot.length();
				afficheMotCarsGrille(lig,col,mot,couleurMotsATrouves,Color.BLACK);
				lig++;
				
			}
	}
	
	private void afficheInfos(){
		int lig=3;
		GrilleMots grilleMots=grillesMots[indexGrilleMotsSelectionnee[0]];
		afficheLigneCentree(lig,"GRILLE "+indexGrilleMotsSelectionnee[0],couleurInfos,Color.BLACK);
		lig=lig+2;
		afficheLigneCentree(lig,grilleMots.getTitre().toUpperCase(),couleurInfos,Color.BLACK);
		lig=lig+2;
		afficheLigneCentree(lig,grilleMots.getType().toUpperCase(),couleurInfos,Color.BLACK);
		lig=lig+2;
		afficheLigneCentree(lig,(grilleMots.getNbMots()+" MOTS").toUpperCase(),couleurInfos,Color.BLACK);
		
	
	
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	private void initialiseGestionnaireSouris(){
		addMouseListener(new MouseAdapter(){
	         public void mouseClicked(MouseEvent e) {
	        	SwingUtilities.windowForComponent(PanneauSelectionGrilleMots.this).dispose();
	         }                
	      });	
	}
	private void initialiseGestionnaireClavier(){
		addKeyListener(new KeyAdapter(){
			  public void keyPressed(KeyEvent e) {                
		            if (e.getKeyCode()==KeyEvent.VK_RIGHT)
		            	selectionneSuivant();
		            else if (e.getKeyCode()==KeyEvent.VK_LEFT)
		            		selectionnePrecedent();
		            	else if (e.getKeyCode()==KeyEvent.VK_ENTER){
			            		SwingUtilities.windowForComponent(PanneauSelectionGrilleMots.this).dispose();
			            		Son.jouerSonCourt(3);
			            	} else if (e.getExtendedKeyCode()==KeyEvent.VK_ESCAPE){
								  System.out.println("A Bientôt !");
								  System.exit(0);
							  }
		            	
		         }                  
	      });	
	}
	
public static void main(String[] args) {
		
	PanneauSelectionGrilleMots panneauSelectionGrilleMots=new PanneauSelectionGrilleMots(new int[1]);
		new FenetreGrilleCars("Sélectionnez une grille de mots (utiliser les flêches du clavier)",panneauSelectionGrilleMots);
			}
}
