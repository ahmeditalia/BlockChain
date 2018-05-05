package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class MulticastReceiver extends Thread {
	private MulticastSocket socket;
	private DatagramPacket packet;
	private NetInfo GROUP = new NetInfo();
	private byte[] recBuf;
	private byte[] sendBuf;

	private String data;
	private ArrayList<PeerSocket> networkPeers;

	// private String recMessage;
	public MulticastReceiver() {
		try {
			socket = new MulticastSocket(GROUP.receivePort);
			socket.joinGroup(GROUP.IP());
			recBuf = new byte[1000];
			networkPeers= new ArrayList<>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (!socket.isClosed()) {
			try {
				packet = new DatagramPacket(recBuf, recBuf.length);
				socket.receive(packet);
				String rec = new String(packet.getData(), 0, packet.getLength());
				sendBuf = data.getBytes();
				packet = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), GROUP.sendPort);
				socket.send(packet);
				String peer = "";
				for (int i = 0; i < networkPeers.size(); i++) {
					peer += networkPeers.get(i).getIP() +"/"+networkPeers.get(i).getPort()+"/";
				}
				sendBuf = peer.getBytes();
				packet = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), GROUP.sendPort);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	

	public void addNetworkPeer(PeerSocket p) {
		this.networkPeers.add(p);
	}

}