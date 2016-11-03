package Launcher;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer implements ServerListener{
	
	public static int PORT = 5557;
	
	
	public MyServer () throws IOException{
		TC cT = new TC(this,MyServer.PORT);
		Thread t1 = new Thread(cT);
		t1.start();
	}
	
	public synchronized void processMessage(TConnection pTC,String pMessage, String pIp, int pPort){
		System.out.println(pIp + ":" + pPort + " :" +pMessage);
		pTC.sendMessage(pMessage);
	}
	
	
	public static void main(String[] args){
		try {
			MyServer ms = new MyServer();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
