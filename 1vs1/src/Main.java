import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;



/**
 * 
 * @author Hulo101
 *
 * "Umgebung" um kleine Spiele zu sammeln
 * 5 pixel Abstand zwichen allen Buttons
 * 
 */


public class Main implements  ActionListener
{
	public static int WIDTH = 1000;
	public static int HEIGHT = 500;
	
	public static int BUTTON_BOUNDS = 100;
	public static int INTERSPACE_SIZE = 5;
	
	public static String ICON_PATH = "icons/";
	private JFrame mainMenu;
	
	private JButton WASDmini;
	private JButton Animationstester;
	
	public Main() {
		this.creatFrame();
		this.addComponentstoFrame();
		this.mainMenu.repaint();
	}

	private void creatFrame() {
		this.mainMenu = new JFrame();
		this.mainMenu.setSize(Main.WIDTH, Main.HEIGHT);
		this.mainMenu.setLocationRelativeTo(null);
		this.mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainMenu.setResizable(false);
		this.mainMenu.setLayout(null);
		this.mainMenu.setVisible(true);
	}

	/*
	 * Default close Operation der Spiele  NICHT auf exit_on_Close stellen
	 * -> terminiert auch das hauptfenster
	 */
	private void addComponentstoFrame() {
		this.WASDmini = new JButton();
		this.WASDmini.setBounds(this.getPosOfButton(1), this.getPosOfButton(1), Main.BUTTON_BOUNDS, Main.BUTTON_BOUNDS);
		try {
			Image img = ImageIO.read(new File(Main.ICON_PATH + "WASDMINI.png"));
		    WASDmini.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  System.out.println("Img error");
		  }
		this.WASDmini.addActionListener(this);
		this.mainMenu.add(WASDmini);
		
		this.Animationstester = new JButton();
		this.Animationstester.setBounds(this.getPosOfButton(2), this.getPosOfButton(1), Main.BUTTON_BOUNDS, Main.BUTTON_BOUNDS);
		try {
			Image img = ImageIO.read(new File(Main.ICON_PATH + "ANIMATIONSTESTER.png"));
			Animationstester.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  System.out.println("Img error");
		  }
		this.Animationstester.addActionListener(this);
		this.mainMenu.add(Animationstester);
		
	}
	
	/* gibt die x bzw y bos des Buttons an relativ zu seiner
	 * x Position (1ter Button von recht, 2ter... oder 1ter von oben, 2ter von oben...)
	 * 
	 * 
	 */
	public int getPosOfButton(int buttonNr) {
		return (buttonNr-1) * Main.BUTTON_BOUNDS + Main.INTERSPACE_SIZE * buttonNr;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(this.WASDmini)) {
			new WASD_Mini();
		}
		else if(ae.getSource().equals(this.Animationstester)) {
			Animationtester a = new Animationtester();
			Thread t = new Thread(a);
			t.start();
		}
	}
	
	public static void main(String args[]){
	Main m = new Main();
	}

}
