import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient {
	Socket s;
	int mes = 5;
	
	public MyClient () throws UnknownHostException, IOException {
		
		s = new Socket("192.168.178.21",5555);
		Scanner sc = new Scanner(s.getInputStream());
		PrintStream p = new PrintStream(s.getOutputStream());
		p.println(mes);
		int temp = sc.nextInt();
		System.out.println(temp);
		sc.close();
		
	}
	
	
	public static void main(String[] args){
		try {
			MyClient mc = new MyClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
