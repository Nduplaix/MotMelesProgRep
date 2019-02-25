package graphisme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import son.Son;

public class PanneauParametrageServeur extends PanneauGrilleCars {

	
	public static final ImageRessource imageLogo=new ImageRessource("/graphisme/cassouletDeMotsServeur.png");

	
	private static final long serialVersionUID = 1L;
	
	// Les couleurs utilisées pour l'affichage.
	
	final static private int couleurQuestion=5;
	final static private int couleurSaisie=10;
	final static private Color couleurFondQuestion=Color.BLACK;
	final static private Color couleurFondSaisie=new Color(20,20,60);
	
	private char saisie[];
	private String saisieS;
	static final private int ligQuestion=20;
	static final private int ligSaisie=24;
	
	public PanneauParametrageServeur(String question,char[] saisie){
		super(0,Color.BLACK);
		this.saisie=saisie;
		saisieS="";
		for (int i=0;i<saisie.length;i++)
			saisie[i]=' ';
		afficheLigneCentree(ligQuestion,question,couleurQuestion,couleurFondQuestion);
		afficheSaisie();
		mettreAJourGrille();
		setFocusable(true);
		requestFocusInWindow();
		//initialiseGestionnaireSouris();
		initialiseGestionnaireClavier();
	}
	
	
	private void afficheSaisie(){
		String saisieA=saisieS;
		while (saisieA.length()<saisie.length)
			saisieA=saisieA+' ';
		afficheLigneCentree(ligSaisie,saisieA,couleurSaisie,couleurFondSaisie);
	}
	
	protected void ajouteCarSaisie(String nouveauCar){
		if (saisieS.length()<saisie.length){
			saisie[saisieS.length()]=nouveauCar.charAt(0);
			saisieS=saisieS+nouveauCar;
			afficheSaisie();
			mettreAJourGrille();
			Son.jouerSonCourt(3);
		} else Son.jouerSonCourt(7);
		
	}
	
	protected void supprimeCarSaisie(){
		if (saisieS.length()>0){
			saisieS=saisieS.substring(0,saisieS.length()-1);
			saisie[saisieS.length()]=' ';
			afficheSaisie();
			mettreAJourGrille();
			Son.jouerSonCourt(3);
		} else Son.jouerSonCourt(7);
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageLogo.getImage(),0,3*tailleRef,tailleRef*nbColsGrille,3*tailleRef+(tailleRef*9),0,0,imageLogo.getImage().getWidth(),imageLogo.getImage().getHeight(),null);
	}
	
	private void initialiseGestionnaireSouris(){
		addMouseListener(new MouseAdapter(){
	         public void mouseClicked(MouseEvent e) {
	        	SwingUtilities.windowForComponent(PanneauParametrageServeur.this).dispose();
	         }                
	      });	
	}
	private void initialiseGestionnaireClavier(){
		addKeyListener(new KeyAdapter(){
			  public void keyPressed(KeyEvent e) { 
				  if ((e.getKeyChar()==' ')||((e.getKeyChar()>='a')&&(e.getKeyChar()<='z'))||((e.getKeyChar()>='A')&&(e.getKeyChar()<='Z')||((e.getKeyChar()>='0')&&(e.getKeyChar()<='9'))))
						  ajouteCarSaisie((""+e.getKeyChar()).toUpperCase());
				  else if (e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					  supprimeCarSaisie();
				  else if (e.getExtendedKeyCode()==KeyEvent.VK_ESCAPE){
					  System.out.println("A Bientôt !");
					  System.exit(0);
				  }
				  else if (e.getKeyCode()==KeyEvent.VK_ENTER)
					  if (saisie[0]!=' ')
						  SwingUtilities.windowForComponent(PanneauParametrageServeur.this).dispose();
				    	
		         }                  
	      });	
	}
	
public static void main(String[] args) {
		
	PanneauParametrageServeur panneauParametrage=new PanneauParametrageServeur("VOTRE PSEUDO",new char[16]);
	
	(new FenetreGrilleCars("Paramétrage",panneauParametrage)).setVisible(true);;
	System.out.println(panneauParametrage.getPreferredSize());
			}
}
