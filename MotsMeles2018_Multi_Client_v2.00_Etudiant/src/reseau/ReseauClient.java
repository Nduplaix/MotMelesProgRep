package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReseauClient {

	/**
	 * A FAIRE
	 * Méthode qui tente de convertir une chaîne de caractères en un numéro de port. 
	 * Si la chaîne de caractères donnée en paramètre ne correspond pas à un entier entre 
	 * 1024 et 65535 (au sens large) la méthode retourne -1, sinon elle retourne l'entier correspondant
	 * à la chaîne de caractère donnée en paramètre.
	 * 
	 * @param chaineCars Une chaîne de caractères (pouvant être null).
	 * @return Un numéro de port correspondant à la chaîne de caractères chaineCars si celle-ci correspond bien à un numéro de port valide (>=1024 et <=65535) sinon -1. 
	 */
	static public int portValide(String chaineCars){
		int port;
		port = Integer.parseInt(chaineCars);

		if (port >= 1024 && port <= 65535){
			return port;
		}

		return -1;
	}
	/**
	 * A FAIRE
	 * Méthode qui tente de convertir une chaîne de caractères en une adresse IP. 
	 * La référence null est retournée si la chaîne de caractères donnée en paramètre ne correspond pas à une adresse IP valide.
	 * 
	 * @param chaineCars Une chaîne de caractères (pouvant être null).
	 * @return Une InetAddress correspondant à la chaîne de caractères chaineCars si celle-ci correspond bien à une adresse IP. 
	 */
	static public InetAddress adresseIPValide(String chaineCars){
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(chaineCars);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}

		return addr;
	}
	
	
	/**
	 * A FAIRE
	 * Méthode tentant de se connecter à un serveur. Le Socket correspondant à la connexion
	 * est retourné par la méthode. Pour tout problème, une trace de l'exception levée est réalisée
	 * et le programme s'arrête (code de sortie 1).
	 *  
	 * @param adresseIPServeur L'adresse IP du serveur.
	 * @param portServeur Le numéro du port du serveur.
	 * @return Le Socket correspondant à la nouvelle connexion est retourné.
	 */
	static public Socket connexion(InetAddress adresseIPServeur,int portServeur){
		Socket socket=null;
		try {
			socket = new Socket(adresseIPServeur, portServeur);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return socket;
	}
	
	/**
	 * A FAIRE
	 * Méthode permettant de créer un BufferedReader correspondant à un flux entrant d'un Socket.
	 * Pour tout problème, une trace de l'exception levée est réalisée
	 * et le programme s'arrête (code de sortie 1).
	 * @param socket Le Socket.
	 * @return Le BufferedReader correspondant au flux entrant du Socket.
	 */
	static public BufferedReader fluxEntrant(Socket socket){

		InputStream inputStream;
		InputStreamReader inputStreamReader;
		BufferedReader fluxEntrant=null;
		try{
			inputStream=socket.getInputStream();
			inputStreamReader = new InputStreamReader (inputStream);
			fluxEntrant=new BufferedReader(inputStreamReader);
		}catch(IOException e ){
			System.err.println( "Problème lors de la création du flux entrant\n");
			return null;
		}
		return fluxEntrant;
	}

	/**
	 * A FAIRE
	 * Méthode permettant de créer un PrintWriter correspondant à un flux sortant d'un Socket.
	 * Pour tout problème, une trace de l'exception levée est réalisée
	 * et le programme s'arrête (code de sortie 1).
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
	 * Pour tout problème, une trace de l'exception levée est réalisée
	 * et le programme s'arrête (code de sortie 1).
	 * @param fluxEntrant Le BufferedReader dans lequel doit être réalisé la ligne.
	 * @return La chaîne de caractères lue.
	 */
	static public String lireLigne(BufferedReader fluxEntrant){
		String ligne=null;
		try {
			ligne = fluxEntrant.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("*** (Client) Reception : "+ligne);
		return ligne;
	}
	
	/**
	 * A FAIRE
	 * Méthode permettant d'envoyer une chaîne de caractères dans un flux sortant.
	 * Pour tout problème, une trace de l'exception levée est réalisée
	 * et le programme s'arrête (code de sortie 1).
	 * @param fluxSortant Le PrintWriter dans lequel est envoyé la chaîne de caractères.
	 * @param ligne La chaîne de caractères à envoyer.
	 */
	static public void envoyerLigne(PrintWriter fluxSortant,String ligne){
		fluxSortant.println(ligne);
		fluxSortant.flush();
		System.out.println("*** (Client) Envoi : "+ligne);
	}
	
	static public boolean donneesDansFluxEntrant(BufferedReader fluxEntrant){
		try {
			if (fluxEntrant.ready())
				return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println( "!!! Problème avec le flux entrant !!!");
			System.exit(1);
		}
		return false;
	}
	
}
