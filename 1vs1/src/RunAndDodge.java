import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RunAndDodge implements Runnable{
	
	public static int TICK_TIME_MIL = 100;
	
	public static int STANDART_BULLET_SIZE = 50;
	public static int STANDART_PLAYER_SIZE = 50;
	
	public static String BULLET_ICON_PATH = "/rec/RunAndDodge/Bullet/";
	public static String PLAYER_ICON_PATH = "/rec/RunAndDodge/Player/";
	
	public static int MAP_SIZE = 500;
	
	
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
		public double xSpeed,ySpeed;
		
		
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
			this.xPos = this.xPos + this.xSpeed * ((systemTime - this.lastTime) / RunAndDodge.TICK_TIME_MIL);
			this.yPos = this.yPos + this.ySpeed * ((systemTime - this.lastTime) /  RunAndDodge.TICK_TIME_MIL);
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
		public double movementspeed = 50;
		
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
			System.out.println("Key :"+ke.getKeyChar());
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
	private JPanel board;
	
	private Player player;
	private ArrayList<Image> playerImages;
	private ArrayList<Image> bulletImages;
	private boolean gotHit = false;
	private ArrayList<Bullet> bullets;
	private int bulletTimer = 500;
	private int bulletSpeed = 10;
	private int graphicSpeed = 333;
	private int playerGraphicCounter = 1;
	
	private double lastTime = System.currentTimeMillis();
	private double graphicTime = lastTime;
	
	
	
	public RunAndDodge() {
		this.playerImages = new ArrayList<Image>();
		this.bulletImages = new ArrayList<Image>();
		bullets = new ArrayList<Bullet>();
		
		
		this.createFrame();
		this.loadGraphics();
		this.fillFrame();
		this.mainFrame.revalidate();
		this.mainFrame.repaint();
		}
	
	private void createFrame() {
		this.board = new JPanel();
		this.board.setPreferredSize(new Dimension(RunAndDodge.MAP_SIZE,RunAndDodge.MAP_SIZE));
		
		this.mainFrame = new JFrame("Run And Dodge");
		this.mainFrame.setBounds(0, 0, RunAndDodge.MAP_SIZE, RunAndDodge.MAP_SIZE );
		this.mainFrame.setLocationRelativeTo(null);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setLayout(null);
		this.mainFrame.setVisible(true);
		
	}
	
	private void fillFrame() {
		this.player = new Player(RunAndDodge.MAP_SIZE / 2,RunAndDodge.MAP_SIZE / 2);
		this.player.setImage(this.playerImages.get(0));
		this.mainFrame.addKeyListener(this.player);
		this.mainFrame.add(this.player);
	}
	
	private void loadGraphics() {
	   //	FileReader fr;
		 //try {
			//fr = new FileReader(RunAndDodge.PLAYER_ICON_PATH + "count.txt");
			//BufferedReader br = new BufferedReader(fr);
			try {
				//int count = Integer.parseInt(br.readLine());
				for(int i = 1; i < 4; i++ ){
					URL bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"D" + i + ".png");
					Image img = ImageIO.read(bildURL); 
					img = ImageIO.read(bildURL);
					bildURL = getClass().getResource(RunAndDodge.PLAYER_ICON_PATH +"L" + i + ".png");
					img = ImageIO.read(bildURL);
					this.playerImages.add(img);
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
		if(this.player.isSpressed) {
			return this.playerImages.get(this.playerGraphicCounter);
		} else {
			return this.playerImages.get(this.playerGraphicCounter +3);
		}
	}
	
	
	
	
	private Bullet createNewBullet() {
		Bullet b = null;
		int a = (int) (Math.round(Math.random() * 3 ));
		System.out.println(a);
		if(a == 0) {
			b = new Bullet (0, RunAndDodge.MAP_SIZE/2 , Math.random() * 10, Math.random() * 20 -10);
		} else if ( a == 1) {
			b = new Bullet (Math.random() * RunAndDodge.MAP_SIZE, 0, Math.random() * 20 -10, Math.random() * -10);
		} else if(a == 2) {
			b = new Bullet (RunAndDodge.MAP_SIZE, Math.random() * RunAndDodge.MAP_SIZE, Math.random() * -10, Math.random() * 20 -10);
		} else if ( a == 3) {
			b = new Bullet (Math.random() * RunAndDodge.MAP_SIZE, RunAndDodge.MAP_SIZE, Math.random() * 20 -10, Math.random() * -10);
		} 
		if (b != null) { return b; }
		return b = new Bullet(0,0,5,5);
	}
	
	@Override
	public void run() {
		while(!gotHit) {
			
			double currentTime = System.currentTimeMillis();
			
			
			if(this.graphicTime - currentTime > 333) {
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
				this.mainFrame.add(b);
				bullets.add(b);
			}
				
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).move();
				if(bullets.get(i).xPos < 0 || bullets.get(i).xPos > 500 || bullets.get(i).yPos < 0 || bullets.get(i).yPos > 500) {
					this.mainFrame.remove(bullets.get(i));
					bullets.remove(i);
					
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
