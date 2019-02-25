package graphisme;

import java.awt.Graphics;

public class AfficheurTexte16 extends AfficheurTexte{
	
	
	private ImageRessource imageAlphabet;
	
	static final private int largeurCar=16;
	
	
public AfficheurTexte16(){
	imageAlphabet=new ImageRessource("/graphisme/alpha16.png");
}

public void afficheCar(Graphics g,int x,int y,int largeur,int coul,char car){
	coul=1+(3*coul);
	
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
