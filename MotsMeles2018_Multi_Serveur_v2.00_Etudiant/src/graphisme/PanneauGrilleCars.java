package graphisme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanneauGrilleCars extends JPanel {

	private static final long serialVersionUID = 1L;

	protected int tailleRef; 			// Taille d'un côté d'une case de la grille.
	protected int nbLigsGrille;		// Nombre de lignes de la grille.
	protected int nbColsGrille;		// Nombre de colonnes de la grille.

	private int hauteur;	// Hauteur du panneau.
	private int largeur;	// Largeur du panneau.

	/* Attributs correspondant à la mise en oeuvre d'une double grille (grille visible et grille non visible dans laquelle sont faites les modifications) */		
	private char carsGrille[][]; 					// Les caractères de la grille.
	private char carsGrilleVisible[][]; 			// Les caractères de la grille visible.
	private int couleursCarsGrille[][]; 			// Les couleurs des caractères de la grille.
	private int couleursCarsGrilleVisible[][]; 		// Les couleurs des caractères de la grille visible.
	private Color couleursFondGrille[][]; 			// Les couleurs de fond des caractères de la grille.
	private Color couleursFondGrilleVisible[][]; 	// Les couleurs de fond des caractères de la grille visible.

static private int tempsRaffraichissement=50; //17
	private Timer timerRaffraichissement;

	
	public PanneauGrilleCars(int couleurCar,Color couleurFond){
		super();
		
		/* Initialisation de la grille visible et de la grille non visible */
		this.tailleRef=ParametresGraphiques.tailleRef;
		this.nbLigsGrille=ParametresGraphiques.nbLigsGrilleGlobale;
		this.nbColsGrille=ParametresGraphiques.nbColsGrilleGlobale;
		couleursCarsGrille=new int[nbLigsGrille][nbColsGrille];
		couleursCarsGrilleVisible=new int[nbLigsGrille][nbColsGrille];
		couleursFondGrille=new Color[nbLigsGrille][nbColsGrille];
		couleursFondGrilleVisible=new Color[nbLigsGrille][nbColsGrille];
		carsGrilleVisible=new char[nbLigsGrille][nbColsGrille];
		carsGrille=new char[nbLigsGrille][nbColsGrille];
		remplirGrille(' ',couleurCar,couleurFond);
		
		/* Lancement du raffraichissement du panneau */
		lanceRaffraichissementPanneau();
		
		/* Calcul de la taille */
		this.hauteur=tailleRef*nbLigsGrille;
		this.largeur=tailleRef*nbColsGrille;
		this.setPreferredSize(new Dimension(largeur,hauteur));
	}

	synchronized public void mettreAJourGrille(){
		for (int lig=0;lig<nbLigsGrille;lig++)
			for (int col=0;col<nbColsGrille;col++){
				carsGrilleVisible[lig][col]=carsGrille[lig][col];
				couleursCarsGrilleVisible[lig][col]=couleursCarsGrille[lig][col];
				couleursFondGrilleVisible[lig][col]=couleursFondGrille[lig][col];
			}
	}

	public void mettreCarGrille(int lig,int col,char car,int couleurCar,Color couleurFond){
		carsGrille[lig][col]=car;
		couleursCarsGrille[lig][col]=couleurCar;
		couleursFondGrille[lig][col]=couleurFond;
	}

	public void mettreCarGrille(int lig,int col,int couleurCar){
		couleursCarsGrille[lig][col]=couleurCar;
	}
	public void mettreCarGrille(int lig,int col,char car){
		carsGrille[lig][col]=car;
	}

	public void mettreCarGrille(int lig,int col,char car,int couleurCar){
		carsGrille[lig][col]=car;
		couleursCarsGrille[lig][col]=couleurCar;
	}

	public void mettreCarGrille(int lig,int col,Color couleurFond){
		couleursFondGrille[lig][col]=couleurFond;
	}

	public void mettreCarGrille(int lig,int col,char car,Color couleurFond){
		carsGrille[lig][col]=car;
		couleursFondGrille[lig][col]=couleurFond;
	}

	public void remplirGrille(char car,int couleurCar,Color couleurFond){
		for (int lig=0;lig<nbLigsGrille;lig++)
			for (int col=0;col<nbColsGrille;col++)
				mettreCarGrille(lig,col,car,couleurCar,couleurFond);	
	}



	private void afficheCarGrilleVisible(Graphics g,int lig,int col){
		g.setColor(couleursFondGrilleVisible[lig][col]);
		g.fillRect(col*tailleRef,lig*tailleRef,tailleRef,tailleRef);
		ParametresGraphiques.afficheurTexte.afficheCar(g,col*tailleRef,lig*tailleRef,tailleRef,couleursCarsGrilleVisible[lig][col],carsGrilleVisible[lig][col]);
		//AfficheurTexte.afficheurTexte.afficheCar(g,col*tailleRef+1,lig*tailleRef,tailleRef+1,1,car);
	}




	public void afficheMatriceCarsGrille(int posLig,int posCol,char[][] cars,int couleurCar,Color couleurFond){
		for (int lig=0;lig<cars.length;lig++)
			for (int col=0;col<cars[0].length;col++)
				mettreCarGrille(lig+posLig, col+posCol,cars[lig][col],couleurCar,couleurFond);
	}

	public void afficheMotCarsGrille(int posLig,int posCol,String mot,int couleurCar,Color couleurFond){
		for (int i=0;i<mot.length();i++)
			mettreCarGrille(posLig, posCol+i,mot.charAt(i),couleurCar,couleurFond);
	}

	public void afficheMotCarsGrille(int posLig,int posCol,String mot,int couleurCar){
		for (int i=0;i<mot.length();i++)
			mettreCarGrille(posLig, posCol+i,mot.charAt(i),couleurCar);
	}
	
	private void afficheCarsGrille(Graphics g){
		for (int lig=0;lig<nbLigsGrille;lig++)
			for (int col=0;col<nbColsGrille;col++)
				afficheCarGrilleVisible(g,lig,col);
	}

	synchronized private void affiche(Graphics g){
		super.paintComponent(g);
		afficheCarsGrille(g);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		affiche(g);
	}

	private void lanceRaffraichissementPanneau() {
		timerRaffraichissement = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timerRaffraichissement.setRepeats(true);
		timerRaffraichissement.setDelay(tempsRaffraichissement);
		timerRaffraichissement.start();
	}

	public void afficheLigneCentree(int lig,String ligne,int couleurCar,Color couleurFond){
		int col=(nbColsGrille-ligne.length())/2;
		afficheMotCarsGrille(lig,col,ligne,couleurCar,couleurFond);
	}
	public void afficheLigneCentree(int lig,String ligne,int couleurCar){
		int col=(nbColsGrille-ligne.length())/2;
		afficheMotCarsGrille(lig,col,ligne,couleurCar);
	}
}
