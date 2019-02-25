package scores;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import grilles.MotGrille;

public class Score implements Comparable<Score>{
private String pseudo;
private Date date;
private long tempsSecondes;
final static private char separateur='*';
final static DateFormat dateFormatFR = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, new Locale("FR","fr"));

public Score(Date date,String pseudo,long tempsSecondes){
	this.date=date;
	this.tempsSecondes=tempsSecondes;
	this.pseudo=pseudo;
}

public Score(String ligne){
	String infos[]=ligne.split("\\"+separateur);
	pseudo=infos[0];
	try {
		date=dateFormatFR.parse(infos[1]);
	} catch (ParseException e) {
		System.err.println("Impossible de lire la date : "+infos[1]);
		e.printStackTrace();
	}
	tempsSecondes=Long.parseLong(infos[2]);
}


/**
 * @return Le pseudo.
 */
public String getPseudo() {
	return pseudo;
}



/**
 * @return La date.
 */
public String getDate() {
	return dateFormatFR.format(date);
}



/**
 * @return Le temps en seconde.
 */
public long getTempsSecondes() {
	return tempsSecondes;
}



/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return pseudo+separateur+dateFormatFR.format(date)+separateur+tempsSecondes;
}

@Override
public int compareTo(Score score) {
	if (tempsSecondes<score.tempsSecondes)
		return -1;
	if (tempsSecondes>score.tempsSecondes)
		return 1;
	if (date.compareTo(score.date)!=0)
		return date.compareTo(score.date);
	return pseudo.compareTo(score.pseudo);
	
}

}
