import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RunAndDodge implements Runnable{
	
	public static int TICK_TIME_MIL = 100;
	
	public static int STANDART_BULLET_SIZE = 30;
	public static int STANDART_PLAYER_SIZE = 50;
	
	public static double STANDART_BULLET_SPEED = 1;
	
	public static String BULLET_ICON_PATH = "/rec/RunAndDodge/Bullet/";
	public static String PLAYER_ICON_PATH = "/rec/RunAndDodge/Player/";
	
	public static int MAP_SIZE = 1000;
	
	
	@SuppressWarnings("serial")
	abstract private class Entity extends JLabel {
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
	
	
	@SuppressWarnings("serial")
	private class Bullet extends Entity{
		public double xSpeed,ySpeed , speedMovement = 1;
		
		
		public Bullet(double pXPos, double pYPos,double pXSpeed,double pYSpeed) {
			super(pXPos,pYPos);
			this.xSpeed = pXSpeed; 
			this.ySpeed = pYSpeed;
			
			this.createBullet();
		}
		
		private void createBullet() {
			this.setBounds(   ((int)Math.round(this.xPos)),  ((int)Math.round(this.yPos)), RunAndDodge.STANDART_BULLET_SIZE , RunAndDodge.STANDART_BULLET_SIZE   );
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
			this.xPos = this.xPos + this.xSpeed * ((systemTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL) * this.speedMovement;
			this.yPos = this.yPos + this.ySpeed * ((systemTime - this.lastTime) /  RunAndDodge.TICK_TIME_MIL) * this.speedMovement;
			this.lastTime = systemTime;
			this.updatePos();
		}
		
		
		
	}
	
	
	@SuppressWarnings("serial")
	private class Player extends Entity implements KeyListener{
		
		public boolean isWpressed = false;
		public boolean isApressed = false;
		public boolean isSpressed = false;
		public boolean isDpressed = false;
		public double movementspeed = 30;
		
		public Player(double pXPos, double pYPos) {
			super(pXPos,pYPos);
			this.createPlayer();
			
		}
		
		
		
		public void createPlayer(){
			this.setBounds(RunAndDodge.MAP_SIZE/2, RunAndDodge.MAP_SIZE/2, RunAndDodge.STANDART_PLAYER_SIZE, RunAndDodge.STANDART_PLAYER_SIZE);
			this.setFocusable(false);
		}
		
		public void setImage(Image img) {
			this.setIcon(new ImageIcon(img));
		}
		
		public void move() {
			double currentTime = System.currentTimeMillis();
			
			
			if(this.isWpressed) {
				if(this.isSpressed) {
					// Do nothing
				} else {
					this.yPos = this.yPos - this.movementspeed * ((currentTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL);
				}
			} else if(this.isSpressed){
				this.yPos = this.yPos + this.movementspeed * ((currentTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL);
			}
			
			
			
			if(this.isApressed) {
				if(this.isDpressed) {
					// Do nothing
				} else {
					this.xPos = this.xPos - this.movementspeed * ((currentTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL);
				}
			} else if(this.isDpressed){
				this.xPos = this.xPos + this.movementspeed * ((currentTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL);
			}
			
			if (this.xPos < 0) { this.xPos = 0;}
			else if(this.xPos > RunAndDodge.MAP_SIZE) { this.xPos = RunAndDodge.MAP_SIZE;}
			if (this.yPos < 0) { this.yPos = 0;}
			else if(this.yPos > RunAndDodge.MAP_SIZE - RunAndDodge.STANDART_PLAYER_SIZE) { this.yPos = RunAndDodge.MAP_SIZE - RunAndDodge.STANDART_PLAYER_SIZE;}
			
			this.lastTime = currentTime;
		}
		
		public boolean isKeypressed() {
			if(this.isApressed || this.isDpressed || this.isSpressed || this.isWpressed) {
				return true;
			}
			else { return false; }
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
	private JLabel board;
	
	private Player player;
	
	private ArrayList<Image> playerCalmImages;
	private ArrayList<Image> playerRunLeftImages;
	private ArrayList<Image> playerRunRigthImages;
	private ArrayList<Image> playerRunDownImages;
	private ArrayList<Image> playerRunUpImages;
	private ArrayList<Image> bulletImages;
	
	private boolean gotHit = false;
	
	private ArrayList<Bullet> bullets;
	private int bulletTimer = 50;
	private int bulletSpeed = 50;
	
	private int graphicSpeed = 250;
	private int playerGraphicCounter = 1;
	
	private double lastTime = System.currentTimeMillis();
	private double graphicTime = lastTime;
	
	
	
	public RunAndDodge() {
		this.playerCalmImages = new ArrayList<Image>();
		this.playerRunLeftImages = new ArrayList<Image>();
		this.playerRunRigthImages = new ArrayList<Image>();
		this.playerRunDownImages = new ArrayList<Image>();
		this.playerRunUpImages = new ArrayList<Image>();
		this.bulletImages = new ArrayList<Image>();
		bullets = new ArrayList<Bullet>();
		
		
		this.createFrame();
		this.loadGraphics();
		this.fillFrame();
		this.mainFrame.revalidate();
		this.mainFrame.repaint();
		}
	
	private void createFrame() {
		this.board = new JLabel();
		this.board.setLayout(null);
		this.board.setPreferredSize(new Dimension(RunAndDodge.MAP_SIZE,RunAndDodge.MAP_SIZE));
		try {
			this.board.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"Background.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.board.setVisible(true);
		
		this.mainFrame = new JFrame("Run And Dodge");
		this.mainFrame.add(this.board);
		this.mainFrame.pack();
		this.mainFrame.setBounds(0, 0, RunAndDodge.MAP_SIZE, RunAndDodge.MAP_SIZE );
		this.mainFrame.setLocationRelativeTo(null);
		this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.mainFrame.setLayout(null);
		this.mainFrame.setVisible(true);
		
	}
	
	private void fillFrame() {
		this.player = new Player(RunAndDodge.MAP_SIZE / 2,RunAndDodge.MAP_SIZE / 2);
		this.player.setImage(this.playerCalmImages.get(0));
		this.mainFrame.addKeyListener(this.player);
		this.board.add(this.player);
	}
	
	private void loadGraphics() {
	   	FileReader fr;
		 
		try {
			URL txtURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH + "count.txt");
			fr = new FileReader(new File((txtURL.getPath())));
			BufferedReader br = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
			try {
				
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"D" + i + ".png");
					Image img = ImageIO.read(bildURL); 
					img = ImageIO.read(bildURL);
					this.playerRunRigthImages.add(img);
				}
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"calm" + i + ".png");
					Image img = ImageIO.read(bildURL); 
					img = ImageIO.read(bildURL);
					this.playerCalmImages.add(img);
				}
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"L" + i + ".png");
					Image img = ImageIO.read(bildURL);
					this.playerRunLeftImages.add(img);
				}
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"L" + i + ".png");
					Image img = ImageIO.read(bildURL);
					this.playerRunUpImages.add(img);
				}
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"L" + i + ".png");
					Image img = ImageIO.read(bildURL);
					this.playerRunDownImages.add(img);
				}
					
					
				URL bildURL2 = getClass().getResource(RunAndDodge.BULLET_ICON_PATH +"1.png");
				Image img2 = ImageIO.read(bildURL2); 
				this.bulletImages.add(img2);
				
			} 
			catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			//br.close();
		//} 
		//catch (FileNotFoundException e) {
		//	e.printStackTrace();
//		 } 
           //   catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
	
	
	
	private Image calcPlayerImage() {
		if(this.player.isKeypressed()) {
			
			if(this.player.isSpressed) {
				
				
				if (this.player.isWpressed) {
					return this.playerCalmImages.get(this.playerGraphicCounter);
				} else { 
					this.playerRunDownImages.get(this.playerGraphicCounter); 
				} 
				
				
			} else if (this.player.isWpressed) {
				return this.playerRunUpImages.get(this.playerGraphicCounter);
			} else if(this.player.isApressed) {
				if(this.player.isDpressed) {
					return this.playerCalmImages.get(this.playerGraphicCounter);
				}
				return this.playerRunLeftImages.get(this.playerGraphicCounter);
			} else if(this.player.isDpressed) {
				return this.playerRunRigthImages.get(this.playerGraphicCounter);
			}
			
		} 
			return this.playerCalmImages.get(this.playerGraphicCounter);
		
	}
	
	
	
	
	private Bullet createNewBullet() {
		Bullet b = null;
		int a = (int) (Math.round(Math.random() * 3 ));
		if(a == 0) {
			b = new Bullet (									0, RunAndDodge.MAP_SIZE/2              , 	 Math.random() * this.bulletSpeed, Math.random() * this.bulletSpeed * 2 - this.bulletSpeed);
		} 
		else if ( a == 1) {
			b = new Bullet (Math.random() * RunAndDodge.MAP_SIZE ,									  0, Math.random() * this.bulletSpeed*2 - this.bulletSpeed,    Math.random() * + this.bulletSpeed);
		} 
		else if(a == 2) {
			b = new Bullet (RunAndDodge.MAP_SIZE				 , Math.random() * RunAndDodge.MAP_SIZE, 	Math.random() * - this.bulletSpeed, Math.random() * this.bulletSpeed * 2 -this.bulletSpeed);
		} 
		else if ( a == 3) {
			b = new Bullet (Math.random() * RunAndDodge.MAP_SIZE , RunAndDodge.MAP_SIZE                , Math.random()  * this.bulletSpeed * 2 - this.bulletSpeed,    Math.random() * -this.bulletSpeed);
		} 
		if (b != null) { return b; }
		return b = new Bullet(0,0,5,5);
	}
	
	@Override
	public void run() {
		while(!gotHit) {
			
			double currentTime = System.currentTimeMillis();
			
			
			if(currentTime - this.graphicTime > this.graphicSpeed) {
				this.graphicTime = this.graphicTime + this.graphicSpeed;
				
				this.playerGraphicCounter = ((this.playerGraphicCounter + 1) % 3) ; 
				Image img = this.calcPlayerImage();
				this.player.setImage(img);
			}
			this.player.move();
			this.player.updatePos();
			this.player.repaint();
			
			
			if(this.lastTime + this.bulletTimer < currentTime) {
				
				this.lastTime = this.lastTime + this.bulletTimer;
				
				Bullet b = this.createNewBullet();
				b.setImage(this.bulletImages.get(0));
				b.speedMovement = RunAndDodge.STANDART_BULLET_SPEED;
				this.board.add(b);
				bullets.add(b);
			}
				
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).move();
				if(bullets.get(i).xPos < 0 - RunAndDodge.STANDART_BULLET_SIZE || bullets.get(i).xPos > RunAndDodge.MAP_SIZE + RunAndDodge.STANDART_BULLET_SIZE|| bullets.get(i).yPos < 0 - RunAndDodge.STANDART_BULLET_SIZE || bullets.get(i).yPos > RunAndDodge.MAP_SIZE + RunAndDodge.STANDART_BULLET_SIZE) {
					this.board.remove(bullets.get(i));
					bullets.remove(i);
					
				} else {
					double bx = bullets.get(i).xPos + RunAndDodge.STANDART_BULLET_SIZE / 2;
					double by = bullets.get(i).yPos + RunAndDodge.STANDART_BULLET_SIZE / 2;
					
					double px = this.player.xPos + RunAndDodge.STANDART_PLAYER_SIZE / 2;
					double py = this.player.yPos + RunAndDodge.STANDART_PLAYER_SIZE / 2;
					
					double xDistance = Math.abs(bx-px);
					double yDistance = Math.abs(by-py);
					
					double trueDistance = Math.sqrt(Math.pow(xDistance, 2) +Math.pow(yDistance, 2));
					
					if(trueDistance < RunAndDodge.STANDART_PLAYER_SIZE/2 + RunAndDodge.STANDART_BULLET_SIZE/2) {
						this.gotHit = true;
						this.mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
					}
				}
					
			}
					
			
			
			this.mainFrame.revalidate();
			this.mainFrame.repaint();
			
		}
		
	}
	
	public static void main(String[] args) {
		RunAndDodge a = new RunAndDodge();
		Thread t = new Thread(a);
		t.start();
	}

}
