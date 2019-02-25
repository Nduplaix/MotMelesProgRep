package grilles;

public class MotGrille implements Comparable<MotGrille>{
private String mot;
private int ligDeb;
private int colDeb;
private int ligFin;
private int colFin;

	public MotGrille(String ligneInformations){
		String infos[]=ligneInformations.split(" ");
		mot=infos[0];
		ligDeb=Integer.parseInt(infos[1]);
		colDeb=Integer.parseInt(infos[2]);
		ligFin=Integer.parseInt(infos[3]);
		colFin=Integer.parseInt(infos[4]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mot+" "+ligDeb+" "+colDeb+" "+ligFin+" "+colFin;
	}

	/**
	 * @return the mot
	 */
	public String getMot() {
		return mot;
	}

	/**
	 * @return the ligDeb
	 */
	public int getLigDeb() {
		return ligDeb;
	}

	/**
	 * @return the colDeb
	 */
	public int getColDeb() {
		return colDeb;
	}

	/**
	 * @return the ligFin
	 */
	public int getLigFin() {
		return ligFin;
	}

	/**
	 * @return the colFin
	 */
	public int getColFin() {
		return colFin;
	}

	

	@Override
	public int compareTo(MotGrille motGrille) {
		
		if (mot.length()<motGrille.mot.length())
			return -1;
		if (mot.length()>motGrille.mot.length())
			return 1;
			
		return mot.compareTo(motGrille.mot);
	}
	
	
}
