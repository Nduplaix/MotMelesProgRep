package jeu;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import scores.Score;

public class JoueurServeur implements Comparable<JoueurServeur>{
private String pseudoJoueur;
private String pseudoJoueurComplet;
private BufferedReader fluxEntrant;
private PrintWriter fluxSortant;
private Socket socket;
private boolean actif;
private int score;
private int scoreTotal;

/**
 * @return the pseudoJoueur
 */
public String getPseudoJoueur() {
	return pseudoJoueur;
}


public String getPseudoJoueurComplet() {
	return pseudoJoueurComplet;
}

/**
 * @return the fluxEntrant
 */
public BufferedReader getFluxEntrant() {
	return fluxEntrant;
}

/**
 * @return the fluxSortant
 */
public PrintWriter getFluxSortant() {
	return fluxSortant;
}

/**
 * @return the socket
 */
public Socket getSocket() {
	return socket;
}

public JoueurServeur(String pseudoJoueur,BufferedReader fluxEntrant,PrintWriter fluxSortant,Socket socket){
	this.pseudoJoueur=pseudoJoueur;
	this.fluxEntrant=fluxEntrant;
	this.fluxSortant=fluxSortant;
	this.socket=socket;
	this.actif=true;
	this.score=0;
	this.scoreTotal=0;
	pseudoJoueurComplet=pseudoJoueur;
	for (int i=pseudoJoueur.length();i<10;i++)
		pseudoJoueurComplet=pseudoJoueurComplet+" ";
}


public int getScore() {
	return score;
}


public void setScore(int score) {
	this.score = score;
}








public int getScoreTotal() {
	return scoreTotal;
}

public void setScoreTotal(int scoreTotal) {
	this.scoreTotal = scoreTotal;
}

/**
 * @return the actif
 */
public boolean isActif() {
	return actif;
}

/**
 * @param actif the actif to set
 */
public void setActif(boolean actif) {
	this.actif = actif;
}

@Override
public int compareTo(JoueurServeur joueur) {
	if (getScore()>joueur.getScore())
		return -1;
	if (getScore()<joueur.getScore())
		return 1;
	if (getScoreTotal()>joueur.getScoreTotal())
		return -1;
	if (getScoreTotal()<joueur.getScoreTotal())
		return 1;
	return pseudoJoueur.compareTo(joueur.pseudoJoueur);
	
}
}
