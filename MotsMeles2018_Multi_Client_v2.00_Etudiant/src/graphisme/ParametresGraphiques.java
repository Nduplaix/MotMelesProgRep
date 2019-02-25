package graphisme;

public class ParametresGraphiques {
	static public int tailleRef=16; 	//16 Taille d'un côté d'une case de la grille globale.
	static final public int nbLigsGrilleGlobale=37;		// Nombre de lignes de la grille globale.
	static final public int nbColsGrilleGlobale=62;		// Nombre de colonnes de la grille globale.
	static public AfficheurTexte afficheurTexte=new AfficheurTexte16();
	
	static public void configureAfficheurTexte(){
		afficheurTexte=meilleurAfficheurTexte();
	}
	static public AfficheurTexte meilleurAfficheurTexte(){
		if (tailleRef<=16)
			return new AfficheurTexte16();
		return new AfficheurTexte24();
	}
}
