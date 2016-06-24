import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Centipede implements Game, KeyListener, Runnable{
	private class Obstracle{
		
	}
	
	
	@Override
	public void run() {
		while(true){
			System.out.println("Hello");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		Thread t = new Thread(this);
		t.start();
		while(t.isAlive()){
			System.out.println("Alive");
		}
	}
	
	public static void main(String[] args){
		(new Centipede()).run();
	}
}
