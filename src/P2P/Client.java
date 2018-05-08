package P2P;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private boolean connected;
	// private BufferedReader in;

	public Client() {
		connected= false;
	}

	public void connect(String serverIP, int serverPort) {
		try {
			socket= new Socket(serverIP, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			connected= true;
			// in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String message) throws IOException {
		// String response = null;
		out.println(message);
		// response = in.readLine();

		// return response;
	}

	public void close() {
		if (connected) {
			try {
				// in.close();
				socket.close();
				out.close();
				connected= false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
