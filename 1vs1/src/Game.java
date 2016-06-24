import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 */

/**
 * @author adrian
 *
 *	Interface for Games. Will eventually used in the future to create a more dynamic Main 
 */
public interface Game {

	@SuppressWarnings("serial")
	abstract public class Entity extends JLabel {
		public double xPos,yPos;
		public double lastTime = System.currentTimeMillis();
		
		public Entity(double pXPos, double pYPos) {
			this.xPos = pXPos;
			this.yPos = pYPos;
			this.lastTime = System.currentTimeMillis();
		}
		
		/*
		 * Updated die optische Position mit der Logischen.
		 */
		public void updatePos() {
			this.setLocation((int)Math.round(this.xPos), (int)Math.round(this.yPos));
		}
		
		public void setImage(Image img) {
			this.setIcon(new ImageIcon(img));
		
		}
		
		abstract public void move(); 
		
		public void moveTo(double x,double y) {
			this.xPos = x;
			this.yPos = y;
		}
		
	}
	
	public abstract void startGame();
}
