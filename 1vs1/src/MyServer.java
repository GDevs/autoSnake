import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
	ServerSocket ss1;
	Socket s;
	
	public MyServer () throws IOException{
		int number, temp;
			ss1 = new ServerSocket(5555);
		    s = ss1.accept();
		    Scanner sc = new Scanner(s.getInputStream());
		    number = sc.nextInt();
		    
		    temp = number*2;
		    
		    PrintStream p = new PrintStream(s.getOutputStream());
		    p.println(temp);
		    sc.close();
	}
	
	
	public static void main(String[] args){
		try {
			MyServer ms = new MyServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
