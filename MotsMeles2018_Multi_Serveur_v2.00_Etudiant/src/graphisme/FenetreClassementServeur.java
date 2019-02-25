package graphisme;

import jeu.GestionnaireJeuServeur;

public class FenetreClassementServeur extends FenetreGrilleCars {

	private static final long serialVersionUID = 1L;
	private PanneauClassementServeur panneauClassement;
	public FenetreClassementServeur(String titre,GestionnaireJeuServeur gestionnaireJeuServeur,String titreClassement,String classement[]){
		super(titre,new PanneauClassementServeur(gestionnaireJeuServeur,titreClassement,classement));
		setModal(false);
		panneauClassement=(PanneauClassementServeur)getContentPane();
	}
	
	public void ajouteClassements(String titreClassement,String classement[]){
		panneauClassement.ajouteClassement(titreClassement,classement);
	}

}
