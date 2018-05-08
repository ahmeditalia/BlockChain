package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.jar.Pack200.Packer;

public class MulticastReceiver extends Thread {
	private MulticastSocket socket;
	private DatagramPacket packet;
	// private NetInfo GROUP = new NetInfo();
	private InetAddress groupIP;
	private int groupPort;
	private byte[] recBuf;
	private byte[] sendBuf;

	private String data;
	private ArrayList<PeerSocket> networkPeers;

	public MulticastReceiver(String groupIP, int groupPort) {
		try {
			this.groupIP = InetAddress.getByName(groupIP);
			this.groupPort = groupPort;
			socket = new MulticastSocket(groupPort);
			socket.joinGroup(this.groupIP);
			recBuf = new byte[10000];
			this.networkPeers = new ArrayList<>();
			data = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!socket.isClosed()) {
			try {
				// receive requist
				packet = new DatagramPacket(recBuf, recBuf.length);
				socket.receive(packet);
				String recData = new String(packet.getData(), 0, packet.getLength());

				if (recData != "hi") {
					// send data
					sendBuf = data.getBytes();
					System.out.println(packet.getPort());
					packet = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), packet.getPort());
					socket.send(packet);

					// send peer list
					String peerSoketList = "";
					for (int i = 0; i < networkPeers.size(); i++) {
						peerSoketList += networkPeers.get(i).getIP() + "/" + networkPeers.get(i).getPort() + "/";
					}
					sendBuf = peerSoketList.getBytes();
					packet = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), packet.getPort());
					socket.send(packet);

					// add that peer to me
					networkPeers.add(new PeerSocket(packet.getAddress().toString(), packet.getPort()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void close() {
		try {
			socket.leaveGroup(groupIP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	}

	public InetAddress getGroupIP() {
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
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

	public void setNetworkPeers(ArrayList<PeerSocket> networkPeers) {
		this.networkPeers = networkPeers;
	}

}