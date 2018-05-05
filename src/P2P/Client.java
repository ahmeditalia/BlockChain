package P2P;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public Client() {
	}

	public void connect(String serverIP) {
		try {
			socket = new Socket(serverIP, new NetInfo().receivePort);
			out = new PrintWriter(socket.getOutputStream(),true);
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String message) throws IOException {
		//String response = null;
		out.println(message);
		//response = in.readLine();

		//return response;
	}
	
	public void close() throws IOException
	{
		in.close();
		out.close();
		socket.close();
	}

	
}
