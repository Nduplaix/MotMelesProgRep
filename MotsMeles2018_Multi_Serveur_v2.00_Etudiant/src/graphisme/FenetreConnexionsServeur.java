package graphisme;

import jeu.GestionnaireJeuServeur;

public class FenetreConnexionsServeur extends FenetreGrilleCars {

	private static final long serialVersionUID = 1L;
	private PanneauConnexionsServeur panneauConnexions;
	public FenetreConnexionsServeur(String titre,GestionnaireJeuServeur gestionnaireJeuServeur){
		super(titre,new PanneauConnexionsServeur(gestionnaireJeuServeur));
		setModal(false);
		panneauConnexions=(PanneauConnexionsServeur)getContentPane();
	}
	
	public void ajouteNouveauPseudo(String pseudo){
		panneauConnexions.ajouteNouveauPseudo(pseudo);
	}

}
