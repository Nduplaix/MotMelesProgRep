package son;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Son {
	
	static private Clip playerSonFond=null;
			
	static private int nombreSonsCourts=9;
			static private Clip playersSonsCourts[]=playersSonsCourts();
	
	public static void preparerSons(){
		 playersSonsCourts=playersSonsCourts();
	}
	private static Clip[] playersSonsCourts(){
		
		Info infoMixer=Mixeur.getMixer();
		if (infoMixer==null) return null;
		playersSonsCourts=new Clip[nombreSonsCourts];
		//System.out.println(playersSonsCourts.length);
		AudioInputStream sonAudio;
		
		for (int i=1;i<=nombreSonsCourts;i++){
		try {
			playersSonsCourts[i-1]=AudioSystem.getClip(infoMixer);
			//playersSonsCourts[i-1].addLineListener(new LigneListener(playerSonCourt));
		} catch (Exception e) {
			System.err.println("Impossible de charger les sons !");
			return null;
		}
		sonAudio=chargementSon("son"+i+".wav");
	try {
		playersSonsCourts[i-1].open(sonAudio);
	} catch (LineUnavailableException e) {
		e.printStackTrace();
		System.err.println("Impossible de charger les sons !");
		return null;
	} catch (IOException e) {
		e.printStackTrace();
		System.err.println("Impossible de charger les sons !");
		return null;
	}
		
		}
		return playersSonsCourts;
	}
	private static AudioInputStream  chargementSon(String nomFichier){
		AudioInputStream son=null;	
			try {
				son=AudioSystem.getAudioInputStream(Son.class.getResource(nomFichier));
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
				System.err.println("Impossible de charger la ressource "+nomFichier+" !");
				System.exit(1);
			}
		return son;
	}
	
static public void supprimerSonFond(){
		if (playerSonFond!=null)
			playerSonFond.close();;
	}
	
static public void pauseSonFond(){
		if (playerSonFond!=null)
			playerSonFond.stop();
	}
static public boolean sonFondActif(){
	if (playerSonFond==null) return false;
	return playerSonFond.isActive();
		
}
	static public void repriseSonFond(){
		if (playerSonFond!=null)
			playerSonFond.start();
	}
	
	static public void jouerSonFond(int son){
		Info infoMixer=Mixeur.getMixer();
		if (infoMixer==null) return;
		
		if (playerSonFond!=null) {
			playerSonFond.stop();
			playerSonFond.close();
		}
		
		AudioInputStream sonAudio;
		try {
			playerSonFond=AudioSystem.getClip(infoMixer);
		} catch (Exception e) {
			System.err.println("Impossible de jouer la musique de fond ...");
			return;
		}
		switch(son){
		case 1:
			sonAudio=chargementSon("musiqueFond1.wav");
			jouerUnSonEnFond(sonAudio);
			break;
		}
	}
	
	
	static private void jouerUnSonEnFond(AudioInputStream son){
		
	try {
		playerSonFond.open(son);
		playerSonFond.loop(Clip.LOOP_CONTINUOUSLY);
	} catch (Exception e) {
		System.err.println("Impossible de jouer la musique de fond !");
	}
}
	static public void jouerSonCourt(int son){
		Info infoMixer=Mixeur.getMixer();
		if (infoMixer==null) return;
		
		//System.out.println(playersSonsCourts.length);
		playersSonsCourts[son-1].setFramePosition(0);
		//playersSonsCourts[son-1].stop();
		playersSonsCourts[son-1].start();
	}

	
	
	
}
