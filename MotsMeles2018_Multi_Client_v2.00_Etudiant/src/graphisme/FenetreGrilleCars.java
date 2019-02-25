package graphisme;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public class FenetreGrilleCars extends JDialog {


	private static final long serialVersionUID = 1L;
	
	public FenetreGrilleCars(String titre,PanneauGrilleCars panneauGrilleCars,boolean modal){
		super((Window)null,titre);
		setContentPane(panneauGrilleCars);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(modal);
		pack();
		setResizable(false); 
		//setVisible(true);		
	}
	
	public FenetreGrilleCars(String titre,PanneauGrilleCars panneauGrilleCars){
		this(titre,panneauGrilleCars,true);
	}
	
	public static void main(String[] args) {
		
		PanneauGrilleCars panneauGrilleCars=new PanneauGrilleCars(5,Color.BLACK);
		new FenetreGrilleCars("Une fenêtre avec un simple panneau de type grille ...",panneauGrilleCars);
			}
}
