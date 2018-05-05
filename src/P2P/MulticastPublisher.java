package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.yaml.snakeyaml.scanner.Constant;

public class MulticastPublisher {
	private MulticastSocket socket;
	private DatagramPacket packet;
	private NetInfo GROUP= new NetInfo();
	private byte[] sendBuf;
	private byte[] recBuf;
	private String data;
	private ArrayList<PeerSocket> networkPeers;

	public MulticastPublisher() {
		try {
			socket = new MulticastSocket(GROUP.sendPort);
			socket.joinGroup(GROUP.IP());
			networkPeers= new ArrayList<>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String multicast(String message) {
		String received = "";
		try {
			sendBuf = message.getBytes();
			packet = new DatagramPacket(sendBuf, sendBuf.length, 
					GROUP.IP(), GROUP.receivePort);
			
			socket.send(packet);
			recBuf = new byte[1000];
			packet = new DatagramPacket(recBuf, recBuf.length);
			socket.receive(packet);
			data = new String(packet.getData(), 0, packet.getLength());
			recBuf = new byte[1000];
			packet = new DatagramPacket(recBuf, recBuf.length);
			socket.receive(packet);
			String[] peer = new String(packet.getData(), 0, packet.getLength()).split("/");
			
			for (int i = 0; i < peer.length; i+=2) {
				
				networkPeers.add(new PeerSocket(peer[i],Integer.parseInt(peer[i+1])));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return received;
	}
	
	public void close() {
		try {
			socket.leaveGroup(GROUP.IP());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
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
	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

	
}
