package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BcastReceiver extends Thread {

	private DatagramSocket socket;
	private DatagramPacket packet;
	private byte[] recBuf;
	private byte[] sendBuf;

	private String data;
	private ArrayList<PeerSocket> networkPeers;

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

	public BcastReceiver(int port) {
		try {
			socket = new DatagramSocket(port);
			networkPeers = new ArrayList<>();
			data = "no Data";
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (!socket.isClosed()) {
			try {
				// receive requist
				recBuf = new byte[1000];
				packet = new DatagramPacket(recBuf, recBuf.length);

				//socket.setSoTimeout(5);
				socket.receive(packet);
				String recData = new String(packet.getData(), 0, packet.getLength());
				System.out.println("in receiver\nsoket port = " + socket.getLocalPort());
				System.out.println("data = " + data);
				System.out.println("recData = " + recData);

				if (recData.equals("hi")) {
					// send data
					sendBuf = data.getBytes();
					System.out.println(packet.getPort());
					System.out.println(packet.getAddress());
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
					networkPeers.add(new PeerSocket(packet.getAddress().toString().substring(1), packet.getPort()));
				}
			} catch (SocketException e) {
				continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void close() {
		socket.close();

	}
}