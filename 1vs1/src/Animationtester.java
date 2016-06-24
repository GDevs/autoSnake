import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Animationtester implements Runnable{
	public static String ANIMATION_STORAGE_PATH = "/rec/Animationtester/";
	private JFrame mainFrame;
	private JButton a;
	
	public Animationtester () {
		mainFrame = new JFrame();
		mainFrame.setBounds(600, 400, 200, 200);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a = new JButton();
		a.setBounds(5, 5, 150, 150);
		mainFrame.add(a);
		mainFrame.setVisible(true);
	}


	@Override
	public void run() {
		while(true){
			for(int i = -10; i < 11; i++){
				try {
					URL bildURL = getClass().getResource(Animationtester.ANIMATION_STORAGE_PATH + Math.abs(i) + ".png");
					Image img = ImageIO.read(bildURL); 
					a.setIcon(new ImageIcon(img));
				} catch (IOException ex) {
				  System.out.println("Img error");
				}
				
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
			}
		}
		
	}
	
	public static void main(String args[]){
		Animationtester a = new Animationtester();
		a.run();
	}
}
