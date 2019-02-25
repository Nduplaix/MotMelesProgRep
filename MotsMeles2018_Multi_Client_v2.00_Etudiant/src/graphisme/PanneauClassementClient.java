package graphisme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jeu.GestionnaireJeuClient;
import son.Son;

public class PanneauClassementClient extends PanneauGrilleCars {

	
	public static final ImageRessource imageLogo=new ImageRessource("/graphisme/classement.png");

	
	private static final long serialVersionUID = 1L;
	
	// Les couleurs utilisées pour l'affichage.
	
	final static private int couleurTitre=5;
	final static private int couleurPseudos=10;
	
	static final private int ligTitre=10;
	static final private int largeurPseudos=20;
	
	private int ligPseudo;
	private int colPseudo;
	private GestionnaireJeuClient gestionnaireJeuClient;
	public PanneauClassementClient(GestionnaireJeuClient gestionnaireJeuClient,String titreClassement,String classement[]){
		super(0,Color.BLACK);
		this.gestionnaireJeuClient=gestionnaireJeuClient;
		ajouteClassement(titreClassement,classement);
		setFocusable(true);
		requestFocusInWindow();
		initialiseGestionnaireClavier();
	}
	
	public void ajouteClassement(String titreClassement,String classement[]){
		remplirGrille(' ',0,Color.BLACK);
		afficheLigneCentree(ligTitre,titreClassement,couleurTitre);
		ligPseudo=ligTitre+2;
		if (classement.length<=24)
			colPseudo=1+largeurPseudos;
		else if (classement.length<=48)
			colPseudo=1+(largeurPseudos/2);
		else
			colPseudo=1;
		for (int i=0;i<classement.length;i++)
			ajouteNouveauClassement(classement[i]);
		mettreAJourGrille();
		Son.jouerSonCourt(6);
	}
	
	public void ajouteNouveauClassement(String classement){
		afficheMotCarsGrille(ligPseudo,colPseudo,classement,couleurPseudos);
		ligPseudo++;
		if (ligPseudo==ParametresGraphiques.nbLigsGrilleGlobale-1){
			ligPseudo=ligTitre+2;
			colPseudo=colPseudo+largeurPseudos;
		}
		mettreAJourGrille();
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageLogo.getImage(),0,0,tailleRef*nbColsGrille,tailleRef*9,0,0,imageLogo.getImage().getWidth(),imageLogo.getImage().getHeight(),null);
	}
	
	
	private void initialiseGestionnaireClavier(){
		addKeyListener(new KeyAdapter(){
			  public void keyPressed(KeyEvent e) { 
				if (e.getExtendedKeyCode()==KeyEvent.VK_ESCAPE){
					 System.out.println("A Bientôt !");
					 System.exit(0);
				  }
				  
				  
				    	
		         }                  
	      });	
	}
	

}
