import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RunAndDodge {
	
	public static int TICK_TIME_MIL = 50;
	public static int STANDART_BULLET_SIZE = 50;
	public static String BULLET_ICON_PATH = "rec/RunAndDodge/Bullet.png";
	public static String PLAYER_ICON_PATH = "rec/RunAndDodge/Player.png";
	
	private class Entity extends JLabel {
		public double xPos,yPos;
		
		public Entity(double pXPos, double pYPos) {
			this.xPos = pXPos;
			this.yPos = pYPos;
		}
		
		/*
		 * Updated die optische Position mit der Logischen.
		 */
		public void updatePos() {
			this.setLocation((int)Math.round(this.xPos), (int)Math.round(this.yPos));
		}
		
	}
	
	private class Bullet extends Entity{
		public double xPos,yPos,xSpeed,ySpeed;
		public double lastTime;
		
		
		public Bullet(double pXPos, double pYPos,double pXSpeed,double pYSpeed) {
			super(pXPos,pYPos);
			this.xSpeed = pXSpeed;
			this.ySpeed = pYSpeed;
			this.lastTime = System.currentTimeMillis();
			
			this.createBullet();
		}
		
		private void createBullet() {
			this.setBounds(   ((int)Math.round(this.xPos)),  ((int)Math.round(this.yPos)), RunAndDodge.STANDART_BULLET_SIZE , RunAndDodge.STANDART_BULLET_SIZE   );
			try {
				Image img = ImageIO.read(new File(RunAndDodge.BULLET_ICON_PATH));
				this.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		/*
		 * Ändert die Flugrichtung der Kugel
		 */
		public void changePath(double pXSpeed, double pYSpeed) {
			this.xSpeed = pXSpeed;
			this.ySpeed = pYSpeed;
		}
		
		public void move() {
			double systemTime = System.currentTimeMillis();
			this.xPos = this.xPos + this.xSpeed * Math.floorDiv((int)(systemTime - this.lastTime),  RunAndDodge.TICK_TIME_MIL);
			this.yPos = this.yPos + this.ySpeed * Math.floorDiv((int)(systemTime - this.lastTime),  RunAndDodge.TICK_TIME_MIL);
			this.updatePos();
		}
		
		
		public void setImage(Image img) {
				this.setIcon(new ImageIcon(img));
			
		}
		
	}
	
	private class Player extends JLabel implements KeyListener{
		
		public double xPos = 250,yPos = 250;
		public boolean isWpressed = false;
		public boolean isApressed = false;
		public boolean isSpressed = false;
		public boolean isDpressed = false;
		
		public Player() {
			this.createPlayer();
		}
		
		public void createPlayer(){
			
				try {
					Image img = ImageIO.read(new File(RunAndDodge.PLAYER_ICON_PATH));
				} catch (IOException e) {
					e.printStackTrace();
				}
			
	
		}
		
		public void setImage(Image img) {
			this.setIcon(new ImageIcon(img));
		
		}
		
		@Override
		public void keyPressed(KeyEvent ke) {
			if(ke.getKeyChar() == 'w' ) {
				isWpressed = true;
			} 
			else if(ke.getKeyChar() == 'a' ) {
				isApressed = true;
			} 
			else if(ke.getKeyChar() == 's' ) {
				isSpressed = true;
			} 
			else if(ke.getKeyChar() == 'd' ) {
				isDpressed = true;
			} 
			
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			if(ke.getKeyChar() == 'w' ) {
				isWpressed = false;
			} 
			else if(ke.getKeyChar() == 'a' ) {
				isApressed = false;
			} 
			else if(ke.getKeyChar() == 's' ) {
				isSpressed = false;
			} 
			else if(ke.getKeyChar() == 'd' ) {
				isDpressed = false;
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private JFrame mainFrame;
	private JPanel player;
	private Bullet[] bullets;
	
	public RunAndDodge() {
		this.createFrame();
	}
	
	public void createFrame() {
		this.mainFrame.setBounds(0, 0, 500, 500);
		this.mainFrame.setLocationRelativeTo(null);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {

	}

}
