package grilles;

public class GrillesMots {
	final static public int nbGrilles=25;
	final static private GrilleMots grillesMots[]=new GrilleMots[nbGrilles];
	
	static public GrilleMots[] getGrillesMots(){
		if (grillesMots[0]==null)
			for (int i=0;i<nbGrilles;i++)
				grillesMots[i]=new GrilleMots(i);
		return grillesMots;
	}
	public static void main(String[] args) {
		GrilleMots[] grillesMots=GrillesMots.getGrillesMots();
		for (int i=0;i<grillesMots.length;i++)
			grillesMots[i].afficheInfosGrille();

	}

}
