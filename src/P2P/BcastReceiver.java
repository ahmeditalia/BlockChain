package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

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
				DatagramPacket recPacket;
				recPacket = new DatagramPacket(recBuf, recBuf.length);

				//socket.setSoTimeout(5);
				socket.receive(recPacket);
				String recData = new String(recPacket.getData(), 0, recPacket.getLength());
				String [] rec=recData.split("/");
				System.out.println("in receiver\nsoket port = " + socket.getLocalPort());
				System.out.println("data = " + data);
				System.out.println("recData = " + recData);

				if (rec[0].equals("hi")) {
					// send data
					sendBuf = data.getBytes();
					System.out.println(recPacket.getPort());
					System.out.println(recPacket.getAddress());
					packet = new DatagramPacket(sendBuf, sendBuf.length, recPacket.getAddress(), recPacket.getPort());
					socket.send(packet);

					// send peer list
					String peerSoketList = "";
					for (int i = 0; i < networkPeers.size(); i++) {
						peerSoketList += networkPeers.get(i).getIP() + "/" + networkPeers.get(i).getPort() + "/";
					}
					sendBuf = peerSoketList.getBytes();
					packet = new DatagramPacket(sendBuf, sendBuf.length, recPacket.getAddress(), recPacket.getPort());
					socket.send(packet);

					// add that peer to me
					networkPeers.add(new PeerSocket(recPacket.getAddress().toString().substring(1), Integer.parseInt(rec[1])));
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