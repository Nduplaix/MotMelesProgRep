package graphisme;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import jeu.GestionnaireJeuClient;
import son.Son;

public class PanneauMotsMelesClient extends PanneauGrilleCars {

	private static final long serialVersionUID = 1L;
	public static final ImageRessource imageBravo=new ImageRessource("/graphisme/bravo.png");

	// Les couleurs utilisées pour l'affichage.
	
	final static private Color couleurFenetreBloquee = new Color(100,255,100);
	final static private Color couleurFondMotBarreAutres=new Color(54,10,10);
	final static private int couleurCarGrilleMotsTrouvesAutres=13;
	final static private Color couleurFondCarGrilleMotsTrouvesAutres=new Color(255,150,255);
	
	final static private Color couleurFondGrilleMots=Color.WHITE;
	final static private int couleurCarGrilleMots=8;
	final static private int couleurCarTemps=1;
	final static private int couleurCarGrilleMotsTrouves=11;
	final static private Color couleurFondMotBarre=new Color(54,55,24);
	
	final static private Color couleurFondCarGrilleMotsTrouves=new Color(255,255,150);
	final static private Color couleurBarre=new Color(255,255,150,100);
	final static private int couleurMotsATrouves=5;
		
	static final private int nbLigsGrilleMots=15;		// Nombre de lignes de la grille de mots.
	static final private int nbColsGrilleMots=60;		// Nombre de colonnes de la grille de mots.
	
	// Attributs utilisés pour les clics.
	
	private int premierClicLig=-1;
	private int premierClicCol=-1;
	private int secondClicLig=-1;
	private int secondClicCol=-1;
	
	// Le gestionnaire de jeu.
	
	GestionnaireJeuClient gestionnaireJeu;
	
	private int positionsMotsATrouver[][]; // Les positions des mots à trouver (ligne,colonne,longueurMot).
	
	MouseMotionAdapter gestionnaireSouris1;
	MouseAdapter gestionnaireSouris2;
	
	private boolean termine;
	
	private long tempsDepart;
	private long tempsSecondes;
	
	private boolean fenetreBloquee;
	
	public PanneauMotsMelesClient(char[][] carsGrilleMots,String mots[],GestionnaireJeuClient gestionnaireJeu){
		super(0,Color.BLACK);
		this.gestionnaireJeu=gestionnaireJeu;
		termine=false;
		initialiseCarsGrilleMots(carsGrilleMots);
		initialiseMotsATrouver(mots);
		miseAJourNbMotsTrouves(0);
		mettreAJourGrille();
		fenetreBloquee=true;
		setFocusable(true);
		initialiseGestionnaireClavier();
		tempsDepart=System.currentTimeMillis();
		tempsSecondes=0;
	}
	
	public void debloque(){
		initialiseSouris();
		fenetreBloquee=false;
	}
	
	public void miseAJourTemps(){
		long temps;
		String tempsS;
		tempsSecondes=(System.currentTimeMillis()-tempsDepart)/1000;
		temps=tempsSecondes;
		tempsS=(temps/3600)+"H ";
		temps=temps%3600;
		tempsS=tempsS+(temps/60)+"MN ";
		temps=temps%60;
		if (temps<10)
			tempsS=tempsS+"0"+temps+"S";
		else
			tempsS=tempsS+temps+"S";
		
		afficheMotCarsGrille(nbLigsGrille-1,nbColsGrille-tempsS.length(),tempsS,couleurCarTemps,Color.BLACK);
		mettreAJourGrille();
	}
	
	public void miseAJourNbMotsTrouves(int nbMotsTrouves){
		afficheMotCarsGrille(nbLigsGrille-1,0,nbMotsTrouves+" MOTS TROUVES SUR "+positionsMotsATrouver.length,couleurCarTemps,Color.BLACK);
	}
	public void initialiseMotsATrouver(String mots[]){
		 positionsMotsATrouver=new int[mots.length][3];
		 int lig=nbLigsGrilleMots+2;
			int col=1;
			int maxTailleMot=0;
			String mot;
			for (int i=0;i<mots.length;i++){
				if (lig==nbLigsGrille-1){
					lig=nbLigsGrilleMots+2;
					col=col+maxTailleMot+1;
					maxTailleMot=0;
				}
				mot=mots[i];
				if (mot.length()>maxTailleMot)
					maxTailleMot=mot.length();
				afficheMotCarsGrille(lig,col,mot,couleurMotsATrouves,Color.BLACK);
			
				positionsMotsATrouver[i][0]=lig;
				positionsMotsATrouver[i][1]=col;
				positionsMotsATrouver[i][2]=mot.length();
				lig++;
				
			}
	}
	
	public void initialiseCarsGrilleMots(char[][] carsGrilleMots){
		afficheMatriceCarsGrille(1,1,carsGrilleMots, couleurCarGrilleMots,couleurFondGrilleMots);
	}
	
	
	public void barreCasesGrille(Graphics g,int ligD,int colD,int ligF,int colF,int largeurBarre){
		int xD,yD,xF,yF;
		xD=tailleRef*colD+(tailleRef/2);
		yD=tailleRef*ligD+(tailleRef/2);
		xF=tailleRef*colF+(tailleRef/2);
		yF=tailleRef*ligF+(tailleRef/2);
		BasicStroke line = new BasicStroke(largeurBarre);
		  ((Graphics2D)g).setStroke(line);
		  g.setColor(couleurBarre);
		g.drawLine(xD, yD, xF, yF);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if ((premierClicLig!=-1)&&(!termine))
			barreCasesGrille(g,premierClicLig,premierClicCol,secondClicLig,secondClicCol,8);
		if (termine)
			g.drawImage(imageBravo.getImage(),0,3*tailleRef,tailleRef*nbColsGrille,3*tailleRef+(tailleRef*9),0,0,imageBravo.getImage().getWidth(),imageBravo.getImage().getHeight(),null);
		else
			miseAJourTemps();
		if (fenetreBloquee){
			((Graphics2D)g).setComposite(AlphaComposite.SrcOver.derive(0.8f));
	            g.setColor(couleurFenetreBloquee);
	            g.fillRect(0,0,nbColsGrille*tailleRef,nbLigsGrille*tailleRef);
		}
	}
	public void barreMot(int i,int ligDeb,int colDeb,int ligFin,int colFin,boolean autres){
		Color couleurFond;
		if (autres) couleurFond=couleurFondMotBarreAutres;
		else couleurFond=couleurFondMotBarre;
		int couleur;
		
		int[] barre=new int[4];
		barre[0]=ligDeb;
		barre[1]=colDeb;
		barre[2]=ligFin;
		barre[3]=colFin;
		int tailleMot=positionsMotsATrouver[i][2];
		for (int k=0;k<tailleMot;k++)
			mettreCarGrille(positionsMotsATrouver[i][0],positionsMotsATrouver[i][1]+k,couleurFond);
		if (autres) {
			couleurFond=couleurFondCarGrilleMotsTrouvesAutres;
			couleur=couleurCarGrilleMotsTrouvesAutres;
		}
		else {
			couleurFond=couleurFondCarGrilleMotsTrouves;
			couleur=couleurCarGrilleMotsTrouves;
		}
		for (int k=0;k<tailleMot;k++){
		mettreCarGrille(ligDeb+1,colDeb+1,couleurFond);
		mettreCarGrille(ligDeb+1,colDeb+1,couleur);
		if (ligDeb<ligFin)
			ligDeb++;
		if (colDeb<colFin)
			colDeb++;
		if (ligDeb>ligFin)
			ligDeb--;
		if (colDeb>colFin)
			colDeb--;
		}
		mettreAJourGrille();
	}
	public int colCaseGrilleFromX(int x){
		return x/tailleRef;
	}
	public int ligCaseGrilleFromY(int y){
		return y/tailleRef;
	}
	
	public void termine(){
		termine=true;
		gestionnaireJeu.donneTempsMisSecondes((System.currentTimeMillis()-tempsDepart)/1000);
		removeMouseMotionListener(gestionnaireSouris1);
		removeMouseListener(gestionnaireSouris2);
		Son.jouerSonCourt(9);
	}
	
	private void initialiseSouris(){
		addMouseMotionListener(gestionnaireSouris1=new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e) {
					int x,y;
					int lig,col;
					x=e.getX(); y=e.getY();
					lig=ligCaseGrilleFromY(y);
					col=colCaseGrilleFromX(x);
					if (premierClicLig!=-1){
						secondClicLig=lig;
						secondClicCol=col;
					}
				}
				});
		addMouseListener(gestionnaireSouris2=new MouseAdapter(){
	         public void mouseClicked(MouseEvent e) {
	         int x,y;
	       
				int lig,col;
				x=e.getX(); y=e.getY();
				lig=ligCaseGrilleFromY(y);
				col=colCaseGrilleFromX(x);
				if ((premierClicLig==-1)&&(lig>0)&&(lig<=nbLigsGrilleMots)&&(col>0)&&(col<=nbColsGrilleMots)){
					Son.jouerSonCourt(3);
					premierClicLig=lig;
					premierClicCol=col;
					secondClicLig=lig;
					secondClicCol=col;
				}
				else if (premierClicLig!=-1){
					gestionnaireJeu.tenteBarrerMot(premierClicLig-1, premierClicCol-1, secondClicLig-1, secondClicCol-1);
					premierClicLig=-1;
					premierClicCol=-1;
					secondClicLig=-1;
					secondClicCol=-1;
					Son.jouerSonCourt(3);
				}
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
		            	
		         }                  
	      });	
	}	
}
