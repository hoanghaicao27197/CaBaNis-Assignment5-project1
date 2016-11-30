

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class BallPanel extends JPanel implements MouseListener{

	//Var
	Color ballColor = Color.RED;
	
	//
	int x0 = 320, y0 = 20, r0 = 50;
	
	//Image Ball
	Circle[] CircleImage = new Circle[4];
	int[] x = {45,145,245,345}, y = {105};
	int nImageIndex = 0;
	
	//Image resources
	BufferedImage imgSoccerBall, imgKABall, imgMasterBall;
	String[] imgURL = {
			"images/unaname.png",
			"images/Tennis.png",
			"imagesbasketball.png"
	};
	
	public void paint(Graphics g){
		super.paint(g);
		
		if(nImageIndex == 0){
			g.setColor(getColor());
			g.fillOval(x0, y0, r0, r0);
		}
		else if(nImageIndex == 1){
			g.drawImage(imgSoccerBall, x0, y0, x0 + r0, y0 + r0, 0, 0, 256, 256, null);
		}
		else if (nImageIndex == 2){
			g.drawImage(imgKABall, x0, y0, x0 + r0, y0 + r0, 0, 0, 333, 328, null);
		}
		else if (nImageIndex == 3){
			g.drawImage(imgMasterBall, x0, y0, x0 + r0, y0 + r0, 0, 0, 400, 400, null);
		}
	
		g.setColor(getColor());
		g.fillOval(x[0], y[0], r0, r0);
		g.drawImage(imgSoccerBall, x[1], y[0], x[1] + r0, y[0] + r0, 0, 0, 256, 256, null);
		g.drawImage(imgKABall, x[2], y[0], x[2] + r0, y[0] + r0, 0, 0, 333, 328, null);
		g.drawImage(imgMasterBall, x[3], y[0], x[3] + r0, y[0] + r0, 0, 0, 400, 400, null);
		
	}
	
	
	public BallPanel(){
		//add component
		setLayout(null);
		
		//ImageBall
		addMouseListener(this);		
		
		CircleImage[0] = new Circle(x[0], y[0], r0);
		CircleImage[1] = new Circle(x[1], y[0], r0);
		CircleImage[2] = new Circle(x[2], y[0], r0);
		CircleImage[3] = new Circle(x[3], y[0], r0);
		
		try {
			imgSoccerBall = ImageIO.read(new File(imgURL[0]));
			imgKABall = ImageIO.read(new File(imgURL[1]));
			imgMasterBall = ImageIO.read(new File(imgURL[2]));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	public BallPanel(int nIndex, Color ballColor){
		this.nImageIndex = nIndex;
		this.ballColor = ballColor;
	}
	
	private void changeBallColor(){
		Color newColor = JColorChooser.showDialog(this, "Choose Ball Color",getColor());
		setColor(newColor);
		repaint();
	}
	
	
	public void mouseClicked(MouseEvent e) {
		for(int i = 0 ; i < 4 ; i++){
			if(CircleImage[i].contains(e.getX(),e.getY())){
				setnImageIndex(i);
			}
		}
		repaint();
	}


	public void setColor(Color newColor){
		this.ballColor = newColor;
	}

	public Color getColor(){
		return ballColor;
	}
	
	public int getnImageIndex() {
		return nImageIndex;
	}


	public void setnImageIndex(int nImageIndex) {
		if(nImageIndex == 0){
			changeBallColor();
		}
		this.nImageIndex = nImageIndex;
	}


	public void mouseEntered(MouseEvent e) {}


	public void mouseExited(MouseEvent e) {}


	public void mousePressed(MouseEvent e) {}


	public void mouseReleased(MouseEvent e) {}

}

class Circle{
	
	int x,y,r;

	public Circle(int x, int y, int r) {
		super();
		this.x = x+r/2;
		this.y = y+r/2;
		this.r = r/2;
	}
	
	public boolean contains(int x, int y){
		
		double range = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
		if(range < this.r){
			return true;
		}
		return false;
	}
	
}
