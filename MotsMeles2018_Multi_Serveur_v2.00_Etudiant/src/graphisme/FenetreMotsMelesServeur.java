package graphisme;

import jeu.GestionnaireJeuServeur;

public class FenetreMotsMelesServeur extends FenetreGrilleCars {

	private static final long serialVersionUID = 1L;
	private PanneauMotsMelesServeur panneauGrille;
	public FenetreMotsMelesServeur(String titre,char[][] carsGrille,String mots[],GestionnaireJeuServeur gestionnaireJeu){
		super(titre,new PanneauMotsMelesServeur(carsGrille,mots,gestionnaireJeu));
		panneauGrille=(PanneauMotsMelesServeur)getContentPane();
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

}
