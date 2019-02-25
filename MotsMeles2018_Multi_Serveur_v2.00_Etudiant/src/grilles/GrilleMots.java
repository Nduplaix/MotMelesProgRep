package grilles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Vector;

// Les grilles ont toujours une taille de 15X60

public class GrilleMots {

	private static int nbLigs=15;
	private static int nbCols=60;
	
	private Vector<MotGrille> grilleMots;
	
	private String[] mots;
	
	private char[][] carsGrille;
	
	private String titre;
	private String auteur;
	private String annee;
	private String type;
	private String cols;
	private String ligs;
	private String nbMots;
	
	/**
	 * @return the motsGrille
	 */
	public Vector<MotGrille> getGrilleMots() {
		return grilleMots;
	}
	
	public String[] getMots(){
		return mots;
	}
	
	public char[][] getCarsGrille(){
		return carsGrille;
	}
	public String getTitre(){
		return titre;
	}
	public String getAuteur(){
		return auteur;
	}
	public String getType(){
		return type;
	}
	public String getAnnee(){
		return annee;
	}
	public int getNbMots(){
		return mots.length;
	}
	
	//InputStream ips = MACLASSE.class.getClassLoader().getResourceAsStream("option.txt");
	//InputStreamReader ipsr = new InputStreamReader(ips);
	//BufferedReader br = new BufferedReader(ipsr);
	
	public GrilleMots(int numGrille){
		grilleMots=new Vector<MotGrille>();
		carsGrille=new char[nbLigs][nbCols];
		InputStream inputStream=null;
		InputStreamReader inputStreamReader=null;
		BufferedReader bufferedReader=null;
		try {
			inputStream = getClass().getResourceAsStream(File.separator+"grilles"+File.separator+"grille"+numGrille+".txt");
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			chargeMotsGrille(bufferedReader);
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
		} catch (Exception e) {
			System.err.println("Problème avec la ressource "+File.separator+"grilles"+File.separator+"grille"+numGrille+".txt");
			e.printStackTrace();
			System.exit(0);
		}
		Collections.sort(grilleMots);
		mots=new String[grilleMots.size()];
		for (int i=0;i<grilleMots.size();i++)
			mots[i]=grilleMots.elementAt(i).getMot();
	}
	/*
	public GrilleMots(int numGrille){
		grilleMots=new Vector<MotGrille>();
		//URL url=getClass().getResource(File.separator+"grilles"+File.separator+"grille"+numGrille+".txt");
		File ficGrille=null;
		 
		try {
			
			//ficGrille=new File(url.toURI());
			ficGrille=new File("grilles"+File.separator+"grille"+numGrille+".txt");
		} catch (Exception e) {
			
			e.printStackTrace();
			System.exit(0);
		}
		carsGrille=new char[nbLigs][nbCols];
		chargeMotsGrille(ficGrille);
		Collections.sort(grilleMots);
		mots=new String[grilleMots.size()];
		for (int i=0;i<grilleMots.size();i++)
			mots[i]=grilleMots.elementAt(i).getMot();
		
	}
	*/
	
public void chargeMotsGrille(BufferedReader bufferMotsGrille) {
		
		int numLigne=0;
		
		String ligne;
		try {
			
			while ((ligne = bufferMotsGrille.readLine()) != null) {
				if ((ligne.trim().length()>0)&&(ligne.charAt(0)!='#')){
					if (numLigne==0)
						titre=ligne.trim();
					else if (numLigne==1)
						auteur=ligne.trim();
					else if (numLigne==2)
						annee=ligne.trim();
					else if (numLigne==3)
						type=ligne.trim();
					else if (numLigne==4)
						cols=ligne.trim();
					else if (numLigne==5)
						ligs=ligne.trim();
					else if (numLigne==6)
						nbMots=ligne.trim();
					else if (numLigne<=nbLigs+6){
						//System.out.println(ligne);
						for (int i=0;i<nbCols;i++)
							carsGrille[numLigne-7][i]=ligne.charAt(i);
					}
					else
						grilleMots.add(new MotGrille(ligne));
				
				numLigne++;
				}

			}
		} catch (IOException e) {
			System.err.println("Impossible de lire la ressource contenant une grille dans le flux " + bufferMotsGrille);
			e.printStackTrace();
			System.exit(0);
		}

		
	}

	/*public void chargeMotsGrille(File fichierGrille) {
		
		BufferedReader bufferMotsGrille = null;
		int numLigne=0;
		try {
			bufferMotsGrille = new BufferedReader(new FileReader(fichierGrille));
		} catch (FileNotFoundException e) {
			System.err.println("Impossible d'ouvrir le fichier " + fichierGrille);
			e.printStackTrace();
			System.exit(0);
		}
		String ligne;
		try {
			
			while ((ligne = bufferMotsGrille.readLine()) != null) {
				if ((ligne.trim().length()>0)&&(ligne.charAt(0)!='#')){
					if (numLigne==0)
						titre=ligne.trim();
					else if (numLigne==1)
						auteur=ligne.trim();
					else if (numLigne==2)
						annee=ligne.trim();
					else if (numLigne==3)
						type=ligne.trim();
					else if (numLigne==4)
						cols=ligne.trim();
					else if (numLigne==5)
						ligs=ligne.trim();
					else if (numLigne==6)
						nbMots=ligne.trim();
					else if (numLigne<=nbLigs+6){
						//System.out.println(ligne);
						for (int i=0;i<nbCols;i++)
							carsGrille[numLigne-7][i]=ligne.charAt(i);
					}
					else
						grilleMots.add(new MotGrille(ligne));
				
				numLigne++;
				}

			}
		} catch (IOException e) {
			System.err.println("Impossible de lire le fichier " + fichierGrille);
			e.printStackTrace();
			System.exit(0);
		}

		try {
			bufferMotsGrille.close();
		} catch (IOException e) {
			System.err.println("Impossible de fermer le fichier " + fichierGrille);
			e.printStackTrace();
			System.exit(0);
		}
	}*/
public void afficheInfosGrille(){
	System.out.println("############################################################");
	System.out.println("# Titre");
	System.out.println(titre);
	System.out.println("# Auteur");
	System.out.println(auteur);
	System.out.println("# Année");
	System.out.println(annee);
	System.out.println("# Type");
	System.out.println(type);
	System.out.println("# Nombre de lignes");
	System.out.println(ligs);
	System.out.println("# Nombre de colonnes");
	System.out.println(cols);
	System.out.println("# Nombre de mots");
	System.out.println(nbMots);
}
	public void afficheMotsGrille(){
		System.out.println("############################################################");
		System.out.println("# Grille");
		for (int lig=0;lig<nbLigs;lig++){
			System.out.println();
			for (int col=0;col<nbCols;col++)
				System.out.print(carsGrille[lig][col]);
		}
		System.out.println();
		System.out.println("############################################################");
		System.out.println("# Mots");
		for (int i=0;i<grilleMots.size();i++)
			System.out.println(grilleMots.elementAt(i));
		System.out.println("############################################################");
	}
	public void verifieGrille(){
		MotGrille motGrille;
		String mot;
		int ligD,colD,ligF,colF;
		for (int i=0;i<grilleMots.size();i++){
			motGrille=grilleMots.elementAt(i);
			mot=motGrille.getMot();
			ligD=motGrille.getLigDeb();
			colD=motGrille.getColDeb();
			ligF=motGrille.getLigFin();
			colF=motGrille.getColFin();
			for (int k=0;k<mot.length();k++){
				if (carsGrille[ligD][colD]!=mot.charAt(k)){
					System.out.println("Problème avec le mot : "+mot);
					System.exit(0);
				}
				if (ligD<ligF)
					ligD++;
				if (colD<colF)
					colD++;
				if (ligD>ligF)
					ligD--;
				if (colD>colF)
					colD--;
			}
		}
	}
	public static void main(String[] args) {
		GrilleMots grilleMots=new GrilleMots(24);
		grilleMots.verifieGrille();
		grilleMots.afficheInfosGrille();
		grilleMots.afficheMotsGrille();
		
	}

}
