package graphisme;

import java.awt.Graphics;

abstract public class AfficheurTexte {
	
	//static final public AfficheurTexte afficheurTexte=new AfficheurTexte();

	
	
	
	protected int largeurCarFinale=ParametresGraphiques.tailleRef;
	
public AfficheurTexte(){}

abstract public void afficheCar(Graphics g,int x,int y,int largeur,int coul,char car);



}
