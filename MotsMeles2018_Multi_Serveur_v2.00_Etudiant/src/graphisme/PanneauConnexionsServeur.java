package graphisme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import jeu.GestionnaireJeuServeur;
import son.Son;

public class PanneauConnexionsServeur extends PanneauGrilleCars {

	
	public static final ImageRessource imageLogo=new ImageRessource("/graphisme/cassouletDeMotsServeur.png");

	
	private static final long serialVersionUID = 1L;
	
	// Les couleurs utilisées pour l'affichage.
	
	final static private int couleurTitre=5;
	final static private int couleurPseudos=10;
	
	static final private int ligTitre=10;
	static final private int largeurPseudos=18;
	
	private int ligPseudo;
	private int colPseudo;
	private GestionnaireJeuServeur gestionnaireJeuServeur;
	public PanneauConnexionsServeur(GestionnaireJeuServeur gestionnaireJeuServeur){
		super(0,Color.BLACK);
		this.gestionnaireJeuServeur=gestionnaireJeuServeur;
		ligPseudo=ligTitre+2;
		colPseudo=5;
		afficheLigneCentree(ligTitre,"ATTENTE DES CONNEXIONS DES JOUEURS ...",couleurTitre);
		mettreAJourGrille();
		setFocusable(true);
		requestFocusInWindow();
		initialiseGestionnaireSouris();
		initialiseGestionnaireClavier();
	}
	
	public void ajouteNouveauPseudo(String pseudo){
		afficheMotCarsGrille(ligPseudo,colPseudo,pseudo,couleurPseudos);
		ligPseudo++;
		if (ligPseudo==ParametresGraphiques.nbLigsGrilleGlobale-1){
			ligPseudo=ligTitre+2;
			colPseudo=colPseudo+largeurPseudos;
		}
		mettreAJourGrille();
		Son.jouerSonCourt(6);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageLogo.getImage(),0,0,tailleRef*nbColsGrille,tailleRef*9,0,0,imageLogo.getImage().getWidth(),imageLogo.getImage().getHeight(),null);
	}
	
	private void initialiseGestionnaireSouris(){
		addMouseListener(new MouseAdapter(){
	         public void mouseClicked(MouseEvent e) {
	        	 gestionnaireJeuServeur.termineAttenteConnexions();
	         }                
	      });	
	}
	private void initialiseGestionnaireClavier(){
		addKeyListener(new KeyAdapter(){
			  public void keyPressed(KeyEvent e) { 
				if (e.getExtendedKeyCode()==KeyEvent.VK_ESCAPE){
					  System.out.println("A Bientôt !");
					  System.exit(0);
				  }
				  else if (e.getKeyCode()==KeyEvent.VK_ENTER)
						  gestionnaireJeuServeur.termineAttenteConnexions();
				  
				    	
		         }                  
	      });	
	}
	
public static void main(String[] args) {
	FenetreConnexionsServeur 	fen=new FenetreConnexionsServeur("Connexions ...",null);
	
	fen.setVisible(true);
	for (int i=0;i<72;i++){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	fen.ajouteNouveauPseudo("AAAAAAAAAAAAAAAA");
	}
	}
}
