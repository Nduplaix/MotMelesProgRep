package jeu;

import graphisme.ParametresGraphiques;

public class CassouletDeMotsServeur {

	public static void main(String[] args) {
		if (args.length==1){
			int tailleRef=Integer.parseInt(args[0]);
			if ((tailleRef>=10)&&(tailleRef<=50)){
				ParametresGraphiques.tailleRef=tailleRef;
				ParametresGraphiques.configureAfficheurTexte();
			}
		}
		new GestionnaireJeuServeur();
	}

}
