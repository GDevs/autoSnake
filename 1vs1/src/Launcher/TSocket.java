package Launcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TSocket extends ServerSocket implements Runnable  {

	private ServerListener parent;

	public TSocket(ServerListener pParent) throws IOException {
		super();
		this.parent = pParent;
	}

	@Override
	public void run() {
		
		Socket s = null;
		try {
			s = this.accept();
		} catch (IOException e) {
			System.out.println("Failed to establish Connection to client");
			e.printStackTrace();
		}
		
		Scanner sc = null;
		
		try {
			sc = new Scanner(s.getInputStream());
		} catch (IOException e) {
			System.out.println("Failed to create OutputStream");
			e.printStackTrace();
		}
		
		
		while(this.isBound()) {
			if(sc != null) {
				if(sc.hasNext()) {
					this.parent.processMessage(sc.next(), s.getInetAddress().getHostAddress(), s.getPort());
				}
			}
		}
		
	}

}
