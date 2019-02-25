package jeu;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Vector;

import graphisme.FenetreClassementServeur;
import graphisme.FenetreConnexionsServeur;
import graphisme.FenetreGrilleCars;
import graphisme.FenetreMotsMelesServeur;
import graphisme.PanneauParametrageServeur;
import graphisme.PanneauSelectionGrilleMots;
import grilles.GrilleMots;
import grilles.MotGrille;
import reseau.ReseauServeur;
import son.Son;

public class GestionnaireJeuServeur {

	
	public final static String nomJeu="Cassoulet de mots ! - Multi (Serveur) - v2.00 - Jean-François Condotta - 2018";
	
	private FenetreMotsMelesServeur fenetreGrille;
	private FenetreClassementServeur 	fenetreClassement;
	private GrilleMots grilleMots;
	
	private Vector<MotGrille> motsG;
	private boolean motsGTrouves[];
	private String lignesGrille[];
	private String mots[];
	private String titreGrille;
	char[][] carsGrille;
	
	private int nbMotsTrouves;
	private long tempsMisSecondes;
	
	static private int nbMaxJoueurs=72;
	private Vector<JoueurServeur> joueurs;
	private boolean attenteConnexion;
	private int numPortEcoute;
	private ServerSocket priseEcoute;
	private String infosDernierMotTrouve;
	private int tempsAttente=50;
	private boolean finPartie;
	private boolean finClassement;
	private boolean finJeu;
	public GestionnaireJeuServeur(){
		attenteConnexion=true;
		joueurs=new Vector<JoueurServeur>();
		lanceJeu();
	}
	
	
	
	public void lanceJeu(){
	
		System.out.println("*****************************************************");
		System.out.println(nomJeu);
		System.out.println("*****************************************************");
		
		// Saisie d'un port valide pour être à l'écoute de demande de connexion des clients.
		parametreNumeroPortServeur();
		
		System.out.println("*****************************************************");
		System.out.println("Numéro de port du serveur : "+numPortEcoute);
		System.out.println("*****************************************************");
	
		
		// Création de la prise pour les demandes de connexion
		priseEcoute=ReseauServeur.nouveauServerSocket(numPortEcoute,nbMaxJoueurs);
		if (priseEcoute==null){
			System.err.println("!!! Impossible de créer le SocketServer pour les demandes de connexion ...");
			System.exit(1);
		}
		System.out.println("*****************************************************");
		System.out.println("Attente de connexions ...");
		System.out.println("*****************************************************");
		
		// Connexions avec différents joueurs
		connexionJoueurs();
		finJeu=false;
		do{
		// Sélection et initialisation de la grille de mots
		selectionEtInitialisationGrilleMots();
		
		// Envoi des mots de la grille aux joueurs
		envoiMotsGrilleJoueurs();
		
		// Mise à zéro des scores des joueurs
		for (int i=0;i<joueurs.size();i++)
			joueurs.get(i).setScore(0);
		
		// Création de la fenêtre de jeu avec la grille
		
		fenetreGrille=new FenetreMotsMelesServeur("Serveur - "+titreGrille+" - "+nomJeu,carsGrille,mots,this);
		fenetreGrille.setModal(false);
		fenetreGrille.setVisible(true);
		finPartie=false;
		envoiDebloqueGrilleJoueurs(); //Deblocage des grilles des joueurs
		while (nbMotsTrouves<mots.length){
			for (int i=0;i<joueurs.size();i++){
				JoueurServeur joueur=joueurs.get(i);
				if ((joueur.isActif())&&(ReseauServeur.donneesDansFluxEntrant(joueur.getFluxEntrant()))){
					String ligne=ReseauServeur.lireLigne(joueur.getFluxEntrant());
					if (ligne==null)
						joueur.setActif(false);
					else {
						if (tentativeBarrerMot(ligne)){
							joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.COUP_GAGNANT));
							miseAJourScores(joueur);
							envoiBarreMotJoueurs(joueur);
							// Envoyer le mot à barrer à tout le monde
							// Envoyer à tout le monde le score
						}else
							if (joueur.isActif())
								joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.COUP_PERDANT));
						
					}
				}		
			}
			attendre(tempsAttente);
		}
		
		// Fin de la partie
		fenetreGrille.termine();
		envoiPartieTermineeJoueurs();
		while (!finPartie){
			attendre(tempsAttente);
		}
		// Affichage et envoi du classement de la partie
		finClassement=false;
		envoiClassementLocal();
		while (!finClassement){
			attendre(tempsAttente);
		}
		envoiFinClassementJoueurs();
		// Affichage et envoi du classement clobal
				finClassement=false;
				envoiClassementGlobal();
				while (!finClassement){
					attendre(tempsAttente);
				}
		fenetreClassement.dispose();
		if (finJeu){
			envoiFinJeuJoueurs();
			attendre(3*1000);
			System.out.println("A Bientôt !");
			System.exit(0);
		}
		// On recommence une partie
		envoiRecommenceJoueurs();
		}
		while (true);
	}
	public void terminePartie(){
		finPartie=true;
	}
	
	public void termineClassement(){
		finClassement=true;
	}
	public void termineJeu(){
		finJeu=true;
	}
	
	
	public void donneTempsMisSecondes(long tempsMisSecondes){
		this.tempsMisSecondes=tempsMisSecondes;
	}
	
	
	public boolean tentativeBarrerMot(String ligne){
		if (! ligne.startsWith("COUP "))
			return false;
		ligne=ligne.replace("COUP ","");
		String infos[]=ligne.split(" ");
		int valeurs[]=new int[4];
		try{
		for (int i=0;i<4;i++){
			System.out.println(infos[i]+" *");
			valeurs[i]=Integer.parseInt(infos[i]);
		}
		}catch(Exception e){
			return false;
		}
		return tentativeBarrerMot(valeurs[0],valeurs[1],valeurs[2],valeurs[3]);
	}
	
	public boolean tentativeBarrerMot(int ligDeb,int colDeb,int ligFin,int colFin){
		MotGrille motG;
		int lD,cD,lF,cF;
		for (int i=0;i<motsG.size();i++)
			if (motsGTrouves[i]==false){
			motG=motsG.elementAt(i);
			lD=motG.getLigDeb();
			cD=motG.getColDeb();
			lF=motG.getLigFin();
			cF=motG.getColFin();
			if (((ligDeb==lD)&&(colDeb==cD)&&(ligFin==lF)&&(colFin==cF))||((ligDeb==lF)&&(colDeb==cF)&&(ligFin==lD)&&(colFin==cD))){
				motsGTrouves[i]=true;
				nbMotsTrouves++;
				fenetreGrille.barreMot(i,ligDeb,colDeb,ligFin,colFin,false);
				fenetreGrille.miseAJourNbMotsTrouves(nbMotsTrouves);
				Son.jouerSonCourt(6);
				infosDernierMotTrouve=i+" "+ligDeb+" "+colDeb+" "+ligFin+" "+colFin;
				return true;
			}
			}		
		return false;
	}
	
	
	
	private void parametreNumeroPortServeur(){
		FenetreGrilleCars fenetre;
		char portTab[]=new char[5];
		do{
		fenetre=new FenetreGrilleCars(nomJeu,new PanneauParametrageServeur("PORT ECOUTE",portTab));
		fenetre.setVisible(true);
		} while ((numPortEcoute=ReseauServeur.portValide(new String(portTab).trim()))==-1);
		
	}
	
	private boolean pseudoJoueurExiste(String pseudoJoueur){
		for (int i=0;i<joueurs.size();i++)
			if (pseudoJoueur.equals(joueurs.get(i).getPseudoJoueur()))
				return true;
		return false;
	}
	private void attendre(int tempsMilliSecondes){
		try {
			Thread.sleep(tempsMilliSecondes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void termineAttenteConnexions(){
		attenteConnexion=false;
	}

	private void connexionJoueurs(){
		FenetreConnexionsServeur fenetreConnexions=new FenetreConnexionsServeur("Connexions (Port "+numPortEcoute+") - "+nomJeu,this);
		fenetreConnexions.setVisible(true);
		BufferedReader fluxEntrant;
		PrintWriter fluxSortant;
		Socket socket;
		String pseudoJoueur=null;
		JoueurServeur joueur;
		while (attenteConnexion){
			socket=ReseauServeur.nouvelleConnexion(priseEcoute,100);
			if (socket!=null){
				System.out.println("Nouvelle Connexion !");
			fluxEntrant=ReseauServeur.fluxEntrant(socket);
			fluxSortant=ReseauServeur.fluxSortant(socket);
			if ((fluxEntrant!=null)&&(fluxSortant!=null))
				pseudoJoueur=ReseauServeur.lireLigne(fluxEntrant,500);
				if ((pseudoJoueur!=null)&&(joueurs.size()<nbMaxJoueurs)){
					if (! pseudoJoueurExiste(pseudoJoueur)){
						System.out.println("Nouveau Joueur : "+pseudoJoueur);
						joueur=new JoueurServeur(pseudoJoueur,fluxEntrant,fluxSortant,socket);
					joueurs.add(joueur);
					joueur.setActif(ReseauServeur.envoyerLigne(fluxSortant,MessagesEchanges.CONNEXION_ACCEPTEE));
					fenetreConnexions.ajouteNouveauPseudo(pseudoJoueur);
					} else 
						ReseauServeur.envoyerLigne(fluxSortant,MessagesEchanges.PSEUDO_UTILISE);		
				}
			}
			attendre(50);
		}
		fenetreConnexions.dispose();
	}
	
	private void selectionEtInitialisationGrilleMots(){
	FenetreGrilleCars fenetre;
	int indexGrilleMotsSelectionnee[]=new int[1];
	indexGrilleMotsSelectionnee[0]=0;
	fenetre=new FenetreGrilleCars("Sélectionnez une grille de mots (utiliser les flêches du clavier)"+" - "+nomJeu,new PanneauSelectionGrilleMots(indexGrilleMotsSelectionnee));
	fenetre.setVisible(true);
	grilleMots=new GrilleMots(indexGrilleMotsSelectionnee[0]);
	motsG=grilleMots.getGrilleMots();
	motsGTrouves=new boolean[motsG.size()];
	for (int i=0;i<motsG.size();i++)
		motsGTrouves[i]=false;
	lignesGrille=new String[15];
	carsGrille=grilleMots.getCarsGrille();
	for (int i=0;i<carsGrille.length;i++)
		lignesGrille[i]=new String(carsGrille[i]);
	mots=grilleMots.getMots();
	titreGrille=grilleMots.getTitre();
	nbMotsTrouves=0;
	}
	
	
	
	private void envoiMotsGrilleJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
		joueur=joueurs.elementAt(i);
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),titreGrille));
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),""+motsG.size()));
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLignes(joueur.getFluxSortant(),mots));
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLignes(joueur.getFluxSortant(),lignesGrille));
		}
	}
	
	private void envoiBarreMotJoueurs(JoueurServeur joueurGagnant){
		JoueurServeur joueur;
		String ligne="BARRE "+infosDernierMotTrouve;
		String ligneAutres="AUTRES_BARRE "+infosDernierMotTrouve;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			if (joueur==joueurGagnant)
				joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),ligne));
			else
				joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),ligneAutres));
			}
	}
	
	private void envoiPartieTermineeJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.TERMINEE));
			}
	}
	
	private void envoiDebloqueGrilleJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.DEBLOQUE_GRILLE));
			}
	}
	
	private void envoiRecommenceJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.RECOMMENCE));
			}
	}
	
	private void envoiFinJeuJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.FIN_JEU));
			}
	}
	private void envoiFinClassementJoueurs(){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),MessagesEchanges.FIN_CLASSEMENT));
			}
	}
	private void miseAJourScores(JoueurServeur joueur){
		JoueurServeur joueur2;
	
			for (int i=0;i<joueurs.size();i++){
				joueur2=joueurs.get(i);
				if (joueur==joueur2){
					joueur.setScore(joueur.getScore()+1);
					joueur.setScoreTotal(joueur.getScoreTotal()+1);
				}
				
			}
	}

	private void envoiClassementLocal(){
		Collections.sort(joueurs);
		JoueurServeur joueur;
		String classement[]=new String[joueurs.size()];
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (i+1<10)
				classement[i]=" "+(i+1)+":"+joueur.getPseudoJoueurComplet()+" "+joueur.getScore();
			else
				classement[i]=""+(i+1)+":"+joueur.getPseudoJoueurComplet()+" "+joueur.getScore();
			joueur.setScore(0);
		}
		fenetreClassement=new FenetreClassementServeur("Serveur - "+"Classement"+" - "+nomJeu,this,"CLASSEMENT DE LA PARTIE",classement);
		fenetreClassement.setVisible(true);
		envoiClassement(classement);
		
	}
	private void envoiClassement(String classement[]){
		JoueurServeur joueur;
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLigne(joueur.getFluxSortant(),""+classement.length));
		if (joueur.isActif())
			joueur.setActif(ReseauServeur.envoyerLignes(joueur.getFluxSortant(),classement));
		}
	}
	private void envoiClassementGlobal(){
		Collections.sort(joueurs);
		JoueurServeur joueur;
		String classement[]=new String[joueurs.size()];
		for (int i=0;i<joueurs.size();i++){
			joueur=joueurs.get(i);
			if (i+1<10)
				classement[i]=" "+(i+1)+":"+joueur.getPseudoJoueurComplet()+" "+joueur.getScoreTotal();
			else
				classement[i]=""+(i+1)+":"+joueur.getPseudoJoueurComplet()+" "+joueur.getScoreTotal();
			joueur.setScore(0);
		}
		fenetreClassement.ajouteClassements("CLASSEMENT GLOBAL", classement);
		envoiClassement(classement);
		
	}
}
