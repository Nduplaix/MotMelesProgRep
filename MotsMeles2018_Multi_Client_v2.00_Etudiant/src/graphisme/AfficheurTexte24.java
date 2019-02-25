package graphisme;

import java.awt.Graphics;

public class AfficheurTexte24 extends AfficheurTexte{
	
	
	private ImageRessource imageAlphabet;
	
	static final private int largeurCar=24;
	
	
public AfficheurTexte24(){
	imageAlphabet=new ImageRessource("/graphisme/alpha24.png");
}

public void afficheCar(Graphics g,int x,int y,int largeur,int coul,char car){
	int indexCar;
	int imgX;
	int imgY=coul*largeurCar;
	//g.drawImage(imageAlphabet.getImage(),x,y,x+largeur,y+largeur,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	if ((car>='A')&&(car<='Z')){
		indexCar=(int)car-(int)'A';
		imgX=indexCar*largeurCar;
	g.drawImage(imageAlphabet.getImage(),x,y,x+largeurCarFinale,y+largeurCarFinale,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	}else if ((car>='0')&&(car<='9')){
		indexCar=(int)car-(int)'0'+26;
		imgX=indexCar*largeurCar;
	g.drawImage(imageAlphabet.getImage(),x,y,x+largeurCarFinale,y+largeurCarFinale,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	}else if (car=='/'){
		indexCar=36;
		imgX=indexCar*largeurCar;
	g.drawImage(imageAlphabet.getImage(),x,y,x+largeurCarFinale,y+largeurCarFinale,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	}else if (car==':'){
		indexCar=37;
		imgX=indexCar*largeurCar;
	g.drawImage(imageAlphabet.getImage(),x,y,x+largeurCarFinale,y+largeurCarFinale,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	}else if (car=='.'){
		indexCar=38;
		imgX=indexCar*largeurCar;
	g.drawImage(imageAlphabet.getImage(),x,y,x+largeurCarFinale,y+largeurCarFinale,imgX,imgY,imgX+largeurCar,imgY+largeurCar,null);
	}

}


}
