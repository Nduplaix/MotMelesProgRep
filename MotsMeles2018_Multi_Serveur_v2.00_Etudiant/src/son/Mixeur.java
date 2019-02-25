package son;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
public class Mixeur {

	private static Info mixer=setMixerOutput2();
	
	public static String[] getNomsMixers(){
		Info mixers[]=AudioSystem.getMixerInfo();
		String resultat[] =new String[mixers.length];
		for (int i=0;i<mixers.length;i++)
			resultat[i]=mixers[i].getName();
		return resultat;
	}
	
	public static Info[] getMixersInfos(){
		return AudioSystem.getMixerInfo();
	}
	
	public static void setMixer(String nomMixer){
		mixer=null;
		 Info[] infosMixers=getMixersInfos();
		 for (int i=0;i<infosMixers.length;i++)
				if (nomMixer.equals(infosMixers[i].getName())) {
					mixer=infosMixers[i];
					break;
				}
	}
	
	public static Info setMixerOutput(){
		Info info=null;
		 Info[] infosMixers=getMixersInfos();
		 for (int i=0;i<infosMixers.length;i++)
				if (infosMixers[i].getDescription().contains("Output")) {
					info=infosMixers[i];
					break;
				}
		 return info;
	}
	public static Info setMixerOutput2(){
		Info info=null;
		 Info[] infosMixers=getMixersInfos();
		 for (int i=0;i<infosMixers.length;i++){
			 try {
					AudioSystem.getClip(infosMixers[i]);
					return infosMixers[i];
				} catch (Exception e) {	
				}
				}
		 System.err.println("Impossible de configurer le mixeur de son !");
		System.exit(1);
		 return info;
	}
	
	public static Clip getClip(){
		if (mixer==null) return null;
		try {
			return AudioSystem.getClip(mixer);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Info getMixer(){
		return mixer;
	}
	public static void main(String[] args) {
		Info[] mixersInfos=getMixersInfos();
		Mixer mixer;
		for (int i=0;i<mixersInfos.length;i++){
			System.out.println("************************************");
			mixer=AudioSystem.getMixer(mixersInfos[i]);
			System.out.println(mixer.getMixerInfo());
			Line.Info[] lineInfos=mixer.getSourceLineInfo();
			if (lineInfos.length==0) System.out.println("*****");
			for (int j=0;j<lineInfos.length;j++){
			
			System.out.println(lineInfos[j]);
			}
		}
		
	}
}
