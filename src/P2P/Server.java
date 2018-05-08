package P2P;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import blockchain.Block;
import blockchain.BlockChain;
import blockchain.FileDate;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	//private PrintWriter out;
	private BufferedReader in;
	private String data;
	public Server(int port)
	{
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void run() {
		try {
			while (!serverSocket.isClosed()) {
				clientSocket = serverSocket.accept();
				//out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				data = in.readLine();
				if(data.charAt(0)=='l')
				{
					FileDate.writeVote(data.substring(1));
				}
				else 
				{
					Block block=new Block(data.substring(1)); 
					if(!BlockChain.blockchain.contains(block))
					{
						BlockChain.blockchain.add(new Block(data.substring(1)));
						System.out.println(BlockChain.isChainValid());
					}
				}
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			in.close();
			//out.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

}
