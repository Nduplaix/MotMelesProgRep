package son;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class  LigneListener implements LineListener {
	private Clip line;
	public  LigneListener(Clip line){
		this.line=line;
	}
	public void update (LineEvent le) {

    LineEvent.Type type = le.getType();
     if (type == LineEvent.Type.STOP) {
        line.close();
       
    }
}
}
