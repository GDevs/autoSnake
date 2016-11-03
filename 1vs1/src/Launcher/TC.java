package Launcher;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TC extends ServerSocket implements Runnable {
	private int port;
	private String ip;
	
	private Socket connectedSocket;
	private Scanner sc;
	private ServerListener ms;
	private PrintStream pStream;
	
	
	
	/*
	 * Constructor of ThreadedConnection
	 * 
	 * 
	 */
	public TC (ServerListener parentServer,int pPort) throws IOException {
		super();
		this.port = pPort;
		this.ms = parentServer;
		
		
		
		
		boolean fail = false;
		
		do{
																		System.out.println("ConnectionThread connect Try :" );
			try {
				//srvSct = new ServerSocket (this.port);
																		System.out.println("Initialisating ServerSocket on Port " + this.port );
				connectedSocket = this.accept();
																		System.out.println("Found connection!");
				sc = new Scanner(connectedSocket.getInputStream());
				
				pStream = new PrintStream(connectedSocket.getOutputStream());
			} catch (IOException e) {
				fail = true;
				e.printStackTrace();
			}
		} while(fail);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		this.ip = connectedSocket.getInetAddress().getHostAddress();
		boolean runLoop = true;
		
		while(runLoop){
			String mes = this.sc.next();
			this.ms.processMessage( this, mes, this.ip,this.port);
			if(mes.equals("quit")) {
				runLoop = false;
				System.out.println("Closing Server ...");
			}
		}
		try {
			this.connectedSocket.close();
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 
	 */
	public void sendMessage(String pMessage){
		this.pStream.println(pMessage);
	}

}
