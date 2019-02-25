package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Vector;

public class ReseauServeur {

	/**
	 * A FAIRE
	 * Méthode qui tente de convertir une chaîne de caractères en un numéro de port utilisable. 
	 * Si la chaîne de caractères donnée en paramètre ne correspond pas à un entier entre 
	 * 1024 et 65535 (au sens large) la méthode retourne -1 sinon elle retourne l'entier correspondant
	 * à la chaîne de caractère donnée en paramètre.
	 * 
	 * @param chaineCars Une chaîne de caractères (pouvant être null).
	 * @return Le numéro de port correspondant à la chaîne de caractères chaineCars si celle-ci correspond bien à un numéro de port valide (>=1024 et <=65535) sinon -1. 
	 */
	static public int portValide(String chaineCars){
		int port;
		port = Integer.parseInt(chaineCars);

		if (port > 1024 && port < 65535){
			return port;
		}

		return -1;
	}
	
	
	/**
	 * A FAIRE
	 * Méthode permettant de créer et retourner un ServerSocket à l'écoute d'un numéro de port donné.
	 * La méthode retourne null en cas de problème. Pour tout problème une trace de l'exception levée doit être affichée.
	 * 
	 * @param numPort Le numéro de port sur lequel est à l'écoute le ServerSocket.
	 * @param nbMaxConnexions Le nombre maximal de connexions possibles (nombre maximal de demande de connexions dans la file d'attente associé au ServerSocket créé).
	 * @return Un ServerSocket à l'écoute du port numPort ou null (en cas d'un problème de création).
	 */
	static public ServerSocket nouveauServerSocket(int numPort,int nbMaxConnexions){
		ServerSocket serverSocket=null;

		try {
			serverSocket = new ServerSocket(numPort,nbMaxConnexions);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return serverSocket;
	}
	
	/**
	 * A FAIRE
	 * Méthode permettant d'attendre une nouvelle connexion sur un ServerSocket. Le Socket
	 * correspondant à la nouvelle connexion est retourné. Si le temps d'attente maximal
	 * donné en paramètre est atteint null est retourné. Pour tout problème, une trace de l'excpetion levée
	 * est affiché et le programme est arrêté.
	 * 
	 * @param serverSocket Le ServerSocket attendant la nouvelle connexion.
	 * @param tempsAttenteMillisecondes Le temps d'attente maximal de la nouvelle connexion.
	 * @return Le Socket correspondant à la nouvelle connexion (null si le temps d'attente maximal est atteint).
	 */
	static public Socket nouvelleConnexion(ServerSocket serverSocket,int tempsAttenteMillisecondes){
		Socket socket=null;

		try {
			serverSocket.setSoTimeout(tempsAttenteMillisecondes);
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}

		try {
			socket = serverSocket.accept();
		} catch (SocketTimeoutException e) {
			System.out.println("fin du temps");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return socket;	
	}
	/**
	 * A FAIRE
	 * Méthode permettant de créer un BufferedReader correspondant à un flux entrant d'un Socket.
	 * La référence null est retournée pour tout problème.
	 * @param socket Le Socket.
	 * @return Le BufferedReader correspondant au flux entrant du Socket.
	 */
	static public BufferedReader fluxEntrant(Socket socket){
		InputStream inputStream;
		BufferedReader fluxEntrant=null;
		InputStreamReader inputStreamReader;

		try {
			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			fluxEntrant = new BufferedReader(inputStreamReader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fluxEntrant;
	}

	/**
	 * A FAIRE
	 * Méthode permettant de créer un PrintWriter correspondant à un flux sortant d'un Socket.
	 * La référence null est retournée pour tout problème.
	 * @param socket Le Socket.
	 * @return Le PrintWriter correspondant au flux sortant du Socket.
	 */
	static public PrintWriter fluxSortant(Socket socket){
		OutputStream outputStream;
		PrintWriter fluxSortant=null;
		try{
			outputStream=socket.getOutputStream();
			fluxSortant=new PrintWriter(outputStream);
		}catch(IOException e ){
			System.err.println( "Problème lors de la création du flux sortant\n");
			return null;
		}
		return fluxSortant;
	}
	
	
	/**
	 * A FAIRE
	 * Méthode permettant de lire une chaîne de caractères dans un flux entrant.
	 * La référence null est retournée pour tout problème.
	 * @param fluxEntrant Le BufferedReader dans lequel doit être réalisé la ligne.
	 * @return La chaîne de caractères lue.
	 */
	static public String lireLigne(BufferedReader fluxEntrant){
		String ligne=null;
		try {
			fluxEntrant.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("*** (Serveur) Reception : "+ligne);
		return ligne;
	}
	
	/**
	 * A FAIRE
	 * Méthode permettant de lire une chaîne de caractères dans un flux entrant avec un temps d'attente
	 * maximal.
	 * La référence null est retournée pour tout problème ou si le temps maximal est écoulé.
	 * @param fluxEntrant Le BufferedReader dans lequel doit être réalisé la ligne.
	 * @param tempsMaximalMilliSecondes Le temps d'attente maximal.
	 * @return La chaîne de caractères lue.
	 */
	
	static public String lireLigne(BufferedReader fluxEntrant,int tempsMaximalMilliSecondes){
		String ligne=null;
		long tempsFin = System.currentTimeMillis()+tempsMaximalMilliSecondes;
		long temps;
		try {
			do{
				temps = System.currentTimeMillis();
			}while (temps < tempsFin && !fluxEntrant.ready());
		}
		catch (IOException e){
			e.printStackTrace();
			return null;
		}

		try {
			if (fluxEntrant.ready()){
				try {
					ligne=fluxEntrant.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}





		System.out.println("*** (Serveur) Réception : "+ligne);
		return ligne;
		
	}
	
	/**
	 * A FAIRE
	 * Méthode permettant d'envoyer une chaîne de caractères dans un flux sortant.
	 * La valeur false est retournée pour tout problème. Dans le cas contraire la valeur true est retournée.
	 * @param fluxSortant Le PrintWriter dans lequel est envoyé la chaîne de caractères.
	 * @param ligne La chaîne de caractères à envoyer.
	 * @return false si un problème est survenu sinon true.
	 */
	static public boolean envoyerLigne(PrintWriter fluxSortant,String ligne){
		fluxSortant.println(ligne);
		fluxSortant.flush();
		System.out.println("*** (Serveur) Envoi : "+ligne);
		return true;
	}
	
	
	/**
	 * A FAIRE
	 * Méthode permettant d'envoyer un tableau de chaînes de caractères dans un flux sortant.
	 * La valeur false est retournée pour tout problème. Dans le cas contraire la valeur true est retournée.
	 * @param fluxSortant Le PrintWriter dans lequel est envoyé la chaîne de caractères.
	 * @param lignes Les chaînes de caractères à envoyer.
	 * @return false si un problème est survenu sinon true.
	 */
	static public boolean envoyerLignes(PrintWriter fluxSortant,String lignes[]){
		boolean result;
		for (int i = 0; i < lignes.length; i++){
			fluxSortant.println(lignes[i]);
			fluxSortant.flush();
		}
		return true;
	}
	
	static public boolean donneesDansFluxEntrant(BufferedReader fluxEntrant){
		try {
			if (fluxEntrant.ready())
				return true;
			
		} catch (IOException e) {
			return false;
		}
		return false;
	}
}
