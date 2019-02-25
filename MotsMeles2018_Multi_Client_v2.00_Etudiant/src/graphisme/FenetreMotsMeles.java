package graphisme;

import jeu.GestionnaireJeuClient;

public class FenetreMotsMeles extends FenetreGrilleCars {

	private static final long serialVersionUID = 1L;
	private PanneauMotsMelesClient panneauGrille;
	public FenetreMotsMeles(String titre,char[][] carsGrille,String mots[],GestionnaireJeuClient gestionnaireJeu){
		super(titre,new PanneauMotsMelesClient(carsGrille,mots,gestionnaireJeu));
		panneauGrille=(PanneauMotsMelesClient)getContentPane();
	}
	
	public void barreMot(int i,int ligDeb,int colDeb,int ligFin,int colFin,boolean autres){
		panneauGrille.barreMot(i,ligDeb,colDeb,ligFin,colFin,autres);
	}
	public void miseAJourNbMotsTrouves(int nbMotsTrouves){
		panneauGrille.miseAJourNbMotsTrouves(nbMotsTrouves);
	}
	public void termine(){
		panneauGrille.termine();
	}
	public void debloque(){
		panneauGrille.debloque();
	}

}
