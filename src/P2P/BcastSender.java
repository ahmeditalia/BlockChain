package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class BcastSender {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private InetAddress bcastAdress;
	private byte[] sendBuf;
	private byte[] recBuf;
	private String data;
	private ArrayList<PeerSocket> networkPeers;
	private int sendCounter = 0,recPort;
	
	public BcastSender(int port,int recPort) {
		try {
			socket = new DatagramSocket(port);
			this.recPort= recPort;
			socket.setBroadcast(true);
			bcastAdress = InetAddress.getByName("255.255.255.255");
			networkPeers = new ArrayList<>();
			data= "no Data";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bCast(String message) {
		// String received = "";

		try {
			// send requist
			sendBuf = message.getBytes();
			packet = new DatagramPacket(sendBuf, sendBuf.length, bcastAdress,recPort);
			socket.send(packet);
			System.out.println("sent data"+packet.getData().toString());
			// recaive Data
			recBuf = new byte[10000];
			packet = new DatagramPacket(recBuf, recBuf.length);
			socket.setSoTimeout(500);
			socket.receive(packet);
			String recData = new String(packet.getData(), 0, packet.getLength());

			if (!recData.equals(message)) {
				data = recData;

				// recaive peers list
				recBuf = new byte[10000];
				packet = new DatagramPacket(recBuf, recBuf.length);
				socket.receive(packet);
				String[] peer = new String(packet.getData(), 0, packet.getLength()).split("/");

				for (int i = 0; i < peer.length; i += 2) {
					networkPeers.add(new PeerSocket(peer[i], Integer.parseInt(peer[i + 1])));
				}
			}
		} catch (SocketTimeoutException e) {
//			if (++sendCounter < 2)
//				bCast(message);
//			else
				System.out.println("receiver timeout!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sendCounter = 0;
	}

	public void close() {
		socket.close();
	}

	/*public InetAddress getGroupIP() {
		return groupIP;
	}

	public void setGroupIP(InetAddress groupIP) {
		this.groupIP = groupIP;
	}

	public int getGroupPort() {
		return groupPort;
	}

	public void setGroupPort(int groupPort) {
		this.groupPort = groupPort;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the networkPeers
	 */
	public void setNetworkPeers(ArrayList<PeerSocket> networkPeers) {
		this.networkPeers = networkPeers;
	}

	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

	/*
	 * private byte[] buf;
	 * 
	 * public BcastSender() { try { socket = new DatagramSocket(); address =
	 * InetAddress.getByName("localhost");
	 * 
	 * } catch (SocketException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (UnknownHostException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * public String sendEcho(String msg) { 
	 * String received = ""; try { buf =
	 * msg.getBytes();
	 * 
	 * DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
	 * 
	 * socket.send(packet);
	 * 
	 * packet = new DatagramPacket(buf, buf.length); socket.receive(packet);
	 * received = new String(packet.getData(), 0, packet.getLength());
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return received;
	 * 
	 * }
	 * 
	 * public void close() { socket.close(); }
	 */
}
