import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RunAndDodge implements Runnable{
	
	public static int TICK_TIME_MIL = 100;
	
	public static int STANDART_BULLET_SIZE = 50;
	public static int STANDART_PLAYER_SIZE = 50;
	
	public static String BULLET_ICON_PATH = "rec/RunAndDodge/Bullet/";
	public static String PLAYER_ICON_PATH = "rec/RunAndDodge/Player/";
	
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
			System.out.println(this.xPos + "updatePos " + (int)Math.round(this.xPos));
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
			try {
				Image img = ImageIO.read(new File(RunAndDodge.BULLET_ICON_PATH + "bullet.png"));
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
		public double movementspeed = 5;
		
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
			
			System.out.println("playerentered MOVE " + this.xPos + "   " + this.yPos);
			
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
	
	private Player player;
	private ArrayList<Image> playerImages;
	private boolean gotHit = false;
	private Bullet[] bullets;
	private int bulletTimer = 1000;
	
	
	
	public RunAndDodge() {
		this.playerImages = new ArrayList<Image>();
		
		this.createFrame();
		this.loadGraphics();
		this.fillFrame();
		this.mainFrame.revalidate();
		this.mainFrame.repaint();
		}
	
	private void createFrame() {
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
		FileReader fr;
		try {
			fr = new FileReader(RunAndDodge.PLAYER_ICON_PATH + "count.txt");
			BufferedReader br = new BufferedReader(fr);
			try {
				int count = Integer.parseInt(br.readLine());
				System.out.println("Count :" + count);
				for(int i = 1; i< count+1; i++) {
					File temp = new File(this.PLAYER_ICON_PATH +"1.png");
					this.playerImages.add(ImageIO.read(temp));
					
				}
			} 
			catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while(!gotHit) {
			this.player.move();
			this.player.updatePos();
			this.player.repaint();
			this.mainFrame.revalidate();
			this.mainFrame.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		RunAndDodge a = new RunAndDodge();
		Thread t = new Thread(a);
		t.start();
	}

}
