package graphisme;

import jeu.GestionnaireJeuClient;

public class FenetreClassementClient extends FenetreGrilleCars {

	private static final long serialVersionUID = 1L;
	private PanneauClassementClient panneauClassement;
	public FenetreClassementClient(String titre,GestionnaireJeuClient gestionnaireJeuClient,String titreClassement,String classement[]){
		super(titre,new PanneauClassementClient(gestionnaireJeuClient,titreClassement,classement));
		setModal(false);
		panneauClassement=(PanneauClassementClient)getContentPane();
	}
	
	public void ajouteClassements(String titreClassement,String classement[]){
		panneauClassement.ajouteClassement(titreClassement,classement);
	}

}
