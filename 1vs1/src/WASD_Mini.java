import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class WASD_Mini implements KeyListener{
	private JFrame mainFrame;
	private JButton aButton,wButton,sButton,dButton;
	private JTextField scoreField;
	
	private double startTime = System.currentTimeMillis()/1000;
	
	private int hitstreak = 0,hit = 0,miss = 0;
	
	private int lastButton = -1;
	
	private char currendButton = 'w';

public WASD_Mini()
{
	this.mainFrame = new JFrame();
	this.mainFrame.setBounds(500, 250, 200, 300);
	this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.mainFrame.setLayout(null);
	this.mainFrame.setResizable(false);
	this.addComponentstoFrame();
	this.mainFrame.revalidate();
	this.mainFrame.setVisible(true);
	this.mainFrame.addKeyListener(this);
	this.mainFrame.requestFocus();
	
	this.newTarget();	
}



private void addComponentstoFrame()
{
	this.aButton = new JButton("A");
	this.aButton.setFocusable(false);
	this.aButton.setBounds(10, 120, 50, 50);
	mainFrame.add(this.aButton);
	
	this.sButton = new JButton("S");
	this.sButton.setFocusable(false);
	this.sButton.setBounds(65, 175, 50, 50);
	mainFrame.add(this.sButton);
	
	this.dButton = new JButton("D");
	this.dButton.setFocusable(false);
	this.dButton.setBounds(120, 120, 50, 50);
	mainFrame.add(this.dButton);
	
	this.wButton = new JButton("W");
	this.wButton.setFocusable(false);
	this.wButton.setBounds(65, 65, 50, 50);
	mainFrame.add(this.wButton);
	
	this.scoreField = new JTextField();
	this.scoreField.setFocusable(false);
	this.scoreField.setBounds(5, 20, 185, 40);
	this.scoreField.setText("Streak: "+hitstreak+" Hit:"+hit+" Miss "+miss+"AVG");
	mainFrame.add(this.scoreField);
}






private void eventManager(char pressedChar)
{
	if(pressedChar == this.currendButton) 
	{
		this.hit++;
		this.hitstreak ++;
		this.resetButtons();
		this.newTarget();
		this.mainFrame.repaint();
	}
	else
	{
		this.miss++;
		this.hitstreak = 0;
	}
	
	double temp = this.hit / (System.currentTimeMillis()/1000 - this.startTime);
	DecimalFormat numberFormat = new DecimalFormat("#.00");
	this.scoreField.setText("Streak:" +hitstreak+ " Hit:" +hit+ " Miss" +miss+ " AVG:" +numberFormat.format(temp));
}





private void resetButtons()
{
	this.aButton.setBackground(null);
	this.sButton.setBackground(null);
	this.dButton.setBackground(null);
	this.wButton.setBackground(null);
}





private  void newTarget()
{
	int temp;
	do{
	temp = (int) Math.round(Math.random() * 3 );
	} while(temp == this.lastButton);
	this.lastButton = temp;
	if(temp == 0)
	{
		this.aButton.setBackground(Color.GREEN);
		this.currendButton = 'a';
	}
	else if(temp == 1)
	{
		this.sButton.setBackground(Color.GREEN);
		this.currendButton = 's';
	}
	else if(temp == 2)
	{
		this.dButton.setBackground(Color.GREEN);
		this.currendButton = 'd';
	}
	else if(temp == 3)
	{
		this.wButton.setBackground(Color.GREEN);
		this.currendButton = 'w';
	}
}





@Override
public void keyPressed(KeyEvent arg0) 
{
		
}





@Override
public void keyReleased(KeyEvent arg0) 
{
		
}





@Override
public void keyTyped(KeyEvent ke) 
{
	this.eventManager(ke.getKeyChar());
}





public static void main(String args[])
{
	WASD_Mini a = new WASD_Mini();
}



}

