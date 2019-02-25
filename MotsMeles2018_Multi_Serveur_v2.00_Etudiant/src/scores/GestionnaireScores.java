package scores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

public class GestionnaireScores {

	public void ajouteScore(int numGrilleMots,String pseudo,long tempsSecondes){
		Score score=new Score(new Date(),pseudo,tempsSecondes);	
		BufferedWriter buffer = null;
	        FileWriter fichier = null;
	            try {
					fichier = new FileWriter("grille"+numGrilleMots+".scorescm1", true);
				
	            buffer = new BufferedWriter(fichier);
	            buffer.write(score.toString());
	            buffer.newLine();
	            buffer.close();
	            fichier.close();
	            }catch (IOException e) {
	            	System.err.println("Problème avec le fichier "+"grille"+numGrilleMots+".scorescm1"+" !");
					e.printStackTrace();
				} 
	}
	
	public Vector<Score> donneScores(int numGrilleMots){
		Vector<Score> scores=new Vector<Score>();
		Score score=null;;
		String ligne;
		BufferedReader buffer = null;
	        FileReader fichier = null;
	            try {
					fichier = new FileReader("grille"+numGrilleMots+".scorescm1");
				
	            buffer = new BufferedReader(fichier);
	            while ((ligne = buffer.readLine()) != null){
	            score=new Score(ligne);
	            scores.add(score);
	            }
	            buffer.close();
	            fichier.close();
	            }catch (IOException e) {
	            	System.err.println("Problème avec le fichier "+"grille"+numGrilleMots+".scorescm1"+" !");
					e.printStackTrace();
				} 
	     Collections.sort(scores);
		return scores;
	}
	
	
	
	public static void main(String[] args) {
		GestionnaireScores gestionnaire=new GestionnaireScores();
		//gestionnaire.ajouteScore(999,"ZSEUDO",8000);
		Vector<Score> scores=gestionnaire.donneScores(999);
		for (int i=0;i<scores.size();i++){
			System.out.println(scores.elementAt(i));
		}
	}

}
