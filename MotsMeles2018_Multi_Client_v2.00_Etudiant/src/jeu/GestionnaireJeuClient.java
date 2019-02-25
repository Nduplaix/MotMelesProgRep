package jeu;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import graphisme.FenetreClassementClient;
import graphisme.FenetreGrilleCars;
import graphisme.FenetreMotsMeles;
import graphisme.PanneauParametrageClient;
import reseau.ReseauClient;
import son.Son;

public class GestionnaireJeuClient {

	
	public final static String nomJeu="Cassoulet de mots ! - Multi (Client) - v2.00 - Jean-François Condotta - 2018";

	private FenetreMotsMeles fenetreGrille;
	private FenetreClassementClient fenetreClassement;
	private boolean motsGTrouves[];
	
	private String classement[];
	private char carsGrille[][];
	private String mots[];
	private String titreGrille;
	
	private int nbMotsTrouves;
	private long tempsMisSecondes;
	
	
	private int portServeur;
	private InetAddress adresseIPServeur;
	private Socket socket;
	private BufferedReader fluxEntrant;
	private PrintWriter fluxSortant;
	
	private String pseudoJoueur;
	private boolean finPartie;
	private boolean finClassement;
	private int tempsAttente=100;
	public GestionnaireJeuClient(){
		lanceJeu();
	}
	
	private String parametrePseudoJoueur(){
		FenetreGrilleCars fenetre;
		char pseudoTab[]=new char[10];
		fenetre=new FenetreGrilleCars(nomJeu,new PanneauParametrageClient("VOTRE PSEUDO",pseudoTab));
		fenetre.setVisible(true);
		String pseudo=new String(pseudoTab);
		return pseudo.trim();
	}
	
	private String parametreAdresseServeur(){
		FenetreGrilleCars fenetre;
		char adresseTab[]=new char[21];
		fenetre=new FenetreGrilleCars(nomJeu,new PanneauParametrageClient("ADRESSE IP ET PORT SERVEUR",adresseTab));
		fenetre.setVisible(true);
		return (new String(adresseTab)).trim();
	}
	
	public void lanceJeu(){
		
		System.out.println("*****************************************************");
		System.out.println(nomJeu);
		System.out.println("*****************************************************");
		
		// Paramétrage du pseudo du joueur
		pseudoJoueur=parametrePseudoJoueur();
		System.out.println("Pseudo du Joueur : "+pseudoJoueur);
		System.out.println("*****************************************************");
		
		// Saisie de l'adresse applicative du serveur de jeu
		parametreAdresseIPPortServeur();
		System.out.println("Adresse IP du serveur : "+adresseIPServeur);
		System.out.println("Numéro de port du serveur : "+portServeur);
		System.out.println("*****************************************************");
		
		// Connexion avec le serveur de jeu
		connexion();
		System.out.println("Connexion avec le serveur effectuée !");
		System.out.println("*****************************************************");
		
		// Envoi du pseudo et réception de la réponse du serveur.
		ReseauClient.envoyerLigne(fluxSortant,pseudoJoueur);
		String reponseServeur=ReseauClient.lireLigne(fluxEntrant);
		if (reponseServeur.equals(MessagesEchanges.PSEUDO_UTILISE))
			System.exit(0);
		
		do{
		// Réception de la grille
		receptionGrille();
		
		// Création de la fenêtre de jeu avec la grille
		nbMotsTrouves=0;
		fenetreGrille=new FenetreMotsMeles(pseudoJoueur+" - "+titreGrille+" - "+nomJeu,carsGrille,mots,this);
		fenetreGrille.setModal(false);
		fenetreGrille.setVisible(true);
		finPartie=false;
		String ligne=ReseauClient.lireLigne(fluxEntrant);
		if (! ligne.equals(MessagesEchanges.DEBLOQUE_GRILLE)){
			System.err.println("!!! Message "+MessagesEchanges.DEBLOQUE_GRILLE+" attendu !!!");
			System.exit(1);
		}
		fenetreGrille.debloque();
		while (!finPartie){
			if (ReseauClient.donneesDansFluxEntrant(fluxEntrant)){
				ligne=ReseauClient.lireLigne(fluxEntrant);
				traiteLigneServeur(ligne);
			}
			attendre(tempsAttente);
		}
		
		// Réception et affichage du classement
		finClassement=false;
		receptionClassement();
		fenetreGrille.dispose();
		fenetreClassement=new FenetreClassementClient(pseudoJoueur+" - "+"Classement"+" - "+nomJeu,this,"CLASSEMENT DE LA PARTIE",classement);
		fenetreClassement.setVisible(true);
		ligne=ReseauClient.lireLigne(fluxEntrant);
		if (! ligne.equals(MessagesEchanges.FIN_CLASSEMENT)){
			System.err.println("!!! Message "+MessagesEchanges.FIN_CLASSEMENT+" attendu !!!");
			System.exit(1);
		}
		receptionClassement();
		fenetreClassement.ajouteClassements("CLASSEMENT GLOBAL", classement);
		
		ligne=ReseauClient.lireLigne(fluxEntrant); 
		fenetreClassement.dispose();
		// Fin du jeu
		if (ligne.equals(MessagesEchanges.FIN_JEU)){
				System.out.println("A Bientôt !");
				System.exit(0);
			}
		 
		if (! ligne.equals(MessagesEchanges.RECOMMENCE)){
			System.err.println("!!! Message "+MessagesEchanges.RECOMMENCE+" attendu !!!");
			System.exit(1);
		}
		
		} while (true);
		
	}
	
	
	public void traiteLigneServeur(String ligne){
		if (ligne.equals(MessagesEchanges.COUP_GAGNANT)){
			nbMotsTrouves++;
			fenetreGrille.miseAJourNbMotsTrouves(nbMotsTrouves);
			Son.jouerSonCourt(6);
		}
		else if (ligne.equals(MessagesEchanges.COUP_PERDANT))
			Son.jouerSonCourt(4);
		else if (ligne.startsWith("BARRE "))
			barreMot(ligne.replace("BARRE ",""));
		else if (ligne.startsWith("AUTRES_BARRE "))
			barreMotAutres(ligne.replace("AUTRES_BARRE ",""));
		else if (ligne.equals(MessagesEchanges.TERMINEE)){
			fenetreGrille.termine();
			finPartie=true;
		}
		
		
	}
	
	public void barreMot(String ligne){
		String infos[]=ligne.split(" ");
		int valeurs[]=new int[5];
		try{
		for (int i=0;i<5;i++)
			valeurs[i]=Integer.parseInt(infos[i]);

		}catch(Exception e){
			e.printStackTrace();
			System.err.println("!!! Problème avec le message BARRE !!!");
			System.exit(1);
		}
		fenetreGrille.barreMot(valeurs[0],valeurs[1],valeurs[2],valeurs[3],valeurs[4],false);
		
	}
	
	public void barreMotAutres(String ligne){
		String infos[]=ligne.split(" ");
		int valeurs[]=new int[5];
		try{
		for (int i=0;i<5;i++)
			valeurs[i]=Integer.parseInt(infos[i]);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("!!! Problème avec le message AUTRES_BARRE !!!");
			System.exit(1);
		}
		fenetreGrille.barreMot(valeurs[0],valeurs[1],valeurs[2],valeurs[3],valeurs[4],true);
		Son.jouerSonCourt(8);
	}
	
	public void donneTempsMisSecondes(long tempsMisSecondes){
		this.tempsMisSecondes=tempsMisSecondes;
	}
	public void tenteBarrerMot(int ligDeb,int colDeb,int ligFin,int colFin){
		ReseauClient.envoyerLigne(fluxSortant,"COUP "+ligDeb+" "+colDeb+" "+ligFin+" "+colFin);	
	}
	
	
	private void parametreAdresseIPPortServeur(){
		portServeur=-1;
		adresseIPServeur=null;
		
		do{
		String saisie=parametreAdresseServeur();
		int positionDeuxPoints=saisie.lastIndexOf(':');
		if ((positionDeuxPoints!=-1)&&(positionDeuxPoints<(saisie.length()-1))){
			portServeur=ReseauClient.portValide(saisie.substring(positionDeuxPoints+1));
			adresseIPServeur=ReseauClient.adresseIPValide(saisie.substring(0,positionDeuxPoints));
		}
		} while((adresseIPServeur==null)||(portServeur==-1));
	}
	
	private void connexion(){
		socket=ReseauClient.connexion(adresseIPServeur, portServeur);
		fluxEntrant=ReseauClient.fluxEntrant(socket);
		fluxSortant=ReseauClient.fluxSortant(socket);
	}
	
	public void receptionGrille(){
		String ligne=ReseauClient.lireLigne(fluxEntrant);
		titreGrille=ligne;
		ligne=ReseauClient.lireLigne(fluxEntrant);
		int nbMots=Integer.parseInt(ligne);
		mots=new String[nbMots];
		for (int i=0;i<nbMots;i++)
			mots[i]=ReseauClient.lireLigne(fluxEntrant);
		carsGrille=new char[15][60];
		for (int i=0;i<15;i++){
			ligne=ReseauClient.lireLigne(fluxEntrant);
			for (int j=0;j<60;j++)
				carsGrille[i][j]=ligne.charAt(j);
		}
	}
	
	public void receptionClassement(){
		String ligne=ReseauClient.lireLigne(fluxEntrant);
		int taille=Integer.parseInt(ligne);
		classement=new String[taille];
		for (int i=0;i<taille;i++)
			classement[i]=ReseauClient.lireLigne(fluxEntrant);
	}
	
	private void attendre(int tempsMilliSecondes){
		try {
			Thread.sleep(tempsMilliSecondes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
